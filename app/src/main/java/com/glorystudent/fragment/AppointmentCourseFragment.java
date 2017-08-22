package com.glorystudent.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.glorystudent.adapter.CoursePactListAdapter;
import com.glorystudent.entity.ContractInfoEntity;
import com.glorystudent.entity.CoursePactEntity;
import com.glorystudent.entity.SubscribeRequestEntity;
import com.glorystudent.golflibrary.base.BaseFragment;
import com.glorystudent.golflife.MyAppointmentActivity;
import com.glorystudent.golflife.R;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;

/**
 * 课程预约Fragment
 * Created by hyj on 2017/2/15.
 */
public class AppointmentCourseFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private final static String TAG = "AppointCourseFragment";
    private int state;

    public static AppointmentCourseFragment getInstance(int state){
        AppointmentCourseFragment appointmentCourseFragment = new AppointmentCourseFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        appointmentCourseFragment.setArguments(bundle);
        return appointmentCourseFragment;
    }
    private CoursePactListAdapter coursePactListAdapter;
    @Bind(R.id.lv_course)
    public ListView lv_course;

    @Bind(R.id.ll_empty)
    public LinearLayout ll_empty;
    @Override
    protected int getContentId() {
        return R.layout.fragment_appointment_course;
    }
    @Override
    protected void getBundle(Bundle bundle) {
        state = bundle.getInt("state");
        if (state != -1){
            load();
        }
    }

    @Override
    protected void init(View view) {
        showLoading();
        coursePactListAdapter = new CoursePactListAdapter(getActivity());
        lv_course.setAdapter(coursePactListAdapter);
        lv_course.setOnItemClickListener(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            state = bundle.getInt("state");
            load();
        }
    }

    private void load() {
        SubscribeRequestEntity subscribeRequestEntity = new SubscribeRequestEntity();
        SubscribeRequestEntity.AppointmentBean appointmentBean = new SubscribeRequestEntity.AppointmentBean();
        appointmentBean.setApponitmentstatus(String.valueOf(state));
        subscribeRequestEntity.setAppointment(appointmentBean);
        String request = new Gson().toJson(subscribeRequestEntity);
        String requestJson2 = RequestUtil.getRequestJson(getActivity(), request);
        String url2 = "/api/APIAppointment/QueryAppointment";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if(statuscode.equals("1")){
                        CoursePactEntity coursePactEntity = new Gson().fromJson(jo.toString(), CoursePactEntity.class);
                        List<ContractInfoEntity.ListContractCourseDetailBean.AppointmentsBean> listAppointment = coursePactEntity.getListAppointment();
                        coursePactListAdapter.setDatas(listAppointment);
                    } else if (statuscode.equals("2")) {
                        ll_empty.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(getActivity(), url2, requestJson2);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ContractInfoEntity.ListContractCourseDetailBean.AppointmentsBean datas = coursePactListAdapter.getDatas(position);
        Intent intent = new Intent(getActivity(), MyAppointmentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", datas);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
