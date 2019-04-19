package com.hyj.demo.demo0117;

import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.SimpleTimeZone;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/1/17 9:39
 * @description :
 * =========================================================
 */
public class Test {
    private static final String TAG = "Test";
    private boolean flag = false;

    public static void main(String args[]) {
//        String number = "1.235";
//        String numberFormat = String.format("%.2f", number);
//        String numberFormat = String.format(Locale.CHINA, "%.2f", number);
//        System.out.print(numberFormat);

//        Thread thread1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 1; i <= 100; i++) {
//                    if (i % 2 == 0) {
//                        System.out.println("thread1: " + i);
//                    }
//                }
//            }
//        });
//
//        Thread thread2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                for (int i = 1; i <= 100; i++) {
//                    if (i % 2 != 0) {
//                        System.out.println("thread2: " + i);
//                    }
//                }
//            }
//        });
//        thread1.start();
//        thread2.start();
        HashMap hashMap = new HashMap();
        hashMap.put(null,"000");
        System.out.print(hashMap.get(null));


    }

    public static String getServiceTimeFrorMH(String time) {
        if (!TextUtils.isEmpty(time) && time.contains("T")) {
            return time.replace("T", "  ");
        }

        return time;
    }


}
