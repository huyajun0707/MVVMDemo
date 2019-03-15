package com.hyj.demo.processdemo.jobscheduler.service;

import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.hyj.demo.processdemo.jobscheduler.SystemUtils;
import com.hyj.demo.processdemo.jobscheduler.service.WorkService;
import com.hyj.demo.processdemo.pxmode.KeepLiveActivity;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/3/13 9:23
 * @description :
 * =========================================================
 */
public class AliveJobService extends JobService {
    private final static String TAG = "KeepAliveService";
    // 告知编译器，这个变量不能被优化
    private volatile static Service mKeepAliveService = null;

    public static boolean isJobServiceAlive() {
        return mKeepAliveService != null;
    }

    private static final int MESSAGE_ID_TASK = 0x01;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            // 具体任务逻辑
            if (SystemUtils.isAppAlive(getApplicationContext(), getApplicationContext().getPackageName())) {
                Toast.makeText(getApplicationContext(), "APP活着的", Toast.LENGTH_SHORT)
                        .show();
            } else {
                //要启动的服务
                WorkService.startService(getApplication());
                Toast.makeText(getApplicationContext(), "APP被杀死，重启...", Toast.LENGTH_SHORT)
                        .show();
            }
            // 通知系统任务执行结束
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                jobFinished((JobParameters) msg.obj, true);
            } else {
                jobFinished((JobParameters) msg.obj, false);
            }
            return true;
        }
    });

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "KeepAliveService----->JobService服务被启动...");
        mKeepAliveService = this;
        // 返回false，系统假设这个方法返回时任务已经执行完毕；
        // 返回true，系统假定这个任务正要被执行
        Message msg = Message.obtain(mHandler, MESSAGE_ID_TASK, params);
        mHandler.sendMessage(msg);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        mHandler.removeMessages(MESSAGE_ID_TASK);
        Log.d(TAG, "KeepAliveService----->JobService服务被关闭");
        return false;
    }


}
