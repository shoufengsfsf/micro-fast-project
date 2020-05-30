package com.micro.fast.generator.db.doc.utils;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库工具类
 *
 * @author shoufeng
 */
public class MyDbTableUtils {

    public static List<Table> getTableInfoList(String dbname) {
        DataSource masterDs = DSFactory.get("masterdb");
        DataSource tableinfoDs = DSFactory.get("tableinfodb");

        List<String> tableNameList = MetaUtil.getTables(masterDs);
        List<Table> tableList = new ArrayList<Table>();
        tableNameList.forEach(tableName -> {
            Table table = MetaUtil.getTableMeta(masterDs, tableName);
            try {
                Entity entity = Db
                        .use(tableinfoDs)
                        .queryOne("select * from TABLES where TABLE_SCHEMA='" + dbname + "' and TABLE_NAME='" + table.getTableName() + "';");
                table.setComment(entity.getStr("table_comment"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            tableList.add(table);
        });
        return tableList;
    }
}
