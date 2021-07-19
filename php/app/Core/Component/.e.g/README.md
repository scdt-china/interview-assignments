## 用户注册 Demo

### 环境说明
  
- 需要 Composer 包管理器，PHP 7.4
- 基于 Phalcon 框架开发，需安装扩展：psr、phalcon 4.0.6、yaconf
- Clone 代码，（假设根目录为：dpath）有效代码目录：
-   dpath/app/Core/Component/.e.g/src/
-   dpath/app/Core/model/
-   dpath/conf/demo/
- 根目录运行：composer install
- yaconf.directory = dpath/conf
- 修改 dpath/conf/project.ini 中命名空间对应的路径
- 修改 dpath/conf/demo/config.php 中数据库连接配置
- 导入 dpath/var/data/demo.sql 数据库表
- 根目录运行：composer run-script demo-start
- 浏览器访问 http://localhost:3000
