## 一、ORM骨架

### dal项目包含JPA和Mybatis实例代码

## 一、微服务项目骨架

包含注册中心，RPC框架，协议。
外围包含配置中心，监控中心，网关

### SpringCloud：一般是 Eureka + Ribbon + REST

### Dubbo：一般是 Zookeeper + dubbo + Socket多种协议
1.下载Zookeeper安装，http://mirror.bit.edu.cn/apache/zookeeper/zookeeper-3.4.14/
2.项目引入dubbo-2.5.3,运行Spring+dubbo项目，打包jar
3.注册服务到zk

4.分离本地配置到zk