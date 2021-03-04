package com.learn.yourvulnerablecode.controller;

import com.learn.yourvulnerablecode.BO.Sqli_JDBC;
import com.learn.yourvulnerablecode.BO.mybatis_mapper.mybatisMapperXML;
import com.learn.yourvulnerablecode.BO.mybatis_mapper.mybatisMapper;
import com.learn.yourvulnerablecode.util.SQL_Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
import java.util.List;

@Controller
public class SqliController {
    //mybatis ClassConfig的自动装配
    @Autowired
    mybatisMapper userMapper;
    //mybatis XMLConfig的自动装配
    @Autowired
    mybatisMapperXML userMapperXML;

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
                    //ResultSet res=jdbc.vulnerable_jdbc_select_2(request_sql);

                    //
                    ResultSet res=jdbc.unvulnerable_jdbc_select_2(request_sql);
                    result_sql=Sqli_JDBC.log_table(res);
                    break;
                case "mybatis" :
                    List<SQL_Class> users=null;
                    //参考https://www.cnblogs.com/ityouknow/p/6037431.html
                    //https://github.com/ityouknow/spring-boot-examples/
                    //这里用的是mybatis-spring-boot-starter，具有两种模式，一种是老派的xml，一种是注解方法

                    //注解方式，需要在YourvulnerablecodeApplication里面配置注解配置类的位置或是在注解配置类加@Mapper，并且需要在使用的位置进行自动装配注解，也就是21 @Autowired，22行 mybatisMapper userMapper;
                    //users=userMapper.vulnerable_mybatisClassConfig_select_1(request_sql);
                    //users=userMapper.test_mybatisClassConfig_TableAndCol_1("t_user","name",request_sql);

                    //xml方式，配置比较麻烦
                    //users=userMapperXML.vulnerable_mybatisXMLConfig_select_1(request_sql);
                    //users=userMapperXML.unvulnerable_mybatisXMLConfig_select_1(request_sql);
                    //users=userMapperXML.unvulnerable_mybatisXMLConfig_testTableCol_1("t_user","name",request_sql);
                    users=userMapperXML.unvulnerable_mybatisXMLConfig_blurryselect_1(request_sql);
                    result_sql=users.toString();
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
