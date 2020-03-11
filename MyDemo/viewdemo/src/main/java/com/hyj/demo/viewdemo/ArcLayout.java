package com.hyj.demo.viewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * @author ： HuYajun <huyajun0707@gmail.com>
 * @version ： 1.0
 * @date ： 2020-02-28 15:36
 * @depiction ：
 */
public class ArcLayout extends RelativeLayout {
    private int width, height;
    private float arcHeight = 0;

    public ArcLayout(Context context) {
        super(context, null);
    }

    public ArcLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public ArcLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public ArcLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable
                .ArcLayout);
        arcHeight = typedArray.getDimension(R.styleable.ArcLayout_arcHeight,
                20f);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        Path path = new Path();
        //贝塞尔曲线
        path.moveTo(0f, height - arcHeight);
        path.quadTo(width / 2f, height, width, height - arcHeight);
        path.lineTo(width, height);
        path.lineTo(0f,height);
        path.close();
        //关闭硬件加速，会使内部view的阴影效果消失
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        int saveCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        //抗锯齿
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawPath(path, paint);
        canvas.restoreToCount(saveCount);
        paint.setXfermode(null);



//        canvas.clipPath()

    }
}
