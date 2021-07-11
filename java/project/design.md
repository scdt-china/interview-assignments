# Java / 短域名服务 / 设计思路

## 架构

一个Application，一个Controller，一个Service。<br/>
<br/>
Service里有两个ConcurrentHashMap存储映射数据。<br/>
为了防止内存溢出，存储有限制。

### 接口

Swagger形容实现的接口:<br/>
http://localhost:8080/swagger-ui/<br/>
<br/>
![apidoc](apidoc.png)


