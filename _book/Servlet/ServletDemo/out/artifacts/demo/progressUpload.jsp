<%--
  Created by IntelliJ IDEA.
  User: daliu-1118
  Date: 2019/11/23
  Time: 9:17 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<!--
上传文件时，如果不对表单做特别处理，提交表单后会转到另一个页面，造成页面的刷新。
而且新页面显示之前，浏览器会因等待而显示白屏。如果上传文件很大，白屏时间可能会很长。
困此需要对表单做一些特别处理，使提交表单后原页面内容不变，同时显示进度条
-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>文件上传示例</title>
    <style type="text/css">
        body, td, div {
            font-size: 12px;
            font-family: 宋体;
        }
        #progressBar {
            width: 400px;
            height: 12px;
            background: #FFFFFF;
            border: 1px solid #000000;
            padding: 1px;
        }
        #progressBarItem {
            width: 30%;
            height: 100%;
            height: 100%;
            background: #FF0000;
        }
    </style>
</head>
<body>

<iframe name="upload iframe" width="0" height="0">
</iframe>

<form action="servlet/ProgressUploadServlet"
      method="post"
      enctype="multipart/form-data"
      target="upload iframe"
      onsubmit="showStatus()">
    <input type="file" name="file1" style="width: 350px;" /> <br />
    <input type="file" name="file2" style="width: 350px;" /> <br />
    <input type="file" name="file3" style="width: 350px;" /> <br />
    <input type="file" name="file4" style="width: 350px;" /> <br />
    <input type="submit" value="开始上传" id="btnSubmit" />
</form>

<div id="status" style="display: none;">
    上传进度条：
    <div id="progressBar">
        <div id="progressBarItem"></div>
    </div>
    <div id="statusInfo"></div>
</div>

<script type="text/javascript">
    var _finished = true; // 标志是否上传结束
    function $(obj) {
        // 返回指定id的HTML元素
        return document.getElementById(obj);
    }
    // 点击submit提交按钮时触发此方法
    function showStatus() {
        _finished = false;
        $('status').style.display = 'block'; // 显示隐藏的进度条
        $('progressBarItem').style.width = '1%';
        $('btnSubmit').disabled = true; // 提交按钮置灰，防止重复点击
        setTimeout("requestStatus()", 100); // 0.1秒后执行requestStatus()方法
    }
    function requestStatus() {
        if (_finished) return;
        var request = createRequest(); // 获取Ajax请求
        request.open("GET", "servlet/ProgressUploadServlet"); // 设置请求路径
        request.onreadystatechange = function (ev) {
            callback(request); // 请求完毕执行callback
        }
        request.send(null); // 发送请求
        setTimeout("requestStatus()", 100); // 0.1秒后重新请求
    }
    function callback(request) {
        /**
         * https://www.w3school.com.cn/ajax/ajax_xmlhttprequest_onreadystatechange.asp
         * readyState: 存有 XMLHttpRequest 的状态。从 0 到 4 发生变化
         0: 请求未初始化
         1: 服务器连接已建立
         2: 请求已接收
         3: 请求处理中
         4: 请求已完成，且响应已就绪
         */
        if (request.readyState == 4) {
            if (request.status != 200) {
                debug("发生错误。request.status: " + request.status + "");
                return;
            }
            if (request == null) {
                debug("status.jsp 返回值：null");
            } else {
                debug("status.jsp 返回值：" + request.responseText);
            }
            // 处理进度信息，格式：
            // 百分比 || 已完成数 || 文件总长度 || 传输速率 || 已用时间 || 估计总时间 || 估计剩余时间 || 正在上传第几个文件
            var ss = request.responseText.split("||");
            $('progressBarItem').style.width = '' + ss[0] + '%';
            // 格式：已完成百分比||已完成数(M)||文件总长度(M)||传输速率(K)已用时间(s)||估计总时间(s)||估计剩余时间(s)||正在上传第几个文件
            $('statusInfo').innerHTML = '已完成百分比：' + ss[0] + '% <br />'
                                        +'已完成数(M): ' + ss[1] + '<br />'
                                        + '文件总长度(M): ' + ss[2] + '<br />'
                                        + '传输速率(K): ' + ss[3] + '<br />'
                                        + '已用时间(s): ' + ss[4] + '<br />'
                                        + '估计总时间(s): ' + ss[5] + '<br />'
                                        + '估计剩余时间(s): ' + ss[6] + '<br />'
                                        + '正在上传第几个文件: ' + ss[7];
            if (ss[1] == ss[2]) {
                _finished = true;
                return;
            }
            $('statusInfo').innerHTML += "<br/><br/><br/>上传完成。";
            $('btnSubmit').disabled = false;
        }
    }
    function debug(obj) {
        var div = document.createElement("div");
        div.innerHTML = "[debug]: " + obj;
        document.body.appendChild(div);
    }
    function createRequest() {
        if (window.XMLHttpRequest) {
            return new XMLHttpRequest();
        } else { // ID浏览器
            try {
                return new ActiveXObject("Msxml2.XMLHTTP");
            } catch (ex) {
                return new ActiveXObject("Microsoft.XMLHTTP");
            }
        }
        return null;
    }

</script>
</body>
</html>
