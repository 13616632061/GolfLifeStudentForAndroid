package com.glorystudent.golflife;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.entity.CloudVideoEntity;
import com.glorystudent.entity.CloudVideoRequestEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.util.Constants;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 视频编辑
 * Created by hyj on 2017/1/13.
 */
public class CloudVideoEditActivity extends BaseActivity {
    private final static String TAG = "CloudVideoEditActivity";

    @Bind(R.id.tv_take_label)
    public TextView tv_take_label;

    @Bind(R.id.et_video_name)
    public EditText et_video_name;

    @Bind(R.id.ll_hsv)
    public LinearLayout ll_hsv;

    private List<CloudVideoEntity.ListvideosBean> datas;
    private int labelnumber = -1;
    private String titleStr;
    private String labelStr;

    @Override
    protected int getContentId() {
        return R.layout.activity_edit_cloud_video;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        datas = (List<CloudVideoEntity.ListvideosBean>) intent.getSerializableExtra("selectedDatas");
        if (datas != null) {
            for (int i = 0; i < datas.size(); i++) {
                if (datas.get(i).getVideo_picpath() != null) {
                    View inflate = LayoutInflater.from(this).inflate(R.layout.item_edit_video_pic, null);
                    ImageView iv_video_pic = (ImageView) inflate.findViewById(R.id.iv_video_pic);
                    GlideUtil.loadImageView(this, datas.get(i).getVideo_picpath(), iv_video_pic);
                    ll_hsv.addView(inflate);
                }
            }
            //单个编辑单独处理
            if (datas.size() == 1) {
                if (datas.get(0).getVideo_tips() != null) {
                    labelnumber = Integer.valueOf(datas.get(0).getVideo_tips());
                    if (labelnumber > 0 && labelnumber <= Constants.LABEL.length) {
                        labelStr = Constants.LABEL[labelnumber - 1];
                    }
                } else {
                    labelStr = "";
                }
                tv_take_label.setText(labelStr);
                if (datas.get(0).getVideo_tag() != null) {
                    titleStr = datas.get(0).getVideo_tag();
                } else {
                    titleStr = "";
                }
                et_video_name.setText(titleStr);
            }
        }
    }

    @OnClick({R.id.back, R.id.add_label, R.id.tv_sure})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.add_label:
                //全部标签
                Intent intent = new Intent(this, LabelActivity.class);
                startActivityForResult(intent, 0x876);
                break;
            case R.id.tv_sure:
                //确定
                if (datas != null && datas.size() == 1) {
                    editOne();
                } else if (datas.size() > 1) {
                    editListVideos();
                }
                break;
        }
    }

    /**
     * 编辑一条视频
     */
    private void editOne() {
        String title = et_video_name.getText().toString().trim();
        String label = tv_take_label.getText().toString();
        if (title.equals(titleStr) && label.equals(labelStr)) {
            //没有发生变化，直接finish
            finish();
        } else {
            if (title != null && !title.isEmpty()) {
                showLoading();
                CloudVideoRequestEntity cloudVideoRequestEntity = new CloudVideoRequestEntity();
                CloudVideoRequestEntity.VideosBean videosBean = new CloudVideoRequestEntity.VideosBean();
                videosBean.setVideo_id(datas.get(0).getVideo_id());
//                videosBean.setVideo_path(datas.get(i).getVideo_path());
//                videosBean.setVideo_datetime(datas.get(i).getVideo_datetime());
                videosBean.setVideo_tag(title);
                if (labelnumber != -1 && !label.isEmpty()) {
                    videosBean.setVideo_tips(labelnumber + "");
                }
                cloudVideoRequestEntity.setVideos(videosBean);
                String reqeust = new Gson().toJson(cloudVideoRequestEntity);
                String reqeustJson = RequestUtil.getRequestJson(this, reqeust);
                String url = "/api/APIVideos/EditVideo";
                Log.i(TAG, "onclick: " + reqeustJson);
                Log.i(TAG, "onclick: " + Constants.BASE_URL + url);
                OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
                    @Override
                    public void parseDatas(String json) {
                        try {
                            JSONObject jo = new JSONObject(json);
                            String statuscode = jo.getString("statuscode");
                            String statusmessage = jo.getString("statusmessage");
                            if (statuscode.equals("1")) {
                                EventBus.getDefault().post(EventBusMapUtil.getStringMap("CloudVideoFragment", "refresh"));
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dismissLoading();
                    }

                    @Override
                    public void requestFailed() {
                        dismissLoading();
                        Toast.makeText(CloudVideoEditActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                    }
                }).getEntityData(this, url, reqeustJson);
            } else {
                Toast.makeText(this, "请输入视频名字", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 编辑多条视频
     */
    private void editListVideos() {
        String title = et_video_name.getText().toString().trim();
        if (title != null && !title.isEmpty()) {
            showLoading();
            String label = tv_take_label.getText().toString();
            CloudVideoRequestEntity cloudVideoRequestEntity = new CloudVideoRequestEntity();
            List<CloudVideoRequestEntity.VideosBean> listVideos = new ArrayList<>();
            for (int i = 0; i < datas.size(); i++) {
                CloudVideoRequestEntity.VideosBean videosBean = new CloudVideoRequestEntity.VideosBean();
                videosBean.setVideo_id(datas.get(i).getVideo_id());
//                videosBean.setVideo_path(datas.get(i).getVideo_path());
//                videosBean.setVideo_datetime(datas.get(i).getVideo_datetime());
                videosBean.setVideo_tag(title);
                if (labelnumber != -1 && !label.isEmpty()) {
                    videosBean.setVideo_tips(labelnumber + "");
                }
                listVideos.add(videosBean);
            }
            cloudVideoRequestEntity.setListvideos(listVideos);
            String reqeust = new Gson().toJson(cloudVideoRequestEntity);
            String reqeustJson = RequestUtil.getRequestJson(this, reqeust);
            String url = "/api/APIVideos/EditVideoList";
            Log.i(TAG, "onclick: " + reqeustJson);
            Log.i(TAG, "onclick: " + Constants.BASE_URL + url);
            OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
                @Override
                public void parseDatas(String json) {
                    try {
                        JSONObject jo = new JSONObject(json);
                        String statuscode = jo.getString("statuscode");
                        String statusmessage = jo.getString("statusmessage");
                        if (statuscode.equals("1")) {
                            EventBus.getDefault().post(EventBusMapUtil.getStringMap("CloudVideoFragment", "refresh"));
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dismissLoading();
                }

                @Override
                public void requestFailed() {
                    dismissLoading();
                    Toast.makeText(CloudVideoEditActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                }
            }).getEntityData(this, url, reqeustJson);
        } else {
            Toast.makeText(this, "请输入视频名字", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x876 && resultCode == RESULT_OK) {
            String label = data.getStringExtra("label");//得到标签名字
            labelnumber = data.getIntExtra("labelnumber", -1);//得到标签下标
            tv_take_label.setText(label);
        }
    }
}
