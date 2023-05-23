## 私人网盘搭建

## 1 技术选型

### 1.1 后端

#### 1.1.1 通用

- SpringBoot3 
- SpringCloud + SpringCloudAlibaba + OpenFeign 实现微服务
- swagger-ui 接口文档管理
- Nacos 服务注册发现
- MySQL

#### 1.1.2 服务模块

- mem-common

该模块为基础工具包，包括常量类、返回包装、异常处理、通用工具类等（非微服务）

- mem-module

该模块为实体类集合（非微服务）

- mem-file

文件操作模块，实现文件的上传、下载等（Minio / OSS）

- mem-user

用户管理模块，实现用户管理功能，包括权限控制（Redis缓存）等

- mem-gateway

  统一网关，实现路由转发和鉴权（SpringCloud Gateway）

- mem-im

实现私聊、群聊等功能（包括群文件、互传文件及聊天） (RocketMQ)

### 1.2 前端

#### 1.2.1 桌面端

以桌面端为主

Vue？/React？（暂没想好） + Electron

#### 1.2.2 移动端

UniApp吧，暂时先以桌面端为主，移动端不考虑

## 2 项目部署文档



## 3 项目遇到问题及解决方案

### 3.1 Nacos启动异常问题

- Nacos启动失败，报

![image-20230519020531078](https://image.adrainty.xyz/images/image-20230519020531078.png)

使用

~~~sh
./startup.sh -m standalone
~~~

- Nacos启动后报



### 3.2 SpringBoot启动异常问题

![image-20230519021141460](https://image.adrainty.xyz/images/image-20230519021141460.png)

原因：mem-gateway模块引入了mem-common，而mem-common引入了spring-boot-starter-web，造成冲突

方案：删除mem-common中的spring-boot-starter-web，在有需要的时候再引入