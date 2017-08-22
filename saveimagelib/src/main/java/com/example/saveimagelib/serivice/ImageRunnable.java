package com.example.saveimagelib.serivice;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.ContentValues.TAG;

/**
 * Created by billlamian on 17-7-31.
 */

public class ImageRunnable implements Runnable {
    private Bitmap bitmap;
    private String fileName;
    private Handler handler;
    private ExecutorManager.OnSaveImageListener onSaveImageListener;
    public ImageRunnable(Bitmap bitmap, String fileName,ExecutorManager.OnSaveImageListener onSaveImageListener){
        this.bitmap=bitmap;
        this.fileName=fileName;
        handler=new Handler(Looper.myLooper());
        this.onSaveImageListener=onSaveImageListener;
    }
    @Override
    public void run() {
        ByteArrayOutputStream babs = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, babs);
        try {
            IOUtils.write(babs.toByteArray(),new FileOutputStream(fileName));
//            System.out.print("图片："+fileName+"====写入成功");
            Log.d(TAG,"图片："+fileName+"====写入成功");
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onSaveImageListener.onSuccess(fileName+"写入成功：");
                }
            });

        } catch (IOException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onSaveImageListener.onFail(fileName+"写入失败：");
                }
            });


        }finally {
            IOUtils.closeQuietly(babs);
        }
    }
}
