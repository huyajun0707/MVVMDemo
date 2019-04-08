package com.hyj.demo.leakcanarydemo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/3/22 16:05
 * @description :
 * =========================================================
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if(LeakCanary.isInAnalyzerProcess(this)){
            //不检测 LeakCanary 的分析进程
            return;
        }
        //初始化并监听内存泄漏
        LeakCanary.install(this);
    }
}
