# JS

###引入方式
- 内部js
- 外部js

###数据类型

JS是弱类型语言
- 原始数据类型
  - number: 整数/小数/NaN
  - string: 字符串 "abc" 'a'都是字符串
  - boolean: true/false
  - null: 对象为空的占位符
  - undefined: 未定义的, 如果一个变量没有初始化, 则默认为undefined
- 引用数据类型: 对象

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script>
        let num = 1;
        let num2 = 1.2;
        let num3 = NaN;
        document.write(num + " ----> " + typeof num + "<br>");
        document.write(num2 + " ----> " + typeof num2 + "<br>");
        document.write(num3 + " ----> " + typeof num3 + "<br>");

        let str = "abc";
        let str2 = 'edf';
        document.write(str + " ----> " + typeof str + "<br>");
        document.write(str2 + " ----> " + typeof str2 + "<br>");

        let flag = true;
        document.write(flag + " ----> " + typeof flag + "<br>");
        let flag2 = +true;
        let flag3 = +false;
        document.write(flag2 + " ----> " + typeof flag2 + "<br>");
        document.write(flag3 + " ----> " + typeof flag3 + "<br>");

        let obj = null;
        let obj2 = undefined;
        let obj3;
        let obj4 = {"hello":"world"};
        document.write(obj + " ----> " + typeof obj + "<br>");
        document.write(obj2 + " ----> " + typeof obj2 + "<br>");
        document.write(obj3 + " ----> " + typeof obj3 + "<br>");
        document.write(obj4 + " ----> " + typeof obj4 + "<br>");

        let b = +"abc";
        document.write(b + " ----> " + typeof b + "<br>");
        let b1 = +"123";
        document.write(b1 + " ----> " + typeof b1 + "<br>");
    </script>
</head>
<body>
</body>
</html>
```

```
1 ----> number
1.2 ----> number
NaN ----> number
abc ----> string
edf ----> string
true ----> boolean
1 ----> number
0 ----> number
null ----> object
undefined ----> undefined
undefined ----> undefined
[object Object] ----> object
NaN ----> number
123 ----> number
```

------

###使用JS修改样式 

```js
// 第1种: 使用元素的style属性设置样式
function changeStyle1() {
    let div1 = document.getElementById("div1");
    // 所有标签都有.style
    div1.style.border = "1px solid red";
    div1.style.width = "200px";
    div1.style.textAlign = "left";
    // font-size --> fontSize
    div1.style.fontSize = "30px";
}

// 第2种: 提前定义好选择器的样式
// 通过元素的className属性设置class属性值
function changeStyle2() {
    let div2 = document.getElementById("div2");
    div2.className = "d2";
}
```

```css
.d1 {
    border: 1px solid red;
    width: 100px;
    height: 100px;
}
.d2 {
    border: 1px solid blue;
    width: 200px;
    height: 200px;
    font-size: 40px;
}
```

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="1.js"></script>
    <style> @import "1.css"; </style>
</head>
<body>
    <div id="div1" onclick="changeStyle1()">
        div 1
    </div>

    <div id="div2" onclick="changeStyle2()">
        div 2
    </div>
</body>
</html>
```