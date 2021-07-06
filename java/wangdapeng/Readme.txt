
设计思路：
1 这是一个单机版的短域名生成服务
2 基于springboot
3 单机版的 short-long-url对应关系存在guava cache中
4 关于内存溢出：
	a 使用控制max_cache_size来硬性控制
	b 控制long-url的字符数 （比如最长支持1000个字符）
	c 这是一个demo环境的假设， 生产环境另说， 比方存储在redis集群，到了阈值内报警等
5 junit使用junit4+mockito进行单元测试
	a 单元测试过滤掉了 package vo, config,controller等简单逻辑包
	b 单元测试覆盖率100%，设置值 不小于0.8
6 生成短域名算法是md5后取6个char，（网上找的源码，copy过来的)
7 文档使用swagger2

程序在下面的环境中 运行成功：
springboot版本 2.06
mvn 版本3.6.1
java se 1.8
