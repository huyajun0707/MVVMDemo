package com.example.contenproviderdemo;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.contenproviderdemo.greendaodemo.entity.MovieCollect;
import com.example.contenproviderdemo.greendaodemo.entity.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ： HuYajun <huyajun0707@gmail.com>
 * @version ： 1.0
 * @date ： 2020-03-16 14:46
 * @depiction ：
 */
public class TestDatabaseHelper {
    //    private static final String TraceDataContentProvider = ".TraceDataContentProvider/";
    private ContentResolver mContentResolver;
    private Uri queryUri;
    private Uri insertUri;

    public TestDatabaseHelper(Context context, String packageName) {
        mContentResolver = context.getContentResolver();
        queryUri = TestContract.TestEntry.QUERY_URI;
        insertUri = TestContract.TestEntry.INSERT_URI;
    }

    public void commitInsert(MovieCollect movieCollect) {
        if (movieCollect != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("movieImage", movieCollect.getMovieImage());
            contentValues.put("title", movieCollect.getTitle());
            contentValues.put("year", movieCollect.getYear());
            mContentResolver.insert(insertUri, contentValues);
        }
    }

    @SuppressLint("NewApi")
    public List<Student> getStudents() {
        Cursor cursor = mContentResolver.query(queryUri, null, null, null);
        ArrayList<Student> students = new ArrayList<Student>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Student student = new Student();
                student._id = cursor.getInt(cursor.getColumnIndex("_id"));
                student.name = cursor.getString(cursor.getColumnIndex("name"));
                student.age = cursor.getInt(cursor.getColumnIndex("age"));
                student.sex = cursor.getInt(cursor.getColumnIndex("sex")) == 0 ? false : true;
                student.info = cursor.getString(cursor.getColumnIndex("info"));
                students.add(student);
            }
            cursor.close();
        }
        return students;

    }


    @SuppressLint("NewApi")
    public List<MovieCollect> getMovieCollect() {
        ArrayList<MovieCollect> movieCollects = new ArrayList<MovieCollect>();
        try {
            Cursor cursor = mContentResolver.query(queryUri, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    MovieCollect movieCollect = new MovieCollect();
                    movieCollect.setMovieImage(cursor.getString(cursor.getColumnIndex("MOVIE_IMAGE")));
                    movieCollect.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    movieCollect.setYear(cursor.getInt(cursor.getColumnIndex("year")));
                    movieCollect.setId(cursor.getLong(cursor.getColumnIndex("_id")));
                    movieCollects.add(movieCollect);
                }
                cursor.close();
            }
        }catch (Exception e){

        }
        return movieCollects;

    }


}
