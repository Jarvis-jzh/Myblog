# SpringBoot-MyBlog

**项目展示：** https://www.jzh.plus



##  项目环境

| 工具       | 名称                             |
| ---------- | -------------------------------- |
| 开发工具   | IDEA                             |
| 开发语言   | JDK 1.8                          |
| 数据库     | MySQL 5.7                        |
| 前端框架   | Bootstarp                        |
| 项目框架   | SpringBoot + MyBatis + Thymeleaf |
| 权限框架   | SpringSecurity                   |
| 分页插件   | PageHelper                       |
| 缓存       | Redis                            |
| 构建工具   | Maven                            |
| 运行环境   | Linux / Docker                   |
| 文章编辑器 | Editor.md                        |
| 其它       | 阿里云OSS、阿里云SMS             |



## 项目运行

### Linux系统

这里提供两种方式运行

第一种在Linux本地运行

进入到项目目录下，使用`bash package_deploy.sh start`命令启动即可。

第二种在docker中运行

进入到项目目录下，使用`bash docker_deploy.sh start`命令启动即可。



## 日志

**Linux系统**

所有日志文件均放在`/data/logs/myblog`目录下



## Arthas

在 `Dockerfile` 文件中添加了 `Arthas`，如果需要使用，请运行脚本 `bash arthas.sh` 即可进入；如果不用，也可以自行到 `./docker/env/Dockerfile` 文件中注释或删除复制命令即可。

