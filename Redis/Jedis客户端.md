# Jedis客户端

Jedis是一款java操作redis数据库的工具.  

操作时,需要把jedis-x.x.x.jar导入工程即可 

入门示例:  

```java
package cn.daliu.jedis.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class JedisTest {
    @Test
    public void test1() {
        // 1. 获取连接
        Jedis jedis = new Jedis("localhost", 6379);
        // 2. 操作
        jedis.set("username", "zhangsan");
        // 3. 关闭连接
        jedis.close();
    }

    @Test
    // string
    public void test2() {
        // 注: 如果使用空参构造, 则默认值是"localhost"和6379
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.set("username", "zhangsan");
        String username = jedis.get("username");
        System.out.println(username);
        // 指定过期时间的 key value
        // key - seconds - value
        // 下面存储的数据20秒后过期
        jedis.setex("activecode", 20, "hehe");
        jedis.close();
    }

    @Test
    // hash
    public void test3() {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.hset("user", "name", "lisi");
        jedis.hset("user", "age", "23");
        jedis.hset("user", "gender", "male");
        String name = jedis.hget("user", "name");
        System.out.println(name);

        // get all
        Map<String, String> user = jedis.hgetAll("user");
        Set<String> keySet = user.keySet();
        for (String key : keySet) {
            String value = user.get(key);
            System.out.println(key + ":" + value);
        }
        jedis.close();
    }

    @Test
    // list
    public void test4() {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.lpush("mylist", "a", "b", "c");
        jedis.rpush("mylist", "a", "b", "c");
        List<String> mylist = jedis.lrange("mylist", 0, -1);
        System.out.println(mylist); // [c, b, a, a, b, c]
        String element = jedis.lpop("mylist");
        System.out.println(element); // c
        System.out.println(jedis.rpop("mylist")); // c

        List<String> mylist2 = jedis.lrange("mylist", 0, -1);
        System.out.println(mylist2); // [b, a, a, b]
        jedis.close();
    }

    @Test
    // set
    public void test5() {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.sadd("myset", "java", "php", "c++");
        Set<String> myset = jedis.smembers("myset");
        System.out.println(myset); // [c++, java, php]
        jedis.close();
    }

    @Test
    // sortedset
    public void test6() {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.zadd("mysortedset", 3, "亚瑟");
        jedis.zadd("mysortedset", 30, "后翌");
        jedis.zadd("mysortedset", 25, "孙悟空");
        Set<String> mysortedset = jedis.zrange("mysortedset", 0, -1);
        System.out.println(mysortedset); // [亚瑟, 孙悟空, 后翌]
        jedis.close();
    }
}
```

