package com.micro.fast.mybatisplus.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作常量
 *
 * @author shoufeng
 */

@AllArgsConstructor
public enum OperationEnum {

    /**
     * 操作
     */
    EQ("等于 =", "eq", "例: eq(\"name\", \"老王\")--->name = '老王'"),
    NE("不等于 <>", "ne", "例: ne(\"name\", \"老王\")--->name <> '老王'"),
    GT("大于 >", "gt", "例: gt(\"age\", 18)--->age > 18"),
    GE("大于等于 >=", "ge", "例: ge(\"age\", 18)--->age >= 18"),
    LT("小于 <", "lt", "例: lt(\"age\", 18)--->age < 18"),
    LE("小于等于 <=", "le", "例: le(\"age\", 18)--->age <= 18"),
    BETWEEN("BETWEEN 值1 AND 值2", "between", "例: between(\"age\", 18, 30)--->age between 18 and 30"),
    NOTBETWEEN("NOT BETWEEN 值1 AND 值2", "notBetween", "例: notBetween(\"age\", 18, 30)--->age not between 18 and 30"),
    LIKE("LIKE '%值%'", "like", "例: like(\"name\", \"王\")--->name like '%王%'"),
    NOTLIKE("NOT LIKE '%值%'", "notLike", "例: notLike(\"name\", \"王\")--->name not like '%王%'"),
    LIKELEFT("LIKE '%值'", "likeLeft", "例: likeLeft(\"name\", \"王\")--->name like '%王'"),
    LIKERIGHT("LIKE '值%'", "likeRight", "例: likeRight(\"name\", \"王\")--->name like '王%'"),
    ISNULL("字段 IS NULL", "isNull", "例: isNull(\"name\")--->name is null"),
    ISNOTNULL("字段 IS NOT NULLs", "isNotNull", "例: isNotNull(\"name\")--->name is not null"),
    IN("字段 IN (value.get(0), value.get(1), ...)", "in", "例: in(\"age\",{1,2,3})--->age in (1,2,3)"),
    NOTIN("字段 IN (value.get(0), value.get(1), ...)", "notIn", "例: notIn(\"age\",{1,2,3})--->age not in (1,2,3)"),
    INSQL("字段 IN ( sql语句 )", "inSql", "例: inSql(\"id\", \"select id from table where id < 3\")--->id in (select id from table where id < 3)"),
    NOTINSQL("字段 NOT IN ( sql语句 )", "notInSql", "例: notInSql(\"id\", \"select id from table where id < 3\")--->age not in (select id from table where id < 3)"),
    GROUPBY("分组：GROUP BY 字段, ...", "groupBy", "例: groupBy(\"id\", \"name\")--->group by id,name"),
    HAVING("HAVING ( sql语句 )", "having", "例: having(\"sum(age) > {0}\", 11)--->having sum(age) > 11¬"),
    ORDERBYASC("排序：ORDER BY 字段, ... ASC", "orderByAsc", "例: orderByAsc(\"id\", \"name\")--->order by id ASC,name ASC"),
    ORDERBYDESC("排序：ORDER BY 字段, ... DESC", "orderByDesc", "例: orderByDesc(\"id\", \"name\")--->order by id DESC,name DESC"),
    ;

    /**
     * 中文名
     */
    @Getter
    private String chineseName;

    /**
     * 英文名
     */
    @Getter
    private String englishName;


    /**
     * 描述
     */
    @Getter
    private String description;

}
