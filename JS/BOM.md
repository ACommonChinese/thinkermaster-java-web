# BOM

Browser Object Model: 浏览器对象模型, 它将浏览器的各个部分封装成对象  

- navigator 浏览器对象
- window 窗口对象, window.document可直接写document
- location 地址
- hitory 历史记录
- screen 显示器屏幕对象  

[W3C](https://www.w3school.com.cn/jsref/dom_obj_window.asp)

------------------

### window 
- 弹框
    1. alert(): 显示带有一段消息和一个确认按钮的警告框
    2. confirm(): 显示带有一段消息以及确认按钮和取消按钮的对话框
    3. prompt(): 显示可提示用户输入的对话框    
- 打开关闭
    - open()
    - close()
- 定时器
    - setTimeout() 在指定的毫秒数后调用函数或计算表达式
    - setInterval()  按照指定的周期(毫秒)来调用函数或计算表达式
    - clearTimeout() 取消由setTimeout()方法设置的timeout

```js
function showAlert() {
    alert("alert");
}

function showConfirm() {
    let flag = confirm("确认要退出吗?");
    alert(flag);
}

function showPrompt() {
    // prompt();
    let result = prompt("请输入用户名");
    alert("用户输入的是: " + result);
}

let openWindow;

function testOpen() {
    // open();
    openWindow = open("https://cn.bing.com/"); // 默认新开一个tab
}

function testClose() {
    // close(); // 默认close当前窗口, 谁调用close(), 关闭的就是谁
    openWindow.close();
}

function testSetTimeout() {
    /**
     * 参数
     * 1. js代码或方法对象
     * 2. 毫秒值, 2000即2秒
     */
    setTimeout(fun, 2000);
}

function fun() {
    alert('boom');
}

function testSetInterval() {
    setInterval(fun, 2000);
}

function testClearTimeout() {
    let handler = setTimeout(fun, 2000);
    clearTimeout(handler);

    let handler2 = setInterval(fun, 1000);
    clearTimeout(handler2);
}
```

------------------

###location  

- reload() 重新加载当前文档, 刷新
- href 设置或返回完整的路径  

5秒后跳转到百度
```js
!function testReload() {
    setInterval(changeTime, 1000);
}();

var second = 5;
var t = document.getElementById("time");

function changeTime() {
    --second;

    if (second <= 0) {
        location.href = "https://www.baidu.com";
    }
    else {
        t.innerHTML = second + "";
    }
}
```

------------------

###history
- length: 当前窗口window所访问过的历史记录列表数量
- back(): 加载history列表中的前一个URL
- forward(): 加载history列表中的下一个URL
- go(): 加载history列表中的某个具体页面  


