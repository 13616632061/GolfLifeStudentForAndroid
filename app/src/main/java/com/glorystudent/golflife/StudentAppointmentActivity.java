package com.glorystudent.golflife;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.adapter.StudentAppointmentListAdapter;
import com.glorystudent.dialog.Dialog;
import com.glorystudent.entity.ContractInfoEntity;
import com.glorystudent.entity.CourseDetailsEntity;
import com.glorystudent.entity.MyCourseEntity;
import com.glorystudent.entity.SubscribeRequestEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflibrary.widget.circleview.CircleImageView;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 学员端课程预约
 * Created by hyj on 2017/2/22.
 */
public class StudentAppointmentActivity extends BaseActivity implements OkGoRequest.OnOkGoUtilListener, StudentAppointmentListAdapter.OnAppointmentClickListener {
    private final static String TAG = "StudentAppActivity";
    @Bind(R.id.lv_course)
    public ListView lv_course;

    private TextView tv_type;

    private TextView tv_count;

    private StudentAppointmentListAdapter studentAppointmentListAdapter;
    private MyCourseEntity.CcourselistBean datas;
    private PopupWindow deletewindow;
    private PopupWindow coursewindow;

    @Override
    protected int getContentId() {
        return R.layout.activity_student_appointment;
    }

    @Override
    protected void init() {
        showLoading();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            datas = (MyCourseEntity.CcourselistBean) bundle.getSerializable("bean");
        }
        View inflate = LayoutInflater.from(this).inflate(R.layout.item_student_appointment_head, null);
        tv_type = (TextView) inflate.findViewById(R.id.tv_type);
        tv_count = (TextView) inflate.findViewById(R.id.tv_count);
        lv_course.addHeaderView(inflate);
        studentAppointmentListAdapter = new StudentAppointmentListAdapter(this);
        lv_course.setAdapter(studentAppointmentListAdapter);
        studentAppointmentListAdapter.setOnAppointmentClickListener(this);
        studentAppointmentListAdapter.setOnDetailsClickListener(new StudentAppointmentListAdapter.OnDetailsClickListener() {
            @Override
            public void onClick(CourseDetailsEntity.ContractcourseBean data) {
                Intent intent1 = new Intent(StudentAppointmentActivity.this, MyAppointmentActivity.class);
                Bundle bundle1 = new Bundle();
                ContractInfoEntity.ListContractCourseDetailBean.AppointmentsBean appointments = data.getAppointments();
                bundle1.putSerializable("bean", appointments);
                intent1.putExtras(bundle1);
                startActivity(intent1);
            }
        });
    }

    @Override
    protected void loadDatas() {
        if (datas != null) {
            tv_type.setText(datas.getContractname());
            int finsh = datas.getCoursefinishfcount();
            int count = datas.getCoursetotalcount();
            tv_count.setText("已上" + finsh + "节/共" + count + "节");
            String request = "{" + "\"contractid\":" + datas.getContractid() + ",\"contracttype\":" + "2" + "}";
            String requestJson = RequestUtil.getRequestJson(this, request);
            String url = "/api/APIContract/QueryContractCourseList";
            OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(this).getEntityData(this, url, requestJson);
        }
    }

    @Override
    public void parseDatas(String json) {
        try {
            Log.d(TAG, "parseDatas: --->" + json);
            JSONObject jo = new JSONObject(json);
            String statuscode = jo.getString("statuscode");
            String statusmessage = jo.getString("statusmessage");
            if (statuscode.equals("1")) {
                CourseDetailsEntity courseDetailsEntity = new Gson().fromJson(jo.toString(), CourseDetailsEntity.class);
                List<CourseDetailsEntity.ContractcourseBean> contractcourse = courseDetailsEntity.getContractcourse();
                if (contractcourse != null) {
                    studentAppointmentListAdapter.setDatas(contractcourse);
                }
            }else {
                Toast.makeText(StudentAppointmentActivity.this, "预约失败,错误码:" + statusmessage, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dismissLoading();
    }

    @Override
    public void requestFailed() {
        dismissLoading();
        Toast.makeText(StudentAppointmentActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(int position) {
        CourseDetailsEntity.ContractcourseBean courseBean = studentAppointmentListAdapter.getDatas(position);
        Intent intent = new Intent(this, AppointmentCourseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ListcontractBean", datas);
        bundle.putSerializable("ContractcourseBean", courseBean);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    /**
     * 显示取消预约的popupWindow
     */
    private void showDeleteAppointment(CourseDetailsEntity.ContractcourseBean data) {
        String times = data.getAppointments().getTimeDefinition();
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popu_delete_appointment, null);
        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        TextView sure = (TextView) view.findViewById(R.id.sure);
        String[] split;
        if (times.contains(",")) {
            split = times.split(",");
        } else {
            split = new String[]{times};
        }
        LinearLayout ll_add_view = (LinearLayout) view.findViewById(R.id.ll_add_view);
        for (int i = 0; i < split.length; i++) {
            TextView textView = new TextView(this);
            textView.setText(split[i]);
            textView.setTextSize(14);
            textView.setTextColor(getResources().getColor(R.color.colorBlack));
            textView.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;
            textView.setLayoutParams(layoutParams);
            ll_add_view.addView(textView);
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletewindow.dismiss();
            }
        });

        sure.setTag(data);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseDetailsEntity.ContractcourseBean tag = (CourseDetailsEntity.ContractcourseBean) v.getTag();
                deleteAppointment(tag.getAppointments().getAppointmentid(), tag.getAppointments().getCcoursedetailid());
            }
        });




        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        deletewindow = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        deletewindow.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        deletewindow.setBackgroundDrawable(dw);
        // 在底部显示
        RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.rl);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        deletewindow.showAtLocation(findViewById(R.id.back), Gravity.CENTER, 0, 0);
    }


    /**
     * 显示popupWindow
     */
    private void showCoursePopwindow(CourseDetailsEntity.ContractcourseBean data) {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.popu_course_details, null);
        CircleImageView iv_img = (CircleImageView) view.findViewById(R.id.iv_img);
        GlideUtil.loadCircleImageView(this, data.getAppointments().getAppontimentheadpic(), iv_img, R.drawable.head_default);
