package com.hyj.demo.leakcanarydemo;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                default:
                    Log.d(TAG, "handleMessage: ");

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.obj = 2;
                message.what = 3;
                handler.sendEmptyMessage(2);
            }
        }, 50000);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Message message = Message.obtain();
//                message.obj = 2;
//                message.what = 3;
//                handler.sendMessageAtTime(message, 500000);
//            }
//        }).start();
    }
}
