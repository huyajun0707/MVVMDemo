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
 * @date :   2019/2/27 16:40
 * @description : 进度条
 * =========================================================
 */
public class ProgressView extends View {

    private Paint paint;
    private static final int RADIUS = (int) Utils.dpToPixel(100);
    private static final int RING_WIDTH = (int) Utils.dpToPixel(10);
    private RectF rectF = new RectF();
    Paint.FontMetrics fontMetrics = new Paint.FontMetrics();

    public ProgressView(Context context) {
        super(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF.set(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#FF858585"));
        paint.setStrokeWidth(RING_WIDTH);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, RADIUS, paint);
        paint.setColor(Color.parseColor("#FFE16B93"));
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(rectF, -90, 270, false, paint);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(Utils.dpToPixel(35));
        paint.setColor(Color.RED);
        paint.getFontMetrics(fontMetrics);
        float offset = (fontMetrics.ascent + fontMetrics.descent) / 2;
        canvas.drawText("aaaa", getWidth() / 2, getHeight() / 2 - offset, paint);


    }
}
