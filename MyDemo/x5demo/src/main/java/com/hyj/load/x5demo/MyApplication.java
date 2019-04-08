package com.hyj.load.x5demo;

import android.app.Application;

import com.hyj.load.x5demo.util.X5Util;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/4/8 11:04
 * @description :
 * =========================================================
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //TODO 初始化
        X5Util.getInstance().init(this);
    }
}
