package com.example.roomdemo.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.roomdemo.entity.StudentEntity;

/**
 * @author ： HuYajun <huyajun0707@gmail.com>
 * @version ： 1.0
 * @date ： 2019-10-14 19:35
 * @depiction ：
 */
@Database(entities = {StudentEntity.class},version = 1)
public abstract class RoomDemoDatabase extends RoomDatabase {

    public abstract StudentDao studentDao();
}
