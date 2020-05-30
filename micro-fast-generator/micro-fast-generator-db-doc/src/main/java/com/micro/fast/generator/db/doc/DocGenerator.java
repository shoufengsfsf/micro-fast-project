package com.micro.fast.generator.db.doc;


import com.micro.fast.generator.db.doc.utils.MyDocUtils;
import com.micro.fast.generator.db.doc.utils.MyExcelUtils;

/**
 * 数据库文档生成器
 *
 * @author shoufengss
 */
public class DocGenerator {
    public static void main(String[] args) throws Exception {
        MyDocUtils.exportDbDoc("xxx数据库文档.docx");
        MyExcelUtils.exportDbExcel();
    }

}
