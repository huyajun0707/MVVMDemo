package com.hyj.demo.demo0117;

import android.os.Build;
import android.os.PersistableBundle;
import androidx.annotation.LongDef;
import androidx.annotation.RequiresApi;
import androidx.core.util.Pools;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


import dalvik.system.BaseDexClassLoader;
import dalvik.system.PathClassLoader;
import okio.BufferedSource;
import okio.Okio;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        float number = (float) 1.235;
////        String numberFormat = String.format("%.2f", number);
//        String numberFormat = String.format(Locale.CHINA, "%.2f", number);
//        Log.e("number", numberFormat);
        TextView textView = findViewById(R.id.tvTest);
        textView.invalidate();
        findViewById(R.id.tvTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread1.start();
                thread2.start();
            }
        });
        tvTest = findViewById(R.id.tvTest);
        tvTest.setText("0000");


//        startActivity();

//        Handler handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//            }
//        };
//        Looper.prepare();
//        Looper.loop();
//        handler.sendMessage()

    }


    private Object block = new Object();
    private boolean flag = false;

    Thread thread1 = new Thread(new Runnable() {
        @Override
        public void run() {
            synchronized (block) {

                for (int i = 1; i <= 100; i++) {
                    if (flag) {
                        try {
                            block.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (i % 2 != 0) {
                            flag = true;
                            Log.d(TAG, "thread1: " + i);
                            block.notifyAll();
                        }
                    }
                }
            }
        }
    });

    Thread thread2 = new Thread(new Runnable() {
        @Override
        public void run() {
            synchronized (block) {
                for (int i = 1; i <= 100; i++) {

                    if (!flag) {
                        try {
                            block.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (i % 2 == 0) {
                            flag = false;
                            Log.d(TAG, "thread2: " + i);
                            block.notifyAll();
                        }
                    }
                }
            }

        }
    });


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void okio() {
        try {
            BufferedSource source = (BufferedSource) Okio.source(new File(""));
            source.readUtf8();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent: ");
        return super.dispatchTouchEvent(ev);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Log.d(TAG, "onTouchEvent: ");
//        return super.onTouchEvent(event);
//    }

    private void hotFix() {
        try {
            ClassLoader classLoader = getClassLoader();
            Class loaderClass = BaseDexClassLoader.class;
            File apk = new File(getCacheDir() + "/hotfix.apk");
            Field pathListField = loaderClass.getDeclaredField("pathList");
            pathListField.setAccessible(true);
            Object pathListObject = pathListField.get(classLoader);
            Class pathListClass = pathListObject.getClass();
            Field dexElementsField = pathListClass.getDeclaredField("dexElements");
            dexElementsField.setAccessible(true);
            Object dexElementObject = dexElementsField.get(pathListObject);
            PathClassLoader newClassLoader = new PathClassLoader(apk.getPath(), null);
            Object newPathListObject = pathListField.get(newClassLoader);
            Object newDexElementsObject = dexElementsField.get(newPathListObject);
            //不能替换，需要插入，数组的插入
            int oldLength = Array.getLength(dexElementObject);
            int newLength = Array.getLength(newDexElementsObject);
            Object concatDexElementsObject = Array.newInstance(dexElementObject.getClass()
                    .getComponentType(), oldLength + newLength);
            for (int i = 0; i < newLength; i++) {
                Array.set(concatDexElementsObject, i, Array.get(newDexElementsObject, i));
            }
            for (int i = 0; i < oldLength; i++) {
                Array.set(concatDexElementsObject, newLength + i, Array.get(dexElementObject, i));
            }
            dexElementsField.set(pathListObject, newDexElementsObject);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }


    private void map(){
        //同步
        Map<String,String > map = Collections.synchronizedMap(new HashMap<String, String>());
     //   ConcurrentHashMap
    //concurrentHashMap的put方法是加锁的    采用了ReentrantLock可重入锁），可以保证线程安全。
    }
}
