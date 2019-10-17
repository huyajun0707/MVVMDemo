package com.example.roomdemo.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author ： HuYajun <huyajun0707@gmail.com>
 * @version ： 1.0
 * @date ： 2019-10-14 19:07
 * @depiction ：
 */

@Entity(tableName = "tb_class")
public class ClassEntity {

    @PrimaryKey(autoGenerate = true)//定义主键
    private long id;
    private String name;

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
}
