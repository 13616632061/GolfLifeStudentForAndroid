package com.glorystudent.golflibrary.util;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by hyj on 2017/3/21.
 */
public class ImageCacheUtil {
    public static String getSDcardPath() {
        String path = Environment.getExternalStorageDirectory().getPath() + "/golf/images";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 保存图片
     * @param mBitmap
     */
    public static String saveBitmap(String path, Bitmap mBitmap)  {
        File f = new File(path);
        if (!f.getParentFile().exists()) {
            f.mkdirs();
        }
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f.getPath();
    }
}
