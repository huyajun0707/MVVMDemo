package com.hyj.demo.processdemo.alarmmode;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.hyj.demo.processdemo.Utils;
import com.hyj.demo.processdemo.constant.KeepAliveConstants;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/3/13 15:45
 * @description :
 * =========================================================
 */
public class HideForegroundService extends Service {
    private static final String TAG = "HideForegroundService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "隐藏前台服务通知启动");
        Utils.startForegroundSafety(this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG,"隐藏前台服务结束");
                Utils.stopForegroundSafety(HideForegroundService.this, true);
                Utils.stopServiceSafety(HideForegroundService.this);
            }
        }, KeepAliveConstants.HIDE_FOREGROUND_SERVICE_STOP_DELAY);
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
