## 私人网盘搭建

此为后端，前端见https://gitlab.adrainty.xyz/adrainty/mem-storage-app

### 1 后端

#### 1.1 通用

- SpringBoot3 
- SpringCloud + SpringCloudAlibaba + OpenFeign 实现微服务
- swagger-ui 接口文档管理
- Nacos 服务注册发现
- MySQL

#### 1.2 服务模块

- mem-common

该模块为基础工具包，包括常量类、返回包装、异常处理、通用工具类等（非微服务）

- mem-module

该模块为实体类集合（非微服务）

- mem-file

文件操作模块，实现文件的上传、下载等（Minio）

- mem-authority

权限控制模块，实现用户鉴权

- mem-user

用户管理模块，实现用户管理功能

- mem-gateway

  统一网关，实现路由转发

- mem-im

实现私聊、群聊等功能（包括群文件、互传文件及聊天） (RocketMQ)

### 2 前端

#### 2.1 桌面端

以桌面端为主

Vue3 + Electron

