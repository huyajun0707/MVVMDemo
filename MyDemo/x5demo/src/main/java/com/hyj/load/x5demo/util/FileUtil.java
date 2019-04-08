package com.hyj.load.x5demo.util;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/4/8 14:16
 * @description :
 * =========================================================
 */
public class FileUtil {

    private static FileUtil mFileUtil;

    private FileUtil() {
        // cannot be instantiated
    }

    public static synchronized FileUtil getInstance() {
        if (mFileUtil == null) {
            mFileUtil = new FileUtil();
        }
        return mFileUtil;
    }

    public static void releaseInstance() {
        if (mFileUtil != null) {
            mFileUtil = null;
        }
    }

    /**
     * 从assets目录中复制某文件内容
     *
     * @param assetFileName assets目录下的文件
     * @param newFileName   复制到/data/data/package_name/files/目录下文件名
     */
    public void copyAssetsFileToAppFiles(Context context, String assetFileName, String
            newFileName) {
        InputStream is = null;
        FileOutputStream fos = null;
        int buffsize = 1024;

        try {
            is = context.getAssets().open(assetFileName);
            fos = context.openFileOutput(newFileName, Context.MODE_PRIVATE);
            int byteCount = 0;
            byte[] buffer = new byte[buffsize];
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
