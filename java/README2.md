# Java Assignment
## 设计
1. 生成短连接使用 Google 的 MurmurHash 实现，正好 8 个。
2. 全部数据在内存中，MappedByteBuffer 映射 文件。
3. 文件分两种，索引文件和数据文件。
4. 索引文件中存储短连接、长连接在数据文件中的位置、长连接在数据文件中的长度。
5. 数据文件中存储长连接。

## 关于存储
1. 数据存储在文件里,文件映射到堆外内存,文件的位置在 target/run/下.
2. 文件分两种,数据文件和索引文件. 索引文件有 500 多个,数量取决于 Config 中的配置.数据文件有一个.
3. 索引文件结构.前 4 个字节存储当前的 position,之后每 16 个字节是一个索引记录.索引中包含短 url 和长 url 在数据文件中的位置和长度
4. 数据文件结构.前 4 个字节存储当前的 position,之后所有的数据依次排列.此文件中存储长 url
5. 索引数据结构.前 8 个字节存储短 url 的值,之后 4 个字节存储长 url 在数据文件中的 position,之后 4 个字节存储长 url 的长度.
6. 索引文件的可以有多个,在 Config 中配置.如何确认数据在那个索引文件中? 短连接取 hash % 索引文件数量.
7. 数据文件有一个,最大重量可以在 Config 中配置.

![Image text](https://upload-images.jianshu.io/upload_images/16120382-c762d5ace1162aa2.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/882/format/webp)


## 关于JMeter
##### 这里有个测试结果.这个结果是没有优化过.用我开发电脑测的.
```
summary =   1000 in 00:00:01 =  758.2/s Avg:   441 Min:     0 Max:   906 Err:    26 (2.60%)
```

##### 测试脚本在这里
java/url.jmx

## 关于 JaCoCo
1. 可以执行maven 的插件,报告在 target/site中
2. 只有本次作业相关的包跑了JaCoCo，storage 和 util，其他的包没跑。


## Swagger
http://127.0.0.1:8080/swagger-ui.html

## 一些说明
2 从官方仓库无法下载依赖或者下载较慢时,请配置国内镜像. 







