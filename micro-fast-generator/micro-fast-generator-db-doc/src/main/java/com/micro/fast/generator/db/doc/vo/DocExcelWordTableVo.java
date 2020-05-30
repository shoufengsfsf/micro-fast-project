package com.micro.fast.generator.db.doc.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author shoufeng
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocExcelWordTableVo implements Serializable {
    /**
     * 字段名称
     */
    @Excel(name = "字段名称")
    private String columnName;

    /**
     * 数据类型
     */
    @Excel(name = "数据类型")
    private String typeName;

    /**
     * 能否为空
     */
    @Excel(name = "能否为空")
    private String nullable;

    /**
     * 注释
     */
    @Excel(name = "注释")
    private String comment;

    /**
     * 获取表信息map
     *
     * @param tableList key: TableComment + & + tableName;
     * @return
     */
    public static Map<String, List<DocExcelWordTableVo>> getTableCommentTableNameDocExcelWordTableVoListMapByTableList(List<Table> tableList) {
        Map<String, List<DocExcelWordTableVo>> excelVoMap = new HashMap<>();
        tableList.forEach(table -> {
            String tableName = table.getTableName();
            String tableComment = table.getComment();
            Collection<Column> columns = table.getColumns();
            List<DocExcelWordTableVo> excelVoList = columns.stream().map(column -> {
                String columnName = column.getName();
                String comment = column.getComment();
                boolean nullable = column.isNullable();
                String typeName = column.getTypeName();
                DocExcelWordTableVo docExcelVo = new DocExcelWordTableVo(columnName, typeName, String.valueOf(nullable), comment);
                return docExcelVo;
            }).collect(Collectors.toList());
            String key = tableName;
            key = tableComment.replace("/", "或") + "&" + tableName;
            excelVoMap.put(key, excelVoList);
        });

        return excelVoMap;
    }
}
