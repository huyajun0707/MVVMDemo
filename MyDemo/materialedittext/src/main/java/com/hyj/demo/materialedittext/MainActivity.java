package com.hyj.demo.materialedittext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private MaterialEditText materialEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        materialEditText = findViewById(R.id.materalEditText);
        materialEditText.postDelayed(new Runnable() {
            @Override
            public void run() {
                materialEditText.setUseFloatingLabel(true);
            }
        }, 3000);


    }
}
