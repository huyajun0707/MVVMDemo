package com.hyj.demo.viewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/2/27 14:02
 * @description :  叠加效果
 * =========================================================
 */
public class AvatarView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int width = (int) Utils.dpToPixel(300);
    private int padding = (int) Utils.dpToPixel(50);
    private int edge_width = (int) Utils.dpToPixel(10);
    private Bitmap bitmap;
    private Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    private RectF rectF = new RectF();

    public AvatarView(Context context) {
        super(context, null);
    }

    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setColor(Color.parseColor("#FFF7C136"));
        bitmap = getBitmap(width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF.set(padding, padding, padding + width, padding + width);
    }

    private Bitmap getBitmap(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //如果设置为true,将不返回bitmap,但是Bitmap的outWidth,outHeight等属性将会赋值,允许调用查询Bitmap,而不需要为Bitmap分配内存.
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.img_1, options);
        options.inJustDecodeBounds = false;
        //设置位图的像素密度，即每英寸有多少个像素
        options.inDensity = options.outWidth;
        //设置绘制位图的屏幕密度,与inScale和inDesity一起使用,来对位图进行放缩.
        options.inTargetDensity = width;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_1, options);
        return Bitmap.createScaledBitmap(bitmap, width, width, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //加边  先画一个外部大圆
        canvas.drawOval(rectF, paint);
        //设置离屏缓冲
        int saved = canvas.saveLayer(rectF, paint);
        //圆里面的小圆
        canvas.drawOval(padding + edge_width, padding + edge_width, padding + width - edge_width, padding + width - edge_width, paint);
        //设置叠加效果
        paint.setXfermode(xfermode);
        canvas.drawBitmap(bitmap, padding, padding, paint);
        //恢复Canvas的状态
        canvas.restoreToCount(saved);


    }
}
