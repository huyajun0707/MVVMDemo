package com.hyj.load.x5demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hyj.load.x5demo.util.Utils;
import com.hyj.load.x5demo.view.ScalableImageView;

public class ScalableViewActivity extends AppCompatActivity {
    private ScalableImageView scalableImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scalable_view);
        scalableImageView = findViewById(R.id.siv);
        scalableImageView.setImageBitmap(Utils.getAvatar(getResources(), 300));
    }
}
