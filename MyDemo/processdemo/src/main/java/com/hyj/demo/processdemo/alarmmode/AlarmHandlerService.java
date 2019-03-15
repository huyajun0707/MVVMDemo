package com.hyj.demo.processdemo.alarmmode;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hyj.demo.processdemo.Utils;
import com.hyj.demo.processdemo.constant.KeepAliveConstants;
import com.hyj.demo.processdemo.jobscheduler.service.WorkService;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/3/13 14:54
 * @description :
 * =========================================================
 */
public class AlarmHandlerService extends Service {
    private static final String TAG = "AlarmHandlerService";
    private static final int INTERVAL_WAKEUP = 3 * 1000;
    private static final int ALARM_MANAGER_REQUEST_CODE = 1000;
    private boolean hasStartAlarmManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startService(this);
//        if (!hasStartAlarmManager) {
            Log.i(TAG, "AlarmHandlerService 启动");
            startAlarmManagerSafety();
//            hasStartAlarmManager = true;
//        } else {
//            Log.i(TAG, "AlarmHandlerService 开始调度");
//        }
        return START_STICKY;
    }


    private void startAlarmManagerSafety() {
        try {
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            if (alarmManager == null) {
                return;
            }
            Intent intent = new Intent(this, AlarmHandlerService.class);
            PendingIntent pendingIntent = PendingIntent.getService(this, ALARM_MANAGER_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            if (pendingIntent == null) {
                return;
            }

            //MARSHMALLOW OR ABOVE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                        SystemClock.elapsedRealtime(), pendingIntent);
            }
            //LOLLIPOP 21 OR ABOVE
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(SystemClock.elapsedRealtime(), pendingIntent);
                alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);
            }
            //KITKAT 19 OR ABOVE
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                        SystemClock.elapsedRealtime(), pendingIntent);
            }
            //FOR BELOW KITKAT ALL DEVICES
            else {
                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        SystemClock.elapsedRealtime(), pendingIntent);
            }
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
//                    System.currentTimeMillis() + INTERVAL_WAKEUP,
//                    INTERVAL_WAKEUP, pendingIntent);
            Log.i(TAG, "AlarmHandlerService 开启AlarmManager定时器");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "AlarmHandlerService 开启AlarmManager定时器失败");
        }
    }

    private void startService(Context context) {
        if (Utils.isServiceRunning(getApplicationContext(), WorkService.class.getName())
                && Utils.isRunningTaskExist(getApplicationContext(), getPackageName() + KeepAliveConstants.REMOTE_PROCESS_NAME)) {
            return;
        }
        if (KeepAliveConstants.OPEN_FOREGROUND_SERVICE) {
            Log.i(TAG, "AlarmHandlerService 开启前台服务");
            Utils.startForegroundSafety(this);
        }
        //启动本地服务
        Intent localIntent = new Intent(context, LocalService.class);
        Utils.startServiceSafety(context, localIntent);
        //启动守护进程
        Intent guardIntent = new Intent(context, RemoteService.class);
        Utils.startServiceSafety(context, guardIntent);
        Log.i(TAG, "AlarmHandlerService 开启LocalService和RemoteService");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
