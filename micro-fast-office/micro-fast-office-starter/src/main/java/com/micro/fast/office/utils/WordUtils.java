package com.micro.fast.office.utils;

import cn.afterturn.easypoi.word.WordExportUtil;
import com.micro.fast.minio.configuration.properties.MicroFastMinioClientProperties;
import com.micro.fast.minio.constant.MicroFastMinioUploadMethodTypeEnum;
import com.micro.fast.minio.request.MicroFastMinioUploadRequest;
import com.micro.fast.minio.service.MicroFastMinioService;
import com.micro.fast.office.configuration.properties.MicroFastOfficeProperties;
import com.micro.fast.office.constant.MicroFastOfficeExceptionCodeEnum;
import com.micro.fast.office.constant.MicroFastOfficeExportTypeEnum;
import com.micro.fast.office.exception.MicroFastOfficeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

/**
 * word工具类
 *
 * @author shoufeng
 */

@Component
@Slf4j
public class WordUtils {

    @Resource
    private MicroFastOfficeProperties microFastOfficeProperties;

    @Resource
    private MicroFastMinioClientProperties microFastMinioClientProperties;

    @Resource
    private MicroFastMinioService microFastMinioService;

    public String exportDocByTemplate(MicroFastOfficeExportTypeEnum exportTypeEnum, String templateFileName, Map<String, Object> dataMap, String targetFileName, HttpServletResponse httpServletResponse) throws Exception {

        templateFileName = microFastOfficeProperties.getTemplateBasePath() + microFastOfficeProperties.getWordTemplateBasePath() + templateFileName;

        File file = new File(templateFileName);
        if (!file.exists()) {
            log.error("导出失败: 模版文件{}不存在", templateFileName);
            throw new MicroFastOfficeException(MicroFastOfficeExceptionCodeEnum.WORD_FAIL.getCode(), "导出失败模版文件" + templateFileName + "不存在");
        }
        XWPFDocument doc = WordExportUtil.exportWord07(templateFileName, dataMap);
        String[] outFileSplit = targetFileName.split("/");
        String fileName = outFileSplit[outFileSplit.length - 1];
        try {
            if (httpServletResponse != null && exportTypeEnum == MicroFastOfficeExportTypeEnum.DIRECT_EXPORT) {
                httpServletResponse.reset();
                httpServletResponse.setContentType("application/octet-stream; charset=utf-8");
                httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "utf-8"));
                doc.write(httpServletResponse.getOutputStream());
                return null;
            }
            if (!microFastMinioClientProperties.isEnable()) {
                throw new MicroFastOfficeException(MicroFastOfficeExceptionCodeEnum.PDF_FAIL.getCode(), "生成下载链接失败: 请先开启minio（micro.fast.minio.enable = true）");
            }
            OutputStream outputStream = new FileOutputStream(targetFileName);
            doc.write(outputStream);
            MicroFastMinioUploadRequest microFastMinioUploadRequest = MicroFastMinioUploadRequest
                    .builder()
                    .bucketName("word")
                    .objectName("./word/" + fileName)
                    .uploadMethodTypeEnum(MicroFastMinioUploadMethodTypeEnum.LOCAL)
                    .fileName(targetFileName)
                    .build();
            return microFastMinioService.upload(microFastMinioUploadRequest);
        } finally {
            doc.close();
        }
    }

}

