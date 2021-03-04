ibatis中如何使用#来进行模糊查询  https://www.cnblogs.com/gaojing/archive/2012/11/22/2844932.html
无效的方法：
select  *  from table1 where name like '%#name#%'
两种有效的方法： 1) 使用$代替#。此种方法就是去掉了类型检查，使用字符串连接，不过可能会有sql注入风险。
select  *  from table1 where name like '%$name$%'
 2) 使用连接符。不过不同的数据库中方式不同。
mysql： 
select  *  from table1 where name like concat('%', #name#, '%')
oracle:
select  *  from table1 where name like '%' || #name# || '%'
sql server:
select  *  from table1 where name like '%' + #name# + '%'
mybatis的话类似##变为#{}
