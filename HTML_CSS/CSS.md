# CSS

Cascading Style Sheet 层叠样式表, 层叠的意思是多个样式可以作用在同一个html元素上.  

根据CSS与html结合方式, 可分为三种方式: 
1. 内联样式: 在标签内使用style属性指定css代码 
2. 内部样式
3. 外部样式

作用范围 1 < 2 < 3  
优先级 3 < 2 < 1  
常用方式 1 < 2 < 3

###内联样式
```
`<div style="color:red">red text here</div>`
```

###内部样式
```html
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        .blueScheme{
            color: blue;
        }
    </style>
</head>
<body>
    <div class="blueScheme">red text here</div>
</body>
```

###外部样式

```html
<!--css/1.css-->
div {
    color: green;
}
```

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="css/1.css">
    <!--也可以如下引入外部css资源文件:
    <style>
    	  @import "css/1.css";
	  </style>
    -->
</head>
<body>
    <div>red text here</div>
</body>
</html>
```
