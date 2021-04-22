# 项目描述：该系统为用户提供一个进行预约挂号的平台，主要分为数据字典、医院、短信、挂号订单、用户管理五个微服务模块。

项目架构：SpringBoot + Mybatis-Plus + SpirngData(MangoDB、Redis) + SpringCloud

技术要点：
Mysql数据库的CRUD、利用SpringData对MangoDB进行CRUD。
SpringCloud微服务架构设计。
Redis+阿里短信云服务进行验证码登录。
EasyExcel进行数据导入和导出。
RabbitMQ实现挂号下单操作，用于实现高并发下的可靠库存更新。


