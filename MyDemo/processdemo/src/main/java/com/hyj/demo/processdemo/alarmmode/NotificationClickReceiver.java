package com.hyj.demo.processdemo.alarmmode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/3/13 16:17
 * @description : 通知
 * =========================================================
 */
public class NotificationClickReceiver extends BroadcastReceiver {
    public static final String CLICK_NOTIFICATION = "notification_clicked";

    @Override
    public void onReceive(Context context, Intent intent) {
        //todo 跳转之前要处理的逻辑
//        Log.i("TAG", "userClick:我被点击啦！！！ ");

    }
}
