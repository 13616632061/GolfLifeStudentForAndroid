package com.glorystudent.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.glorystudent.aliyun.ResuambleUploadSamples;
import com.glorystudent.entity.AliyunRequestEntity;
import com.glorystudent.entity.AliyunVideoRequestEntity;
import com.glorystudent.entity.UpLoadAliyunEntity;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.golflife.R;
import com.glorystudent.util.Constants;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.util.FileToMD5Util;
import com.glorystudent.util.ImageUtil;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.google.gson.Gson;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyj on 2017/1/17.
 */
public class UpLoadRecyclerAdapter extends SwipeMenuAdapter<UpLoadRecyclerAdapter.ViewHolder> {
    private final static String TAG = "UpLoadRecyclerAdapter";
    private OSS oss;

    // 运行sample前需要配置以下字段为有效的值
    private static String endpoint;
    private static String accessKeyId;
    private static String accessKeySecret;
    private static String testBucket;

    private static String uploadFilePath;
    private static String uploadObject;

    private Context context;
    private List<UpLoadAliyunEntity> datas;
    private List<UpLoadAliyunEntity> deleteDatas;
    private LocalBroadcastManager localBroadcastManager;
    private int uploadCount;//正在上传的视频数量

    public UpLoadRecyclerAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
        deleteDatas = new ArrayList<>();
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
        loadOss(context);
    }

    private void loadOss(final Context context) {
        AliyunRequestEntity aliyunRequestEntity = RequestUtil.getAliyunOSS();
        if (aliyunRequestEntity != null) {
            Log.i(TAG, "loadOss: 从本地拿配置");
            getKeyId(context, aliyunRequestEntity);
        } else {
            OkGoRequest.getOkGoRequest().setOnGetOssListener(new OkGoRequest.OnGetOssListener() {
                @Override
                public void getOssSucceed(AliyunRequestEntity aliyunRequestEntity) {
                    List<AliyunRequestEntity.ListsettingvalueBean> listsettingvalue = aliyunRequestEntity.getListsettingvalue();
                    for (int i = 0; i < listsettingvalue.size(); i++) {
                        SharedUtil.putString(Constants.SETTING[i], listsettingvalue.get(i).getSettingvalue());
                    }
                    getKeyId(context, aliyunRequestEntity);
                }
            }).getAliyunOSS(context);
        }
    }

    private void getKeyId(Context context, AliyunRequestEntity aliyunRequestEntity) {
        List<AliyunRequestEntity.ListsettingvalueBean> listsettingvalue = aliyunRequestEntity.getListsettingvalue();
        accessKeyId = listsettingvalue.get(0).getSettingvalue();
        accessKeySecret = listsettingvalue.get(1).getSettingvalue();
        endpoint = "https://" + listsettingvalue.get(2).getSettingvalue() + ".aliyuncs.com";
        testBucket = listsettingvalue.get(3).getSettingvalue();
        getOss(context, accessKeyId, accessKeySecret, endpoint);
    }

    private void getOss(Context context, String accessKeyId, String accessKeySecret, String endpoint) {
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        oss = new OSSClient(context.getApplicationContext(), endpoint, credentialProvider, conf);
    }

    public void setDatas(List<UpLoadAliyunEntity> datas) {
        this.datas = datas;
        uploadCount = datas.size();
        Log.d(TAG, "setDatas: " + uploadCount);
        notifyDataSetChanged();
    }

    public void delete(int position) {
        UpLoadAliyunEntity upLoadAliyunEntity = datas.get(position);
        deleteDatas.add(upLoadAliyunEntity);
        datas.remove(position);
        notifyDataSetChanged();
        uploadCount--;
        sendBroadcase(uploadCount);
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_upload_video_list, null);
        return inflate;
    }

    @Override
    public ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new ViewHolder(realContentView);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String path = datas.get(position).getPath();
        File file = new File(path);
        if (!file.exists()) {
            return;//文件不存在直接退出
        }
        //赋值
        holder.tv_video_title.setText(datas.get(position).getTitle());
        byte[] picBytes = datas.get(position).getPicBytes();
        Bitmap bitmap = null;
        if (picBytes != null) {
            bitmap = BitmapFactory.decodeByteArray(picBytes, 0, picBytes.length);
            holder.iv_video_pic.setImageBitmap(bitmap);
        }
        long fileLength = file.length();//得到的是 字节B 单位
        String sumSize = getStringSize(fileLength);
        holder.tv_upload_sumsize.setText(sumSize);

        String state = datas.get(position).getState();
        if (state.equals("2")) {
            //已上传的视频
            holder.tv_upload_size.setText(sumSize);
            holder.tv_upload.setText("上传成功");
            holder.pb.setMax(100);
            holder.pb.setProgress(100);
            uploadCount--;
            sendBroadcase(uploadCount);
        } else if (state.equals("1")) {
            //需要上传的视频
            uploadFilePath = path;
            final String fileMD5 = datas.get(position).getFileMD5();
            final String picPath = ImageUtil.saveBitmap(bitmap, System.currentTimeMillis() + "");
            String uid = SharedUtil.getString(Constants.USER_ID);
            String userId = uid.replace("/", "");
            uploadObject = userId + "/videos/" + fileMD5 + ".mp4";
            final String title = datas.get(position).getTitle();
            final String content = datas.get(position).getContent();
            ResuambleUploadSamples upload = new ResuambleUploadSamples(oss, testBucket, uploadObject, uploadFilePath, holder.pb)
                    .setOnUpLoadVideoListener(new ResuambleUploadSamples.OnUpLoadVideoListener() {
                        @Override
                        public void onUpSuccess(String url) {
                            Log.d("123print", " 获得Object run: ----->" + uploadObject);
                            uploadImg(holder.tv_upload, picPath, url, fileMD5, title, content);
                        }

                        @Override
                        public void onUpFailure() {

                        }
                    });
            upload.setOnProgressListener(new ResuambleUploadSamples.OnProgressListener() {
                @Override
                public void onProgress(final long sum, final long current) {
                    //更新上传大小
                    holder.tv_upload_size.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.tv_upload_size.setText(getStringSize(current));
                        }
                    });
                    //更新进度
                    holder.tv_upload.post(new Runnable() {
                        @Override
                        public void run() {
                            double percent = current * 100.0 / sum;
                            int progess = (int) Math.round(percent);
                            if (progess < 100) {
                                holder.tv_upload.setText("已经上传" + progess + "%");
                            }
                        }
                    });
                }
            });
            upload.resumableUploadWithRecordPathSetting();
        }
    }

    private void uploadImg(final TextView tv_upload, String picPath, final String videoPath, final String fileMD5, final String title, final String content) {
        uploadFilePath = picPath;
        final String picMD5 = FileToMD5Util.getFileMD5(new File(uploadFilePath));
        String uid = SharedUtil.getString(Constants.USER_ID);
        String userId = uid.replace("/", "");
        uploadObject = userId + "/images/" + picMD5 + ".png";
        ResuambleUploadSamples up = new ResuambleUploadSamples(oss, testBucket, uploadObject, uploadFilePath,
                new ProgressBar(context)).setOnUpLoadVideoListener(new ResuambleUploadSamples.OnUpLoadVideoListener() {
            @Override
            public void onUpSuccess(String url) {
                boolean flag = true;
                for (int i = 0; i < deleteDatas.size(); i++) {
                    if (deleteDatas.get(i).getFileMD5().equals(fileMD5)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    AliyunVideoRequestEntity aliyunVideoRequestEntity = new AliyunVideoRequestEntity();
                    AliyunVideoRequestEntity.VideosBean videosBean = new AliyunVideoRequestEntity.VideosBean();
                    videosBean.setVideo_path(videoPath);
                    videosBean.setVideo_filemd5(fileMD5);
                    videosBean.setVideo_picpath(url);
                    videosBean.setVideo_filePicMD5(picMD5);
                    videosBean.setVideo_datetime(RequestUtil.getCurrentTime());
                    videosBean.setVideo_tag(title);
                    String[] label = Constants.LABEL;
                    for (int i = 0; i < label.length; i++) {
                        if (label[i].equals(content)) {
                            videosBean.setVideo_tips((i + 1) + "");
                            break;
                        }
                    }
                    aliyunVideoRequestEntity.setVideos(videosBean);
                    String request = new Gson().toJson(aliyunVideoRequestEntity);
                    String requestJson = RequestUtil.getRequestJson(context, request);
                    String requestUrl = "/api/APIVideos/AddVideos";
                    Log.d(TAG, "onUpSuccess: --->" + requestJson);
                    Log.d(TAG, "onUpSuccess: --->" + Constants.BASE_URL + requestUrl);
                    OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
                        @Override
                        public void parseDatas(String json) {
                            Log.d("print", "parseDatas: --->" + json);
                            try {
                                JSONObject jo = new JSONObject(json);
                                String statuscode = jo.getString("statuscode");
                                String statusmessage = jo.getString("statusmessage");
                                if (statuscode.equals("1")) {
                                    tv_upload.setText("上传成功");
                                    EventBus.getDefault().post(EventBusMapUtil.getStringMap("CloudVideoFragment", "refresh"));
                                    uploadCount--;
                                    sendBroadcase(uploadCount);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void requestFailed() {
                            Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
                        }
                    }).getEntityData(context, requestUrl, requestJson);
                }
            }

            @Override
            public void onUpFailure() {

            }
        });
        up.resumableUploadWithRecordPathSetting();
        up.setOnProgressListener(new ResuambleUploadSamples.OnProgressListener() {
            @Override
            public void onProgress(long sum, long current) {

            }
        });
    }

    /**
     * 根据文件长度显示单位
     *
     * @param length
     * @return
     */
    @NonNull
    private String getStringSize(long length) {
        String size = "";
        DecimalFormat df = new DecimalFormat("0.00");
        if (length > 1024 * 1024 * 1024) {
            //大于1G 单位G
            size = df.format(length * 1.0 / 1024 / 1024 / 1024) + "G";
        } else if (length > 1024 * 1024) {
            //大于1M小于1G 单位M
            size = df.format(length * 1.0 / 1024 / 1024) + "M";
        } else if (length > 1024) {
            //小于1M大于1K 单位K
            size = df.format(length * 1.0 / 1024) + "K";
        } else {
            //小于1K 单位B
            size = length + "B";
        }
        return size;
    }

    /**
     * 通知上个页面显示上传视频个数
     */
    private void sendBroadcase(int uploadCount) {
        Intent intent;
        if (uploadCount <= 0) {
            intent = new Intent("com.glory.broadcast.NATIVE_UPLOADEND");
        } else {
            intent = new Intent("com.glory.broadcast.NATIVE_UPLOADING");
            intent.putExtra("uploadCount", uploadCount);
        }
        localBroadcastManager.sendBroadcast(intent);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_video_pic;
        private TextView tv_video_title;
        private TextView tv_upload;
        private TextView tv_upload_size;
        private TextView tv_upload_sumsize;
        private ProgressBar pb;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_video_pic = (ImageView) itemView.findViewById(R.id.iv_video_pic);
            tv_video_title = (TextView) itemView.findViewById(R.id.tv_video_title);
            tv_upload = (TextView) itemView.findViewById(R.id.tv_upload);
            tv_upload_size = (TextView) itemView.findViewById(R.id.tv_upload_size);
            tv_upload_sumsize = (TextView) itemView.findViewById(R.id.tv_upload_sumsize);
            pb = (ProgressBar) itemView.findViewById(R.id.pb);
        }
    }
}
