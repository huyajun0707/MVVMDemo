package com.hyj.demo.toolbardemo.ui;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hyj.demo.toolbardemo.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btFullScreen, btBannerImmersion, btStatusBarSameColor, btFragmentStatusBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        setListener();


    }


    private void findViewById() {
        btFullScreen = findViewById(R.id.btFullScreen);
        btBannerImmersion = findViewById(R.id.btStatusBar1);
        btStatusBarSameColor = findViewById(R.id.btStatusBarSameColor);
        btFragmentStatusBar = findViewById(R.id.btFragmentStatusBar);
    }

    private void setListener() {
        btFullScreen.setOnClickListener(this);
        btBannerImmersion.setOnClickListener(this);
        btStatusBarSameColor.setOnClickListener(this);
        btFragmentStatusBar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btFullScreen:
                startActivity(new Intent(this, FullScreenActivity.class));
                break;
            case R.id.btStatusBar1:
                startActivity(new Intent(this, BannerImmersionAcitivity.class));
                break;
            case R.id.btStatusBarSameColor:
                startActivity(new Intent(this, StatusBarSameColorActivity.class));
                break;
            case R.id.btFragmentStatusBar:
                startActivity(new Intent(this, FragmentStatusBarActivity.class));
                break;
        }

    }
}
