package com.example.roomdemo.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author ： HuYajun <huyajun0707@gmail.com>
 * @version ： 1.0
 * @date ： 2019-10-14 19:11
 * @depiction ：
 */
@Entity(tableName = "tb_student",//表名
                indices = @Index(value = {"name"}, unique = false),
//        ,//定义索引
        foreignKeys = {@ForeignKey(entity = ClassEntity.class,
        parentColumns = "id",
        childColumns = "class_id")})//定义外键
public class StudentEntity {
    @PrimaryKey(autoGenerate = true)//定义主键
    private long id;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    //    @ColumnInfo(name = "name")
    private String name;
    @Ignore
    private String ignoreText;

//    @ColumnInfo(name = "class_id")
    private long class_id;
//    @ColumnInfo(name = "sex")
    private int sex;

    @Embedded
    public Address address;


    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getIgnoreText() {
        return ignoreText;
    }

    public void setIgnoreText(String ignoreText) {
        this.ignoreText = ignoreText;
    }

    public long getClass_id() {
        return class_id;
    }

    public void setClass_id(long class_id) {
        this.class_id = class_id;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ignoreText='" + ignoreText + '\'' +
                ", class_id=" + class_id +
                ", sex=" + sex +
                ", address=" + address +
                ", age=" + age +
                '}';
    }
}
