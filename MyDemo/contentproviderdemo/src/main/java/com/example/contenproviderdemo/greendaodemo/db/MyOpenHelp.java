package com.example.contenproviderdemo.greendaodemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.contenproviderdemo.greendaodemo.Constant;


/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/4/18 10:30
 * @description :
 * =========================================================
 */
public class MyOpenHelp extends SQLiteOpenHelper {
    private static final String TAG = "MyOpenHelp";

    public MyOpenHelp(Context context) {
        super(context, Constant.STUDENT_DB_NAME, null, 2);
    }

    public MyOpenHelp(Context context, String name, SQLiteDatabase.CursorFactory factory, int
            version) {
        super(context, name, factory, version);
    }

    /**
     * 数据库第一次创建的时候调用
     *
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //autoincrement自动递增
        sqLiteDatabase.execSQL("create table IF NOT EXISTS student(_id integer primary key " +
                "autoincrement,id varchar(20),name varchar(20),age int,sex boolean,info varchar" +
                "(40))");
    }

    /**
     * 如果版本号不为0，同时和最新版本号进行比较，如果大于的话，就执行升级操作onUpgrade方法，否则就执行降级onDowngrade方法
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: ");
        //首先对原来的表rename 然后创建新的表，然后将数据进行迁移
        Utils.rename(db,"student",1);


    }
}
