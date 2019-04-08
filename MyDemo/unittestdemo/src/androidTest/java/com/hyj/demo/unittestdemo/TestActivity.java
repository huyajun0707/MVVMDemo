package com.hyj.demo.unittestdemo;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/4/3 10:03
 * @description :
 * =========================================================
 */
public class TestActivity extends ActivityInstrumentationTestCase2<MainActivity> {

    private Context ctx;

    public TestActivity() {
        super(MainActivity.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ctx = getActivity().getApplicationContext();
    }

    @Test
    public void testStart() {
        Intent intent = new Intent(ctx, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }
}
