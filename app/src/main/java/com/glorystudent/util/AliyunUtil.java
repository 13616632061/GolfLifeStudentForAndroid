package com.glorystudent.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.glorystudent.aliyun.ResuambleUploadSamples;
import com.glorystudent.entity.AliyunRequestEntity;
import com.glorystudent.golflibrary.util.SharedUtil;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 */

public class AliyunUtil {
    private static final String TAG = "AliyunUtil";
    private final static int SUCCESS = 0x123;
    private final static int FAILURE = 0x321;
    private static String accessKeyId;
    private static String accessKeySecret;
    private static String endpoint;
    private static String testBucket;
    private static OSS oss;

    /**
     * @param context 上下文
     * @param path    文件路径
     * @param handler 处理返回值handler
     */
    public static void loadOss(final Context context, final String path, final Handler handler) {
        AliyunRequestEntity aliyunOSS = RequestUtil.getAliyunOSS();
        if (aliyunOSS != null) {
            getKeyId(context, path, handler, aliyunOSS);
        } else {
            OkGoRequest.getOkGoRequest().setOnGetOssListener(new OkGoRequest.OnGetOssListener() {
                @Override
                public void getOssSucceed(AliyunRequestEntity aliyunRequestEntity) {
                    List<AliyunRequestEntity.ListsettingvalueBean> listsettingvalue = aliyunRequestEntity.getListsettingvalue();
                    for (int i = 0; i < listsettingvalue.size(); i++) {
                        SharedUtil.putString(Constants.SETTING[i], listsettingvalue.get(i).getSettingvalue());
                    }
                    getKeyId(context, path, handler, aliyunRequestEntity);
                }
            }).getAliyunOSS(context);
        }
    }

    private static void getKeyId(Context context, String path, Handler handler, AliyunRequestEntity aliyunRequestEntity) {
        List<AliyunRequestEntity.ListsettingvalueBean> listsettingvalue = aliyunRequestEntity.getListsettingvalue();
        accessKeyId = listsettingvalue.get(0).getSettingvalue();
        accessKeySecret = listsettingvalue.get(1).getSettingvalue();
        endpoint = "https://" + listsettingvalue.get(2).getSettingvalue() + ".aliyuncs.com";
        testBucket = listsettingvalue.get(3).getSettingvalue();
        getOss(context, accessKeyId, accessKeySecret, endpoint);
        uploadImage(context, path, handler);
    }

    private static void getOss(Context context, String accessKeyId, String accessKeySecret, String endpoint) {
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        oss = new OSSClient(context.getApplicationContext(), endpoint, credentialProvider, conf);
    }

    private static void uploadImage(final Context context, String path, final Handler handler) {
        String fileMD5 = FileToMD5Util.getFileMD5(new File(path));
        String userId = SharedUtil.getString(Constants.USER_ID).replace("/", "");
        final String uploadFilePath = path;
        final String finalUploadCloud = userId + "/images/" + fileMD5 + ".png";
        new Thread(new Runnable() {
            @Override
            public void run() {
                ResuambleUploadSamples up = new ResuambleUploadSamples(oss, testBucket, finalUploadCloud, uploadFilePath, new ProgressBar(context))
                        .setOnUpLoadVideoListener(new ResuambleUploadSamples.OnUpLoadVideoListener() {

                            @Override
                            public void onUpSuccess(String url) {
                                Log.d(TAG, "onUpSuccess: --->" + "上传阿里云成功：" + url);
                                Message.obtain(handler, SUCCESS, url).sendToTarget();
                            }

                            @Override
                            public void onUpFailure() {
                                Log.d(TAG, "onUpFailure: --->" + "上传阿里云失败");
                                Message.obtain(handler, FAILURE, "").sendToTarget();
                            }
                        });
                up.setOnProgressListener(new ResuambleUploadSamples.OnProgressListener() {
                    @Override
                    public void onProgress(long sum, long current) {
                        final long ss = sum;
                        final long cu = current;
                        Log.d(TAG, "onProgress: " + ss + " " + cu);
                    }
                });
                up.resumableUploadWithRecordPathSetting();
            }
        }).start();
    }
}
