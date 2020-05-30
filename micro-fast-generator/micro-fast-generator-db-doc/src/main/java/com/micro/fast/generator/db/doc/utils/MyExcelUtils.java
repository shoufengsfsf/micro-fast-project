package com.micro.fast.generator.db.doc.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.db.meta.Table;
import cn.hutool.setting.Setting;
import com.micro.fast.generator.db.doc.vo.DocExcelWordTableVo;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel工具类
 *
 * @author zhihao.mao
 */
public class MyExcelUtils {

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

    public static <T> void exportExcel(List<T> dataList, String sheetName, Class clazz, String targetXlsName) throws IOException {
        Map<String, Object> sheetMap = createSheetMap(sheetName, dataList, clazz);
        List<Map<String, Object>> sheetMapList = new ArrayList<>();
        sheetMapList.add(sheetMap);
        Workbook workbook = ExcelExportUtil.exportExcel(sheetMapList, ExcelType.HSSF);
        if (workbook != null) {
            File dir = new File("./数据库文档/excel/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream("./数据库文档/excel/" + targetXlsName);
            workbook.write(fileOutputStream);
        }
    }

    public static void exportDbExcel() {
        Setting setting = new Setting("./config/dbdoc.setting");
        String dbname = setting.getByGroup("dbname", "docinfo");
        List<Table> tableList = MyDbTableUtils.getTableInfoList(dbname);
        Map<String, List<DocExcelWordTableVo>> excelVoMap = DocExcelWordTableVo.getTableCommentTableNameDocExcelWordTableVoListMapByTableList(tableList);
        excelVoMap.forEach((s, docExcelWordTableVos) -> {
            String[] strings = s.split("&");
            String sheetName = s;
            String fileName = s;
            if (strings.length == 2) {
                fileName = strings[0] + "（" + strings[1] + "）";
                sheetName = strings[0];
            }
            fileName += ".xls";
            try {
                exportExcel(docExcelWordTableVos, sheetName, DocExcelWordTableVo.class, fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
