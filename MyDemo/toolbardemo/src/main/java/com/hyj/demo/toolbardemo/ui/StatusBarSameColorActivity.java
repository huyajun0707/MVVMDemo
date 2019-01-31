package com.hyj.demo.toolbardemo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hyj.demo.toolbardemo.R;
import com.hyj.demo.toolbardemo.util.FitStateUtil;

public class StatusBarSameColorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_bar_color);
        int color = getResources().getColor(R.color.status_toolBar_same_color);
        FitStateUtil.setStatusToolbarColor(this, color);
    }
}
