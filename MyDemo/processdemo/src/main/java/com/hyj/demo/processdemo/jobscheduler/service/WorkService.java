package com.hyj.demo.processdemo.jobscheduler.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;



/**
 * =========================================================
 * @author      :   HuYajun     <13426236872@163.com>
 * @date        :   2019/3/13 10:41
 * @version     :
 * @description :   需要保活的业务服务
 * =========================================================
 */
public class WorkService extends Service {

    private static final String TAG = "WorkService";
    private final static String ACTION_START = "action_start";


    /**
     * 停止服务
     * @param context
     */
    public static void stopservice(Context context){
        if(context != null){
            Log.d(TAG, "WorkService ------- stopService");
            Intent intent = new Intent(context, WorkService.class);
            context.stopService(intent);
        }
    }


    public static void startService(Context context) {
        Log.d(TAG, "WorkService ------- startService");
        if(context != null) {
            Intent intent = new Intent(context, WorkService.class);
            intent.setAction(ACTION_START);
            context.startService(intent);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "WorkService -------   onBind");
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //todo 启动子线程执行耗时操作
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, "WorkService ---------- onStartCommand Service工作了");
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 1000, 1000);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "WorkService ------- is onDestroy!!!");
    }
}
