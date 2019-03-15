package com.hyj.demo.materialedittext;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/3/5 16:18
 * @description :
 * =========================================================
 */
public class MaterialEditText extends AppCompatEditText {
    private Paint paint;
    private final int TOP_SIZE = (int) Utils.dpToPixel(20);
    private final int TEXT_SIZE = (int) Utils.dpToPixel(15);
    private final int TOP_PADDING = (int) Utils.dpToPixel(23);
    private final int TEXT_OFFSET = (int) Utils.dpToPixel(16);
    private Rect backgroundBounds = new Rect();
    private ObjectAnimator animatorShow;
    private boolean useFloatingLabel;
    float ariety = 1; //变化精度
    private boolean isShow;

    public float getAriety() {
        return ariety;
    }

    public void setAriety(float ariety) {
        this.ariety = ariety;
        invalidate();
    }

    public boolean isUseFloatingLabel() {
        return useFloatingLabel;
    }

    public void setUseFloatingLabel(boolean useFloatingLabel) {
        this.useFloatingLabel = useFloatingLabel;
        changed();
        invalidate();
    }

    public MaterialEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText);
        useFloatingLabel = typedArray.getBoolean(R.styleable.MaterialEditText_useFloatingLabel, false);
        typedArray.recycle();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(TEXT_SIZE);
        getBackground().getPadding(backgroundBounds);
        changed();

    }

    private void changed() {
        if (useFloatingLabel) {
            setPadding(backgroundBounds.left, backgroundBounds.top + TOP_SIZE, backgroundBounds.right, backgroundBounds.bottom);
            addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (TextUtils.isEmpty(s) && isShow) {
                        getAnimator().start();
                        isShow = false;
                    } else if (!TextUtils.isEmpty(s) && !isShow) {
                        getAnimator().reverse();
                        isShow = true;
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } else {
            setPadding(backgroundBounds.left, backgroundBounds.top, backgroundBounds.right, backgroundBounds.bottom);
        }
    }


    @SuppressLint("ObjectAnimatorBinding")
    private ObjectAnimator getAnimator() {
        if (animatorShow == null)
            animatorShow = ObjectAnimator.ofFloat(this, "ariety", 0);
        return animatorShow;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (useFloatingLabel) {
            paint.setAlpha((int) (255 * ariety));
            float extraOffset = TEXT_OFFSET * (1 - ariety);
            canvas.drawText(getHint().toString(), getPaddingLeft(), TOP_PADDING + extraOffset, paint);
        }
    }
}
