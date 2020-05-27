# 数据库模块使用说明

## 简介

数据库模块主要基于mybatis-plus实现，在mybatis-plus的基础主要新添加了以下功能点：1、每次做新增操作时自动更新字段的创建时间和修改时间，做修改操作时更新修改时间。2、使用BaseQueryDto<T>实现单表查询的所有功能，比如EQ、NE、GT、GE、BETWEEN、LIKE、IN等等。

## 使用说明

具体使用案例请查看：[micro-fast-demo-db](https://github.com/shoufengsfsf/micro-fast-project/tree/develop/micro-fast-demo/micro-fast-demo-db)

### 1、添加依赖

```java
<dependency>
    <groupId>com.shoufeng.project</groupId>
    <artifactId>micro-fast-mybatis-plus-starter</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 2、修改application.yml，启动依赖

```java
micro:
  fast:
    mybatisplus:
      enable: true
```

### 3、Entity继承BaseEntity

```java
/**
 * 用户entity
 *
 * @author shoufeng
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fast_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FastUserEntity extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;

   /**
    * 用户名
    */
   private String userName;
}
```

### 4、编写Entity的Dao层

```java
/**
 * 用户dao
 *
 * @author shoufeng
 */
@Mapper
public interface FastUserDao extends BaseDao<FastUserEntity> {

}
```

### 5、编写Entity的Service层

```java
/**
 * @author shoufeng
 */
public interface FastUserService extends BaseService<FastUserEntity> {
}
```

```java
/**
 * @author shoufeng
 */
@Service
public class FastUserServiceImpl extends BaseServiceImpl<FastUserDao, FastUserEntity> implements FastUserService {
}
```

### 6、编写Entity的Controller层

```java
/**
 * @author shoufeng
 */
@RestController
@RequestMapping("/fastuser")
public class FastUserController {

    @Resource
    private FastUserService fastUserService;

    @PostMapping("/save")
    public void save(@RequestParam String userName) {
        fastUserService.save(FastUserEntity.builder().userName(userName).build());
    }

    @PutMapping("/update")
    public void save(@RequestBody FastUserEntity fastUserEntity) {
        fastUserService.updateById(fastUserEntity);
    }

    @GetMapping("/list")
    public String list(@RequestBody BaseQueryDto<FastUserEntity> fastUserEntityBaseQueryDto) {
        fastUserEntityBaseQueryDto.setClazz(FastUserEntity.class);
        return JSON.toJSONString(fastUserService.listByBaseQueryDto(fastUserEntityBaseQueryDto));
    }
}
```

### 7、接口调用说明

增删改没什么特别需要说明的，跟普通使用mybatis-plus一样，下面着重说明一下BaseQueryDto<T>的使用。

BaseQueryDto主要包含三个重要字段：1、表实体clazz（用于表面具体是查询那张表）。2、字段操作映射fieldOperationMap（用于表明该字段具体是做什么操作，比如name:like）。3、字段值映射fieldValueMap（用于表明该字段操作的具体是什么值）

```java
/**
 * 查询公共类
 *
 * @author shoufeng
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseQueryDto<T> {

    /**
     * 表实体
     */
    private Class<T> clazz;

    /**
     * 能否为空（true：可以，为空字段也查询，false：否，为空字段忽略不查询）
     */
    private Boolean isNullAble = false;

    /**
     * 字段操作映射，比如，name:like
     */
    private Map<String, String> fieldOperationMap;

    /**
     * 字段值映射，比如，name:张三
     */
    private Map<String, Object> fieldValueMap;

    public Map<String, String> getFieldOperationMap() {
        fieldOperationMap = Optional.ofNullable(fieldOperationMap).orElseGet(HashMap::new);
        return fieldOperationMap;
    }

    public Map<String, Object> getFieldValueMap() {
        fieldValueMap = Optional.ofNullable(fieldValueMap).orElseGet(HashMap::new);
        return fieldValueMap;
    }

    /**
     * 分页信息
     */
    private Page<T> page;

    public void setFieldValueMapByEntity(T entity) throws IllegalAccessException {
        this.clazz = (Class<T>) entity.getClass();
        Field[] fields = this.clazz.getDeclaredFields();
        if (ObjectUtils.isEmpty(fieldValueMap)) {
            fieldValueMap = new HashMap<>();
        }
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object fieldValue = field.get(entity);
            if (ObjectUtils.isEmpty(fieldValue) && !isNullAble) {
                continue;
            }
            fieldValueMap.put(fieldName, fieldValue);
        }
    }
}
```

fieldOperationMap目前支持的操作（使用操作枚举类型的englishName传入即可）：

```java
**
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
```

### 另外说明

目前Entity、Dao、Service、Controller还是手动添加的，以后计划是会出一个代码生成器、以及idea生成器插件。

