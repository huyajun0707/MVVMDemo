package com.hyj.demo.viewdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;

import java.io.File;
import java.io.IOException;

/**
 * @author ： HuYajun <huyajun0707@gmail.com>
 * @version ： 1.0
 * @date ： 2020-06-29 15:18
 * @depiction ：
 */
public class BitmapView {


    public Bitmap loadBitmap(String path) {
        File file = new File(path);
        Bitmap bitmap = null;
        try {
            //创建实例
            BitmapRegionDecoder mDecoder = BitmapRegionDecoder.newInstance(file.getAbsolutePath(), false);
            //获取图片宽高
            mDecoder.getWidth();
            mDecoder.getHeight();
            //加载（10,10）-(80,80)区域内原始精度的Bitmap对象
            Rect rect = new Rect(10, 10, 80, 80);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            bitmap = mDecoder.decodeRegion(rect, options);
            //回收释放Native层内存
            mDecoder.recycle();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
