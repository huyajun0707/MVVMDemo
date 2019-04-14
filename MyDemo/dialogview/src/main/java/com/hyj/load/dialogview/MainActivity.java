package com.hyj.load.dialogview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btShowDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDiaolog.create(getSupportFragmentManager()).setMessageBottom("温馨提示:运营商网络上传视频可能会导致超额流量，确认开启?")
                        .setNegative("取消")
                        .setPositive("允许")
                        .setRequestCode(111)
                        .show(MainActivity.this);

            }
        });

    }
}
