package com.hyj.demo.processdemo.pxmode;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.hyj.demo.processdemo.pxmode.utils.KeepLiveManager;


/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/3/12 17:35
 * @description :
 * =========================================================
 */
public class KeepLiveActivity extends AppCompatActivity {
    private static final String TAG = "KeepLiveActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "start Keep app activity");
        Window window = getWindow();
        //设置这个act 左上角
        window.setGravity(Gravity.START | Gravity.TOP);
        //宽 高都为1
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = 1;
        attributes.height = 1;
        attributes.x = 0;
        attributes.y = 0;
        window.setAttributes(attributes);
        KeepLiveManager.getInstance().setKeep(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "stop keep app activity");
    }
}
