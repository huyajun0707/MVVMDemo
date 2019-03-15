package com.hyj.demo.processdemo.alarmmode;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.hyj.demo.processdemo.GuardAidl;
import com.hyj.demo.processdemo.Utils;
import com.hyj.demo.processdemo.constant.KeepAliveConstants;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/3/13 15:08
 * @description :
 * =========================================================
 */
public class RemoteService extends Service {
    private static final String TAG = "RemoteService";
    private MyBilder mBilder;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mBilder == null) {
            mBilder = new MyBilder();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBilder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "RemoteService 开启，绑定到 LocalService");
        Intent it = new Intent(this, LocalService.class);
        Utils.bindServiceSafety(this, it, connection);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "RemoteService 停止，解绑 LocalService");
        Utils.unbindServiceSafety(this, connection);
    }

    private final class MyBilder extends GuardAidl.Stub {

        @Override
        public void wakeUp() throws RemoteException {
            if (KeepAliveConstants.OPEN_FOREGROUND_SERVICE) {
                // 开启前台服务
                Log.i(TAG, "RemoteService 开启前台服务");
                Utils.startForegroundSafety(RemoteService.this);
                //隐藏服务通知
                Log.i(TAG, "RemoteService 开启隐藏前台通知服务");
                Intent hideForegroundIntent = new Intent(RemoteService.this, HideForegroundService.class);
                Utils.startServiceSafety(RemoteService.this, hideForegroundIntent);
            }
        }

    }

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "RemoteService 断开与 LocalService 的连接");
            Intent localServiceIntent = new Intent(RemoteService.this, LocalService.class);
            Utils.startServiceSafety(RemoteService.this, localServiceIntent);
            Utils.bindServiceSafety(RemoteService.this, localServiceIntent, connection);
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "RemoteService 与 LocalService 连接成功");
        }
    };
}
