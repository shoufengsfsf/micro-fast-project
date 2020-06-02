package com.micro.fast.office.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
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
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.xmlpull.v1.XmlPullParserException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * excel工具类
 *
 * @author shoufeng
 */

@Component
@Slf4j
public class ExcelUtils {

    @Resource
    private MicroFastOfficeProperties microFastOfficeProperties;

    @Resource
    private MicroFastMinioClientProperties microFastMinioClientProperties;

    @Resource
    private MicroFastMinioService microFastMinioService;

    public static <T> Map<String, Object> createSheetMap(String sheetName, List<T> list, Class clazz) {
        // 创建参数对象（用来设定excel的sheet的内容等信息）
        ExportParams param = new ExportParams();
        // 设置sheet的名称
        param.setSheetName(sheetName);
        // 设置sheet标题名称
        param.setTitle(sheetName);
        // 创建sheet1使用的map
        Map<String, Object> map = new HashMap<>();
        // title的参数为ExportParams类型，目前仅在ExportParams中设置了sheetName
        map.put("title", param);
        // 模版导出对应得实体类型
        map.put("entity", clazz);
        // sheet中要填充得数据
        map.put("data", list);
        return map;
    }

    /**
     * 通过数据对象导出excel
     */
    public String exportExcel(MicroFastOfficeExportTypeEnum exportTypeEnum, List<T> dataList, String sheetName, Class clazz, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, XmlPullParserException, NoSuchAlgorithmException, RegionConflictException, InvalidKeyException, InvalidExpiresRangeException, IllegalAccessException, InvalidArgumentException, ErrorResponseException, NoResponseException, InvalidBucketNameException, InsufficientDataException, InternalException {
        Map<String, Object> sheetMap = createSheetMap(sheetName, dataList, clazz);
        List<Map<String, Object>> sheetMapList = new ArrayList<>();
        sheetMapList.add(sheetMap);
        Workbook workbook = ExcelExportUtil.exportExcel(sheetMapList, ExcelType.HSSF);
        String timeStr = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss", Locale.CHINA);

        return exportExcelCore(exportTypeEnum, workbook, sheetName + timeStr + ".xls", httpServletResponse);
    }

    /**
     * 通过模版导出excel
     */
    public String exportExcelByTemplate(MicroFastOfficeExportTypeEnum exportTypeEnum, String templateFileName, Map<String, Object> dataMap, String targetFileName, HttpServletResponse httpServletResponse) throws IOException, InvalidKeyException, NoSuchAlgorithmException, XmlPullParserException, InvalidExpiresRangeException, IllegalAccessException, InvalidArgumentException, ErrorResponseException, NoResponseException, InvalidBucketNameException, InsufficientDataException, InternalException, RegionConflictException {
        templateFileName = microFastOfficeProperties.getTemplateBasePath() + microFastOfficeProperties.getExcelTemplateBasePath() + templateFileName;
        File file = new File(templateFileName);
        if (!file.exists()) {
            log.error("导出失败: 模版文件{}不存在", templateFileName);
            throw new MicroFastOfficeException(MicroFastOfficeExceptionCodeEnum.EXCEL_FAIL.getCode(), "导出失败: 模版文件" + templateFileName + "不存在");
        }
        TemplateExportParams params = new TemplateExportParams(templateFileName);
        Workbook workbook = ExcelExportUtil.exportExcel(params, dataMap);

        return exportExcelCore(exportTypeEnum, workbook, targetFileName, httpServletResponse);
    }

    private String exportExcelCore(MicroFastOfficeExportTypeEnum exportTypeEnum, Workbook workbook, String fileName, HttpServletResponse httpServletResponse) throws IOException, XmlPullParserException, NoSuchAlgorithmException, RegionConflictException, InvalidKeyException, InvalidExpiresRangeException, IllegalAccessException, InvalidArgumentException, ErrorResponseException, NoResponseException, InvalidBucketNameException, InsufficientDataException, InternalException {
        if (workbook != null) {
            if (httpServletResponse != null && exportTypeEnum == MicroFastOfficeExportTypeEnum.DIRECT_EXPORT) {
                httpServletResponse.reset();
                httpServletResponse.setContentType("application/octet-stream; charset=utf-8");
                String[] outFileSplit = fileName.split("/");
                fileName = outFileSplit[outFileSplit.length - 1];
                httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "utf-8"));
                workbook.write(httpServletResponse.getOutputStream());
            }
            if (!microFastMinioClientProperties.isEnable()) {
                throw new MicroFastOfficeException(MicroFastOfficeExceptionCodeEnum.EXCEL_FAIL.getCode(), "生成下载链接失败: 请先开启minio（micro.fast.minio.enable = true）");
            }
            //临时缓冲区
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //创建临时文件
            workbook.write(out);
            byte[] bookByteAry = out.toByteArray();
            ByteArrayInputStream in = new ByteArrayInputStream(bookByteAry);
            MicroFastMinioUploadRequest microFastMinioUploadRequest = MicroFastMinioUploadRequest
                    .builder()
                    .bucketName("excel")
                    .objectName("./excel/" + fileName)
                    .uploadMethodTypeEnum(MicroFastMinioUploadMethodTypeEnum.STREAM)
                    .stream(in)
                    .build();
            return microFastMinioService.upload(microFastMinioUploadRequest);
        } else {
            throw new MicroFastOfficeException(MicroFastOfficeExceptionCodeEnum.EXCEL_FAIL.getCode(), "文件导出出错!");
        }
    }

}
