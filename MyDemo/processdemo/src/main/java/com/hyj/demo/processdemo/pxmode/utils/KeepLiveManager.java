package com.hyj.demo.processdemo.pxmode.utils;

import android.content.Context;
import android.content.Intent;

import com.hyj.demo.processdemo.pxmode.KeepLiveActivity;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/3/12 18:06
 * @description :
 * =========================================================
 */
public class KeepLiveManager {
    private KeepLiveActivity keepLiveActivity = null;
    private static volatile KeepLiveManager instance;

    private KeepLiveManager() {
    }

    public static KeepLiveManager getInstance() {
        if (instance == null) {
            synchronized (KeepLiveManager.class) {
                if (instance == null) {
                    instance = new KeepLiveManager();
                }
            }
        }
        return instance;
    }

    public void setKeep(KeepLiveActivity keep) {
        keepLiveActivity = keep;
    }

    public void startKeepLiveActivity(Context context) {
        Intent intent = new Intent(context, KeepLiveActivity.class);
        context.startActivity(intent);
    }

    public void finishKeepLiveActivity() {
        if (keepLiveActivity != null)
            keepLiveActivity.finish();
    }

}
