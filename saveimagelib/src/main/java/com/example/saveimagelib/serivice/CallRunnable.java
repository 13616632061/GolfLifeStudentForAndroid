package com.example.saveimagelib.serivice;

import android.graphics.Bitmap;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

import static android.content.ContentValues.TAG;

/**
 * Created by billlamian on 2017/8/1.
 */

public class CallRunnable implements Callable<String> {
    private Bitmap bitmap;
    private String fileName;

    public CallRunnable(Bitmap bitmap, String fileName) {
        this.bitmap = bitmap;
        this.fileName = fileName;
    }

    @Override
    public String call() throws Exception {
        ByteArrayOutputStream babs = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, babs);
        try {
            IOUtils.write(babs.toByteArray(), new FileOutputStream(fileName));
//            System.out.print("图片："+fileName+"====写入成功");
            Log.d(TAG, "图片：" + fileName + "====写入成功");
            return fileName + "====写入成功";
        } catch (IOException e) {
            return fileName + "====写入失败";

        } finally {
            IOUtils.closeQuietly(babs);
        }
    }
}
