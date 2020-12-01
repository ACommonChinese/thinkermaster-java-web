# jQuery方式

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="js/jquery-3.3.1.min.js"></script>
    <script>
        function ajaxTest() {
            // 使用$.ajax()发送异步请求
            // $.ajax(url, option)
            $.ajax({
                url: "/ajaxServlet", // 请求路径
                type: "POST", // 请求方式
                //OK: data: "username=jerry&age=30", // 请求参数
                data: {"username":"jerry", "age":30},
                success: function (data) {
                    alert(data); // hello: jerry
                    document.getElementById("mydiv").innerHTML = data;
                }
            });
        }
    </script>
</head>
<body>
    <input type="button" value="发送异步请求" onclick="ajaxTest()">
    <div id="mydiv">
    </div>
</body>
</html>
```

遇到的坑: `$.ajax`发送的请求未到servlet, 检查原因是 `WEB-INF/js/jquery-3.3.1.min.js`, 不可把jquery放入WEB-INF下, 拖到外面web目录下, 然后更改引用路径为: `js/jquery-3.3.1.min.js`即可. 

### jQuery Get

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="js/jquery-3.3.1.min.js"></script>
    <script>
        function ajaxTest() {
            $.get("ajaxServlet", {
                "username":"daliu"
            }, function (data) {
                document.getElementById("mydiv").innerHTML = data;
            });
        }
    </script>
</head>
<body>
    <input type="button" value="发送异步请求" onclick="ajaxTest()">
    <div id="mydiv">
    </div>
</body>
</html>
```