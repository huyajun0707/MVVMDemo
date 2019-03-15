package com.hyj.demo.viewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/2/28 15:30
 * @description :  camera 变换  倒着绘制   因为有  Z坐标
 * =========================================================
 */
public class CameraView extends View {
    private Paint paint;
    private Bitmap bitmap;
    private Camera camera;
    private static final int WIDTH = (int) Utils.dpToPixel(150);

    public CameraView(Context context) {
        super(context, null);
    }

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap = getBitmap(WIDTH);
        camera = new Camera();
        camera.setLocation(0, 0, -8);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //上半部分
        canvas.save();
        canvas.translate(100 + bitmap.getWidth() / 2, 100 + bitmap.getHeight() / 2);
        canvas.rotate(-20);
        camera.rotateX(-30);
        camera.applyToCanvas(canvas);
        canvas.clipRect(-bitmap.getWidth(), -bitmap.getHeight(), bitmap.getWidth(), 0);
        canvas.rotate(20);
        canvas.translate(-(100 + bitmap.getWidth() / 2), -(100 + bitmap.getHeight() / 2));
        canvas.drawBitmap(bitmap, 100, 100, paint);
        canvas.restore();
        //下半部分
        canvas.save();
        canvas.translate(100 + bitmap.getWidth() / 2, 100 + bitmap.getHeight() / 2);
        canvas.rotate(-20);
        camera.rotateX(60);
        camera.applyToCanvas(canvas);
        canvas.clipRect(-bitmap.getWidth(), 0, bitmap.getWidth(), bitmap.getHeight());
        canvas.rotate(20);
        canvas.translate(-(100 + bitmap.getWidth() / 2), -(100 + bitmap.getHeight() / 2));
        canvas.drawBitmap(bitmap, 100, 100, paint);
        canvas.restore();
    }


    private Bitmap getBitmap(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.img_1, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.img_1, options);
    }

}
