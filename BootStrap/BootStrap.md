# BootStrap

[BootStrap官网](https://www.bootcss.com/)
[BootStrap中文网](https://www.bootcss.com/)
[BootStrap菜鸟教程](https://www.runoob.com/bootstrap/bootstrap-tutorial.html)  

BootStrap是一个前端开发框架, 它主要包含了BootStrap CSS , BootStrap 布局组件以及一些插件  

1. 定义了很多CSS样式和JS插件
2. 响应式布局, 即同一套页面可以兼容不同分率辨的设备, 根据不同的分辨率自动调整大小. 比如 `https://www.apple.com` 可以使用Chrome的开发者工具, 选择设备验证, 而淘宝针对PC和手机做了两套, 分别是`taobao.com`和`m.taobao.com`

-------

###配置

1. 下载: 使用BootStrap需要先[下载](https://v3.bootcss.com/getting-started/#download)
2. 导入: 在项目中把下载的BootStrap复制进去
3. 使用: 在使用时引入必要的文件 

基本模板 

```html
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Bootstrap 101 Template</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    <h1>你好，世界！</h1>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
  </body>
</html>
```

```html
<!--如果把BootStrap下面css|js|fonts复制到了工程中, 简化引入:-->
<!--本示例把jQuery也导入了工程中-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Bootstrap 101 Template</title>
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <!--<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>-->
		<script src="jquery/jquery-3.2.1.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
</head>
```

