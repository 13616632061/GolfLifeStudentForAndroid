package com.glorystudent.golflife;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.dialog.SharePopupWindow;
import com.glorystudent.entity.CompetityRequestEntity;
import com.glorystudent.entity.EventCompetityEntity;
import com.glorystudent.entity.ShareModel;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.util.Constants;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.util.TimeUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * 我发布活动的查看详情页面
 * Created by Administrator on 2017/4/26.
 */

public class LookDetailActivity extends BaseActivity implements PlatformActionListener {
    private static final String TAG = "LookDetailActivity";
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.tv_share)
    TextView tvShare;
    @Bind(R.id.tv_detail_name)
    TextView name;
    @Bind(R.id.tv_detail_cost)
    TextView cost;
    @Bind(R.id.tv_detail_apply_number)
    TextView applyNumber;
    @Bind(R.id.tv_detail_date)
    TextView date;
    @Bind(R.id.tv_detail_address)
    TextView address;
    @Bind(R.id.tv_detail_look_info)
    TextView lookInfo;
    @Bind(R.id.ll_detail_apply_list)
    LinearLayout applyList;
    @Bind(R.id.ll_detail_edit)
    LinearLayout edit;
    @Bind(R.id.ll_detail_photo_wall)
    LinearLayout photoWall;
    @Bind(R.id.tv_detail_photo_wall_number)
    TextView photoWallNumber;
    @Bind(R.id.tv_detail_cancel)
    TextView cancel;
    @Bind(R.id.rl_bottom_cancel)
    RelativeLayout rlBottomCancel;

    private EventCompetityEntity.ListeventactivityBean datas;
    private ShareModel model;
    private int eventActivity_id;
    private PopupWindow popupWindow;//取消活动的对话框提示
    //    private EventWithSignInfoEntity eventWithSignInfoEntity;
    private EventCompetityEntity eventCompetityEntity;

    @Override
    protected int getContentId() {
        return R.layout.activity_look_detail;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        eventActivity_id = intent.getIntExtra("id", -1);
        showLoading();
    }

    /**
     * 初始化数据显示
     */
    private void initValues() {
        name.setText(datas.getEventactivity_name());
        applyNumber.setText(datas.getSignUpNumber() + "");
        String startTime = TimeUtil.getCertificateTime(TimeUtil.getStandardDate(datas.getEventactivity_begintime()));
        String endTime = TimeUtil.getCertificateTime(TimeUtil.getStandardDate(datas.getEventactivity_endtime()));
        date.setText(startTime + " - " + endTime);
        address.setText(datas.getEventactivity_address());
        if (datas.getEventactivity_costtype().equals("1")) {
            cost.setText("免费活动");
        } else {
            DecimalFormat df = new DecimalFormat("0.00");
            cost.setText("￥" + df.format(datas.getEventactivity_cost()));
            cost.setTextColor(getResources().getColor(R.color.colorOrange));
        }
        photoWallNumber.setText(datas.getListEventPicWallCount() + "");
    }

    /**
     * 初始化分享实体
     */
    private void initShareModel() {
        model = new ShareModel();
        if (datas.getListeventpic() != null && datas.getListeventpic().size() > 0) {
            model.setImageUrl(datas.getListeventpic().get(0).getEventactivity_picpath());
        } else {
            //默认图片
            model.setImageUrl("http://glorygolflife.oss-cn-shenzhen.aliyuncs.com/XXQ2Lsd0yq3b72wWdapS4a5Rg24MhxF6cxEOYyODBFQ%3D%2Fcompetitions%2Fbanners%2Faf2026710e2859c421fc19f67eca1fc8.png");
        }
        model.setText(datas.getEventactivity_detail());
        model.setTitle(datas.getEventactivity_name());
        model.setUrl(String.format(Constants.EVENT_SHARE_URL, datas.getEventActivity_id()));
    }

    @Override
    protected void loadDatas() {
        CompetityRequestEntity competityRequestEntity = new CompetityRequestEntity();
        CompetityRequestEntity.EventactivityBean eventactivityBean = new CompetityRequestEntity.EventactivityBean();
        eventactivityBean.setEventActivity_id(eventActivity_id);
        competityRequestEntity.setEventactivity(eventactivityBean);
        String json = new Gson().toJson(competityRequestEntity);
        String requestJson = RequestUtil.getRequestJson(this, json);
        Log.i(TAG, "loadDatas: " + requestJson);
        String url = "/Public/APIPublicEventActivity/QueryEventActivityByEventActivityID";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        eventCompetityEntity = new Gson().fromJson(jo.toString(), EventCompetityEntity.class);
                        List<EventCompetityEntity.ListeventactivityBean> listeventactivity = eventCompetityEntity.getListeventactivity();
                        if (listeventactivity != null && listeventactivity.size() > 0) {
                            datas = listeventactivity.get(0);
                            if (datas != null) {
                                initValues();
                                initShareModel();
                            }
                        }
                    } else if (statuscode.equals("2")) {//暂无数据
                        Log.i(TAG, "parseDatas: " + statusmessage);
                    } else {
                        Log.i(TAG, "parseDatas: " + statusmessage);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(LookDetailActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
    }

    @OnClick({R.id.back, R.id.tv_share, R.id.tv_detail_look_info, R.id.ll_detail_apply_list, R.id.ll_detail_edit,
            R.id.ll_detail_photo_wall, R.id.tv_detail_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
        if (datas != null) {//数据未加载时没有点击事件
            switch (view.getId()) {
                case R.id.tv_share:
                    share();
                    break;
                case R.id.tv_detail_look_info:
                    //赛事活动预览
                    startEventDetail();
                    break;
                case R.id.ll_detail_apply_list:
                    //报名列表
                    startApplyList();
                    break;
                case R.id.ll_detail_edit:
                    //编辑活动
                    startEventReleased();
                    break;
//            case R.id.ll_detail_setting:
//                //高级设置
//                startReleasedSetting();
//                break;
                case R.id.ll_detail_photo_wall:
                    //照片墙
                    startPhotoWall();
                    break;
                case R.id.tv_detail_cancel:
                    //取消活动
                    cancelEvent();
                    break;
            }
        }
    }

    /**
     * 取消活动
     */
    private void cancelEvent() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popu_cancel_layout, null);
        final TextView cancelInfo = (TextView) view.findViewById(R.id.et_cancel_info);
        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        TextView sure = (TextView) view.findViewById(R.id.sure);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String text = cancelInfo.getText().toString().trim();
                if (text.isEmpty()) {
                    Toast.makeText(LookDetailActivity.this, "取消原因不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                popupWindow.dismiss();
                LookDetailActivity.this.showLoading();
                String json = "\"eventactivity\":{" + "\"cancelrefuse\":" + "\"" + text + "\"" + "," + "\"eventActivity_id\":" + eventActivity_id + "}";
                String requestJson = RequestUtil.getJson(LookDetailActivity.this, json);
                Log.i(TAG, "onClick: " + requestJson);
                String url = "/Public/APIPublicEventActivity/CancelEventActivity";
                OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
                    @Override
                    public void parseDatas(String json) {
                        try {
                            JSONObject jo = new JSONObject(json);
                            String statuscode = jo.getString("statuscode");
                            String statusmessage = jo.getString("statusmessage");
                            if (statuscode.equals("1")) {
                                Toast.makeText(LookDetailActivity.this, "取消活动成功", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(EventBusMapUtil.getStringMap("MyReleasedEventFragment", "refresh"));
                                finish();
                            } else {
                                Log.i(TAG, "parseDatas: " + statusmessage);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        LookDetailActivity.this.dismissLoading();
                    }

                    @Override
                    public void requestFailed() {
                        LookDetailActivity.this.dismissLoading();
                        Toast.makeText(LookDetailActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                    }
                }).getEntityData(LookDetailActivity.this, url, requestJson);
            }
        });
        //显示popupwindow
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        popupWindow.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        popupWindow.setBackgroundDrawable(dw);
        // 在中间显示
        popupWindow.showAtLocation(view.findViewById(R.id.cancel), Gravity.CENTER, 0, 0);
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });

    }

    /**
     * 开启照片墙
     */
    private void startPhotoWall() {
        Intent intent = new Intent(this, PhotoWallActivity.class);
        intent.putExtra("id", eventActivity_id);
        startActivity(intent);
    }

