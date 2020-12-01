# DOM操作

### 内容操作
- html(): 获取/设置元素的标签体内容
    - `<a><font>内容</font></a>` -- $("a").html() --> `<font>内容</font>`
- text(): 获取/设置元素的标签体纯文本内容 
    - `<a><font>内容</font></a>` --> -- $("a").text() --> `内容`
- val(): 获取/设置元素的value属性值

### 属性操作
1. 通用属性操作
  - attr(): 获取/设置 元素属性
  - removeAttr(): 删除属性
  - prop(): 获取/设置 元素属性
  - removeProp(): 删除属性
  - attr和prop区别:
    - 如果是html固有属性, 建议使用prop
    - 如果操作的是自定义的属性, 建议使用attr
2. class属性操作
  - addClass(): 添回class属性值
  - removeClass(): 删除class属性值
  - toggleClass(): 切换class属性, toggleClass("one"), 如果元素对象上存在class="one", 则将属性one删除掉. 如果不存在, 则添加

#### CURD操作
- append(): 父元素将子元素追回到末尾, 对象1.append(对象2): 将对象2添加到对象1元素内部, 并且在末尾
- prepend(): 父元素将子元素追回到开头, 对象1.append(对象2): 将对象2添加到对象1元素内部, 并且在开头
- appendTo(): 对象1.appendTo(对象2): 将对象1添加到对象2内部, 并且在末尾
- prependTo(): 对象1.prependTo(对象2): 将对象1添加到对象2内部, 并且在开头
- after(): 对象1.after(对象2): 将对象2添加到对象1后面, 兄弟关系
- before(): 对象1.after(对象2): 将对象2添加到对象1前面, 兄弟关系
- insertAfter():  对象1.insertAfter(对象2): 将对象2添加到对象1后面, 兄弟关系
- insertBefore():  对象1.insertBefore(对象2): 将对象2添加到对象1前面, 兄弟关系
- remove(): 对象1.remove(): 将对象1删除掉
- empty(): 对象1.empty(): 清空对象1的所有子元素, 但保留当前对象以及其属性节点

对应代码: `./JQuery/dom操作`