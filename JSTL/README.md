# JSTL

- 概念: JSTL, Java Server Page Tag Library, JSP标准标签库, 是Apache组织提供的免费开源的jsp标签  
- 作用: 简化和替换jsp页面上的java代码
- 使用步骤: 
  - 导入jstl相关jar包
  - 引入标签库: `<%@ taglib %>`
  - 使用标签

参见 [CSDN](https://blog.csdn.net/qq_32115439/article/details/54685786)
一般使用JSTL1.1以上版本,现在最新版是JSTL1.2.5, 在Apache Tomcat官网下载JSTL标签库(Taglibs), [下载地址](http://tomcat.apache.org/download-taglibs.cgi)

从README得知: 
如果不使用JSTL1.0标签,可以忽略taglibs-standard-jstlel包,
README没有介绍taglibs-standard-compat包,估计应该是兼容以前版本标签库,
所以一般只需要 taglibs-standard-impl 和 taglibs-standard-spec 两个jar包

下载页面有4个jar包: 

```
- Impl:   taglibs-standard-impl-1.2.5.jar    JSTL实现类库 (下载)
- Spec:   taglibs-standard-spec-1.2.5.jar    JSTL标准接口 (下载)
- EL:     taglibs-standard-jstlel-1.2.5.jar  JSTL1.0标签-EL相关 (可忽略)
- Compat: taglibs-standard-compat-1.2.5.jar  兼容版本 (可忽略)
```

Maven依赖配置如下:

```xml
<dependency>
  <groupId>org.apache.taglibs</groupId>
  <artifactId>taglibs-standard-spec</artifactId>
  <version>1.2.5</version>
</dependency>
<dependency>
  <groupId>org.apache.taglibs</groupId>
  <artifactId>taglibs-standard-impl</artifactId>
  <version>1.2.5</version>
</dependency>
```

把上述两个jar包加到项目,或者复制到Tomcat/lib(所有项目都可以共用,一劳永逸)
