package com.hyj.demo.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationSet;

public class MainActivity extends AppCompatActivity {
    private CameraView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraView = findViewById(R.id.cameraView);
        @SuppressLint("ObjectAnimatorBinding")
        ObjectAnimator bottomFlipAnimator = ObjectAnimator.ofFloat(cameraView, "bottomFlip", 45);
        bottomFlipAnimator.setDuration(1500);

        @SuppressLint("ObjectAnimatorBinding")
        ObjectAnimator flipRotationAnimator = ObjectAnimator.ofFloat(cameraView, "flipRotation", 270);
        bottomFlipAnimator.setDuration(1500);

        @SuppressLint("ObjectAnimatorBinding")
        ObjectAnimator topFlipAnimator = ObjectAnimator.ofFloat(cameraView, "topFlip", -45);
        topFlipAnimator.setDuration(1500);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(bottomFlipAnimator, flipRotationAnimator, topFlipAnimator);
        animatorSet.setStartDelay(1000);
        animatorSet.start();

    }

    @SuppressLint("ObjectAnimatorBinding")
    private void initAnimation() {


    }

}
