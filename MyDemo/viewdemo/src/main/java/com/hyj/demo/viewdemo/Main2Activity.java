package com.hyj.demo.viewdemo;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main2Activity extends AppCompatActivity {
    private DownloadProgressView dpView;
    ValueAnimator valueAnimator;
    private boolean isFirst = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        dpView = findViewById(R.id.dpView);
        valueAnimator = ValueAnimator.ofInt(0, 100).setDuration(10000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                dpView.setProgress(value);
            }
        });
        findViewById(R.id.dpView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dpView.isStop()) {
                    if (isFirst) {
                        valueAnimator.start();
                        isFirst = false;
                    } else {
                        valueAnimator.resume();
                    }
                } else {
                    valueAnimator.pause();
                }
                dpView.setStop(!dpView.isStop());
            }
        });

    }
}
