package com.example.contenproviderdemo;

import android.app.Application;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/4/18 15:03
 * @description :
 * =========================================================
 */
public class MyApplication extends Application {
    private static MyApplication application;

    public static MyApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        application = this;
        super.onCreate();
    }
}
