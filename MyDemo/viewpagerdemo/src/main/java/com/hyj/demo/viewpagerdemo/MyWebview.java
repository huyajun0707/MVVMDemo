package com.hyj.demo.viewpagerdemo;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/4/29 18:17
 * @description :
 * =========================================================
 */
public class MyWebview extends WebView {
    private boolean isScrollY;

    public MyWebview(Context context) {
        super(context);
    }

    public MyWebview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyWebview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEventCompat.getPointerCount(event) == 1) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isScrollY = false;
//                    getViewPager(this).requestDisallowInterceptTouchEvent(true);
                    getParent().getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_MOVE:
                    getParent().getParent().requestDisallowInterceptTouchEvent(isScrollY);
                    break;
                default:
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                    break;

            }
        } else {
            getParent().getParent().requestDisallowInterceptTouchEvent(true);
        }

        return super.onTouchEvent(event);


    }

    private ViewPager getViewPager(View view) {
        if (view.getParent() != null) {
            if (view.getParent() instanceof ViewPager) {
                return (ViewPager) view.getParent();
            } else {
                getViewPager(view);
            }
        }
        return null;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        isScrollY = clampedY;
    }
}
