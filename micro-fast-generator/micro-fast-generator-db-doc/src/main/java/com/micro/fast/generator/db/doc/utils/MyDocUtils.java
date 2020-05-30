package com.micro.fast.generator.db.doc.utils;

import cn.afterturn.easypoi.word.WordExportUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.db.meta.Table;
import cn.hutool.poi.word.TableUtil;
import cn.hutool.poi.word.Word07Writer;
import cn.hutool.setting.Setting;
import com.micro.fast.generator.db.doc.vo.DocExcelWordTableVo;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.awt.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * word工具类
 *
 * @author shoufeng
 */
public class MyDocUtils {
    public static final String USER_DIR = System.getProperty("user.dir");

    /**
     * 导出word（docx）
     *
     * @param targetDocxName 导出的文件（包含.docx）
     * @throws Exception
     */
    public static void exportDbDoc(String targetDocxName) throws Exception {
        Setting setting = new Setting("./config/dbdoc.setting");
        String systemname = setting.getByGroup("systemname", "docinfo");
        String departmentname = setting.getByGroup("departmentname", "docinfo");
        String username = setting.getByGroup("username", "docinfo");
        String dbname = setting.getByGroup("dbname", "docinfo");
        List<Table> tableList = MyDbTableUtils.getTableInfoList(dbname);
        Map<String, List<DocExcelWordTableVo>> excelVoMap = DocExcelWordTableVo.getTableCommentTableNameDocExcelWordTableVoListMapByTableList(tableList);

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("systemname", systemname);
        dataMap.put("departmentname", departmentname);
        dataMap.put("year", DateUtil.year(new Date()));
        dataMap.put("month", DateUtil.month(new Date()) + 1);
        dataMap.put("username", username);
        dataMap.put("finisheddate", DateUtil.formatDate(new Date()));
        XWPFDocument xwpfDocument = WordExportUtil.exportWord07(USER_DIR + "/数据库文档/template/数据库文档模版.docx", dataMap);
        Word07Writer word07Writer = new Word07Writer(xwpfDocument);
        Map<String, String> indexKeyMap = new HashMap<>();
        indexKeyMap.put("0", "字段名称");
        indexKeyMap.put("1", "数据类型");
        indexKeyMap.put("2", "能否为空");
        indexKeyMap.put("3", "注释");
        AtomicInteger atomicInteger = new AtomicInteger(1);
        excelVoMap.forEach((s, docExcelWordVos) -> {
            String[] strings = s.split("&");
            word07Writer.addText(new Font("方正小标宋简体", Font.PLAIN, 20), "1." + atomicInteger.getAndIncrement() + strings[0]);
            word07Writer.addText(new Font("方正小标宋简体", Font.PLAIN, 20), "概念名称：" + strings[0]);
            word07Writer.addText(new Font("方正小标宋简体", Font.PLAIN, 20), "物理名称：" + strings[1]);
            List<HashMap<String, String>> dataList = docExcelWordVos.stream().map(docExcelWordVo -> {
                HashMap<String, String> map = new HashMap<>();
                map.put("字段名称", docExcelWordVo.getColumnName());
                map.put("数据类型", docExcelWordVo.getTypeName());
                map.put("能否为空", docExcelWordVo.getNullable());
                map.put("注释", docExcelWordVo.getComment());
                return map;
            }).collect(Collectors.toList());
            XWPFTable xwpfTable = TableUtil.createTable(xwpfDocument);
            for (int i = 0; i < (dataList.size() + 1); i++) {
                XWPFTableRow xwpfTableRow = TableUtil.getOrCreateRow(xwpfTable, i);
                if (i == 0) {
                    for (int i1 = 0; i1 < 4; i1++) {
                        XWPFTableCell xwpfTableCell = xwpfTableRow.getCell(i1);
                        if (xwpfTableCell == null) {
                            xwpfTableCell = xwpfTableRow.createCell();
                        }
                        xwpfTableCell.setText(indexKeyMap.get(i1 + ""));
                    }
                    continue;
                }
                HashMap<String, String> data = dataList.get(i - 1);
                for (int i1 = 0; i1 < 4; i1++) {
                    XWPFTableCell xwpfTableCell = xwpfTableRow.getCell(i1);
                    if (xwpfTableCell == null) {
                        xwpfTableCell = xwpfTableRow.createCell();
                    }
                    xwpfTableCell.setText(data.get(indexKeyMap.get(i1 + "")));
                }
            }
        });

        // 写出到文件
        word07Writer.flush(FileUtil.file(USER_DIR + "/数据库文档/word/" + targetDocxName));
        word07Writer.close();
    }
}
