package com.hyj.demo.toolbardemo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hyj.demo.toolbardemo.R;
import com.hyj.demo.toolbardemo.util.FitStateUtil;

public class BannerImmersionAcitivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_immersion_acitivity);
        FitStateUtil.setImmersionState(this);
    }
}
