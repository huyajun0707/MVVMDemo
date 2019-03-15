package com.hyj.demo.processdemo.pxmode.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.hyj.demo.processdemo.pxmode.utils.KeepLiveManager;


/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/3/12 18:19
 * @description :
 * =========================================================
 */
public class KeepLiveReceiver extends BroadcastReceiver {
    private static final String TAG = "KeepLiveReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.e(TAG, "receive action:" + action);
        //屏幕关闭事件
        if (TextUtils.equals(action, Intent.ACTION_SCREEN_OFF)) {
            //关屏 开启1px activity
            KeepLiveManager.getInstance().startKeepLiveActivity(context);
            Log.d(TAG, "onReceive:屏幕关闭事件 ");
            // 解锁事件
        } else if (TextUtils.equals(action, Intent.ACTION_USER_PRESENT)) {
            KeepLiveManager.getInstance().finishKeepLiveActivity();
            Log.d(TAG, "onReceive: 屏幕解锁事件");
        }
//        KeepLiveManager.getInstance().startKeepLiveActivity(context);
    }
}
