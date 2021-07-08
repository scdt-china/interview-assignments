# Getting Started

### Reference Documentation
设计思路与假设
1.短域名生成使用的自增序列，然后将10进制自增序列Sequence转为62进制（a-z、A-Z 和 0-9 共 62 个字符）的字符串，这里限制了最多转换为6位。
  同时为了防止被用户猜测遍历，做了如下两步优化：
   （1）打乱生成62进制BASE字符串  eg: private static final String BASE = "LxF2vtuc3mW5AoBEIJ1wKfM4XOqPQR7ST8VbYZ6aH9dCDegNhijk0lnGUprsyz"
   （2）加入随机码，转换成的62进制字符串末尾加入两位随机码。
2.自增序列示例中使用原子类AtomicLong下面的getAndIncrement()方法，可以保证线程安全，真实生产环境可以使用数据库自增序列来实现。
3.数据存储使用一个ConcurrentHashMap存储,为防止内存溢出，进行了限元素个数制。真实生产环境数据需要存在数据库，要预估好业务规模增长情况，进行分库分表，同时为了为了提高性能，可以考虑加入读缓存和写缓存。
4.运行环境为idea+jdk8+maven3.2.5



