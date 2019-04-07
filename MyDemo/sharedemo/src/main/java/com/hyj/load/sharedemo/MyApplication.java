package com.hyj.load.sharedemo;

import android.app.Application;

import com.mob.MobSDK;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/4/4 21:51
 * @description :
 * =========================================================
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this);

    }
}
