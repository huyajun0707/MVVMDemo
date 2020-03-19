package com.example.contenproviderdemo;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;

/**
 * @author ： HuYajun <huyajun0707@gmail.com>
 * @version ： 1.0
 * @date ： 2020-03-16 11:28
 * @depiction ： 观察 Uri引起 ContentProvider 中的数据变化 & 通知外界（即访问该数据访问者
 */
public class TestContentObserver extends ContentObserver {

    private Context mContext;

    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public TestContentObserver(Handler handler, Context context) {
        super(handler);
        this.mContext = context;
    }

    /**
     * 当ContentObserver所观察的Uri发生变化时，便会触发它回调onChange方法
     *
     * @param selfChange
     */
    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);

    }
}
