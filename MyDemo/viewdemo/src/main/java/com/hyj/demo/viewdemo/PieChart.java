package com.hyj.demo.viewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/2/27 9:30
 * @description :  扇形图
 * =========================================================
 */
public class PieChart extends View {
    private Paint paint;
    private int[] angles = {60, 120, 80, 100};
    private static final int RADIUS = 300;
    private RectF bounds = new RectF();
    private int index = 2;
    private int length = 20;
    private int colors[] = {Color.parseColor("#FFFF4081"), Color.parseColor("#FFE2C234"),
            Color.parseColor("#FF7A6DF1"), Color.parseColor("#FF5EDF59")};


    public PieChart(Context context) {
        super(context, null);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bounds.set(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int currentAngle = 0;
        for (int i = 0; i < angles.length; i++) {
            paint.setColor(colors[i]);
            canvas.save();
            if (i == index)
                //移动画布绘制中心点
                canvas.translate(-(float) (Math.cos(Math.toRadians(angles[i] / 2))) * length, -(float) Math.sin(Math.toRadians(angles[i] / 2)) * length);
            canvas.drawArc(bounds, currentAngle, angles[i], true, paint);
            canvas.restore();
            currentAngle += angles[i];
        }


    }
}
