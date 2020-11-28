# 宝塔面板webhook的设置

[这里](https://www.wispx.cn/17.html)有一篇文章,详细记录了在Gitee中设置webhook的方法, 如果在GitHub中设置webhook类似, 区别在于 Content type 需要设置成 `application/json`的形式, 而且最好在Secret中填入access_key的值:

![](images/1.png)

http://47.111.243.90:8888/hook?access_key=SNr33v7GkubnVpYEruq42psZjx1y5X0tOWVgoGG9uunKR53h&param=thinkermaster-ios
