# 创建表格user
```sql
create table user (
    id int(11) not null auto_increment,
    username varchar(32) not null comment '用户名称',
    birthday datetime default null comment '生日',
    sex char(1) default null comment '性别',
    address varchar(256) default null comment '地址',
    primary key(id)
);
```

# 往表格user中插入数据
```sql
insert into user values(null, '刘威振', '1988-02-01 08:23:12', '1', '河南郑州');
insert into user values(null, '李小龙', '1968-02-01 07:23:12', '1', '香港');
insert into user values(null, '李连杰', '1967-02-01 07:23:22', '1', '山东烟台');
insert into user values(null, '成龙', '1967-02-01 02:23:22', '1', '四川成都');
insert into user values(null, '刘德华', '1937-02-01 01:23:22', '1', '湖北武汉');
```

