package com.hyj.demo.processdemo;

import android.content.Intent;
import android.content.IntentFilter;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hyj.demo.processdemo.alarmmode.AlarmHandlerService;
import com.hyj.demo.processdemo.jobscheduler.JobSchedulerManager;
import com.hyj.demo.processdemo.pxmode.receiver.KeepLiveReceiver;

public class MainActivity extends AppCompatActivity {
    KeepLiveReceiver keepLiveReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "哈哈", Toast.LENGTH_SHORT).show();
            }
        });
//        playPxMode();
        findViewById(R.id.btStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                playJobScheduler(true);
                playAlarmMode();
            }
        });
        findViewById(R.id.btStop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                playJobScheduler(false);
            }
        });

    }

    /**
     * 通过像素点将进程提到前台
     * 如果程序强制杀死，无法保活
     */
    private void playPxMode() {
        keepLiveReceiver = new KeepLiveReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(keepLiveReceiver, intentFilter);
    }

    /**
     * 主要适用于Android 5.0以上的版本。在Android 5.0以上版本中不受forcestop影响，被强制停止的应用依然可以被拉起，
     * 在Android 5.0以上版本拉活效果是非常好的。（但是在小米手机上可能会偶尔出现无法拉活的问题。）
     * 1、由于小米系统的深度定制，使用方需要：
     * 设置---自启动管理---选择应用（包名）---打开自启动即可。
     * 2. 华为屏蔽
     */
    private void playJobScheduler(boolean isStart) {
        if (isStart)
            JobSchedulerManager.getJobSchedulerInstance(this).startJobScheduler();
//        else
//            JobSchedulerManager.getJobSchedulerInstance(this).stopJobScheduler();
    }

    /**
     * 采用计时器
     * android 4.0 以上，虽然alarmmanager他是系统级别的服务
     * 在android4.4以前的手机上报时仍然准确，但是在4.4以后的手机上是不准确
     */
    private void playAlarmMode() {
        Intent intent = new Intent(MainActivity.this, AlarmHandlerService.class);
        Utils.startServiceSafety(MainActivity.this, intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(keepLiveReceiver);
    }
}
