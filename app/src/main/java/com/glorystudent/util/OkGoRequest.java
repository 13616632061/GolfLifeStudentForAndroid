package com.glorystudent.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;


import com.glorystudent.entity.AliyunRequestEntity;
import com.glorystudent.golflibrary.util.MD5Util;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.golflibrary.util.ZipUtil;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;


import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by hyj on 2016/12/26.
 */
public class OkGoRequest {
    private static final String TAG = "OkGoRequest";
    private OnOkGoUtilListener onOkGoUtilListener;
    private OnGetOssListener onGetOssListener;
    private Context okgocontext;

    public static OkGoRequest getOkGoRequest() {
        return new OkGoRequest();
    }

    public OkGoRequest setOnOkGoUtilListener(OnOkGoUtilListener onOkGoUtilListener) {
        this.onOkGoUtilListener = onOkGoUtilListener;
        return this;
    }

    public OkGoRequest setOnGetOssListener(OnGetOssListener onGetOssListener) {
        this.onGetOssListener = onGetOssListener;
        return this;
    }

    public void getAliyunOSS(Context context) {
        okgocontext = context;
        String request = RequestUtil.getEmptyParameter(context);
        Log.i(TAG, "getAliyunOSS: " + request);
        String url = "/api/APISettings/SelectAllByOSS";
        OkGo.post(Constants.BASE_URL + url)
                .tag(context)
                .params("request", request)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jo = new JSONObject(s);
                            String statuscode = jo.getString("statuscode");
                            String statusmessage = jo.getString("statusmessage");
                            if (statuscode.equals("1")) {
                                AliyunRequestEntity aliyunRequestEntity = new Gson().fromJson(jo.toString(), AliyunRequestEntity.class);
                                List<AliyunRequestEntity.ListsettingvalueBean> listsettingvalue = aliyunRequestEntity.getListsettingvalue();
                                for (int i = 0; i < listsettingvalue.size(); i++) {
                                    SharedUtil.putString(Constants.SETTING[i], listsettingvalue.get(i).getSettingvalue());
                                }
                                if (onGetOssListener != null) {
                                    onGetOssListener.getOssSucceed(aliyunRequestEntity);
                                }
                            } else {
                                Toast.makeText(okgocontext, "请重新登录", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {

                    }
                });
    }

    public void getEntityData(Context context, final String url,final String request) {
        OkGo.post(Constants.BASE_URL + url)
                .tag(context)
                .params("request", request)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (onOkGoUtilListener != null) {
                            Log.i(TAG, "-----------------------------------------start-------------------------------");
                            Log.i(TAG, "URL:{"+Constants.BASE_URL + url+"}");
                            Log.i(TAG, "Request:{"+request+"}");
                            Log.i(TAG, "Response:{"+s+"}");
                            Log.i(TAG, "------------------------------------------end--------------------------------");
                            onOkGoUtilListener.parseDatas(s);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        if (onOkGoUtilListener != null) {
                            onOkGoUtilListener.requestFailed();
                        }
                    }
                });
    }


    /**
     * TODO 根据文件路径上传图片
     */
    public void upLoadFile(Context context, String filePath) {
        int bitmapSize = 0;
        File file = new File(filePath);
        String name = file.getName();
        String[] split = name.split("\\.");

        String headStr = split[0];
        //设置图片存储路径
        String picName = filePath;
        //获取图片的大小

        bitmapSize = (int) file.length();
        Log.d("OkGo", "upLoadFile: --->" + name + " " + picName + " " + bitmapSize);
        //Gzip压缩，并对其Base64转换成字符串
        String bitmapStrBase64 = ZipUtil.compressForGzip(picName);
        //对图片名称MD5加密
        String headPicMd5 = MD5Util.MD5(headStr);
        //图片全称
        String headPicName = name;
        //图片MD5名称
        String partmd5 = headPicMd5;
        //图片最终上传的base64字符串图片数据
        String partdata = bitmapStrBase64;
        //上传路径
        String url = Constants.BASE_URL + "/api/APIUpLoadFile/UploadFile";
        Log.d("ceshi", "upLoadFile: --->测试");
        String request = RequestUtil.getFileUp(context, headPicMd5, headPicName, partmd5, bitmapSize, bitmapSize, partdata);
        FileUtil.saveFile(request, ImageUtil.getSDCardPath() + "/ceshi.txt");
        Log.d("ceshi", "upLoadFile: --->" + request);
        OkGo.post(url)
                .tag(this)
                .params("request", request)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (onOkGoUtilListener != null) {
                            onOkGoUtilListener.parseDatas(s);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        if (onOkGoUtilListener != null) {
                            onOkGoUtilListener.requestFailed();
                        }
                    }
                });
    }


    /**
     * TODO 通过对bitmap，进行保存，gzip压缩，再通过base64转成字符串，上传到服务器
     *
     * @param context
     * @param mbitmap
     */
    public void upImgDatas(Context context, Bitmap mbitmap) {
        int bitmapSize = 0;
        String headStr = String.valueOf(System.currentTimeMillis());
        if (mbitmap != null) {
            //设置图片存储路径
            String picName = ImageUtil.saveBitmap(mbitmap, headStr);//保存剪切之后的图片,并得到图片路径
            //获取图片的大小
            FileInputStream inputStream = null;

            bitmapSize = (int) (new File(picName).length());
            //Gzip压缩，并对其Base64转换成字符串
            String bitmapStrBase64 = ZipUtil.compressForGzip(picName);
            //对图片名称MD5加密
            String headPicMd5 = MD5Util.MD5(headStr);
            //图片全称
            String headPicName = headStr + ".png";
            //图片MD5名称
            String partmd5 = headPicMd5;
            //图片最终上传的base64字符串图片数据
            String partdata = bitmapStrBase64;
            //上传路径
            Log.d("json", "upImgDatas: --->1" + headPicMd5);
            Log.d("json", "upImgDatas: --->2" + headPicName);
            Log.d("json", "upImgDatas: --->3" + partmd5);
            Log.d("json", "upImgDatas: --->4" + bitmapSize);
            Log.d("json", "upImgDatas: --->5" + bitmapSize);
            Log.d("json", "upImgDatas: --->5" + partdata);
            String url = Constants.BASE_URL + "/api/APIUpLoadFile/UploadFile";
            String request = RequestUtil.getFileUp(context, headPicMd5, headPicName, partmd5, bitmapSize, bitmapSize, partdata);
            Log.d("json", "upImgDatas: --->" + request);
            OkGo.post(url)
                    .tag(this)
                    .params("request", request)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            if (onOkGoUtilListener != null) {
                                onOkGoUtilListener.parseDatas(s);
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            if (onOkGoUtilListener != null) {
                                onOkGoUtilListener.requestFailed();
                            }
                        }
                    });
        }
    }

    public interface OnOkGoUtilListener {
        void parseDatas(String json);

        void requestFailed();
    }

    public interface OnGetOssListener {
        void getOssSucceed(AliyunRequestEntity aliyunRequestEntity);
    }
}
