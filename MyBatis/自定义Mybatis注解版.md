# 自定义Mybatis注解版

自定义Mybatis注解版和上一篇"自定义Mybatis"基本一致, 只需简单修改如下:

### 1. 修改resources/SqlMapConfig.xml: 

```xml
<mappers>
    <!-- <mapper resource="com/daliu/dao/IUserDao.xml"> -->
    <mapper class="com.daliu.dao.IUserDao" />
</mappers>
```

### 2. 修改Dao接口, 加annotation标注

```java
// -- com.daliu.dao.IUserDao.java --
@Select("select * from user")
List<User> findAll();
```

### 3. 新建注解类

```java
// -- com.daliu.mybatis.annotations.Select.java --
package com.daliu.mybatis.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Select {
    String value();
}
```

### 4. 打开上一篇中XMLConfigBuilder中注释解析注解的代码 

```java
// -- com.daliu.util.XMLConfigBuilder.java --
private static Map<String,Mapper> loadMapperAnnotation(String daoClassPath)throws Exception{
    //定义返回值对象
    Map<String,Mapper> mappers = new HashMap<String, Mapper>();

    //1.得到dao接口的字节码对象
    Class daoClass = Class.forName(daoClassPath);
    //2.得到dao接口中的方法数组
    Method[] methods = daoClass.getMethods();
    //3.遍历Method数组
    for(Method method : methods){
        //取出每一个方法，判断是否有select注解
        boolean isAnnotated = method.isAnnotationPresent(Select.class);
        if(isAnnotated){
            //创建Mapper对象
            Mapper mapper = new Mapper();
            //取出注解的value属性值
            Select selectAnno = method.getAnnotation(Select.class);
            String queryString = selectAnno.value();
            mapper.setQueryString(queryString);
            //获取当前方法的返回值，还要求必须带有泛型信息
            Type type = method.getGenericReturnType();//List<User>
            //判断type是不是参数化的类型
            if(type instanceof ParameterizedType){
                //强转
                ParameterizedType ptype = (ParameterizedType)type;
                //得到参数化类型中的实际类型参数
                Type[] types = ptype.getActualTypeArguments();
                //取出第一个
                Class domainClass = (Class)types[0];
                //获取domainClass的类名
                String resultType = domainClass.getName();
                //给Mapper赋值
                mapper.setResultType(resultType);
            }
            //组装key的信息
            //获取方法的名称
            String methodName = method.getName();
            String className = method.getDeclaringClass().getName();
            String key = className+"."+methodName;
            //给map赋值
            mappers.put(key,mapper);
        }
    }
    return mappers;
}
```


