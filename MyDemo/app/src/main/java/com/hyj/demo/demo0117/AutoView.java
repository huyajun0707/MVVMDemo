package com.hyj.demo.demo0117;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/4/2 14:30
 * @description :
 * =========================================================
 */
public class AutoView extends ViewGroup {
    public AutoView(Context context) {
        super(context);
    }

    public AutoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        postInvalidateOnAnimation();
    }



}