//    /**
//     * 修改高级设置
//     */
//    private void startReleasedSetting() {
//        Map<String, EventCompetityEntity.ListeventactivityBean> map = new HashMap<>();
//        map.put("ReleasedSettingActivity", datas);
//        EventBus.getDefault().postSticky(map);
//        Intent intent = new Intent(this, ReleasedSettingActivity.class);
//        startActivity(intent);
//    }

    /**
     * 编辑活动
     */
    private void startEventReleased() {
//        Map<String, EventWithSignInfoEntity> map = new HashMap<>();
//        map.put("EventReleasedActivity", eventWithSignInfoEntity);
//        EventBus.getDefault().postSticky(map);
        Intent intent = new Intent(this, EventReleasedActivity.class);
        intent.putExtra("id", eventActivity_id);
        startActivity(intent);
    }

    /**
     * 报名名单
     */
    private void startApplyList() {
        Intent intent = new Intent(this, ApplyListActivity.class);
        intent.putExtra("id", eventActivity_id);
        intent.putExtra("name", datas.getEventactivity_name());
        startActivity(intent);
    }

    /**
     * 预览赛事详情
     */
    private void startEventDetail() {
        if (datas != null) {
            Intent intent = new Intent(this, EventDetail2Activity.class);
            intent.putExtra("id", eventActivity_id);
            intent.putExtra("state", datas.getEventactivity_state());
            startActivity(intent);
        }
    }

    /**
     * 分享
     */
    private void share() {
        SharePopupWindow sharePopupWindow = new SharePopupWindow(this);
        sharePopupWindow.setPlatformActionListener(this);
        sharePopupWindow.initShareParams(model);
        sharePopupWindow.showShareWindow();
        // 显示窗口 (设置layout在PopupWindow中显示的位置)
        sharePopupWindow.showAtLocation(this.findViewById(R.id.back),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        sharePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(Map<String, String> map) {
        if (map.containsKey("LookDetailActivity")) {
            if (map.get("LookDetailActivity").equals("refresh")) {
                loadDatas();
            }
        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }
}
