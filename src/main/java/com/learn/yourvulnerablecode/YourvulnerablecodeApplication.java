package com.learn.yourvulnerablecode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//下面这个注解是配置mybatis下mapper方式的sql语句的配置类们在哪，或者直接在 Mapper 类上面添加注解@Mapper
@MapperScan("com.learn.yourvulnerablecode.BO.mybatis_mapper")
public class YourvulnerablecodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(YourvulnerablecodeApplication.class, args);
    }

}
