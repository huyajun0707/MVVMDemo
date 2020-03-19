package com.example.contenproviderdemo.greendaodemo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.contenproviderdemo.greendaodemo.entity.Student;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/4/18 11:03
 * @description :
 * =========================================================
 */
public class DBManager {
    private MyOpenHelp helper;

    public SQLiteDatabase getDb() {
        return db;
    }

    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new MyOpenHelp(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }


    /**
     * add students
     *
     * @param students
     */
    public void add(List<Student> students) {
        db.beginTransaction();    //开始事务
        try {
            for (Student student : students) {
                db.execSQL("INSERT INTO student VALUES(null, ?,?, ?,?, ?)", new Object[]{student
                        .id, student.name, student.age, true, student.info});
            }
            db.setTransactionSuccessful();    //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }


    /**
     * add student
     *
     * @param student
     */
    public void add(Student student) {
        db.beginTransaction();    //开始事务
        try {
            db.execSQL("INSERT INTO student VALUES(null, ?,?, ?,?, ?)", new Object[]{student
                    .id, student.name, student.age, true, student.info});
            db.setTransactionSuccessful();    //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }


    /**
     * update student's age
     *
     * @param student
     */
    public void updateAge(Student student) {
        ContentValues cv = new ContentValues();
        cv.put("age", student.age);
        db.update("student", cv, "name = ?", new String[]{student.name});
    }

    /**
     * delete old student
     *
     * @param student
     */
    public void deleteOldStudent(Student student) {
        db.delete("student", "age >= ?", new String[]{String.valueOf(student.age)});
    }

    public <T> List<T> getCommonListEntity(Class<T> clazz, String sql, String[] contentvalue) {
        List<Map<String, String>> maplist = getCommonListMap(sql, contentvalue);
        List<T> entitylist = new ArrayList<>();
        try {
            for (int i = 0; i < maplist.size(); i++) {
                Map<String, String> kvs = maplist.get(i);
                T t = clazz.newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (Field item : fields) {
                    item.setAccessible(true);  //在用反射时访问私有变量
                    if (kvs.get(item.getName()) != null) {
                        t = setItemValues(t, item, kvs.get(item.getName()));
                    }
                }
                entitylist.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entitylist;
    }

    public List<Map<String, String>> getCommonListMap(String sql, String[] contentvalue) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Cursor cursor = db.rawQuery(sql, contentvalue);
        while (cursor.moveToNext()) {
            Map<String, String> contents = new HashMap<String, String>();
            String[] keys = cursor.getColumnNames();
            for (int i = 0; i < keys.length; i++) {
                contents.put(keys[i], cursor.getString(cursor.getColumnIndex(keys[i])));
            }
            list.add(contents);
        }
        cursor.close();
        return list;
    }


    private <T> T setItemValues(T t, Field item, String value) {
        try {
            if (value == null) return t;

            if (item.getGenericType().toString().contains("String")) {//对String类型的判断
                item.set(t, value);
            }
            if (item.getGenericType().toString().contains("int")) {//对int类型的判断
                item.set(t, Integer.parseInt(value));
            }
            if (item.getGenericType().toString().contains("Integer")) {//对int类型的判断
                item.set(t, new Integer(value));
            }
            if (item.getGenericType().toString().contains("long")) {//对long类型的判断
                if (value.equals("")) {
                    item.set(t, 0l);
                } else {
                    item.set(t, Long.valueOf(value));
                }
            }
            if (item.getGenericType().toString().contains("float")) {//对float类型的判断
                item.set(t, Float.valueOf(value));
            }
            if (item.getGenericType().toString().contains("Date")) {//对date类型的判断
                if (value.equals("0") || value.equals("")) {
                    item.set(t, null);
                } else {
                    item.set(t, new Date(Long.valueOf(value)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * query all students, return list
     *
     * @return List<Student>
     */
    public List<Student> query() {
        ArrayList<Student> students = new ArrayList<Student>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            Student student = new Student();
            student._id = c.getInt(c.getColumnIndex("_id"));
            student.name = c.getString(c.getColumnIndex("name"));
            student.age = c.getInt(c.getColumnIndex("age"));
            student.sex = c.getInt(c.getColumnIndex("sex")) == 0 ? false : true;
            student.info = c.getString(c.getColumnIndex("info"));
            students.add(student);
        }
        c.close();
        return students;
    }

    /**
     * query all students, return cursor
     *
     * @return Cursor
     */
    public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM student", null);
        return c;
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }
}
