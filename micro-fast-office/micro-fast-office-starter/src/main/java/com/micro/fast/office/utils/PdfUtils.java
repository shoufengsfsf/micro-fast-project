package com.micro.fast.office.utils;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.micro.fast.minio.configuration.properties.MicroFastMinioClientProperties;
import com.micro.fast.minio.constant.MicroFastMinioUploadMethodTypeEnum;
import com.micro.fast.minio.request.MicroFastMinioUploadRequest;
import com.micro.fast.minio.service.MicroFastMinioService;
import com.micro.fast.office.configuration.properties.MicroFastOfficeProperties;
import com.micro.fast.office.constant.MicroFastOfficeExceptionCodeEnum;
import com.micro.fast.office.constant.MicroFastOfficeExportTypeEnum;
import com.micro.fast.office.exception.MicroFastOfficeException;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xmlpull.v1.XmlPullParserException;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Pdf工具类
 *
 * @author shoufeng
 */

@Component
@Slf4j
public class PdfUtils {

    @Resource
    private MicroFastOfficeProperties microFastOfficeProperties;

    @Resource
    private MicroFastMinioClientProperties microFastMinioClientProperties;

    @Resource
    private MicroFastMinioService microFastMinioService;

    /**
     * 通过模版导出PDF
     */
    public String exportPdfByTemplate(MicroFastOfficeExportTypeEnum exportTypeEnum, String templateFileName, String targetFileName, Map<String, String> map, HttpServletResponse httpServletResponse)
            throws IOException, DocumentException, XmlPullParserException, NoSuchAlgorithmException, RegionConflictException, InvalidKeyException, InvalidExpiresRangeException, IllegalAccessException, InvalidArgumentException, ErrorResponseException, NoResponseException, InvalidBucketNameException, InsufficientDataException, InternalException {
        targetFileName = System.getProperty("user.dir") + "/" + targetFileName;
        templateFileName = microFastOfficeProperties.getTemplateBasePath() + microFastOfficeProperties.getPdfTemplateBasePath() + templateFileName;

        // 模版文件目录
        PdfReader pdfReader = new PdfReader(templateFileName);
        // 生成的输出流
        PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(targetFileName));
        AcroFields acroFields = pdfStamper.getAcroFields();
        //设置文本域表单的字体
        // 对于模板要显中文的，在此处设置字体比在pdf模板中设置表单字体的好处：
        //1.模板文件的大小不变；2.字体格式满足中文要求
        BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        for (Map.Entry entry : map.entrySet()) {
            acroFields.setFieldProperty(entry.getKey() + "", "textfont", bf, null);
            acroFields.setField(entry.getKey() + "", entry.getValue() + "");
        }
        // 这句不能少
        pdfStamper.setFormFlattening(true);
        pdfStamper.close();
        pdfReader.close();

        String[] outFileSplit = targetFileName.split("/");
        String fileName = outFileSplit[outFileSplit.length - 1];
        try {
            if (httpServletResponse != null && exportTypeEnum == MicroFastOfficeExportTypeEnum.DIRECT_EXPORT) {
                httpServletResponse.reset();
                httpServletResponse.setContentType("application/octet-stream; charset=utf-8");
                httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "utf-8"));
                ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
                servletOutputStream.write(Files.readAllBytes(Paths.get(targetFileName)));
                return null;
            }
            if (!microFastMinioClientProperties.isEnable()) {
                throw new MicroFastOfficeException(MicroFastOfficeExceptionCodeEnum.PDF_FAIL.getCode(), "生成下载链接失败: 请先开启minio（micro.fast.minio.enable = true）");
            }
            MicroFastMinioUploadRequest microFastMinioUploadRequest = MicroFastMinioUploadRequest
                    .builder()
                    .bucketName("pdf")
                    .objectName("./pdf/" + fileName)
                    .uploadMethodTypeEnum(MicroFastMinioUploadMethodTypeEnum.LOCAL)
                    .fileName(targetFileName)
                    .build();
            return microFastMinioService.upload(microFastMinioUploadRequest);
        } finally {
            //删除临时文件
            Files.deleteIfExists(Paths.get(targetFileName));
        }

    }

}
