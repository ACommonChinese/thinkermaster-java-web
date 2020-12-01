# 全局CSS样式

BootStrap定义的CSS样式非常多, 一般即用即查: [全局CSS](https://v3.bootcss.com/css/)  

一些常用CSS样式:  

### 排版  
Bootstrap 将全局 font-size 设置为 14px，line-height 设置为 1.428。这些属性直接赋予 <body> 元素和所有段落元素。另外，<p> （段落）元素还被设置了等于 1/2 行高（即 10px）的底部外边距（margin） 



###按钮
```html
<!-- Standard button -->
<button type="button" class="btn btn-default">（默认样式）Default</button>

<!-- Provides extra visual weight and identifies the primary action in a set of buttons -->
<button type="button" class="btn btn-primary">（首选项）Primary</button>

<!-- Indicates a successful or positive action -->
<button type="button" class="btn btn-success">（成功）Success</button>

<!-- Contextual button for informational alert messages -->
<button type="button" class="btn btn-info">（一般信息）Info</button>

<!-- Indicates caution should be taken with this action -->
<button type="button" class="btn btn-warning">（警告）Warning</button>

<!-- Indicates a dangerous or potentially negative action -->
<button type="button" class="btn btn-danger">（危险）Danger</button>

<!-- Deemphasize a button by making it look like a link while maintaining button behavior -->
<button type="button" class="btn btn-link">（链接）Link</button>
```

----------------------------------------------------------------

### 图片

```html
<!--img-responsive: 屏幕在任意尺寸占100%-->
<img src="img/banner_1.jpg" class="img-responsive" alt="Responsive image"><br>
<img src="img/01.jpg" class="img-responsive img-rounded"><br>
<img src="img/02.jpg" class="img-responsive img-circle"><br>
<img src="img/03.jpg" class="img-responsive img-thumbnail"><br> <!--相框形-->
```

----------------------------------------------------------------

### 表格

[W3C](https://v3.bootcss.com/css/#tables)

```html
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>大刘测试</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="jquery/1.12.4/jquery-3.2.1.min"></script>
    <script src="js/bootstrap.min.js"></script>

    <link href="1.css" rel="stylesheet">
</head>
<body>
<table class="table table-bordered table-hover">
    <tr>
        <th class="success">编号</th>
        <th class="success">姓名</th>
        <th class="success">年龄</th>
    </tr>
    <tr>
        <td class="success">001</td>
        <td class="success">张三</td>
        <td class="success">20</td>
    </tr>
    <tr>
        <td class="success">002</td>
        <td class="success">李四</td>
        <td class="success">23</td>
    </tr>
    <tr>
        <td class="success">003</td>
        <td class="success">大刘</td>
        <td class="success">32</td>
    </tr>
</table>
</body>
</html>
```

------

### 一些示例 

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>大刘测试</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="jquery/1.12.4/jquery-3.2.1.min"></script>
    <script src="js/bootstrap.min.js"></script>
    <link href="1.css" rel="stylesheet">
</head>
<body>
<div class="container">
    0. <p class="lead">This is the lead 段落</p>
    1. You can use the mark tag to <mark>highlight</mark> text.<br>
    2. You can use the del tag to <del>del</del> text.<br>
    3. <s>This line of text is meant to be treated as no longer accurate.</s><br>
    4. <ins>This line of text is meant to be treated as an addition to the document.</ins><br>
    5. <u>This line of text will render as underlined</u><br>
    6. <small>This line of text is meant to be treated as fine print.</small><br>
    7. <strong>rendered as bold text</strong><br>
    8. <em>rendered as italicized text</em><br>
    9. <p class="text-left">Left aligned text.</p>
    10. <p class="text-center">Center aligned text.</p>
    11. <p class="text-right">Right aligned text.</p>
    12. <p class="text-justify">Justified text.</p>
    13. <p class="text-nowrap">No wrap text.</p>
    14. <p class="text-lowercase">Lowercased text.</p>
    15. <p class="text-uppercase">Uppercased text.</p>
    16. <p class="text-capitalize">Capitalized text.</p>
    17. An of the word attribute is <abbr title="attribute">attr</abbr><br>
    18. <abbr title="HyperText Markup Language" class="initialism">HTML</abbr> is the best thing since sliced bread.<br>
    19. <strong>地址</strong><br>
    <address>
        <strong>Twitter, Inc.</strong><br>
        795 Folsom Ave, Suite 600<br>
        San Francisco, CA 94107<br>
        <abbr title="Phone">P:</abbr> (123) 456-7890
    </address>

    <address>
        <strong>Full Name</strong><br>
        <a href="mailto:#">first.last@example.com</a>
    </address>
    20. 引用 blockquote
    <blockquote>
        <p>孔子站在河边上说: 时间一天天过的很快</p>
    </blockquote>
    21. blockquote
    <blockquote>
        <p>时间一天天过的很快</p>
        <footer>出自孔子 <cite title="Source Title">论语</cite></footer>
    </blockquote>
    22. blockquote
    <blockquote class="blockquote-reverse">
        <p>右对齐blockquote-reverse 孔子吃完饭站在河边上说: 时间一天天过的真快</p>
    </blockquote>
    23. 无样式列表<br>
    <ul class="list-unstyled">
        <li>唐僧</li>
        <li>孙悟空</li>
        <li>大刘</li>
        <li>朱八戒</li>
        <li>沙和尚</li>
    </ul>
    24. 在同一行的无样式列表, 即内联样式的列表<br>
    <ul class="list-inline">
        <li>唐僧</li>
        <li>孙悟空</li>
        <li>大刘</li>
        <li>朱八戒</li>
        <li>沙和尚</li>
    </ul>
    25. 描述性列表<br>
    <dl>
        <dt>计算机</dt>
        <dd>用来计算的仪器 ... ...</dd>
        <dt>显示器</dt>
        <dd>以视觉方式显示信息的装置 ... ...</dd>
    </dl>
    26.水平排列的描述<br>
    <dl class="dl-horizontal">
        <dt>作者</dt>
        <dd>孔子</dd>
        <dt>名言</dt>
        <dd>时间过类真快啊</dd>
        <dt>联系方式</dt>
        <dd>地下3层万科天空之城18弄301室</dd>
    </dl>
    27. 内联代码&lt;code&gt;标签<br>
    <!--注: &lt为左括号，&gt为右括号-->
    For example, <code>&lt;section&gt;</code> should be wrapped as inline.<br>
    28. 用户输入&lt;kbd&gt;标签<br>
    To switch directories, type <kbd>cd</kbd> followed by the name of the directory.<br>
    To edit settings, press <kbd>ctrl + ,</kbd><br>
    28. 代码块&lt;pre&gt;标签<br>
    <pre>
&lt;p&gt;Sample text here...&lt;/p&gt;
public static void main(String[] args) {
    System.out.println("Hello World! Hello China! Hello DaLiu!");
}</pre><br>
    29. 变量<br>
    <var>y</var> = <var>m</var><var>x</var> + <var>b</var><br>
    30. 程序输出<br>
    <samp>This text is meant to be treated as sample output from a computer program.</samp><br>
    31. 表格
    <table class="table">
        <caption>Optional table caption.</caption>
        <thead>
        <tr>
            <td class="caption">序号</td>
            <td class="caption">姓名</td>
            <td class="caption">分数</td>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th>1</th>
            <th>李小龙</th>
            <th>98</th>
        </tr>
        <tr>
            <td>2</td>
            <td>大刘</td>
            <td>92</td>
        </tr>
        <tr>
            <td>3</td>
            <td>屠呦呦</td>
            <td>100</td>
        </tr>
        </tbody>
    </table>
    32. 条纹状表格
    <table class="table table-striped">
        <thead>
        <tr>
            <th>#</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Username</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th scope="row">1</th>
            <td>Mark</td>
            <td>Otto</td>
            <td>@mdo</td>
        </tr>
        <tr>
            <th scope="row">2</th>
            <td>Jacob</td>
            <td>Thornton</td>
            <td>@fat</td>
        </tr>
        <tr>
            <th scope="row">3</th>
            <td>Larry</td>
            <td>the Bird</td>
            <td>@twitter</td>
        </tr>
        </tbody>
    </table>
    33. 带边框表格
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>#</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Username</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th scope="row">1</th>
            <td>Mark</td>
            <td>Otto</td>
            <td>@mdo</td>
        </tr>
        <tr>
            <th scope="row">2</th>
            <td>Jacob</td>
            <td>Thornton</td>
            <td>@fat</td>
        </tr>
        </tbody>
    </table>
    34. 表格, 鼠标悬停效果
    <table class="table table-hover">
        <thead>
        <tr>
            <th>#</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Username</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th scope="row">1</th>
            <td>Mark</td>
            <td>Otto</td>
            <td>@mdo</td>
        </tr>
        <tr>
            <th scope="row">2</th>
            <td>Jacob</td>
            <td>Thornton</td>
            <td>@fat</td>
        </tr>
        </tbody>
    </table>
    35. 紧缩表格
    <table class="table table-condensed">
        <thead>
        <tr>
            <th>#</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Username</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th scope="row">1</th>
            <td>Mark</td>
            <td>Otto</td>
            <td>@mdo</td>
        </tr>
        <tr>
            <th scope="row">2</th>
            <td>Jacob</td>
            <td>Thornton</td>
            <td>@fat</td>
        </tr>
        </tbody>
    </table>
    36. 为表格添加状态
    <table class="table table-bordered table-striped">
        <colgroup>
            <col class="col-xs-1">
            <col class="col-xs-7">
        </colgroup>
        <thead>
        <tr>
            <th>Class</th>
            <th>描述</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th scope="row">
                <code>.active</code>
            </th>
            <td>鼠标悬停在行或单元格上时所设置的颜色</td>
        </tr>
        <tr>
            <th scope="row">
                <code>.success</code>
            </th>
            <td>标识成功或积极的动作</td>
        </tr>
        <tr>
            <th scope="row">
                <code>.info</code>
            </th>
            <td>标识普通的提示信息或动作</td>
        </tr>
        <tr>
            <th scope="row">
                <code>.warning</code>
            </th>
            <td>标识警告或需要用户注意</td>
        </tr>
        <tr>
            <th scope="row">
                <code>.danger</code>
            </th>
            <td>标识危险或潜在的带来负面影响的动作</td>
        </tr>
        </tbody>
    </table>
    <table class="table">
        <thead>
        <tr>
            <th>#</th>
            <th>Column heading</th>
            <th>Column heading</th>
            <th>Column heading</th>
        </tr>
        </thead>
        <tbody>
        <tr class="active">
            <th scope="row">1</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr>
            <th scope="row">2</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr class="success">
            <th scope="row">3</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr>
            <th scope="row">4</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr class="info">
            <th scope="row">5</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr>
            <th scope="row">6</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr class="warning">
            <th scope="row">7</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr>
            <th scope="row">8</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr class="danger">
            <th scope="row">9</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        </tbody>
    </table>
    37. 响应式表格
    将任何 .table 元素包裹在 .table-responsive 元素内，即可创建响应式表格，其会在小屏幕设备上（小于768px）水平滚动。当屏幕大于 768px 宽度时，水平滚动条消失
    <div class="table-responsive">
        <table class="table">
            <thead>
            <tr>
                <th>#</th>
                <th>Table heading</th>
                <th>Table heading</th>
                <th>Table heading</th>
                <th>Table heading</th>
                <th>Table heading</th>
                <th>Table heading</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <th scope="row">1</th>
                <td>Table cell</td>
                <td>Table cell</td>
                <td>Table cell</td>
                <td>Table cell</td>
                <td>Table cell</td>
                <td>Table cell</td>
            </tr>
            <tr>
                <th scope="row">2</th>
                <td>Table cell</td>
                <td>Table cell</td>
                <td>Table cell</td>
                <td>Table cell</td>
                <td>Table cell</td>
                <td>Table cell</td>
            </tr>
            <tr>
                <th scope="row">3</th>
                <td>Table cell</td>
                <td>Table cell</td>
                <td>Table cell</td>
                <td>Table cell</td>
                <td>Table cell</td>
                <td>Table cell</td>
            </tr>
            </tbody>
        </table>
    </div><!-- /.table-responsive -->
    38. 表单<br>
    所有设置了 <code>.form-control</code> 类的 <code>&lt;input&gt;</code>、<code>&lt;textarea&gt;</code> 和 <code>&lt;select&gt;</code> 元素都将被默认设置宽度属性为 <code>width: 100%;</code>。 将 <code>label</code> 元素和前面提到的控件包裹在 <code>.form-group</code> 中可以获得最好的排列
    <form>
        <div class="form-group">
            <label for="exampleInputEmail1">Email address</label>
            <input type="email" class="form-control" id="exampleInputEmail1" placeholder="Email">
        </div>
        <div class="form-group">
            <label for="exampleInputPassword1">Password</label>
            <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
        </div>
        <div class="form-group">
            <label for="exampleInputFile">File input</label>
            <input type="file" id="exampleInputFile">
            <p class="help-block">Example block-level help text here.</p>
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox"> Check me out
            </label>
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
    </form>
    39. 内联表单<br>
    <form class="form-inline">
        <div class="form-group">
            <label class="sr-only" for="exampleInputEmail3">Email address</label>
            <input type="email" class="form-control" id="exampleInputEmail3" placeholder="Email">
        </div>
        <div class="form-group">
            <label class="sr-only" for="exampleInputPassword3">Password</label>
            <input type="password" class="form-control" id="exampleInputPassword3" placeholder="Password">
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox"> Remember me
            </label>
        </div>
        <button type="submit" class="btn btn-default">Sign in</button>
    </form>
    40. 水平排列的表单
    <form class="form-horizontal">
        <div class="form-group">
            <label for="inputEmail3" class="col-sm-2 control-label">Email</label>
            <div class="col-sm-10">
                <input type="email" class="form-control" id="inputEmail3" placeholder="Email">
            </div>
        </div>
        <div class="form-group">
            <label for="inputPassword3" class="col-sm-2 control-label">Password</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="inputPassword3" placeholder="Password">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <div class="checkbox">
                    <label>
                        <input type="checkbox"> Remember me
                    </label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Sign in</button>
            </div>
        </div>
    </form>
    41. <input type="text" class="form-control" placeholder="Text input"><br>
    42. <textarea class="form-control" rows="3"></textarea><br>
    43.
    <div class="checkbox">
    <label>
        <input type="checkbox" value="">
        Option one is this and that&mdash;be sure to include why it's great
    </label>
    </div>
    <div class="checkbox disabled">
        <label>
            <input type="checkbox" value="" disabled>
            Option two is disabled
        </label>
    </div>

    <div class="radio">
        <label>
            <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>
            Option one is this and that&mdash;be sure to include why it's great
        </label>
    </div>
    <div class="radio">
        <label>
            <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">
            Option two can be something else and selecting it will deselect option one
        </label>
    </div>
    <div class="radio disabled">
        <label>
            <input type="radio" name="optionsRadios" id="optionsRadios3" value="option3" disabled>
            Option three is disabled
        </label>
    </div>
    44. 内联单选和多选框<br>
    <label class="checkbox-inline">
        <input type="checkbox" id="inlineCheckbox1" value="option1"> 1
    </label>
    <label class="checkbox-inline">
        <input type="checkbox" id="inlineCheckbox2" value="option2"> 2
    </label>
    <label class="checkbox-inline">
        <input type="checkbox" id="inlineCheckbox3" value="option3"> 3
    </label>

    <label class="radio-inline">
        <input type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"> 1
    </label>
    <label class="radio-inline">
        <input type="radio" name="inlineRadioOptions" id="inlineRadio2" value="option2"> 2
    </label>
    <label class="radio-inline">
        <input type="radio" name="inlineRadioOptions" id="inlineRadio3" value="option3"> 3
    </label><br>
    45. 下拉列表<br>
    <select class="form-control">
        <option>1</option>
        <option>2</option>
        <option>3</option>
        <option>4</option>
        <option>5</option>
    </select>
    46. 可多选下拉列表<br>
    <select multiple class="form-control">
        <option>1</option>
        <option>2</option>
        <option>3</option>
        <option>4</option>
        <option>5</option>
    </select>
    <br>
    47. 静态控制<br>
    如果需要在表单中将一行纯文本和 label 元素放置于同一行，为 <p> 元素添加 .form-control-static 类即可。
    <form class="form-horizontal">
    <div class="form-group">
        <label class="col-sm-2 control-label">Email</label>
        <div class="col-sm-10">
            <p class="form-control-static">email@example.com</p>
        </div>
    </div>
    <div class="form-group">
        <label for="inputPassword" class="col-sm-2 control-label">Password</label>
        <div class="col-sm-10">
            <input type="password" class="form-control" id="inputPassword" placeholder="Password">
        </div>
    </div>
    </form>
</div>
...... 
</body>
</html>
```