//        iv_head_img.setDrawingCacheEnabled(true);
//        Bitmap obmp = Bitmap.createBitmap(iv_head_img.getDrawingCache());
//        iv_img.setImageBitmap(obmp);
//        iv_head_img.setDrawingCacheEnabled(false);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_name.setText(data.getAppointments().getCoachname());
        TextView tv_age = (TextView) view.findViewById(R.id.tv_age);
        TextView tv_veteran = (TextView) view.findViewById(R.id.tv_veteran);
        tv_age.setText(data.getAppointments().getAppontimentage() + "岁");
        tv_veteran.setText("球龄" + data.getAppointments().getAppontimengolfage() + "年");

        TextView tv_course_content = (TextView) view.findViewById(R.id.tv_course_content);
        tv_course_content.setText(data.getAppointments().getCcoursedetailname());
        TextView tv_class_type = (TextView) view.findViewById(R.id.tv_class_type);
        tv_class_type.setText(data.getAppointments().getPackagename());
        TextView tv_play_information = (TextView) view.findViewById(R.id.tv_play_information);
        tv_play_information.setText(data.getAppointments().getPositionsname());
        TextView tv_class_time = (TextView) view.findViewById(R.id.tv_class_time);
        tv_class_time.setText(data.getAppointments().getTimeDefinition());

        TextView tv_remind = (TextView) view.findViewById(R.id.tv_remind);
        //学员端隐藏提醒签课
        tv_remind.setVisibility(View.GONE);

        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        TextView tv_contact_him = (TextView) view.findViewById(R.id.tv_contact_him);
        final String appointmenttel = data.getAppointments().getAppointmenttel();
        cancel.setTag(data);
        //取消
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseDetailsEntity.ContractcourseBean tag = (CourseDetailsEntity.ContractcourseBean) v.getTag();
                showDeleteAppointment(tag);
            }
        });
        //联系他
        tv_contact_him.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coursewindow.dismiss();
                //联系他
                Dialog.getInstance().setOnShowDialogListener(new Dialog.OnShowDialogListener() {
                    @Override
                    public void onSure() {
                        //意图：想干什么事
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        //url:统一资源定位符
                        //uri:统一资源标示符（更广）
                        intent.setData(Uri.parse("tel:" + appointmenttel));
                        try {
                            //开启系统拨号器
                            startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(StudentAppointmentActivity.this, "请手动打开拨打电话权限", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                }).showDialog(StudentAppointmentActivity.this, "拨打电话", "你确定拨打该学员电话吗?", "拨打");
            }
        });

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        coursewindow = new PopupWindow(view,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        coursewindow.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        coursewindow.setBackgroundDrawable(dw);
        // 在底部显示
        RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.rl);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        coursewindow.showAtLocation(findViewById(R.id.back), Gravity.CENTER, 0, 0);

        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        coursewindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    private void deleteAppointment(int appointmentid, int ccoursedetailid) {
        SubscribeRequestEntity subscribeRequestEntity = new SubscribeRequestEntity();
        SubscribeRequestEntity.AppointmentBean appointmentBean = new SubscribeRequestEntity.AppointmentBean();
        appointmentBean.setAppointmentid(appointmentid);
        appointmentBean.setCcoursedetailid(ccoursedetailid);
        subscribeRequestEntity.setAppointment(appointmentBean);
        String request = new Gson().toJson(subscribeRequestEntity);
        String requestJson = RequestUtil.getRequestJson(this, request);
        String url = "/api/APIAppointment/DeleteAppointment";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        deletewindow.dismiss();
                        coursewindow.dismiss();
                        loadDatas();
                        Toast.makeText(StudentAppointmentActivity.this, "取消预约成功", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailed() {

            }
        }).getEntityData(StudentAppointmentActivity.this, url, requestJson);
    }


    @OnClick(R.id.back)
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBus(Map<Integer, Integer> map) {
        if (map.containsKey(3)) {
            if (map.get(3) == 1) {
                loadDatas();
            }
        }
    }
}
