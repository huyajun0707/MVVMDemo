package com.hyj.demo.demo0117;

import android.graphics.Rect;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewDebug;
import android.view.ViewParent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.SimpleTimeZone;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/1/17 9:39
 * @description :
 * =========================================================
 */
public class Test {
    private static final String TAG = "Test";
    private boolean flag = false;

    public static void main(String args[]) {
        //        String number = "1.235";
        //        String numberFormat = String.format("%.2f", number);
        //        String numberFormat = String.format(Locale.CHINA, "%.2f", number);
        //        System.out.print(numberFormat);

        //        Thread thread1 = new Thread(new Runnable() {
        //            @Override
        //            public void run() {
        //                for (int i = 1; i <= 100; i++) {
        //                    if (i % 2 == 0) {
        //                        System.out.println("thread1: " + i);
        //                    }
        //                }
        //            }
        //        });
        //
        //        Thread thread2 = new Thread(new Runnable() {
        //            @Override
        //            public void run() {
        //
        //                for (int i = 1; i <= 100; i++) {
        //                    if (i % 2 != 0) {
        //                        System.out.println("thread2: " + i);
        //                    }
        //                }
        //            }
        //        });
        //        thread1.start();
        //        thread2.start();
        //        HashMap hashMap = new HashMap();
        //        hashMap.put(null,"000");
        //        System.out.print(hashMap.get(null));
        //    String s = "";
        //    final int num ;
        //    num=11;
        //    final String str;
        //    str="11";

        String editRate = "281328000 11728249".substring(0, 2);
        String duration = "187554";
        int time = Integer.parseInt(duration) / Integer.parseInt(editRate);
        System.out.println("time:" + time);

    }

    public static String getServiceTimeFrorMH(String time) {
        if (!TextUtils.isEmpty(time) && time.contains("T")) {
            return time.replace("T", "  ");
        }

        return time;

    }

    void invalidate(boolean invalidateCache) {
        if (ViewDebug.TRACE_HIERARCHY) {
            ViewDebug.trace(this, ViewDebug.HierarchyTraceType.INVALIDATE);
        }
        if (skipInvalidate()) {
            return;
        }
        if ((mPrivateFlags & (DRAWN | HAS_BOUNDS)) == (DRAWN | HAS_BOUNDS) || (invalidateCache && (mPrivateFlags & DRAWING_CACHE_VALID) == DRAWING_CACHE_VALID) || (mPrivateFlags & INVALIDATED) != INVALIDATED || isOpaque() != mLastIsOpaque) {
            mLastIsOpaque = isOpaque();
            mPrivateFlags &= ~DRAWN;
            mPrivateFlags |= DIRTY;
            if (invalidateCache) {
                mPrivateFlags |= INVALIDATED;
                mPrivateFlags &= ~DRAWING_CACHE_VALID;
            }
            final AttachInfo ai = mAttachInfo;
            final ViewParent p = mParent;
            if (!HardwareRenderer.RENDER_DIRTY_REGIONS) {
                if (p != null && ai != null && ai.mHardwareAccelerated) {

                    p.invalidateChild(this, null);
                    return;
                }
            }
            if (p != null && ai != null) {
                final Rect r = ai.mTmpInvalRect;
                r.set(0, 0, mRight - mLeft, mBottom - mTop);
                p.invalidateChild(this, r);
            }
        }
    }

}
