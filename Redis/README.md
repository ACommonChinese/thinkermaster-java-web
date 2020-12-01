# Redis

[runoob](https://www.runoob.com/redis/redis-tutorial.html)

Redis是一款高性能的NoSQL系列的非关系型数据库软件, REmote DIctionary Server(Redis) 是一个由Salvatore Sanfilippo写的key-value存储系统, 开源, 使用ANSI C语言编写

常见的非关系型(NoSQL)数据库: redis, hbase, mogo, 特点:
- 非关系型数据库一般不会维护表示关系型的表, 比如直接通过key-value的形式存储数据, 数据之间没有关联关系
- 数据存储在内存中

主流的NOSQL产品
- 键值(key-value)存储数据库
    产品: Redis, Tokyo Cabinet/Tyrant, Voldemort, Berkeley DB
    应用: 内容缓存, 主要用于处理大量数据的高访问负载
    数据模型: 一系列键值对
    优: 快速查询
    劣: 存储的数据缺少结构化
- 列存储数据库
    产品: Cassandra, HBase, Riak
    应用: 分布式的文件系统
    数据模型: 以列簇式存储, 将同一列数据存在一起
    优: 查找速度快, 可扩展强, 易进行分布式扩展
    劣: 功能局限
- 文档型数据库
    产品: CouchDB, MongoDB
    应用: Web应用(类似key-value, value是结构化的)
    数据模型: 一系列键值对
    优: 数据结构要求不严格
    劣: 查询性能不高, 缺乏统一的查询语法
- 图形(Graph)数据库
    产品: Neo4J, InfoGrid, Infinite Graph
    应用: 社交网络
    数据模型: 图
    优: 利用图结构相关算法
    劣: 需要对整个图计算才能得出结果, 不易做分布式的集群方案

### Redis  

Redis是用C语言开发的一个开源的高性能键值对（key-value）数据库，官方提供测试数据，50个并发执行100000个请求,读的速度是110000次/s,写的速度是81000次/s ，且Redis通过提供多种键值数据类型来适应不同场景下的存储需求，目前为止Redis支持的键值数据类型如下：
1) 字符串类型 string
2) 哈希类型 hash
3) 列表类型 list
4) 集合类型 set
5) 有序集合类型 sortedset

### redis的应用场景
•	缓存（数据查询、短连接、新闻内容、商品内容等等）
•	聊天室的在线好友列表
•	任务队列。（秒杀、抢购、12306等等）
•	应用排行榜
•	网站访问统计
•	数据过期处理（可以精确到毫秒)
•	分布式集群架构中的session分离