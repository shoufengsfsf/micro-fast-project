# 简易数据库文档生成器使用说明

## 简介

由于项目尾期的时候需要交付系统设计文档，系统设计文档中必然少不了数据库设计文档，除了一些临时小项目之外，数据库中的表少说也有一二百张，故而做了个建议的简易的数据库文档生成器。因为生成器的主要目的是取出数据库中的表结构以及comment，所以对生成的文档格式并没有做太规范的调整，建议生成后的文档基础上调整样式后再交付。

## 使用说明

### 1、配置说明

db.setting（数据库配置）

```
## 可选配置
# 是否在日志中显示执行的SQL
showSql = true
# 是否格式化显示的SQL
formatSql = false
# 是否显示SQL参数
showParams = true
# 打印SQL的日志等级，默认debug，可以是info、warn、error
sqlLevel = debug

## 数据库配置（替换ip即可，information_schema是固定的）
[tableinfodb]
url = jdbc:mysql://127.0.0.1:3306/information_schema?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false
user = root
pass = 123456

## 数据库配置
[masterdb]
url = jdbc:mysql://127.0.0.1:3306/demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false
user = root
pass = 123456
```

dbdoc.setting（导出文件参数配置）

```
## 导出文件参数配置
[docinfo]
## 数据库名称（必填无论是导出excel还是word）
dbname = demo
## 系统名称（导出word时必填）
systemname = xxx系统
## 部门名称（导出word时必填）
departmentname = xxx开发一部
## 用户名称（导出word时必填）
username = 张三
```

2、生成word文档

```java
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
//        MyExcelUtils.exportDbExcel();
    }

}
```

![image-20200530231144254](https://tva1.sinaimg.cn/large/007S8ZIlly1gfavr0oindj30jq0rwwi0.jpg)

![image-20200530231523727](https://tva1.sinaimg.cn/large/007S8ZIlly1gfavutzr1uj31170u076q.jpg)

![image-20200530231547030](https://tva1.sinaimg.cn/large/007S8ZIlly1gfavv8qg6xj313y0u0tas.jpg)

![image-20200530231605556](https://tva1.sinaimg.cn/large/007S8ZIlly1gfavvjwdjuj310t0u0gr2.jpg)

![image-20200530231632141](https://tva1.sinaimg.cn/large/007S8ZIlly1gfavw00ut6j312j0u0n3a.jpg)

3、生成excel文档

```java
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
//        MyDocUtils.exportDbDoc("xxx数据库文档.docx");
        MyExcelUtils.exportDbExcel();
    }

}
```

![image-20200530231847839](https://tva1.sinaimg.cn/large/007S8ZIlly1gfavycv9s9j30js0vewik.jpg)

![image-20200530231910224](https://tva1.sinaimg.cn/large/007S8ZIlly1gfavyt88gbj30t4160wj7.jpg)