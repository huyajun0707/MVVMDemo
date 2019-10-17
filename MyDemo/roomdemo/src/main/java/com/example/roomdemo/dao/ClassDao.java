package com.example.roomdemo.dao;

import android.view.ViewDebug;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomdemo.entity.ClassEntity;
import com.example.roomdemo.entity.StudentEntity;

import java.util.List;

/**
 * @author ： HuYajun <huyajun0707@gmail.com>
 * @version ： 1.0
 * @date ： 2019-10-14 19:28
 * @depiction ：
 */
@Dao
public interface ClassDao {

    @Query("SELECT * FROM tb_class")
    List<ClassEntity> getAll();

    @Query("SELECT * FROM tb_class WHERE id IN (:ids)")
    List<ClassEntity> getAllByIds(long[] ids);

    @Insert(onConflict = 1)
    void insert(ClassEntity... entities);

    @Delete
    void delete(ClassEntity entity);

    @Update
    void update(ClassEntity entity);


}
