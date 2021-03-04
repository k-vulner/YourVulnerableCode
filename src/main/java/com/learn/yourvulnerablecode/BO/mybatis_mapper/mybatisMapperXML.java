package com.learn.yourvulnerablecode.BO.mybatis_mapper;

import com.learn.yourvulnerablecode.util.SQL_Class;

import java.util.List;

//这里是mybatis xml方式的
public interface mybatisMapperXML {


    List<SQL_Class> vulnerable_mybatisXMLConfig_select_1(String name);

    List<SQL_Class> unvulnerable_mybatisXMLConfig_select_1(String name);

    List<SQL_Class> unvulnerable_mybatisXMLConfig_testTableCol_1(String tablename,String colname,String name);


    List<SQL_Class> unvulnerable_mybatisXMLConfig_blurryselect_1(String request_sql);
}
