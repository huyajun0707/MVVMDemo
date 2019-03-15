package com.hyj.demo.processdemo.alarmmode;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.hyj.demo.processdemo.GuardAidl;
import com.hyj.demo.processdemo.Utils;
import com.hyj.demo.processdemo.constant.KeepAliveConstants;
import com.hyj.demo.processdemo.pxmode.receiver.KeepLiveReceiver;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/3/13 15:06
 * @description :
 * =========================================================
 */
public class LocalService extends Service {
    private static final String TAG = "LocalService";
    private KeepLiveReceiver mScreenReceiver;

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
        Log.i(TAG, "LocalService 开启");
        //像素保活
        if (mScreenReceiver == null) {
            mScreenReceiver = new KeepLiveReceiver();
        }
        IntentFilter screenFilter = new IntentFilter();
        screenFilter.addAction(Intent.ACTION_SCREEN_OFF);
        screenFilter.addAction(Intent.ACTION_SCREEN_ON);
        Utils.registerBroadcastReceiverSafety(this, mScreenReceiver, screenFilter);

        if (KeepAliveConstants.OPEN_FOREGROUND_SERVICE) {
            Log.i(TAG, "LocalService 开启前台服务");
            Utils.startForegroundSafety(this);
        }

        // 绑定守护进程
        Intent remoteServiceIntent = new Intent(this, RemoteService.class);
        Utils.bindServiceSafety(this, remoteServiceIntent, connection);
        Log.i(TAG, "LocalService 绑定到 RemoteService");

        return START_STICKY;
    }


    private final class MyBilder extends GuardAidl.Stub {

        @Override
        public void wakeUp() throws RemoteException {

        }
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "LocalService 断开与 RemoteService 的连接");
            Intent remoteServiceIntent = new Intent(LocalService.this, RemoteService.class);
            Utils.startServiceSafety(LocalService.this, remoteServiceIntent);
            Utils.bindServiceSafety(LocalService.this, remoteServiceIntent, connection);
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "LocalService 与 RemoteService 连接成功，通知开启前台服务");
            try {
                GuardAidl guardAidl = GuardAidl.Stub.asInterface(service);
                guardAidl.wakeUp();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "LocalService 停止，解绑 RemoteService");
        unbindService(connection);
        unregisterReceiver(mScreenReceiver);
    }
}
