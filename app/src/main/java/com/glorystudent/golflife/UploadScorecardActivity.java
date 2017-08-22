package com.glorystudent.golflife;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.GridView;
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
import com.glorystudent.entity.CourtLocationEntity;
import com.glorystudent.entity.SingleLocationEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.util.Constants;
import com.glorystudent.util.FileToMD5Util;
import com.glorystudent.util.ImageTool;
import com.glorystudent.util.ImageUtil;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 上传记分卡
 * Created by hyj on 2016/12/28.
 */
public class UploadScorecardActivity extends BaseActivity implements OkGoRequest.OnOkGoUtilListener {
    private final static String TAG = "UploadScorecardActivity";
    private static final int IMG_COUNT = 8;//最多选IMG_COUNT - 1张图片
    private static final String IMG_ADD_TAG = "a";//添加图片的标记
    private GridView gridView;
    private GVAdapter adapter;//GridView的适配器
    private List<String> list;//本地图片路径
    private List<String> scorecard_image_list;//要上传到服务器的图片路径
    @Bind(R.id.tv_select_date)
    public TextView tv_select_date;//日期
    @Bind(R.id.tv_ballpark_name)
    public TextView tv_ballpark_name;//球场名称
    private int[] times;//记录时间数组
    private String path;//图片路径
    private OSS oss;

    private String provider;//位置提供器
    private LocationManager locationManager;//位置服务
    private Location location;

    // 运行sample前需要配置以下字段为有效的值
    private static String endpoint;
    private static String accessKeyId;
    private static String accessKeySecret;
    private static String uploadFilePath;

