package com.learn.yourvulnerablecode.controller;

import com.learn.yourvulnerablecode.BO.Sqli_JDBC;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;

@Controller
public class SqliController {



    @ResponseBody
    @RequestMapping("/sql")
    public ModelAndView sql(HttpServletRequest request, HttpServletResponse response){
        String result_sql="";
        ModelAndView modelAndView = new ModelAndView("/sql");
        modelAndView.addObject("result_sql", result_sql);
        return modelAndView;

    }
    @ResponseBody
    @RequestMapping("/sql_deal")
    public String sql_deal(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        String request_sql=request.getParameter("sql");
        String result_sql="";
        if(request_sql!=null){
            switch(request.getParameter("sql_way")){
                case "jdbc" :
                    Sqli_JDBC jdbc=new Sqli_JDBC();
                    /*
                    //发现jdbc对hsqldb很多方法不支持，只能用next方法，游标不能随意移动，只能一个结果集判断是否为空，一个结果集来取数
                    ResultSet res=jdbc.vulnerable_select_2(request_sql);
                    ResultSet ori_res=jdbc.vulnerable_select_2(request_sql);;
                    //Sqli_JDBC.log_table(res);   //大坑，注意游标所在位置，发现hsqldb的first()和last不能用

                    //System.out.println(res.getRow());   getRow()这个函数不知为什么，有结果集也是空
                    //res.beforeFirst()
                    if(!res.next()){
                        result_sql="resultset is null";
                    }
                    else{
                        result_sql=Sqli_JDBC.log_table(ori_res);
                    }
                    */
                    //或者直接就查不到的就返回空

                    //漏洞版
                    //ResultSet res=jdbc.vulnerable_select_2(request_sql);

                    //
                    ResultSet res=jdbc.unvulnerable_select_2(request_sql);
                    result_sql=Sqli_JDBC.log_table(res);
                    break;
                case "mybatis" :
                    //这里用的是mybatis-spring-boot-starter，具有两种模式，一种是老派的xml，一种是注解方式

                    break;
                case "ibatis" :

                    break;
                case "hibernate" :

                    break;
                case "jpa" :

                    break;


                default :
                    result_sql="error";
            }}

//        ModelAndView modelAndView = new ModelAndView("/sql");
//        modelAndView.addObject("result_sql", result_sql);
//        return modelAndView;
        return result_sql;
    }
}
