package com.hyj.demo.toolbardemo.ui;

import android.graphics.Color;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hyj.demo.toolbardemo.R;
import com.hyj.demo.toolbardemo.adapter.MyFragementAdapter;
import com.hyj.demo.toolbardemo.fragment.Fragment1;
import com.hyj.demo.toolbardemo.fragment.Fragment2;
import com.hyj.demo.toolbardemo.util.FitStateUtil;

import java.util.ArrayList;
import java.util.List;

public class FragmentStatusBarActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyFragementAdapter pagerAdapter;
    private View statusView = null;

    public View getStatusView() {
//        Log.d("-------->", "get" + statusView.toString());
        return statusView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_status_bar);
        statusView = FitStateUtil.addStatusBar(this, statusView, Color.RED);
        Log.d("fragment里的stausview", statusView.toString());
        tabLayout = findViewById(R.id.slidingTabs);
        viewPager = findViewById(R.id.viewpager);
        List<String> titles = new ArrayList<>();
        titles.add("fragment1");
        titles.add("fragment2");
        List<Fragment> fragments = new ArrayList<>();
        Fragment1 fragment1 = new Fragment1();
        Fragment2 fragment2 = new Fragment2();
        fragments.add(fragment1);
        fragments.add(fragment2);
        pagerAdapter = new MyFragementAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(1)));
        tabLayout.setupWithViewPager(viewPager);
        statusView.setBackgroundColor(Color.YELLOW);

    }
}
