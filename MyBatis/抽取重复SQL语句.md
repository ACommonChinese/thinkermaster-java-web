# 抽取重复SQL语句

**resources/com/daliu/dao/IUserDao.xml**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.daliu.dao.IUserDao">
    <!--声明复用的sql语句, 也可放在后面书写-->
		<sql id="defaultUser">
        select * from user
    </sql>
    
    <select id="findAll" resultType="com.daliu.domain.User">
        <!--select * from user-->
        <include refid="defaultUser"></include>
    </select>

    <select id="findUserInIds" resultMap="userMap" parameterMap="queryVo">
        <include refid="defaultUser"></include>
        <where>
            <if test="ids != null and ids.size()>0">
                <foreach collection="ids" open="and id in (" close=")" item="uid" separator=",">
                    #{uid}
                </foreach>
            </if>
        </where>
    </select>
    ...
</mapper>
```
