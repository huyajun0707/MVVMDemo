package com.example.contenproviderdemo;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author ： HuYajun <huyajun0707@gmail.com>
 * @version ： 1.0
 * @date ： 2020-03-13 18:13
 * @depiction ：
 */
public class TestContract {

    protected static final String CONTENT_AUTHORITY = "com.example.contenproviderdemo.TestProvider";
    protected static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    protected static final String PATH_QUERY = "query";
    protected static final String PATH_INSERT = "insert";

    public static final class TestEntry implements BaseColumns {

        public static final Uri QUERY_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_QUERY).build();
        public static final Uri INSERT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INSERT).build();

        protected static Uri buildUri(long id) {
            return ContentUris.withAppendedId(QUERY_URI, id);
        }

        protected static final String TABLE_NAME = "test";

        public static final String COLUMN_NAME = "name";
    }

}
