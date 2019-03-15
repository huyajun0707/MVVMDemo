package com.hyj.demo.toucheventdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btInner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "button点击了！", Toast.LENGTH_SHORT).show();
                Log.d("---->","button点击了！");
            }
        });

        findViewById(R.id.llBackGround).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "BackGround点击了！", Toast.LENGTH_SHORT).show();
                Log.d("---->","BackGround点击了！");
            }
        });

    }
}
