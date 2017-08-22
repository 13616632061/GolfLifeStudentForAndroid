package com.glorystudent.aliyun;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ResumableUploadRequest;
import com.alibaba.sdk.android.oss.model.ResumableUploadResult;

import java.io.File;

/**
 * Created by zhouzhuo on 12/3/15.
 */
public class ResuambleUploadSamples {

    private OSS oss;
    private String testBucket;
    private String testObject;
    private String uploadFilePath;
    private ProgressBar pb;
    private OnProgressListener onProgressListener;
    private OnUpLoadVideoListener onUpLoadVideoListener;
    private boolean first = true;
    private ResumableUploadRequest request;
    private OSSAsyncTask resumableTask;

    public ResuambleUploadSamples setOnProgressListener(OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
        return this;
    }

    public ResuambleUploadSamples setOnUpLoadVideoListener(OnUpLoadVideoListener onUpLoadVideoListener) {
        this.onUpLoadVideoListener = onUpLoadVideoListener;
        return this;
    }

    public ResuambleUploadSamples(OSS client, String testBucket, String testObject, String uploadFilePath) {
        this.oss = client;
        this.testBucket = testBucket;
        this.testObject = testObject;
        this.uploadFilePath = uploadFilePath;
    }

    public ResuambleUploadSamples(OSS client, String testBucket, String testObject, String uploadFilePath, ProgressBar pb) {
        this.oss = client;
        this.testBucket = testBucket;
        this.testObject = testObject;
        this.uploadFilePath = uploadFilePath;
        this.pb = pb;
    }

    // 异步断点上传，不设置记录保存路径，只在本次上传内做断点续传
    public void resumableUpload(final Context context) {
        // 创建断点上传请求
        ResumableUploadRequest request = new ResumableUploadRequest(testBucket, testObject, uploadFilePath);
        // 设置上传过程回调
        request.setProgressCallback(new OSSProgressCallback<ResumableUploadRequest>() {
            @Override
            public void onProgress(ResumableUploadRequest request, long currentSize, long totalSize) {
                Log.d("resumableUpload", "currentSize: " + currentSize + " totalSize: " + totalSize + "当前进度---》" + currentSize * 1.0 / totalSize * 1.0);
            }
        });
        // 异步调用断点上传
        OSSAsyncTask resumableTask = oss.asyncResumableUpload(request, new OSSCompletedCallback<ResumableUploadRequest, ResumableUploadResult>() {
            @Override
            public void onSuccess(ResumableUploadRequest request, ResumableUploadResult result) {
                Toast.makeText(context, "调用了断点续传", Toast.LENGTH_SHORT).show();
                Log.d("resumableUpload", "success!");
            }

            @Override
            public void onFailure(ResumableUploadRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });

        resumableTask.waitUntilFinished();
    }

    // 异步断点上传，设置记录保存路径，即使任务失败，下次启动仍能继续
    public void resumableUploadWithRecordPathSetting() {

        String recordDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() + "/oss_record/";

        File recordDir = new File(recordDirectory);

        // 要保证目录存在，如果不存在则主动创建
        if (!recordDir.exists()) {
            recordDir.mkdirs();
        }
        // 创建断点上传请求，参数中给出断点记录文件的保存位置，需是一个文件夹的绝对路径
        request = new ResumableUploadRequest(testBucket, testObject, uploadFilePath, recordDirectory);
        // 请求异常
        // 本地异常如网络异常等
        // 服务异常
        resumableTask = oss.asyncResumableUpload(request, new OSSCompletedCallback<ResumableUploadRequest, ResumableUploadResult>() {
            @Override
            public void onSuccess(ResumableUploadRequest request, ResumableUploadResult result) {
                Log.d("resumableUpload123", "断点续传success!");
                String eTag = result.getETag();
                try {
                    String url = oss.presignPublicObjectURL(testBucket, testObject);
                    if (onUpLoadVideoListener != null) {
                        onUpLoadVideoListener.onUpSuccess(url);
                    }
                    Log.d("print", "onSuccess:  url ----> " + url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(ResumableUploadRequest request, ClientException clientExcepion, ServiceException serviceException) {
                if (onUpLoadVideoListener != null) {
                    onUpLoadVideoListener.onUpFailure();
                }
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
        // 设置上传过程回调
        request.setProgressCallback(new OSSProgressCallback<ResumableUploadRequest>() {
            @Override
            public void onProgress(ResumableUploadRequest request, long currentSize, long totalSize) {
                if (pb != null) {
//                    pb.setMax((int) totalSize);
//                    pb.setProgress((int) currentSize);
                    pb.setMax(100);
                    pb.setProgress((int) Math.round(currentSize * 100.0 / totalSize));
                }
                if (onProgressListener != null) {
                    Log.d("print", "onProgress: ");
                    onProgressListener.onProgress(totalSize, currentSize);
                }
                Log.d("resumableUpload", "currentSize: " + "    " + currentSize + " totalSize: " + totalSize + "当前进度---》" + currentSize * 1.0 / totalSize * 1.0);
            }
        });

//        resumableTask.waitUntilFinished();
    }

    public void deleteUpLoad() {
        Log.d("hyj", "deleteUpLoad: 被删除了");
        resumableTask.cancel();
        request.deleteUploadOnCancelling();
    }

    public void cancelUpLoad() {
        resumableTask.cancel();
    }

    public interface OnProgressListener {
        void onProgress(long sum, long current);
    }

    public interface OnUpLoadVideoListener {
        void onUpSuccess(String url);

        void onUpFailure();
    }

}
