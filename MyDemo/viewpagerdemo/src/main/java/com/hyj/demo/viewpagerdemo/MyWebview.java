package com.hyj.demo.viewpagerdemo;

import android.content.Context;
import androidx.core.view.MotionEventCompat;
import androidx.viewpager.widget.ViewPager;
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
                    requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_MOVE:
                    requestDisallowInterceptTouchEvent(!isScrollY);
                    break;
                default:
                    requestDisallowInterceptTouchEvent(false);
                    break;

            }
        } else {
            requestDisallowInterceptTouchEvent(true);
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
        isScrollY = clampedY || clampedX;//是否达到滚动范围的最大值
    }
}
