package com.hyj.demo.viewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class DownloadProgressView extends View {
    public static String TAG = "DownloadProgressView";
    private Paint paint;
    private int radius = (int) Utils.dpToPixel(15);
    private final int RING_WIDTH = (int) Utils.dpToPixel(2);
    private int viewSize;
    private @ColorInt
    int progressColor = Color.parseColor("#f56e1d");
    private @ColorInt
    int borderColor = Color.parseColor("#303F9F");
    private RectF rectFPro = new RectF();
    private RectF rectFBitmap = new RectF();
    int progress;
    private boolean isStop;
    private Context context;
    private Bitmap downloadBitmap,stopBitmap;

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        this.isStop = stop;
        if(progress==100)
            return;
        invalidate();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        if(progress>100)
            progress=100;
        this.progress = progress;
        invalidate();
    }

    public DownloadProgressView(Context context) {
        super(context, null);
    }

    public DownloadProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DownloadProgressView);
        borderColor = typedArray.getColor(R.styleable.DownloadProgressView_borderColor, borderColor);
        progressColor = typedArray.getColor(R.styleable.DownloadProgressView_progressColor, progressColor);
        progress = typedArray.getColor(R.styleable.DownloadProgressView_progress, 0);
        downloadBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_state_download);
        stopBitmap =BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_state_pause);

    }

    private Bitmap getBitmap() {
        if (isStop)
            return stopBitmap;
        else
            return downloadBitmap;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewSize = Math.min(getMeasuredWidth(), getMeasuredHeight());
        radius = viewSize / 2 - RING_WIDTH;
        rectFPro.set(RING_WIDTH, RING_WIDTH, viewSize - RING_WIDTH, viewSize - RING_WIDTH);
        rectFBitmap.set(viewSize / 3, viewSize / 3, viewSize / 3 * 2, viewSize / 3 * 2);
        setMeasuredDimension(viewSize, viewSize);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

//    @Override
//    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
//        super.onSizeChanged(width, height, oldw, oldh);
//        Log.d(TAG, "onSizeChanged: " + width);
////        width = Math.min(width, height);
//        rectFPro.set(viewSize / 2 - radius, viewSize / 2 - radius, viewSize / 2 + radius, viewSize / 2 + radius);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(borderColor);
        paint.setStrokeWidth(RING_WIDTH);
        canvas.drawCircle(radius + RING_WIDTH, radius + RING_WIDTH, radius, paint);
        paint.setColor(progressColor);
        paint.setStrokeCap(Paint.Cap.ROUND);
        float sweepAngle = progress * 36 / 10;
        canvas.drawArc(rectFPro, -90, sweepAngle, false, paint);
        canvas.drawBitmap(getBitmap(), null, rectFBitmap, paint);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        downloadBitmap.recycle();
        downloadBitmap=null;
        stopBitmap.recycle();
        stopBitmap=null;
    }
}
