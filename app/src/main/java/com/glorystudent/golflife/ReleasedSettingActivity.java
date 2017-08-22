package com.glorystudent.golflife;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.entity.ReleasedRequestEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.widget.switchbutton.SwitchView;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.PickerViewUtil;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.util.TimeUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/10.
 */

public class ReleasedSettingActivity extends BaseActivity {

    private static final String TAG = "ReleasedSettingActivity";
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.tv_apply_begin_time)
    TextView beginTime;
    @Bind(R.id.tv_apply_end_time)
    TextView endTime;
    @Bind(R.id.et_sponsor)
    EditText sponsor;
    @Bind(R.id.switch_view_apply_list)
    SwitchView applyList;
    @Bind(R.id.switch_view_photo_wall)
    SwitchView photoWall;
    private PickerViewUtil pickerViewUtil;
    private ReleasedRequestEntity entity;
//    private boolean editFlag = false;//是否是编辑状态
//    private EventCompetityEntity.ListeventactivityBean datas;//编辑传过来的数据

    @Override
    protected int getContentId() {
        return R.layout.activity_released_setting;
    }

    @Override
    protected void init() {
        pickerViewUtil = new PickerViewUtil(this);
//        if (editFlag && datas != null) {//编辑
//            entity = new ReleasedRequestEntity();
//            entity.setEventactivity(new ReleasedRequestEntity.EventactivityBean());
//            entity.getEventactivity().setEventActivity_id(datas.getEventActivity_id());
//            if (datas.getEventactivity_signupbegintime() != null) {
//                Date signupBeginDate = TimeUtil.getStandardDate(datas.getEventactivity_signupbegintime());
//                entity.getEventactivity().setEventactivity_signupbegintime(TimeUtil.getUploadingTime(signupBeginDate));
//            }
//            if (datas.getEventactivity_signupendtime() != null) {
//                Date signupEndDate = TimeUtil.getStandardDate(datas.getEventactivity_signupendtime());
//                entity.getEventactivity().setEventactivity_signupendtime(TimeUtil.getUploadingTime(signupEndDate));
//            }
//            if (datas.getEventactivity_organizer() != null) {
//                entity.getEventactivity().setEventactivity_organizer(datas.getEventactivity_organizer());
//            }
//            entity.getEventactivity().setEventactivity_ifshowphotowall(datas.isEventactivity_ifshowphotowall());
//            entity.getEventactivity().setEventactivity_ifshowsignname(datas.isEventactivity_ifshowsignname());
//        } else {//从发布页面进来
            entity = EventReleasedActivity.releasedRequestEntity;
//        }
        initValues();
    }

    /**
     * 赋初始值
     */
    private void initValues() {
        if (entity != null) {
            String beginTimeStr = entity.getEventactivity().getEventactivity_signupbegintime();
            Log.i(TAG, "init: " + beginTimeStr);
            if (null != beginTimeStr && !"".equals(beginTimeStr)) {
                beginTime.setText(TimeUtil.getReleasedTime(TimeUtil.getDateFromUploading(beginTimeStr), "开始"));
            } else {
                beginTime.setText("");
            }
            String endTimeStr = entity.getEventactivity().getEventactivity_signupendtime();
            if (null != endTimeStr && !"".equals(endTimeStr)) {
                endTime.setText(TimeUtil.getReleasedTime(TimeUtil.getDateFromUploading(endTimeStr), "结束"));
            } else {
                endTime.setText("");
            }
            String organizerStr = entity.getEventactivity().getEventactivity_organizer();
            if (organizerStr != null) {
                sponsor.setText(organizerStr);
            } else {
                sponsor.setText("");
            }
            applyList.setOpened(!entity.getEventactivity().getEventactivity_ifshowsignname());
            photoWall.setOpened(!entity.getEventactivity().getEventactivity_ifshowphotowall());
        }
    }

    @OnClick({R.id.back, R.id.tv_apply_begin_time, R.id.tv_apply_end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                close();
                break;
            case R.id.tv_apply_begin_time:
                applyBeginTime();
                break;
            case R.id.tv_apply_end_time:
                applyEndTime();
                break;
        }
    }

    private void close() {
        //获取所有填入的数据
        acquireData();
//        if (editFlag) {
//            //编辑时返回提示对话框
//            Dialog.getInstance().setOnShowDialogListener(new Dialog.OnShowDialogListener() {
//                @Override
//                public void onSure() {
//                    //确认，则上传修改，成功则关闭页面
//                    requestEditSetting();
//                }
//
//                @Override
//                public void onCancel() {
//                    //取消，则不上传修改直接关闭
//                    finish();
//                }
//            }).showDialog(this, "修改设置", "确认修改设置", "确认");
//        } else {
            //发布活动进入时直接关闭
            finish();
//        }
    }

    /**
     * 访问服务器修改设置
     */
    private void requestEditSetting() {
        showLoading();
        String json = new Gson().toJson(entity);
        String requestJson = RequestUtil.getRequestJson(this, json);
        Log.i(TAG, "requestEditSetting: " + requestJson);
        String url = "/Public/APIPublicEventActivity/EditEventActivity";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        Toast.makeText(ReleasedSettingActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(EventBusMapUtil.getStringMap("LookDetailActivity", "refresh"));
                    } else if (statuscode.equals("2")) {//暂无数据
                        Log.i(TAG, "parseDatas: " + statusmessage);
                    } else {//失败
                        Log.i(TAG, "parseDatas: " + statusmessage);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();
                finish();
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(ReleasedSettingActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
    }

    private void applyEndTime() {
        Calendar currentCalendar = null;
        if (entity.getEventactivity().getEventactivity_signupendtime() != null) {
            Date currentDate = TimeUtil.getDateFromUploading(entity.getEventactivity().getEventactivity_signupendtime());
            currentCalendar = new GregorianCalendar();
            currentCalendar.setTime(currentDate);
        }
        pickerViewUtil.showTimePickerView("报名截止时间", currentCalendar, new PickerViewUtil.TimeLisener() {
            @Override
            public void onSubmit(Date date, View v) {
                endTime.setText(TimeUtil.getReleasedTime(date, "结束"));
                entity.getEventactivity().setEventactivity_signupendtime(TimeUtil.getUploadingTime(date));
            }
        });
    }

    private void applyBeginTime() {
        Calendar currentCalendar = null;
        if (entity.getEventactivity().getEventactivity_signupendtime() != null) {
            Date currentDate = TimeUtil.getDateFromUploading(entity.getEventactivity().getEventactivity_signupbegintime());
            currentCalendar = new GregorianCalendar();
            currentCalendar.setTime(currentDate);
        }
        pickerViewUtil.showTimePickerView("报名开始时间", currentCalendar, new PickerViewUtil.TimeLisener() {
            @Override
            public void onSubmit(Date date, View v) {
                beginTime.setText(TimeUtil.getReleasedTime(date, "开始"));
                entity.getEventactivity().setEventactivity_signupbegintime(TimeUtil.getUploadingTime(date));
            }
        });
    }

    private void acquireData() {
        entity.getEventactivity().setEventactivity_organizer(sponsor.getText().toString().trim());
        entity.getEventactivity().setEventactivity_ifshowphotowall(!photoWall.isOpened());
        entity.getEventactivity().setEventactivity_ifshowsignname(!applyList.isOpened());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

//    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
//    public void getEvent(Map<String, EventCompetityEntity.ListeventactivityBean> map) {
//        if (map.containsKey("ReleasedSettingActivity")) {
//            EventCompetityEntity.ListeventactivityBean datas = map.get("ReleasedSettingActivity");
//            this.datas = datas;
//            this.editFlag = true;
//        }
//    }

    @Override
    public void onBackPressed() {
        close();
    }
}
