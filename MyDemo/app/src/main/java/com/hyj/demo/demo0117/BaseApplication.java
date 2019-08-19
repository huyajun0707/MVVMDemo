package com.hyj.demo.demo0117;

import android.app.Application;
import android.os.Debug;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/5/28 15:24
 * @description :
 * =========================================================
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Debug.startMethodTracing("App");
        Debug.stopMethodTracing();
    }
}
