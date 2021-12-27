package com.example.contenproviderdemo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.contenproviderdemo.greendaodemo.db.DBManager;
import com.example.contenproviderdemo.greendaodemo.entity.MovieCollect;
import com.example.contenproviderdemo.greendaodemo.entity.Student;
import com.example.contenproviderdemo.greendaodemo.greendaodb.DaoManager;

/**
 * @author ： HuYajun <huyajun0707@gmail.com>
 * @version ： 1.0
 * @date ： 2020-03-13 18:17
 * @depiction ：
 */
public class TestProvider extends ContentProvider {

    private final static int QUERY = 100;
    private final static int INSERT = 101;
    private DBManager dbManager;
    private DaoManager daoManager;

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = TestContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, TestContract.PATH_QUERY, QUERY);
        matcher.addURI(authority, TestContract.PATH_INSERT, INSERT);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        dbManager = new DBManager(getContext());
        daoManager = new DaoManager();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        switch (buildUriMatcher().match(uri)) {
            case QUERY:
                Log.d("------>","query");
//                cursor = daoManager.getDaoSession().getMovieCollectDao().();
//                String sql = "select * from MOVIECOLLECT";
//                cursor = daoManager.getDaoSession().getDatabase().rawQuery(sql, null);
                break;
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (buildUriMatcher().match(uri)) {
            case INSERT:
//                int i = 5/0;
//                Student student = new Student();
//                student.id = values.getAsInteger("id");
//                student.name = values.getAsString("name");
//                student.age = values.getAsInteger("age");
//                student.sex = values.getAsBoolean("sex");
//                student.info = values.getAsString("info");
//                dbManager.add(student);
//                MovieCollect movieCollect = new MovieCollect();
//                movieCollect.setMovieImage(values.getAsString("movieImage"));
//                movieCollect.setTitle(values.getAsString("title"));
//                movieCollect.setYear(values.getAsInteger("year"));
//
//                daoManager.getDaoSession().getMovieCollectDao().insertInTx(movieCollect);
                Log.d("------>","insert");
                break;
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {


        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
