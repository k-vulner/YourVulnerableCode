package com.learn.yourvulnerablecode.util;

public class SQL_Class {
    private int id;
    private String name;
    private String password;
    private int age;
    private String sercet;

    @Override
    public String toString() {
        return "SQL_Class{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", sercet='" + sercet + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }
//为啥一打开这个构造函数，自动装配就炸？？？
//    public SQL_Class(String name, String password, int age) {
//        this.name = name;
//        this.password = password;
//        this.age = age;
//    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSercet(String sercet) {
        this.sercet = sercet;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public String getSercet() {
        return sercet;
    }


}
