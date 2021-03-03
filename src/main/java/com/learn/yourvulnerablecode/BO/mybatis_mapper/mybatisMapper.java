package com.learn.yourvulnerablecode.BO.mybatis_mapper;

import org.apache.ibatis.annotations.*;
import com.learn.yourvulnerablecode.util.SQL_Class;

import java.util.List;

//必须得是interface
public interface  mybatisMapper {
    @Select("SELECT * FROM t_user WHERE name = ${id}")
    //SQL_Class selectById(int id);
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "password", column = "password"),
            @Result(property = "age", column = "age"),
            @Result(property = "sercet", column = "sercet")
    })
    List<SQL_Class> getAll();
//
//    @Select("SELECT * FROM users WHERE id = #{id}")
//    @Results({
//            @Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
//            @Result(property = "nickName", column = "nick_name")
//    })
//    UserEntity getOne(Long id);
//
//    @Insert("INSERT INTO users(userName,passWord,user_sex) VALUES(#{userName}, #{passWord}, #{userSex})")
//    void insert(UserEntity user);
//
//    @Update("UPDATE users SET userName=#{userName},nick_name=#{nickName} WHERE id =#{id}")
//    void update(UserEntity user);
//
//    @Delete("DELETE FROM users WHERE id =#{id}")
//    void delete(Long id);
}
