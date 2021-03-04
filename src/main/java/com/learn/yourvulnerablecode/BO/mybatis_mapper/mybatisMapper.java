package com.learn.yourvulnerablecode.BO.mybatis_mapper;

import org.apache.ibatis.annotations.*;
import com.learn.yourvulnerablecode.util.SQL_Class;

import java.util.List;

//必须得是interface
public interface  mybatisMapper {

    //mybatis种使用${}就是静态拼接，造成sql注入，字符串类型还得加上'
    @Select("SELECT id,name,password,age,sercet FROM t_user WHERE name = '${name}'")
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "password", column = "password"),
            @Result(property = "age", column = "age"),
            @Result(property = "sercet", column = "sercet")
    })
    //SQL_Class selectById(int id); 这个是一个对象就相当于一行，List<CLASS>就是结果集，然后定义具体的方法，注意入参
    List<SQL_Class> vulnerable_mybatisClassConfig_select_1(String name);


    @Select("SELECT id,name,password,age,sercet FROM t_user WHERE name = #{name}")
    //SQL_Class selectById(int id);
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "password", column = "password"),
            @Result(property = "age", column = "age"),
            @Result(property = "sercet", column = "sercet")
    })
    List<SQL_Class> unvulnerable_mybatisClassConfig_select_1(String name);

    @Select("SELECT id,name,password,age,sercet FROM ${tablename} WHERE ${colname} = '${name}'")
    //表名列名不能#{}，会报错org.hsqldb.HsqlException: unexpected token: ?
    //@Select("SELECT id,name,password,age,sercet FROM #{tablename} WHERE #{colname} = '${name}'")
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "password", column = "password"),
            @Result(property = "age", column = "age"),
            @Result(property = "sercet", column = "sercet")
    })
    List<SQL_Class> test_mybatisClassConfig_TableAndCol_1(String tablename,String colname,String name);

}
