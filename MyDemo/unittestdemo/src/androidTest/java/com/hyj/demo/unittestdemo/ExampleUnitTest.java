package com.hyj.demo.unittestdemo;

import android.arch.lifecycle.Observer;
import android.util.Log;

import org.junit.Test;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/4/3 9:42
 * @description :
 * =========================================================
 */
public class ExampleUnitTest {
    private static final String TAG = "ExampleUnitTest";
    @Test
    public void testPublishSubject() {
        System.out.println("aaaaaas");
    }

    @Test
    public void testSubject(){
        Log.d(TAG, "testSubject: ");
    }

}
