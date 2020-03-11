package com.hyj.demo.viewdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * @author ： HuYajun <huyajun0707@gmail.com>
 * @version ： 1.0
 * @date ： 2019-10-18 15:37
 * @depiction ：
 */
@SuppressLint("AppCompatCustomView")
public class SupEditText extends EditText {
    public SupEditText(Context context) {
        super(context);
    }

    public SupEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SupEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SupEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private OnFinishComposingListener mFinishComposingListener;

    public void setOnFinishComposingListener(OnFinishComposingListener listener) {
        this.mFinishComposingListener = listener;
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new MyInputConnection(super.onCreateInputConnection(outAttrs), false);
    }

    public class MyInputConnection extends InputConnectionWrapper {
        public MyInputConnection(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        @Override
        public boolean finishComposingText() {
            boolean finishComposing = super.finishComposingText();
            if (mFinishComposingListener != null) {
                mFinishComposingListener.finishComposing();
            }
            return finishComposing;
        }
    }

    public interface OnFinishComposingListener {
        public void finishComposing();
    }
}

