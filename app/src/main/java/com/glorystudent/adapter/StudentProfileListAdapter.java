package com.glorystudent.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.entity.ContractInfoEntity;
import com.glorystudent.entity.ContractInfoEntity.ListcontractsBean;
import com.glorystudent.entity.DateChangeRequestEntity;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflife.R;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.util.TimeUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hyj on 2017/4/20.
 */
public class StudentProfileListAdapter extends AbsBaseAdapter<ListcontractsBean> implements ProfileCourseListAdapter.OnAppointmentClickListener {
    private int[] times;//记录时间数组

    private OnAppointmentListener onAppointmentListener;

    public void setOnAppointmentListener(OnAppointmentListener onAppointmentListener) {
        this.onAppointmentListener = onAppointmentListener;
    }

    public StudentProfileListAdapter(Context context) {
        super(context, R.layout.item_course_participants_list);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        String[] time = dateStr.split("-");
        times = new int[]{Integer.valueOf(time[0]), Integer.valueOf(time[1]), Integer.valueOf(time[2])};
    }

    @Override
    public void bindView(ViewHolder viewHolder, ListcontractsBean data) {
        TextView tv_apply_state = (TextView) viewHolder.getView(R.id.tv_apply_state);
        viewHolder.setTextView(R.id.tv_type, data.getContractname());
        viewHolder.setTextView(R.id.tv_contract_money, data.getRealprice() + "");
        ImageView iv_expand = (ImageView) viewHolder.getView(R.id.iv_expand);
        LinearLayout ll_context = (LinearLayout) viewHolder.getView(R.id.ll_context);
        iv_expand.setTag(ll_context);
        iv_expand.setTag(R.id.stuprofileexpand, true);
        iv_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView iv = (ImageView) v;
                LinearLayout tag = (LinearLayout) v.getTag();
                int visibility = tag.getVisibility();
                if (visibility == View.GONE) {
                    iv.setImageResource(R.drawable.nav_video_expand);
                    tag.setVisibility(View.VISIBLE);
                } else {
                    iv.setImageResource(R.drawable.nav_video_packup);
                    tag.setVisibility(View.GONE);
                }
            }
        });

        int status = data.getStatus();
        switch (status) {
            case 0:
                //待审核
                viewHolder.setTextView(R.id.tv_contract_state, "待审核");
                break;
            case 1:
                //有效
                viewHolder.setTextView(R.id.tv_contract_state, "有效");
                break;
            case 2:
                //已过期
                viewHolder.setTextView(R.id.tv_contract_state, "已过期");
                break;
            case 3:
                //已完成
                viewHolder.setTextView(R.id.tv_contract_state, "已完成");
                break;
            case 4:
                //无效
                viewHolder.setTextView(R.id.tv_contract_state, "无效");
                break;
            case 5:
                //已驳回
                viewHolder.setTextView(R.id.tv_contract_state, "已驳回");
                break;
        }

        viewHolder.setTextView(R.id.tv_contract_time, TimeUtil.getUTC2StandardTime(data.getEffectivedates()) + "～" + TimeUtil.getUTC2StandardTime(data.getExpirydate()));
        String applyStatus = data.getApplyStatus();
        if (applyStatus == null) {
            //审核中
            tv_apply_state.setBackgroundResource(R.drawable.shape_btn_other_add);
            tv_apply_state.setText("合约延期申请");
            tv_apply_state.setTag(data);
            tv_apply_state.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final ListcontractsBean tag = (ListcontractsBean) v.getTag();
                    //选择日期
                    new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            String month = (monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1) + "";
                            String day = dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth + "";
                            String date = String.format("%d-%s-%s", year, month, day);
                            DateChangeRequestEntity dateChangeRequestEntity = new DateChangeRequestEntity();
                            DateChangeRequestEntity.ApplyContractDateChangeBean applyContractDateChangeBean = new DateChangeRequestEntity.ApplyContractDateChangeBean();
                            applyContractDateChangeBean.setContractid(tag.getContractid());
                            applyContractDateChangeBean.setDelaytime(date);
                            dateChangeRequestEntity.setApplyContractDateChange(applyContractDateChangeBean);
                            String request = new Gson().toJson(dateChangeRequestEntity);
                            String requestJson = RequestUtil.getRequestJson(context, request);
                            Log.d("print", "onDateSet: --->" + requestJson);
                            String url = "/api/APIApplyContractDateChange/ApplyContractDateChange";
                            OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
                                @Override
                                public void parseDatas(String json) {
                                    Log.d("print", "parseDatas: --->" + json);
                                    try {
                                        JSONObject jo = new JSONObject(json);
                                        String statuscode = jo.getString("statuscode");
                                        String statusmessage = jo.getString("statusmessage");
                                        if (statuscode.equals("1")) {
                                            EventBus.getDefault().post(EventBusMapUtil.getStringMap("StudentProfileActivity", "1"));
                                            Toast.makeText(context, "提交申请成功，请耐心等待审核", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, "提交申请失败", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void requestFailed() {

                                }
                            }).getEntityData(context, url, requestJson);
                        }
                    }, times[0], times[1] - 1, times[2]).show();
                }
            });
        } else {
            //已审核
            tv_apply_state.setBackgroundResource(R.drawable.shape_btn_no_apply);
            tv_apply_state.setText("延期申请中");
        }
        ListView lv_course = (ListView) viewHolder.getView(R.id.lv_course);
        int userid = data.getUserid();
        ProfileCourseListAdapter profileCourseListAdapter = new ProfileCourseListAdapter(context);
        lv_course.setAdapter(profileCourseListAdapter);
        profileCourseListAdapter.setDatas(data.getListContractDetail());
        profileCourseListAdapter.setOnAppointmentClickListener(this);
    }

    @Override
    public void onClick(ContractInfoEntity.ListContractCourseDetailBean data) {
        Log.d("print", "onClick: 234" + data.getCcoursedetailname());
        if (onAppointmentListener != null) {
            Log.d("print", "onClick: -----");
            onAppointmentListener.onClick(data);
        }
    }

    public interface OnAppointmentListener{
        void onClick(ContractInfoEntity.ListContractCourseDetailBean data);
    }
}
