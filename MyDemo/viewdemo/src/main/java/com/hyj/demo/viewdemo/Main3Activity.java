package com.hyj.demo.viewdemo;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.pitt.library.fresh.FreshDownloadView;

import is.arontibo.library.ElasticDownloadView;

public class Main3Activity extends AppCompatActivity {
    ElasticDownloadView elasticDownloadView;
    ValueAnimator valueAnimator;
    FreshDownloadView freshDownloadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        elasticDownloadView = findViewById(R.id.downloadView);
        freshDownloadView = findViewById(R.id.pitt);
        elasticDownloadView.startIntro();
        valueAnimator = ValueAnimator.ofInt(0, 100).setDuration(10000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                freshDownloadView.upDateProgress(value);
                elasticDownloadView.setProgress(value);

            }
        });
        findViewById(R.id.btStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                freshDownloadView.reset();
                freshDownloadView.upDateProgress(0);
                valueAnimator.start();

            }
        });

    }
}
