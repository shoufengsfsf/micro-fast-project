# 任务调度模块使用说明

## 简介

任务调度模块主要基于[xxl-job](https://github.com/xuxueli/xxl-job)实现，在xxl-job的基础上主要添加了microFastIJobHandler通用任务处理器，该任务处理器可以调用Sringboot容器内任意无参或单个String参数的方法。

## 使用说明

具体使用案例请查看：[micro-fast-demo-job](https://github.com/shoufengsfsf/micro-fast-project/tree/develop/micro-fast-demo/micro-fast-demo-job)

### 1、部署并启动xxl-job-admin

建立xxl-job-admin数据库

```sql
# XXL-JOB v2.2.1-SNAPSHOT
# Copyright (c) 2015-present, xuxueli.

CREATE database if NOT EXISTS `xxl_job` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `xxl_job`;

SET NAMES utf8mb4;

CREATE TABLE `xxl_job_info`
(
    `id`                        int(11)      NOT NULL AUTO_INCREMENT,
    `job_group`                 int(11)      NOT NULL COMMENT '执行器主键ID',
    `job_cron`                  varchar(128) NOT NULL COMMENT '任务执行CRON',
    `job_desc`                  varchar(255) NOT NULL,
    `add_time`                  datetime              DEFAULT NULL,
    `update_time`               datetime              DEFAULT NULL,
    `author`                    varchar(64)           DEFAULT NULL COMMENT '作者',
    `alarm_email`               varchar(255)          DEFAULT NULL COMMENT '报警邮件',
    `executor_route_strategy`   varchar(50)           DEFAULT NULL COMMENT '执行器路由策略',
    `executor_handler`          varchar(255)          DEFAULT NULL COMMENT '执行器任务handler',
    `executor_param`            varchar(512)          DEFAULT NULL COMMENT '执行器任务参数',
    `executor_block_strategy`   varchar(50)           DEFAULT NULL COMMENT '阻塞处理策略',
    `executor_timeout`          int(11)      NOT NULL DEFAULT '0' COMMENT '任务执行超时时间，单位秒',
    `executor_fail_retry_count` int(11)      NOT NULL DEFAULT '0' COMMENT '失败重试次数',
    `glue_type`                 varchar(50)  NOT NULL COMMENT 'GLUE类型',
    `glue_source`               mediumtext COMMENT 'GLUE源代码',
    `glue_remark`               varchar(128)          DEFAULT NULL COMMENT 'GLUE备注',
    `glue_updatetime`           datetime              DEFAULT NULL COMMENT 'GLUE更新时间',
    `child_jobid`               varchar(255)          DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
    `trigger_status`            tinyint(4)   NOT NULL DEFAULT '0' COMMENT '调度状态：0-停止，1-运行',
    `trigger_last_time`         bigint(13)   NOT NULL DEFAULT '0' COMMENT '上次调度时间',
    `trigger_next_time`         bigint(13)   NOT NULL DEFAULT '0' COMMENT '下次调度时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `xxl_job_log`
(
    `id`                        bigint(20) NOT NULL AUTO_INCREMENT,
    `job_group`                 int(11)    NOT NULL COMMENT '执行器主键ID',
    `job_id`                    int(11)    NOT NULL COMMENT '任务，主键ID',
    `executor_address`          varchar(255)        DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
    `executor_handler`          varchar(255)        DEFAULT NULL COMMENT '执行器任务handler',
    `executor_param`            varchar(512)        DEFAULT NULL COMMENT '执行器任务参数',
    `executor_sharding_param`   varchar(20)         DEFAULT NULL COMMENT '执行器任务分片参数，格式如 1/2',
    `executor_fail_retry_count` int(11)    NOT NULL DEFAULT '0' COMMENT '失败重试次数',
    `trigger_time`              datetime            DEFAULT NULL COMMENT '调度-时间',
    `trigger_code`              int(11)    NOT NULL COMMENT '调度-结果',
    `trigger_msg`               text COMMENT '调度-日志',
    `handle_time`               datetime            DEFAULT NULL COMMENT '执行-时间',
    `handle_code`               int(11)    NOT NULL COMMENT '执行-状态',
    `handle_msg`                text COMMENT '执行-日志',
    `alarm_status`              tinyint(4) NOT NULL DEFAULT '0' COMMENT '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败',
    PRIMARY KEY (`id`),
    KEY `I_trigger_time` (`trigger_time`),
    KEY `I_handle_code` (`handle_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `xxl_job_log_report`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT,
    `trigger_day`   datetime         DEFAULT NULL COMMENT '调度-时间',
    `running_count` int(11) NOT NULL DEFAULT '0' COMMENT '运行中-日志数量',
    `suc_count`     int(11) NOT NULL DEFAULT '0' COMMENT '执行成功-日志数量',
    `fail_count`    int(11) NOT NULL DEFAULT '0' COMMENT '执行失败-日志数量',
    PRIMARY KEY (`id`),
    UNIQUE KEY `i_trigger_day` (`trigger_day`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `xxl_job_logglue`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `job_id`      int(11)      NOT NULL COMMENT '任务，主键ID',
    `glue_type`   varchar(50) DEFAULT NULL COMMENT 'GLUE类型',
    `glue_source` mediumtext COMMENT 'GLUE源代码',
    `glue_remark` varchar(128) NOT NULL COMMENT 'GLUE备注',
    `add_time`    datetime    DEFAULT NULL,
    `update_time` datetime    DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `xxl_job_registry`
(
    `id`             int(11)      NOT NULL AUTO_INCREMENT,
    `registry_group` varchar(50)  NOT NULL,
    `registry_key`   varchar(255) NOT NULL,
    `registry_value` varchar(255) NOT NULL,
    `update_time`    datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `i_g_k_v` (`registry_group`, `registry_key`, `registry_value`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `xxl_job_group`
(
    `id`           int(11)     NOT NULL AUTO_INCREMENT,
    `app_name`     varchar(64) NOT NULL COMMENT '执行器AppName',
    `title`        varchar(12) NOT NULL COMMENT '执行器名称',
    `address_type` tinyint(4)  NOT NULL DEFAULT '0' COMMENT '执行器地址类型：0=自动注册、1=手动录入',
    `address_list` varchar(512)         DEFAULT NULL COMMENT '执行器地址列表，多地址逗号分隔',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `xxl_job_user`
(
    `id`         int(11)     NOT NULL AUTO_INCREMENT,
    `username`   varchar(50) NOT NULL COMMENT '账号',
    `password`   varchar(50) NOT NULL COMMENT '密码',
    `role`       tinyint(4)  NOT NULL COMMENT '角色：0-普通用户、1-管理员',
    `permission` varchar(255) DEFAULT NULL COMMENT '权限：执行器ID列表，多个逗号分割',
    PRIMARY KEY (`id`),
    UNIQUE KEY `i_username` (`username`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `xxl_job_lock`
(
    `lock_name` varchar(50) NOT NULL COMMENT '锁名称',
    PRIMARY KEY (`lock_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


INSERT INTO `xxl_job_group`(`id`, `app_name`, `title`, `address_type`, `address_list`)
VALUES (1, 'xxl-job-executor-sample', '示例执行器', 0, NULL);
INSERT INTO `xxl_job_info`(`id`, `job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`,
                           `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`,
                           `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`,
                           `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`)
VALUES (1, 1, '0 0 0 * * ? *', '测试任务1', '2018-11-03 22:21:31', '2018-11-03 22:21:31', 'XXL', '', 'FIRST',
        'demoJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2018-11-03 22:21:31', '');
INSERT INTO `xxl_job_user`(`id`, `username`, `password`, `role`, `permission`)
VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 1, NULL);
INSERT INTO `xxl_job_lock` (`lock_name`)
VALUES ('schedule_lock');

commit;
```

docker-compose启动xxl-job-admin（mysql配置根据实际情况修改）

```java
version: '3.1'
services:
  xxl-job-admin2.2.0:
    image: xuxueli/xxl-job-admin:2.2.0
    ports:
      - 8081:8080
    environment:
      PARAMS: --spring.datasource.url=jdbc:mysql://10.10.71.210:3306/xxl_job?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Shanghai
        --spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
        --spring.datasource.username=root
        --spring.datasource.password=123456
    volumes:
      - /tmp:/data/applogs
```

### 2、修改application.yml配置

```java
# Tomcat
server:
  tomcat:
    uri-encoding: UTF-8
    max-threads: 1000
    min-spare-threads: 30
  port: 7070

  servlet:
    context-path: /
    session:
      cookie:
        http-only: false

spring:
  application:
    name: fast-demo-job
  profiles:
    active: dev
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8
  servlet:
    multipart:
      max-file-size: 100MB
      max-request-size: 100MB
      enabled: true
      location: ./data/file

micro:
  fast:
    xxljob:
      enable: true
      admin-addresses: http://127.0.0.1:8081/xxl-job-admin
  #    appname: fast-demo
  #    log-path: /Users/shoufeng/work/fast-rest-project/logs/fast-demo/xxljob/jobhandler


```

```java
/**
 * xxl-job配置参数
 *
 * @author shoufeng
 */
@Data
@Component
@ConfigurationProperties(prefix = "micro.fast.xxljob")
@NoArgsConstructor
@AllArgsConstructor
public class MicroFastXxlJobClientProperties {

    /**
     * 是否开启
     */
    private boolean enable = false;

    /**
     * 调度中心部署跟地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行"执行器心跳注册"和"任务结果回调"；为空则关闭自动注册；
     */
    private String adminAddresses;

    /**
     * 执行器通讯TOKEN [选填]：非空时启用；
     */
    private String accessToken;

    /**
     * 执行器AppName [选填]：执行器心跳注册分组依据；为空则关闭自动注册
     */
    private String appname;

    /**
     * 执行器注册 [选填]：优先使用该配置作为注册地址，为空时使用内嵌服务 ”IP:PORT“ 作为注册地址。从而更灵活的支持容器类型执行器动态IP和动态映射端口问题。
     */
    private String address;

    /**
     * 执行器IP [选填]：默认为空表示自动获取IP，多网卡时可手动设置指定IP，该IP不会绑定Host仅作为通讯实用；地址信息用于 "执行器注册" 和 "调度中心请求并触发任务"；
     */
    private String ip;

    /**
     * 执行器端口号 [选填]：小于等于0则自动获取；默认端口为9999，单机部署多个执行器时，注意要配置不同执行器端口；
     */
    private int port;

    /**
     * 执行器运行日志文件存储磁盘路径 [选填] ：需要对该路径拥有读写权限；为空则使用默认路径；
     */
    private String logPath;

    /**
     * 执行器日志文件保存天数 [选填] ： 过期日志自动清理, 限制值大于等于3时生效; 否则, 如-1, 关闭自动清理功能；
     */
    private int logRetentionDays = 7;
}
```

### 3、启动项目

### 4、配置xxl-job-admin

1、登录xxl-job-admin

默认账号密码admin 123456

http://127.0.0.1:8081/xxl-job-admin/toLogin

![xll-job-admin登录](https://tva1.sinaimg.cn/large/007S8ZIlly1gf750st4j6j30z20mut9p.jpg)

2、新增执行器

![新增执行器](https://tva1.sinaimg.cn/large/007S8ZIlly1gf7513mttoj31sx0u00y8.jpg)

3、显示执行器注册机器

![显示执行器注册机器](https://tva1.sinaimg.cn/large/007S8ZIlly1gf751ppmr9j31tx0u07am.jpg)

4、新增任务

![新增任务](https://tva1.sinaimg.cn/large/007S8ZIlly1gf752vtjk0j31p70u0grw.jpg)

5、执行任务

![执行任务](https://tva1.sinaimg.cn/large/007S8ZIlly1gf75fno5o1j31si0u00ya.jpg)

6、查看执行日志

![查看执行日志](https://tva1.sinaimg.cn/large/007S8ZIlly1gf75fydyltj31su0u0gqi.jpg)