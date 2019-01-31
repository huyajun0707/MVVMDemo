package com.hyj.demo.demo0117;

import android.support.v4.util.Pools;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        float number = (float) 1.235;
//        String numberFormat = String.format("%.2f", number);
        String numberFormat = String.format(Locale.CHINA, "%.2f", number);
        Log.e("number", numberFormat);
    }


}
