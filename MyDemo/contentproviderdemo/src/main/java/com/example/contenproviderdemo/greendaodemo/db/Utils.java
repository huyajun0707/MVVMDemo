package com.example.contenproviderdemo.greendaodemo.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Arrays;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/4/18 14:06
 * @description :
 * =========================================================
 */
public class Utils {
    public static void rename(SQLiteDatabase db, String tableName, int type) {
        try{

        String tempTableName = "_temp_" + tableName;
        String sql = "ALTER TABLE " + tableName + " RENAME TO " + tempTableName;
        db.beginTransaction();
        db.execSQL(sql);
        String createSql = "create table IF NOT EXISTS student(_id integer primary key " +
                "autoincrement,id varchar(20),name varchar(20),age int,sex boolean,info varchar" +
                "(40),class int)";
        db.execSQL(createSql);
        String columns;
        if (type == 1) {
            columns = Arrays.toString(getColumnNames(db, tempTableName)).replace("[", "").replace
                    ("]", "");
        } else if (type == -1) {
            columns = Arrays.toString(getColumnNames(db, tableName)).replace("[", "").replace
                    ("]", "");
        } else {
            throw new IllegalArgumentException("OPERATION_TYPE error");
        }
        sql = "INSERT INTO "+tableName +
                " ("+ columns+") "+
                " SELECT "+ columns+" FROM "+tempTableName;
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS "+tempTableName;
        db.execSQL(sql);
        db.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }


    private static String[] getColumnNames(SQLiteDatabase db, String tableName) {
        String[] columnNames = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("PRAGMA table_info(" + tableName + ")", null);
            if (cursor != null) {
                int columnIndex = cursor.getColumnIndex("name");
                if (columnIndex == -1) {
                    return null;
                }

                int index = 0;
                columnNames = new String[cursor.getCount()];
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    columnNames[index] = cursor.getString(columnIndex);
                    index++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return columnNames;
    }

}
