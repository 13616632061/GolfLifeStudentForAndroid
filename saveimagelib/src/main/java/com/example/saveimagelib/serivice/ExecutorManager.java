package com.example.saveimagelib.serivice;


import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by billlamian
 */

public class ExecutorManager {
    private static ExecutorManager manager;
    private ExecutorService executor;
    private List<Future<String>> list = new ArrayList<>();

    public static ExecutorManager getInstance() {
        if (manager == null) {
            manager = new ExecutorManager();
            manager.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2 + 1);
        }
        return manager;
    }

    public synchronized void run(Bitmap bitmap, String fileName, OnSaveImageListener onSaveImageListener) {
        Runnable runnable = new ImageRunnable(bitmap, fileName, onSaveImageListener);
        executor.execute(runnable);
        executor.shutdown();
    }

    public synchronized void run(Bitmap bitmap, String fileName) {
        CallRunnable callRunnable = new CallRunnable(bitmap, fileName);
        Future<String> future = executor.submit(callRunnable);
        list.add(future);
    }

    public List<Future<String>> getList() {
        return list;
    }

    public interface OnSaveImageListener {
        void onSuccess(String position);

        void onFail(String s);
    }
}
