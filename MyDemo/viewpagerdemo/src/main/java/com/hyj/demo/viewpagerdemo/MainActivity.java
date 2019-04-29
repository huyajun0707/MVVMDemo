package com.hyj.demo.viewpagerdemo;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    TabLayout tabLayout;
    ViewPager viewPager;

    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);tabLayout = findViewById(R.id.tl_tabs);
        viewPager = findViewById(R.id.vp_content);

        fragments.add(MyFragment2.newInstance("11111", "11111"));
        fragments.add(MyFragment2.newInstance("22222", "22222"));
        fragments.add(MyFragment2.newInstance("33333", "33333"));
        fragments.add(MyFragment2.newInstance("44444", "44444"));
        fragments.add(MyFragment2.newInstance("55555", "55555"));
        titles.add("fragment1");
        titles.add("fragment2");
        titles.add("fragment3");
        titles.add("fragment4");
        titles.add("fragment5");
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {

                return titles.get(position);
            }
        });

        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
