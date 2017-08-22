package com.glorystudent.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.entity.ApplyCoachRequestEntity;
import com.glorystudent.golflibrary.base.BaseFragment;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.golflibrary.widget.pickerscrollview.PickerScrollView;
import com.glorystudent.golflibrary.widget.pickerscrollview.Pickers;
import com.glorystudent.golflife.ChooseSchoolActivity;
import com.glorystudent.golflife.GetAchievementActivity;
import com.glorystudent.golflife.PersonalProfileActivity;
import com.glorystudent.golflife.R;
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
 * Created by hyj on 2016/12/26.
 */
public class CertificationCoachFragment extends BaseFragment implements View.OnClickListener, PickerScrollView.onSelectListener, OkGoRequest.OnOkGoUtilListener {
    private final static String TAG = "CerCoachFragment";
    @Bind(R.id.tv_cer_school)
    public TextView tv_cer_school;
    @Bind(R.id.tv_cer_age)
    public TextView tv_cer_age;
    @Bind(R.id.tv_cer_profile)
    public TextView tv_cer_profile;
    @Bind(R.id.tv_cer_achievement)
    public TextView tv_cer_achievement;
    private PickerScrollView pickerscrlllview;
    private PopupWindow window;
    private List<Pickers> list;
    private String[] id;
    private String[] name;
    private int size = 30;
    private String saveGolfAge;
    private String groupid;

    @Override
    protected int getContentId() {
        return R.layout.fragment_certification_coach;
    }

    @OnClick({R.id.cer_school, R.id.cer_age, R.id.cer_profile, R.id.cer_achievement, R.id.tv_submit})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.cer_school:
                //所属学院
                Intent intent = new Intent(getActivity(), ChooseSchoolActivity.class);
                startActivityForResult(intent, 0x045);
                break;
            case R.id.cer_age:
                //教龄
                showPopwindow();
                break;
            case R.id.cer_profile:
                //个人简介
                Intent ppintent = new Intent(getActivity(), PersonalProfileActivity.class);
                ppintent.putExtra("pp", tv_cer_profile.getText().toString());
                startActivityForResult(ppintent, 0x041);
                break;
            case R.id.cer_achievement:
                //所获成就
                Intent achievementIntent = new Intent(getActivity(), GetAchievementActivity.class);
                achievementIntent.putExtra("achievement", tv_cer_achievement.getText().toString());
                startActivityForResult(achievementIntent, 0x043);
                break;
            case R.id.tv_submit:
                //提交
                if(tv_cer_school.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "任职院校不能为空哟～", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (tv_cer_age.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "球龄不能为空哟～", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (tv_cer_profile.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "个人简介不能为空哟～", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (tv_cer_achievement.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "所获成就不能为空哟～", Toast.LENGTH_SHORT).show();
                }
                showWaiting();

                String url = "/api/APIApplyCoach/ApplyCoach";
                ApplyCoachRequestEntity applyCoachRequestEntity = new ApplyCoachRequestEntity();
                ApplyCoachRequestEntity.ApplyCoachGroupBean applyCoachGroupBean = new ApplyCoachRequestEntity.ApplyCoachGroupBean();
                applyCoachGroupBean.setCoachage(saveGolfAge);
                applyCoachGroupBean.setCoachgroupid(groupid);
                applyCoachGroupBean.setProfile(tv_cer_profile.getText().toString());
                applyCoachGroupBean.setCoachvostro(tv_cer_achievement.getText().toString());
                applyCoachRequestEntity.setApplyCoachGroup(applyCoachGroupBean);
                String request = new Gson().toJson(applyCoachRequestEntity);
                String requestJson = RequestUtil.getRequestJson(getActivity(), request);
                OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(this).getEntityData(getActivity(), url, requestJson);
                break;
        }
    }
    /**
     * 显示popupWindow
     */
    private void showPopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popu_golfage_layout, null);
        TextView tv_cancel = (TextView) view.findViewById(R.id.cancel);
        TextView tv_sure = (TextView) view.findViewById(R.id.sure);
        tv_cancel.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        pickerscrlllview = (PickerScrollView) view.findViewById(R.id.pickerscrlllview);
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        window.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(getActivity().findViewById(R.id.cer_age),
                Gravity.BOTTOM, 0, 0);

        list = new ArrayList<Pickers>();
        id = new String[size + 1];
        name = new String[size + 1];
        for (int i = 0; i <= size; i++) {
            id[i] = i + "";
            name[i] = i + "年";
        }
        for (int i = 0; i < name.length; i++) {
            list.add(new Pickers(name[i], id[i]));
        }
        // 设置数据，默认选择第一条
        pickerscrlllview.setData(list);
        pickerscrlllview.setSelected(0);
        saveGolfAge = id[0];
        pickerscrlllview.setOnSelectListener(this);
    }
    @Override
    public void onSelect(Pickers pickers) {
        saveGolfAge = pickers.getShowId();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                //取消
                window.dismiss();
                break;
            case R.id.sure:
                //确定
                tv_cer_age.setText(saveGolfAge + "年");
                window.dismiss();
                break;
        }
    }

    /**
     * 跳转回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //选择院校
        if (requestCode == 0x045 && resultCode == 0x046 && data != null) {
            String personalProfile = data.getStringExtra("address");
            groupid = data.getStringExtra("groupid");

            tv_cer_school.setText(personalProfile);
        }

        //个人简介
        if (requestCode == 0x041 && resultCode == 0x042 && data != null) {
            String personalProfile = data.getStringExtra("pp");
            tv_cer_profile.setText(personalProfile);
        }
        //所获成就
        if (requestCode == 0x043 && resultCode == 0x044 && data != null) {
            String personalProfile = data.getStringExtra("achievement");
            tv_cer_achievement.setText(personalProfile);
        }
    }

    @Override
    public void parseDatas(String json) {
        try {
            JSONObject jo = new JSONObject(json);
            String statuscode = jo.getString("statuscode");
            String statusmessage = jo.getString("statusmessage");
            if (statuscode.equals("1")) {
                SharedUtil.putString(Constants.ATTESTATION_STATE, "2");
                Toast.makeText(getActivity(), "申请成功，请耐心等待审核结果", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(EventBusMapUtil.getStringMap("MyAttestationActivity", "close"));
            } else {
                Toast.makeText(getActivity(), "申请失败", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dismissWaiting();
    }

    @Override
    public void requestFailed() {
        dismissWaiting();
        Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
    }
}
