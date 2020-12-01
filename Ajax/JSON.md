# JSON

数据由键值对组成
- 键: 一般用双引引号引起来
- 值:
  - 数字
  - 字符串
  - 逻辑值true/false
  - 数组(在括号中)
  - 对象(在花括号中)
- null

获取JSON数据:  
1. json对象.键名
2. json对象["键名"]
3. 数组对象[索引]

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script>
        var person = {
            "name":"daliu",
            "age":32,
            "gender":true
        };
        for (var key in person) {
            // alert(key);
            // name
            // age
            // gender
            alert(key + ":" + person[key]);
        }

        var ps = [
            {"name":"张三","age":23,"gender":true},
            {"name":"李四","age":23,"gender":false},
            {"name":"王五","age":28,"gender":true}
        ];
        for (var i = 0; i < ps.length; i++) {
            var p = ps[i];
            for (var key in p) {
                alert(key + ":" + p[key]);
            }
        }
    </script>
</head>
<body>

</body>
</html>
```

```html
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script>
        var person = {
            "name":"daliu",
            "age":32,
            "gender":true
        };
        for (var key in person) {
            // alert(key);
            // name
            // age
            // gender
            alert(key + ":" + person[key]);
        }

        var ps = [
            {"name":"张三","age":23,"gender":true},
            {"name":"李四","age":23,"gender":false},
            {"name":"王五","age":28,"gender":true}
        ];
        for (var i = 0; i < ps.length; i++) {
            var p = ps[i];
            for (var key in p) {
                alert(key + ":" + p[key]);
            }
        }
    </script>
</head>
```

### JSON数据和Java对象相互转换
常见解析器: Jsonlib, Gson, fastjson, jackson(spring框架内置), jackson示例:    

```java
package cn.com.test;

import cn.com.domain.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.FileWriter;

public class JacksonTest {
    // java对象 --> JSON
    @Test
    public void javaToJSON() throws Exception  {
        Person p = new Person();
        p.setName("张三");
        p.setAge(23);
        p.setGender("男");
        ObjectMapper mapper = new ObjectMapper();
        //
        /**
         * mapper.writeValue(参数1, obj);
         * 参数1:
         *     File: 将obj对象转换为json字符串,并保存到指定的文件中
         *     Writer: 将obj对象转换为json字符串, 并将json数据填充到字符输出流中
         *     OutputStream:将obj对象转换为json字符串,并将json数据填充到字节输出流中
         * writeValueAsString(obj): 将对象转为json字符串
         */
        String json = mapper.writeValueAsString(p);
        System.out.println(json); // {"name":"张三","age":23,"gender":"男"}

        // 将数据写入文件中
        // mapper.writeValue(new File("/Users/liuweizhen/Desktop/testjson/1.json"), p);
        mapper.writeValue(new FileWriter("/Users/liuweizhen/Desktop/testjson/1.json"), p);
    }
}
```

```java
package cn.com.domain;

public class Person {
    private String name;
    private int age;
    private String gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}
```

### 注解
@JsonIgnore: 排除属性
@JsonFormat: 属性值格式化

### java对象和json转换  

```java
package cn.com.test;

import cn.com.domain.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.FileWriter;
import java.util.*;

public class JacksonTest {
    // java对象 --> JSON
    @Test
    public void javaToJSON() throws Exception  {
        Person p = new Person();
        p.setName("张三");
        p.setAge(23);
        p.setGender("男");
        ObjectMapper mapper = new ObjectMapper();
        //
        /**
         * mapper.writeValue(参数1, obj);
         * 参数1:
         *     File: 将obj对象转换为json字符串,并保存到指定的文件中
         *     Writer: 将obj对象转换为json字符串, 并将json数据填充到字符输出流中
         *     OutputStream:将obj对象转换为json字符串,并将json数据填充到字节输出流中
         * writeValueAsString(obj): 将对象转为json字符串
         */
        String json = mapper.writeValueAsString(p);
        System.out.println(json); // {"name":"张三","age":23,"gender":"男"}

        // 将数据写入文件中
        // mapper.writeValue(new File("/Users/liuweizhen/Desktop/testjson/1.json"), p);
        mapper.writeValue(new FileWriter("/Users/liuweizhen/Desktop/testjson/1.json"), p);
    }

    @Test
    public void test2() throws Exception {
        Person p = new Person();
        p.setName("张三");
        p.setAge(23);
        p.setGender("男");
        p.setBirthday(new Date());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(p);
        System.out.println(json);
    }

    @Test
    public void test3() throws Exception {
        Person p1 = new Person();
        p1.setName("张三");
        p1.setAge(23);
        p1.setGender("男");
        p1.setBirthday(new Date());

        Person p2 = new Person();
        p2.setName("李四");
        p2.setAge(33);
        p2.setGender("女");
        p2.setBirthday(new Date());

        Person p3 = new Person();
        p3.setName("王五");
        p3.setAge(22);
        p3.setGender("男");
        p3.setBirthday(new Date());

        List<Person> ps = new ArrayList<>();
        ps.add(p1);
        ps.add(p2);
        ps.add(p3);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(ps);
        System.out.println(json);
    }

    @Test
    public void test4() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", 23);
        map.put("gender", "男");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);
        System.out.println(json);
    }

    // JSON --> Java object
    @Test
    public void test5() throws Exception {
        String json = "{\"gender\":\"男\",\"name\":\"张三\",\"age\":23}";
        ObjectMapper mapper = new ObjectMapper();
        Person person = mapper.readValue(json, Person.class);
        System.out.println(person);
    }
}
```