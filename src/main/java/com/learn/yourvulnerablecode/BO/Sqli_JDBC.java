package com.learn.yourvulnerablecode.BO;

import com.alibaba.fastjson.JSONObject;

import java.sql.*;
//https://blog.csdn.net/QZC295919009/article/details/43059781
//https://www.jianshu.com/p/82009d13847d     有关Statement与PreparedStatement区别
//JDBC的操作数据库的方式很古朴，就是方法直接干，这里封装了个类，来将这些方法单拎出来
public  class Sqli_JDBC
{
    Connection conn = null;   //这个变量是连接数据库的
    Statement stmt = null;    //Statement是静态拼接一个sql字符串直接喂给数据库，数据库进行编译，然后查询，如果有用户可控的参数直接拼接进入则存在sql注入风险
    PreparedStatement pstmt= null;   //预编译方式，通过占位符来把入参加入sql语句中，安全

    //构造函数，来连接数据库的，连接数据库就是初始化初始化Connection conn和Statement stmt
    public Sqli_JDBC( ) throws ClassNotFoundException, SQLException {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        conn = DriverManager.getConnection("jdbc:hsqldb:mem://127.0.0.1:9001/sqlidb", "sa", "");
        //JDBC查询的基本操作，连数据库，这个应该是可以确定JDBC的关键特征
        stmt = conn.createStatement();
    }

    //这个函数是来返回查询到的结果集ResultSet的
    public static  String log_table(ResultSet rs) throws SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        JSONObject res=new JSONObject();
        int rowscount=0;
        while (rs.next()) {

            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = rs.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
                res.put(rsmd.getColumnName(i), columnValue);
            }
            rowscount++;
            System.out.println("");
        }
        //rs.beforeFirst();  这个方法也不能用java.sql.SQLFeatureNotSupportedException: feature not supported
        //rs.previous()  也不支持，也就是说hsqldb游标只能一路向下
        return res.toJSONString();
    }

    //executeUpdate函数的漏洞版
    public int vulnerable_jdbc_select_1(String name) throws SQLException {
        String sql="select * from t_user where name='"+name+"' ";
        //executeUpdate主要本意用于执行 INSERT、UPDATE 或 DELETE 语句以及 SQL DDL（数据定义语言）语句。
        // INSERT、UPDATE 或 DELETE 语句的效果是修改表中零行或多行中的一列或多列。executeUpdate 的返回值是一个整数（int），指示受影响的行数（即更新计数）。
        // 对于 CREATE TABLE 或 DROP TABLE 等不操作行的语句，executeUpdate 的返回值总为零。
        //但是其实用于select也行
        int res=stmt.executeUpdate(sql);
        stmt.close();
        return res;
    }

    //executeQuery函数的漏洞版，这个
    public ResultSet vulnerable_jdbc_select_2(String name) throws SQLException {
        String sql="select * from t_user where name='"+name+"' ";
        //用于产生单个结果集（ResultSet）的语句
        System.out.println(sql);
        ResultSet res=stmt.executeQuery(sql);
        stmt.close();
        return res;
    }

    //execute函数的漏洞版
    public boolean vulnerable_jdbc_select_3(String name) throws SQLException {
        String sql="select * from t_user where name='"+name+"' ";
        //可用于执行任何SQL语句，返回一个boolean值，表明执行该SQL语句是否返回了ResultSet。
        // 如果执行后第一个结果是ResultSet，则返回true，否则返回false。
        //这个方法比较笼统，当连开发都不确定是什么sql语句的时候可以执行这个，如果返回true就可以通过下面代码把结果集弄出来
        /*boolean hasResultSet = stmt.execute(sql);
            //如果执行后有ResultSet结果集

            if (hasResultSet)

            {
                //获取结果集
                rs = stmt.getResultSet();

                //ResultSetMetaData是用于分析结果集的元数据接口
                ResultSetMetaData rsmd = rs.getMetaData();

                int columnCount = rsmd.getColumnCount();
                //迭代输出ResultSet对象

                while (rs.next())

                {//依次输出每列的值
                    for (int i = 0 ; i < columnCount ; i++ )
                    {
                        System.out.print(rs.getString(i + 1) + "/t");

                    }
                    System.out.print("/n");

                }
            }

            else
            {
                System.out.println("该SQL语句影响的记录有" + stmt.getUpdateCount() + "条");
            }

*/
        boolean res=stmt.execute(sql);
        return res;
    }

    //错误使用PreparedStatement
    public ResultSet vulnerable_jdbc_select_4(String name) throws SQLException {
        pstmt = conn.prepareStatement("");    //或者直接在这里拼接
        if(pstmt.execute("select * from t_user where name='"+name+"'")){
            ResultSet res=pstmt.getResultSet();
            return res;
        }
        else{
            return null;
        }
    }

    //使用PreparedStatement来进行预编译查询
    public ResultSet unvulnerable_jdbc_select_2(String name) throws SQLException {
        String SQL = "select * from t_user where name= ?";  //问号?就是占位符号，来避免拼接
        pstmt = conn.prepareStatement(SQL);
        pstmt.setString(1,  name); //把占位符填上
        //PreparedStatement不能用来设置表名、字段名!!!!fortify扫描出该种问题时候，必须审查表名和字段名的来源是否可控
        ResultSet res=pstmt.executeQuery();
        return res;
    }

}
