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

    @PrimaryKey
    private long id;
}
