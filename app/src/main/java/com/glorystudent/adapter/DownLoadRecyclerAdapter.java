package com.glorystudent.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
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

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.glorystudent.aliyun.GetObjectSamples;
import com.glorystudent.entity.AliyunRequestEntity;
import com.glorystudent.entity.CloudVideoEntity;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.golflife.R;
import com.glorystudent.util.Constants;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.util.TimeUtil;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyj on 2017/1/17.
 */
public class DownLoadRecyclerAdapter extends SwipeMenuAdapter<DownLoadRecyclerAdapter.ViewHolder> {
    private final static String TAG = "DownLoadRecyclerAdapter";
    private OSS oss;

    // 运行sample前需要配置以下字段为有效的值
    private static String endpoint;
    private static String accessKeyId;
    private static String accessKeySecret;
    private static String testBucket;
    private static String downloadObject;

    private Context context;
    private LocalBroadcastManager localBroadcastManager;
    private SQLiteDatabase sqLiteDatabase;
    private List<CloudVideoEntity.ListvideosBean> datas;
    private List<CloudVideoEntity.ListvideosBean> deleteDatas;
    private int downloadCount;//正在下载的视频数量
    private MediaMetadataRetriever media;

    public DownLoadRecyclerAdapter(Context context) {
        this.context = context;
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(context.getDatabasePath("video.db"), null);
        datas = new ArrayList<>();
        deleteDatas = new ArrayList<>();
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

    public void setDatas(List<CloudVideoEntity.ListvideosBean> datas) {
        this.datas = datas;
        downloadCount = this.datas.size();
        notifyDataSetChanged();
    }

    public void delete(int position) {
        CloudVideoEntity.ListvideosBean bean = datas.get(position);
        deleteDatas.add(bean);
        datas.remove(position);
        notifyDataSetChanged();
        downloadCount--;
        sendBroadcase(downloadCount);
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        GlideUtil.loadImageView(context, datas.get(position).getVideo_picpath(), holder.iv_video_pic);
        holder.tv_video_title.setText(datas.get(position).getVideo_tag());

        holder.tv_upload.setText("已经下载" + 0 + "%");
        final String video_filemd5 = datas.get(position).getVideo_filemd5();

        String state = datas.get(position).getState();
        if (state.equals("2")) {
            //已下载的视频
            File file = new File(getVideoPathFromFileMd5(video_filemd5));
            String sumSize = getStringSize(file.length());
            holder.tv_upload_sumsize.setText(sumSize);
            holder.tv_upload_size.setText(sumSize);
            holder.tv_upload.setText("下载成功");
            holder.pb.setMax(100);
            holder.pb.setProgress(100);
            downloadCount--;
            sendBroadcase(downloadCount);
        } else {
            //需要下载的视频
            String uid = SharedUtil.getString(Constants.USER_ID);
            final String userId = uid.replace("/", "");
            downloadObject = userId + "/videos/" + video_filemd5 + ".mp4";
            String fileDirs = Environment.getExternalStorageDirectory().getPath() + "/golf/download/video/";
            String fileName = System.currentTimeMillis() + ".mp4";

            final String title = datas.get(position).getVideo_tag();
            String content = null;
            String labelNumber = datas.get(position).getVideo_tips();
            if (labelNumber != null && !labelNumber.isEmpty()) {
                content = Constants.LABEL[Integer.valueOf(labelNumber) - 1];
            }
            final String finalContent = content;
            Log.d("adapter", "run: --->" + downloadObject);
            GetObjectSamples download = new GetObjectSamples(oss, testBucket, downloadObject, fileDirs, fileName, holder.pb);
            download.setOnProgressListener(new GetObjectSamples.OnProgressListener() {
                @Override
                public void onProgress(final long sum, final long current) {
                    //更新总大小
                    holder.tv_upload_sumsize.post(new Runnable() {
                        @Override
                        public void run() {
                            String sumSize = getStringSize(sum);
                            holder.tv_upload_sumsize.setText(sumSize);
                        }
                    });
                    //更新下载的大小
                    holder.tv_upload_size.post(new Runnable() {
                        @Override
                        public void run() {
                            String curSize = getStringSize(current);
                            holder.tv_upload_size.setText(curSize);
                        }
                    });
                    //更新进度
                    holder.tv_upload.post(new Runnable() {
                        @Override
                        public void run() {
                            double percent = current * 100.0 / sum;
                            int progess = (int) Math.round(percent);
                            if (progess < 100) {
                                holder.tv_upload.setText("已经下载" + progess + "%");
                            }
                        }
                    });
                }
            });
            download.setOnDownSuccessListener(new GetObjectSamples.OnDownSuccessListener() {
                @Override
                public void onDownComplete(String path) {
                    saveNativeVideo(holder.tv_upload, video_filemd5, path, title, finalContent);
                    downloadCount--;
                    sendBroadcase(downloadCount);
                }
            });
            download.asyncGetObjectSample();
        }
    }

    /**
     * 保存到本地数据库
     *
     * @param fileMD5
     * @param video_path
     * @param title
     * @param content
     */
    public void saveNativeVideo(TextView tv, String fileMD5, String video_path, String title, String content) {
        boolean flag = true;
        for (int i = 0; i < deleteDatas.size(); i++) {
            if (deleteDatas.get(i).getVideo_filemd5().equals(fileMD5)) {
                flag = false;
                break;
            }
        }
        if (flag) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("type", "1");//1拍摄的视频，2本地导入的视频
            contentValues.put("path", video_path);
            contentValues.put("date", RequestUtil.getCurrentTime());
            if (media == null) {
                media = new MediaMetadataRetriever();
            }
            media.setDataSource(video_path);
            String durationMs = media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            String durationTime = TimeUtil.getToDay(Integer.valueOf(durationMs));
            contentValues.put("duration", durationTime);

            if (title != null && !title.isEmpty()) {
                contentValues.put("title", title);
            }
            if (content != null && !content.isEmpty()) {
                contentValues.put("content", content);
            }
            contentValues.put("fileMd5", fileMD5);

            Bitmap bitmap = media.getFrameAtTime();//获取第一帧图片
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            contentValues.put("picBytes", out.toByteArray());
            sqLiteDatabase.insert("videoModel", null, contentValues);
            tv.setText("下载成功");
            downloadCount--;
            sendBroadcase(downloadCount);
            EventBus.getDefault().post(EventBusMapUtil.getStringMap("LocalVideoFragment", "refresh"));
        }
    }

    /**
     * 数据库中查询视频的path
     *
     * @param filemd5
     * @return
     */
    public String getVideoPathFromFileMd5(String filemd5) {
        Cursor cursor = sqLiteDatabase.rawQuery("select * from videoModel where fileMd5 = ?", new String[]{filemd5});
        String path = null;
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                path = cursor.getString(cursor.getColumnIndex("path"));
            }
            cursor.close();
        }
        return path;
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
    private void sendBroadcase(int downloadCount) {
        Intent intent;
        if (downloadCount <= 0) {
            intent = new Intent("com.glory.broadcast.CLOUD_DOWNLOADED");
        } else {
            intent = new Intent("com.glory.broadcast.CLOUD_DOWNLOADING");
            intent.putExtra("uploadCount", downloadCount);
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
