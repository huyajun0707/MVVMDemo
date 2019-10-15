package com.hyj.demo.viewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/2/26 15:20
 * @description : 仪表盘
 * =========================================================
 */
public class DashboardView extends View {
    private static final int ANGLE = 120;
    private static final int RADIUS = 300;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path path;
    private Path dash;
    private PathMeasure pathMeasure;
    private static final int LENGTH = 200;

    public DashboardView(Context context) {
        super(context, null);
    }

    public DashboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        dash = new Path();
        dash.addRect(0, 0, 5, 15, Path.Direction.CW);
        //第二个参数advance 是两个dash之间的距离  第三个参数 phase  第一个dash要空多少距离，第四个参数是风格

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        path = new Path();
        //drawArc(, false, paint);
        path.addArc(50, 50, RADIUS * 2 + 50, RADIUS * 2 + 50, 90 + ANGLE / 2, 360 - ANGLE);
        //第一个参数是要测量的path路径  第二个参数是是否封口
        pathMeasure = new PathMeasure(path, false);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画线
        canvas.drawPath(path, paint);
        float length = pathMeasure.getLength() - 5;
        //画刻度
        paint.setPathEffect(new PathDashPathEffect(dash, length / 20, 0, PathDashPathEffect.Style.ROTATE));
        canvas.drawPath(path, paint);
        //画指针
//        int currentAngle = 90 + ANGLE / 2;
        canvas.drawLine(50 + RADIUS, 50 + RADIUS,
                (float) Math.cos(Math.toRadians(getAngleFromMark(15))) * LENGTH + 50 + RADIUS,
                (float) Math.sin(Math.toRadians(getAngleFromMark(15))) * LENGTH + 50 + RADIUS,
                paint);

    }

    private int getAngleFromMark(int mark) {
        return (int) (90 + (float) ANGLE / 2 + (360 - (float) ANGLE) / 20 * mark);
    }
}
