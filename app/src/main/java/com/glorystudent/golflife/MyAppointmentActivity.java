package com.glorystudent.golflife;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.dialog.Dialog;
import com.glorystudent.entity.ContractInfoEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.GlideUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我的预约详情
 * Created by hyj on 2017/2/22.
 */
public class MyAppointmentActivity extends BaseActivity implements Dialog.OnShowDialogListener {
    @Bind(R.id.iv_school_logo)
    public ImageView iv_school_logo;
    @Bind(R.id.tv_school_name)
    public TextView tv_school_name;
    @Bind(R.id.tv_name)
    public TextView tv_name;
    @Bind(R.id.tv_date)
    public TextView tv_date;
    @Bind(R.id.tv_time)
    public TextView tv_time;
    @Bind(R.id.tv_coach)
    public TextView tv_coach;
    @Bind(R.id.tv_play)
    public TextView tv_play;
    private int callType;
    private String callPhoneNumber;
    private ContractInfoEntity.ListContractCourseDetailBean.AppointmentsBean datas;
    private String message;

    @Override
    protected int getContentId() {
        return R.layout.activity_my_appointment;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            datas = (ContractInfoEntity.ListContractCourseDetailBean.AppointmentsBean) bundle.getSerializable("bean");
        }
        if (datas != null) {
            GlideUtil.loadImageView(this, datas.getCoachgrouplogo(), iv_school_logo);
            tv_school_name.setText(datas.getCoachgroupname());
            tv_name.setText(datas.getCcoursedetailname());
            String appointmentdate = datas.getAppointmentdate();
            String[] ts = appointmentdate.split("T");
            tv_date.setText(ts[0]);
            tv_time.setText(datas.getTimeDefinition());
            tv_coach.setText(datas.getCoachname());
            tv_play.setText(datas.getPositionsname());
        }
    }

    @OnClick({R.id.back, R.id.tv_call_school, R.id.tv_call_coach})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.tv_call_school:
                //联系学院
                callType = 1;
                message = "你确定拨打学院电话吗?";
                callPhoneNumber = datas.getCoachgrouptel();
                call();
                break;
            case R.id.tv_call_coach:
                //联系教练
                callType = 2;
                message = "你确定拨打教练电话吗?";
                callPhoneNumber = datas.getCoachtel();
                call();
                break;
        }
    }

    private void call(){
        Dialog.getInstance().setOnShowDialogListener(this).showDialog(this, "拨打电话", message, "拨打");
    }

    @Override
    public void onSure() {
        //意图：想干什么事
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        //url:统一资源定位符
        //uri:统一资源标示符（更广）
        intent.setData(Uri.parse("tel:" + callPhoneNumber));
        try {
            //开启系统拨号器
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MyAppointmentActivity.this, "请手动打开拨打电话权限", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onCancel() {

    }
}
