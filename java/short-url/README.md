实现短域名服务（细节可以百度/谷歌）
撰写两个 API 接口:

短域名存储接口：接受长域名信息，返回短域名信息
短域名读取接口：接受短域名信息，返回长域名信息。
限制：

短域名长度最大为 8 个字符
采用SpringBoot，集成Swagger API文档；
JUnit编写单元测试, 使用Jacoco生成测试报告(测试报告提交截图即刻)；
映射数据存储在JVM内存即可，防止内存溢出；
========================================================

设计 ：

 一 ,设计两个接口，在一个控制器里面有两个接口方法
 com.sequoia.shorturl.controller包下
 ShortUrlController.java   提供两个API接口

 1.接口名为   http://127.0.0.1:8080/createShortUrl  //接受长域名信息，返回短域名信息
 请求方式   method : POST
 请求参数名 originalUrl ： string  //例子 https://zhuanlan.zhihu.com/p/194199097
 返回结果 ：
 返回 json数据 { code:0,     //状态码  0，成功，1 失败
               message: "success",
               data: "http://www.baidu.com/t/uDqYOnfQ"  // http://www.baidu.com/t/ 为配置文件配置短域名前缀，uDqYOnfQ 为转换后的8短码
              }

2.接口名为   http://127.0.0.1:8080/getOriginalUrl //接受短域名信息，返回长域名信息
 请求方式   method : POST
 请求参数名 originalUrl ： string    //例子  http://www.baidu.com/t/uDqYOnfQ
 返回结果 ：
 返回 json数据 { code:0,     //状态码  0，成功，1 失败
                message: "success",
                data: "https://zhuanlan.zhihu.com/p/194199097"  // 为对应的原 https://zhuanlan.zhihu.com/p/194199097 
              }

二 生成 短域名长度最大为 8 个字符 算法
  生成的短链接要求每个链接都是唯一的，并且长度尽可能短为8位
  算法方式有多种 ，考虑到并发量，算法的性能，还有能包含数据量比较大海量，不重复，不碰撞，
  采用 26个大写字母 26小写字母，10个数字，共62个字符，用62进制数来表示
  8位 可以表示 62^8个地址  远大于 568亿个组合数，足够用了
  算法方式有：
   1.Hash算法
     简单的对长链接进行加盐md5，会生成一个32位的字符串，随机从里面取6个字符，
     或者简单粗暴取最后6位，但是md5只包含0-9A-Fa-f,比字母表的里面字符还少，冲突几率更大
   2.MD5压缩算法
     1)将长网址md5生成32位签名串,分为4段, 每段8个字节;
     2)对这四段循环处理, 取8个字节, 将他看成16进制串与0x3fffffff(30位1)与操作, 即超过30位的忽略处理;
     3)这30位分成6段, 每5位的数字作为字母表的索引取得特定字符, 依次进行获得6位字符串;
     4)总的md5串可以获得4个6位串; 取里面的任意一个就可作为这个长url的短url地址;
     这种算法,虽然会生成4个,但是仍然存在重复几率
   3.随机数算法 
     将 a-zA-Z0-9 这64位取6位组合,可产生500多亿个组合数量.把数字和字符组合做一定的映射,就可以产生唯一的字符串,如第62个组合就是aaaaa9,第63个组合就是aaaaba,再利用洗牌算法，
     把原字符串打乱后保存，那么对应位置的组合字符串就会是无序的组合，
     缺点 ：难免会出现重复冲突
   4。自增id算法
      采用给每个长链接一个ID号，解决了有损压缩和重复的问题
      优点：根据有限的长链生成有限的id，不会重复。
      缺点：自增id暴率在外，有很大的安全风险，造成链接信息泄露；不支持长链接重复查询。
      SnowFlake算法生成，数据库发号器，中间件产生 如：redis的getAndIncrement，zookeeper的sequenceId
   5.采用jdk uuid算法改良
     算法利用62个可打印字符，通过随机生成32位UUID，由于UUID都为十六进制，所以将UUID分成8组，每4个为一组，
     然后通过模62操作，结果作为索引取出字符
     uuid优点:
     32 位 UUID 本身不可能重复，线程安全，完美支持高并发,实现简单，
     本程序暂时使用此算法，不重复，性能好，实现简单，不依赖第三方组件

三. 实现：
   com.sequoia.shorturl.util包
   ShortCodeUtil.java 为实现工具类
   public static String generateShortCode() //产生8位短地址方法
   public static synchronized List<String> getBatchShortCodeList() //批量产生方法

   业务实现接口 ：
   com.sequoia.shorturl.service包下面
   ShortUrlService.java
   业务实现接口实现类
   com.sequoia.shorturl.service.impl包下面
   ShortUrlServiceImpl.java 

四 ，缓存  caffeine缓存  com.sequoia.shorturl.config 包下面
   CacheConfig.java  
   用来存放映射关系  ，防止内存溢出 
   会过期，不访问，或活跃低的数据会过期
   
五 .配置 短域名前缀采用配置文件 application.yml
   配置变量
    #短域名网站前缀
    shorturl:
        prefix: http://www.baidu.com/t/
    可以修改
    
 六  单元测试 test 测试包下面

    四个测试类 com.sequoia.shorturl.service.ShortUrlServiceImplTest.java 测试业务类
             com.sequoia.shorturl.util.ShortCodeUtilTest.java  测试算法类
             com.sequoia.shorturl.controller.ShortUrlControllerTest.java  restFull接口测试类
             com.sequoia.shorturl.SuiteExecuteTests.java 集成测试所有单元测试
      
    
 七 。采用SpringBoot，集成Swagger API文档
    pom.xml引入  集成Swagger 包
    服务启动类 SpringBootShortUrlApplication.java 加入注解 @EnableSwagger2Doc
    生成http://127.0.0.1:8080/swagger-ui.html 接口文档，可以接口测试
   
 八 . Jacoco生成测试报告
     pom.xml 引入 Jacoco 配置
     生成报告mvn命令 集成测试
     mvn clean test -Dtest=SuiteExecuteTests  -Dmaven.test.failure.ignore=trure verify

 九 .安全过滤跟安全控制  暂未全部实现
   1. 短链接虽然方便了传输和记忆，但是由于链接组成的字符个数少，更容易被爆破、猜测攻击，攻击者可以轻松遍历所有字符组成的链接！

     所以不建议使用短链接发送具有私密性的网址，比如说重置密码链接，对一些权限、敏感信息的链接要做好二次鉴权！
   2.对创建短域名接口保护 限制IP的单日请求总数，超过阈值则直接拒绝服务。光限制IP的请求数还不够，因为黑客一般手里有上百万台肉鸡的，IP地址大大的有，
      所以光限制IP作用不大。 可以用一台Redis作为缓存服务器，存储的不是 ID->长网址，而是 长网址->ID，仅存储一天以内的数据，
      用LRU机制进行淘汰。这样，如果黑客大量发同一个长网址过来，直接从缓存服务器里返回短网址即可
   3. 转换查询接口保护， 对无效请求过滤 ，不是短域名前缀的都过滤
      实现 接口过滤，过滤器过滤
      
     com.sequoia.shorturl.filter.ShortUrlFilter.java 拦截过滤
     增加防止xss