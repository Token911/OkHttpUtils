package com.mushroom.okhttpdemo;

/**
 * Created by Guo on 2017/2/19 0019.
 */

public class User {
    private int age;
    private String name;

    public User(){}
    public User(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}'+"哈哈哈哈";
    }
}