    private static String testBucket;
    private static String uploadObject;
    private static String downloadObject;
    private int page = 1;
    private final static int SUCCESS = 0x123;
    private final static int FAILURE = 0x321;
    private final static int UPLOAD = 0x381;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SUCCESS:
                    removeItem();
                    list.add(path);
                    list.add(IMG_ADD_TAG);
                    refreshAdapter();
                    scorecard_image_list.add(aliyunUrl);
                    dismissWaiting();
                    break;
                case FAILURE:
                    dismissWaiting();
                    Toast.makeText(UploadScorecardActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                    break;
                case UPLOAD:
                    showWaiting();
                    Bitmap bitmap = ImageUtil.createImageThumbnail(path);
                    OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
                        @Override
                        public void parseDatas(String json) {
                            try {
                                JSONObject jo = new JSONObject(json);
                                String statuscode = jo.getString("statuscode");
                                String scorecard_image = jo.getString("failePath");
                                if (statuscode.equals("1")) {
                                    if (scorecard_image != null) {
                                        aliyunUrl = scorecard_image;
                                        handler.sendEmptyMessage(SUCCESS);
                                    }
                                } else {
                                    handler.sendEmptyMessage(FAILURE);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void requestFailed() {
                            handler.sendEmptyMessage(FAILURE);
                        }
                    }).upImgDatas(UploadScorecardActivity.this, bitmap);
                    break;
            }
        }
    };
    private String aliyunUrl;


    @Override
    protected int getContentId() {
        return R.layout.activity_upload_scorecar;
    }

    @Override
    protected void init() {
        scorecard_image_list = new ArrayList<>();
        gridView = (GridView) findViewById(R.id.gridview);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        String[] time = dateStr.split("-");
        times = new int[]{Integer.valueOf(time[0]), Integer.valueOf(time[1]), Integer.valueOf(time[2])};
        tv_select_date.setText(dateStr);

        showLoading();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);//获得位置服务
        provider = judgeProvider(locationManager);

        if (provider != null) {//有位置提供器的情况
            //为了压制getLastKnownLocation方法的警告
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                Log.d(TAG, "init: ---1223>" + location.getLatitude() + " " + location.getLongitude());
                SingleLocationEntity singleLocationEntity = new SingleLocationEntity();
                SingleLocationEntity.CourtBean courtBean = new SingleLocationEntity.CourtBean();
                courtBean.setCourt_latitude(location.getLatitude() + "");
                courtBean.setCourt_longitude(location.getLongitude() + "");
                singleLocationEntity.setCourt(courtBean);
                singleLocationEntity.setPage(page);
                String request = new Gson().toJson(singleLocationEntity);
                String requestJson = RequestUtil.getRequestJson(this, request);
                String url = "/public/APIPublicCourt/QueryCourt";
                OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
                    @Override
                    public void parseDatas(String json) {
                        try {
                            JSONObject jo = new JSONObject(json);
                            CourtLocationEntity courtLocationEntity = new Gson().fromJson(jo.toString(), CourtLocationEntity.class);
                            List<CourtLocationEntity.ListCourtBean> listCourt = courtLocationEntity.getListCourt();
                            if (listCourt != null && listCourt.size() > 0) {
                                String court_name = listCourt.get(0).getCourt_name();
                                tv_ballpark_name.setText(court_name);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dismissLoading();
                    }

                    @Override
                    public void requestFailed() {
                        dismissLoading();
                        Toast.makeText(UploadScorecardActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                    }
                }).getEntityData(this, url, requestJson);
            } else {
                Log.d(TAG, "init: 无法获得当前位置");
            }
        } else {//不存在位置提供器的情况

        }

        initData();
    }

    /**
     * 判断是否有可用的内容提供器
     *
     * @return 不存在返回null
     */
    private String judgeProvider(LocationManager locationManager) {
        List<String> prodiverlist = locationManager.getProviders(true);
        if (prodiverlist.contains(LocationManager.NETWORK_PROVIDER)) {
            return LocationManager.NETWORK_PROVIDER;
        } else if (prodiverlist.contains(LocationManager.GPS_PROVIDER)) {
            return LocationManager.GPS_PROVIDER;
        } else {
            Toast.makeText(UploadScorecardActivity.this, "请手动打开安全中心定位权限", Toast.LENGTH_SHORT).show();
        }
        return null;
    }


    private void initData() {
        if (list == null) {
            list = new ArrayList<>();
            list.add(IMG_ADD_TAG);
        }
        adapter = new GVAdapter();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list.get(position).equals(IMG_ADD_TAG)) {
                    if (list.size() < IMG_COUNT) {
                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, 0);
                    } else
                        Toast.makeText(UploadScorecardActivity.this, "最多只能选择7张照片！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        refreshAdapter();
    }

    private void refreshAdapter() {
        if (list == null) {
            list = new ArrayList<>();
        }
        if (adapter == null) {
            adapter = new GVAdapter();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void parseDatas(String json) {
        JSONObject jo = null;
        try {
            jo = new JSONObject(json);
            String statuscode = jo.getString("statuscode");
            String scorecard_image = jo.getString("failePath");
            if (statuscode.equals("1")) {
                removeItem();
                list.add(path);
                list.add(IMG_ADD_TAG);
                refreshAdapter();
                scorecard_image_list.add(scorecard_image);
            } else {
                Toast.makeText(UploadScorecardActivity.this, "错误码:" + scorecard_image, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestFailed() {

    }

    private class GVAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplication()).inflate(R.layout.activity_add_photo_gv_items, parent, false);
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.main_gridView_item_photo);
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.main_gridView_item_cb);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String s = list.get(position);
            if (!s.equals(IMG_ADD_TAG)) {
                holder.checkBox.setVisibility(View.VISIBLE);
                holder.imageView.setImageBitmap(ImageTool.createImageThumbnail(s));
            } else {
                holder.checkBox.setVisibility(View.GONE);
                holder.imageView.setImageResource(R.drawable.btn_home_card_add_n);
            }
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scorecard_image_list.remove(position);
                    list.remove(position);
                    refreshAdapter();
                }
            });
            return convertView;
        }

        private class ViewHolder {
            ImageView imageView;
            CheckBox checkBox;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            Log.d(TAG, "onActivityResult: 返回值为空");
            return;
        }
        if (requestCode == 0) {
            final Uri uri = data.getData();
            path = ImageTool.getImageAbsolutePath(this, uri);
            Log.d("print", "onActivityResult: -->" + path);
            if (list.size() == IMG_COUNT) {
                removeItem();
                refreshAdapter();
                return;
            }
            handler.sendEmptyMessage(UPLOAD);

            Log.d(TAG, "onActivityResult: --->" + path);
//            loadOss();
        }
        if (requestCode == 0x189 && resultCode == RESULT_OK) {
            String courtname = data.getStringExtra("courtname");
            if (courtname != null) {
                tv_ballpark_name.setText(courtname);
            }
        }
    }


    protected void loadOss() {
        showWaiting();
        AliyunRequestEntity aliyunOSS = RequestUtil.getAliyunOSS();
        if (aliyunOSS != null) {
            startGoUpLoad(aliyunOSS);
        } else {
            OkGoRequest.getOkGoRequest().setOnGetOssListener(new OkGoRequest.OnGetOssListener() {
                @Override
                public void getOssSucceed(AliyunRequestEntity aliyunRequestEntity) {
                    startGoUpLoad(aliyunRequestEntity);
                }
            }).getAliyunOSS(this);
        }
    }


    public void setKeyId(AliyunRequestEntity aliyunRequestEntity) {
        List<AliyunRequestEntity.ListsettingvalueBean> listsettingvalue = aliyunRequestEntity.getListsettingvalue();
        accessKeyId = listsettingvalue.get(0).getSettingvalue();
        accessKeySecret = listsettingvalue.get(1).getSettingvalue();
        endpoint = "https://" + listsettingvalue.get(2).getSettingvalue() + ".aliyuncs.com";
        testBucket = listsettingvalue.get(3).getSettingvalue();
        setOss();
    }

    public void setOss() {
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider, conf);
        upLoadScoreCard();
    }


    private void upLoadScoreCard() {
        uploadFilePath = path;
        String fileMD5 = FileToMD5Util.getFileMD5(new File(path));
        String uid = SharedUtil.getString(Constants.USER_ID);
        String userId = uid.replace("/", "");
        uploadObject = userId + "/scorecards/" + fileMD5 + ".png";
        new Thread(new Runnable() {
            @Override
            public void run() {
                ResuambleUploadSamples up = new ResuambleUploadSamples(oss, testBucket, uploadObject, uploadFilePath, new ProgressBar(UploadScorecardActivity.this))
                        .setOnUpLoadVideoListener(new ResuambleUploadSamples.OnUpLoadVideoListener() {

                            @Override
                            public void onUpSuccess(String url) {
                                Log.d(TAG, "onUpSuccess: --->" + url);
                                aliyunUrl = url;
                                handler.sendEmptyMessage(SUCCESS);
                            }

                            @Override
                            public void onUpFailure() {
                                handler.sendEmptyMessage(FAILURE);
                            }
                        });

                up.resumableUploadWithRecordPathSetting();

                up.setOnProgressListener(new ResuambleUploadSamples.OnProgressListener() {
                    @Override
                    public void onProgress(long sum, long current) {
                        final long ss = sum;
                        final long cu = current;
                        Log.d(TAG, "onProgress: " + ss + " " + cu);
                    }
                });
            }
        }).start();
    }

    private void startGoUpLoad(AliyunRequestEntity aliyunRequestEntity) {
        setKeyId(aliyunRequestEntity);
    }


    private void removeItem() {
        if (list.size() != IMG_COUNT) {
            if (list.size() != 0) {
                list.remove(list.size() - 1);
                Log.d(TAG, "removeItem: --->" + (list.size() - 1));
            }
        }
    }

    /**
     * 以最省内存的方式读取本地资源的图片 或者SDCard中的图片
     *
     * @param imagePath 图片在SDCard中的路径
     * @return
     */
    public static Bitmap getSDCardImg(String imagePath) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, opt);
        return compressImage(bitmap);
    }


    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 500) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    @OnClick({R.id.back, R.id.tv_select_date, R.id.btn_login, R.id.ll_choose_pitch})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.tv_select_date:
                //打球时间

                //选择日期
                new DatePickerDialog(UploadScorecardActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String month = (monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1) + "";
                        String day = dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth + "";
                        String date = String.format("%d-%s-%s", year, month, day);
                        tv_select_date.setText(date);
                    }
                }, times[0], times[1] - 1, times[2]).show();
                break;
            case R.id.btn_login:
                //上传
                String url = "/api/APIScorecard/AddScorecard";
                String ballParkName = tv_ballpark_name.getText().toString();
                String date = tv_select_date.getText().toString();
                StringBuffer imgUrl = new StringBuffer();
                for (int i = 0; i < scorecard_image_list.size(); i++) {
                    if (i != 0) {
                        imgUrl.append(",");
                    }
                    imgUrl.append(scorecard_image_list.get(i));
                }
                if (ballParkName.isEmpty()) {
                    //球场名称为空
                    Toast.makeText(UploadScorecardActivity.this, "球场名称不能为空哟～", Toast.LENGTH_SHORT).show();
                } else if (imgUrl.toString().isEmpty()) {
                    //未选择记分卡图片
                    Toast.makeText(UploadScorecardActivity.this, "请选择记分卡图片", Toast.LENGTH_SHORT).show();
                } else {
                    showWaiting();
                    String request = RequestUtil.uploadFile(this, ballParkName, date, imgUrl.toString());
                    Log.d(TAG, "onclick: -->" + request);
                    OkGo.post(Constants.BASE_URL + url)
                            .tag(this)
                            .params("request", request)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    dismissWaiting();
                                    JSONObject jo = null;
                                    try {
                                        jo = new JSONObject(s);
                                        String statuscode = jo.getString("statuscode");
                                        if (statuscode.equals("1")) {
                                            scorecard_image_list.clear();
                                            list.clear();
                                            list.add(IMG_ADD_TAG);
                                            refreshAdapter();
                                            tv_ballpark_name.setText("");
                                            tv_ballpark_name.setHint("请选择球场");
                                            Toast.makeText(UploadScorecardActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(UploadScorecardActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    dismissWaiting();
                                }
                            });
                }
                break;
            case R.id.ll_choose_pitch:
                //选择球场
                Intent intent = new Intent(this, ChoosePitchActivity.class);
                if (location != null) {
                    String longitude = location.getLongitude() + "";
                    String latitude = location.getLatitude() + "";
                    intent.putExtra("longitude", longitude);
                    intent.putExtra("latitude", latitude);
                }
                startActivityForResult(intent, 0x189);
                break;
        }
    }
}
