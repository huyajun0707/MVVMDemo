package com.wajiu.crm.android.debug;

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

    private static MyApplication application;

    public static MyApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        MobSDK.init(this);

    }
}
