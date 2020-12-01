<%--
  Created by IntelliJ IDEA.
  User: daliu-1118
  Date: 2019/11/15
  Time: 2:01 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>带进度条的文件上传</title>
    <style type="text/css">
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
            background: #FF0000;
        }
    </style>
</head>
<body>
    <div id="progressBar">
        <div id="progressBarItem"></div>
    </div>
</body>
</html>
