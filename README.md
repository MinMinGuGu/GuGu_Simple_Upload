# GuGu_Simple_Upload

咕咕的多对一形式的文件上传系统。

## 目的

由于MinMin购买了新机器，想利用局域网来复制旧机器文件到新机器上。

## 发展

目前项目差管理模块，感觉可以发展成局域网网盘的样子，但是目前还未编写成那种程度。

## 架构

前后端分离。

### 后端

基于Java的SpringBoot框架

### 前端

React

### 日志
日志默认输出在`${user.home}/GuGu_Simple_Upload/log/`下，详细请查看`logback-spring.xml`文件

### 启动
通过`back-end/src/test/java/com/gugu/upload/utils/JasyptUtilTest.java`测试类生成加密字符串，然后配置在`application.yml`即可