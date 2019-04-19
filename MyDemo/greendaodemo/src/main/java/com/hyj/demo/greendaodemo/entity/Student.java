package com.hyj.demo.greendaodemo.entity;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/4/18 11:03
 * @description :
 * =========================================================
 */
public class Student {
    public int _id;
    public int id;
    public String name;
    public int age;
    public boolean sex;
    public String info;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", info='" + info + '\'' +
                '}';
    }
}
