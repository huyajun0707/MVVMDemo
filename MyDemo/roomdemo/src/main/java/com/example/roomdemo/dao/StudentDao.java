package com.example.roomdemo.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomdemo.entity.StudentEntity;

import java.util.List;

/**
 * @author ： HuYajun <huyajun0707@gmail.com>
 * @version ： 1.0
 * @date ： 2019-10-14 19:28
 * @depiction ：
 */
@Dao
public interface StudentDao {

    @Query("SELECT * FROM tb_student")
    List<StudentEntity> getAll();

    @Query("SELECT * FROM tb_student WHERE id IN (:ids)")
    List<StudentEntity> getAllByIds(long[] ids);

    @Insert
    void insert(StudentEntity... entities);

    @Delete
    void delete(StudentEntity entity);

    @Update
    void update(StudentEntity entity);


}
