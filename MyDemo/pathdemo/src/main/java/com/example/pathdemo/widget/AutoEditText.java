package com.example.kdemo.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

/**
 * @author ： HuYajun <huyajun0707@gmail.com>
 * @version ： 1.0
 * @date ： 2019-09-26 15:58
 * @depiction ：
 */
@SuppressLint("AppCompatCustomView")
public class AutoEditText extends EditText {

    private Drawable mClearDrawble;
    private Rect mBounds;

    public AutoEditText(Context context) {
        super(context, null);
    }

    public AutoEditText(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AutoEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AutoEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initEditText();
    }

    private void initEditText() {
        setEditTextDrawable();
        addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence,
                                          int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence paramCharSequence,
                                      int arg1, int arg2, int arg3) {
                AutoEditText.this.setEditTextDrawable();
            }
        });
    }

    public void setEditTextDrawable() {
        if (getText().toString().length() == 0) {
            setCompoundDrawables(null, null, null, null);
        } else {
            setCompoundDrawables(null, null, mClearDrawble, null);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mClearDrawble = null;
        mBounds = null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if ((mClearDrawble != null) && (motionEvent.getAction() == MotionEvent.ACTION_UP)) {
            mBounds = mClearDrawble.getBounds();
            Log.d("onTouchEvent", "mBounds: "+mBounds);
            int i = (int) motionEvent.getRawX();

            if (i > getRight() - mBounds.width()) {
                setText("");
                motionEvent.setAction(MotionEvent.ACTION_CANCEL);
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override
    public void setCompoundDrawables(Drawable left,Drawable top, Drawable right,Drawable bottom) {
        if (right != null)
            mClearDrawble = right;
        super.setCompoundDrawables(left,top,right,bottom);
    }
}
