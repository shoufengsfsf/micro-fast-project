# micro-fast-project
## 简介

原先的[fast-rest-project](https://github.com/shoufengsfsf/fast-rest-project)后端快速开发框架依赖越加越多，感觉脱离了我一开始创建项目的目的，micro-fast-project这个项目吸取上个项目的教训，每一类模块仅使用一种市面上常用技术，希望能达到项目小而快的初衷。

## 技术选型

| 模块                                                         | 选用技术          | 进度 | 文档 |
| ------------------------------------------------------------ | ----------------- | ---- | ---- |
| [数据库模块](https://github.com/shoufengsfsf/micro-fast-project/tree/develop/micro-fast-db/micro-fast-mybatis-plus-starter) | mybatis-plus      | ✅    |      |
| [权限模块](https://github.com/shoufengsfsf/micro-fast-project/tree/develop/micro-fast-auth) | jwt               | ✅    | ✅    |
| [日志模块](https://github.com/shoufengsfsf/micro-fast-project/tree/develop/micro-fast-log/micro-fast-log-starter) | logback           | ✅    | ✅    |
| [消息队列模块](https://github.com/shoufengsfsf/micro-fast-project/tree/develop/micro-fast-mq/micro-fast-mq-rabbit-starter) | rabbitmq          | ✅    | ✅    |
| [文档模块](https://github.com/shoufengsfsf/micro-fast-project/tree/develop/micro-fast-doc/micro-fast-swagger-starter) | swagger           | ✅    | ✅    |
| [任务调度模块](https://github.com/shoufengsfsf/micro-fast-project/tree/develop/micro-fast-job/micro-fast-job-xxl-starter) | xxl-job           | ✅    | ✅    |
| [文件存储模块](https://github.com/shoufengsfsf/micro-fast-project/tree/develop/micro-fast-file/micro-fast-file-minio-starter) | Minio             | ✅    | ✅    |
| 代码生成器模块                                               |                   |      |      |
| [数据库文档生成器模块](https://github.com/shoufengsfsf/micro-fast-project/tree/master/micro-fast-generator/micro-fast-generator-db-doc) |                   | ✅    | ✅    |
| 应用监控模块                                                 | spring-boot-admin |      |      |
| 注册中心模块                                                 | nacos             | ✅    | ❌    |
| 动态配置模块                                                 | nacos             | ✅    | ❌    |
| 服务调用模块                                                 | fegin、ribbon     |      |      |
| CI/CD模块                                                    | jenkins           |      |      |
| Office模块                                                   | easypoi、itextpdf | ✅    |      |

