package com.learn.yourvulnerablecode.util;
import com.learn.yourvulnerablecode.BO.Sqli_JDBC;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


import java.sql.*;

//网上找的springboot运行前执行，下面是初始化数据库操作
@Component
public class Build_HsqlDB implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            conn = DriverManager.getConnection("jdbc:hsqldb:mem://127.0.0.1:9001/sqlidb", "sa", "");
            stmt = conn.createStatement();

            String sql = "CREATE TABLE t_user (" +
                    "id BIGINT NOT NULL, " +
                    "name VARCHAR(20) NOT NULL," +
                    "password VARCHAR(32) NOT NULL, " +
                    "age SMALLINT, " +
                    "sercet VARCHAR(32)," +
                    "PRIMARY KEY (id)" +
                    "); " ;



            int result = stmt.executeUpdate(sql);

            System.out.println(sql+" result:"+result);
            String sql2="INSERT INTO t_user VALUES(1,'Alice','password123',21,'alice is cat'); ";
            String sql3="INSERT INTO t_user VALUES(2,'Bill','password456',23,'bill is dog');";

            int result2=stmt.executeUpdate(sql2);
            int result3=stmt.executeUpdate(sql3);
            System.out.println(sql2+" result:"+result2);
            String sql4="select * from t_user";
            Sqli_JDBC.log_table(stmt.executeQuery(sql4));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


