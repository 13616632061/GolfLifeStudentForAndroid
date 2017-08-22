package com.glorystudent.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.glorystudent.adapter.ContractUserListAdapter;
import com.glorystudent.entity.ContractUserEntity;
import com.glorystudent.entity.ContractuserSortEntity;
import com.glorystudent.golflibrary.base.BaseFragment;
import com.glorystudent.golflife.R;
import com.glorystudent.golflife.StudentProfileActivity;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.widget.PullToRefreshLayout;
import com.glorystudent.widget.PullableNoUpListView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * 课程学员
 * Created by hyj on 2017/1/6.
 */
public class CourseParticipantsFragment extends BaseFragment implements OkGoRequest.OnOkGoUtilListener, TextWatcher, AdapterView.OnItemClickListener {
    private final static String TAG = "CourseFragment";
    @Bind(R.id.course_lv)
    public PullableNoUpListView course_lv;
    private ContractUserListAdapter contractUserListAdapter;
    private List<ContractUserEntity.ListContractuserBean> listContractuser;
    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refresh_view;
    private boolean isRefresh;//true 是下拉刷新， false 是上拉加载
    private List<ContractUserEntity.ListfriendsBean> listfriends;

    @Bind(R.id.ll_empty)
    public LinearLayout ll_empty;

    @Override
    protected int getContentId() {
        return R.layout.fragment_course_participants;
    }

    @Override
    protected void init(View view) {
        ll_empty.setVisibility(View.GONE);
        EventBus.getDefault().register(this);
        refresh_view.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //下拉刷新回调
                isRefresh = true;
                loadDatas();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                //上拉加载回调
                isRefresh = false;
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.NOMORE);
            }
        });

        showLoading();
        contractUserListAdapter = new ContractUserListAdapter(getActivity());
        course_lv.setAdapter(contractUserListAdapter);
        View head = LayoutInflater.from(getActivity()).inflate(R.layout.item_search_list, null);
        EditText et_search = (EditText) head.findViewById(R.id.search_address);
        et_search.addTextChangedListener(this);
        course_lv.addHeaderView(head);
        course_lv.setOnItemClickListener(this);
    }

    @Override
    protected void loadDatas() {
        String url = "/api/APIUser/QueryContractUser";
        String request = RequestUtil.getEmptyParameter(getActivity());
        new OkGoRequest().setOnOkGoUtilListener(this).getEntityData(getActivity(), url, request);
    }

    private long start,end;
    @Override
    public void parseDatas(String json) {
        try {
            Log.d(TAG, "parseDatas: --->" + json);
            JSONObject jo = new JSONObject(json);
            String statuscode = jo.getString("statuscode");
            String statusmessage = jo.getString("statusmessage");
            if(statuscode.equals("1")){
                ll_empty.setVisibility(View.GONE);
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                ContractUserEntity contractUserEntity = new Gson().fromJson(jo.toString(), ContractUserEntity.class);
                listContractuser = contractUserEntity.getListContractuser();
                if (listContractuser != null) {
                    List<ContractUserEntity.ListContractuserBean> listContractuserBeen = finishCourseSort(listContractuser);
                    contractUserListAdapter.setDatas(listContractuserBeen);
                }
            } else if (statuscode.equals("2")) {
                ll_empty.setVisibility(View.VISIBLE);
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
            }else {
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
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
        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
    private List<ContractUserEntity.ListContractuserBean> changedDatas;
    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString();
        changedDatas = new ArrayList<>();
        for (ContractUserEntity.ListContractuserBean listContractuserBean : listContractuser) {
            if(listContractuserBean.getUsername().contains(text)){
                changedDatas.add(listContractuserBean);
            }
        }
        contractUserListAdapter.setDatas(changedDatas);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ContractUserEntity.ListContractuserBean datas = contractUserListAdapter.getDatas(position - 1);
        Intent intent = new Intent(getActivity(), StudentProfileActivity.class);
        int userid = datas.getUserid();

        Bundle bundle = new Bundle();
        bundle.putSerializable("ContractuserExt", datas);
        intent.putExtras(bundle);
        intent.putExtra("userid", userid);
        if (datas.getStatus() == 8) {
//            intent.putExtra("type", 2);
            Toast.makeText(getActivity(), "请等待审核完成", Toast.LENGTH_SHORT).show();
        }else if (datas.getStatus() == 0) {
            intent.putExtra("type", 1);
            startActivity(intent);
        }
    }

    private List<ContractUserEntity.ListContractuserBean> finishCourseSort(List<ContractUserEntity.ListContractuserBean> contractDatas) {
        List<ContractuserSortEntity> datas = new ArrayList<>();
        List<ContractUserEntity.ListContractuserBean> finishList = new ArrayList<>();
        List<ContractUserEntity.ListContractuserBean> otherList = new ArrayList<>();
        for (int i = 0; i < contractDatas.size(); i++) {
            if (contractDatas.get(i).getStatus() == 0) {
                //合约未完成的
                ContractuserSortEntity contractuserSortEntity = new ContractuserSortEntity();
                contractuserSortEntity.setContractuserBean(contractDatas.get(i));
                datas.add(contractuserSortEntity);
            } else {
                //合约已完成的或正在审核的
                otherList.add(contractDatas.get(i));
            }
        }
        ContractUserEntity.ListContractuserBean temp;
        for (int i = 0; i < datas.size() - 1; i++) {
            if (datas.get(i).getContractuserBean().getStatus() == 0) {
                for (int j = 0; j < datas.size() - 1 - i; j++) {
                    if (datas.get(j).getContractuserBean().getStatus() == 0) {
                        Log.d(TAG, "finishCourseSort: ");
                        double a = datas.get(j).getContractuserBean().getFinishNum() * 1.0 / datas.get(j).getContractuserBean().getTotalNum();
                        double b = datas.get(j + 1).getContractuserBean().getFinishNum() * 1.0 / datas.get(j + 1).getContractuserBean().getTotalNum();
                        if (a < b) {
                            Log.d(TAG, "finishCourseSort: 交换" + i  + " " + j + " " + a + " " + b);
                            temp = datas.get(j).getContractuserBean();
                            datas.get(j).setContractuserBean(datas.get(j + 1).getContractuserBean());
                            datas.get(j + 1).setContractuserBean(temp);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < datas.size(); i++) {
            finishList.add(datas.get(i).getContractuserBean());
        }
        finishList.addAll(otherList);
        return finishList;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getBus(Map<Integer, Integer> map){
        if (map.containsKey(1)) {
            if (map.get(1).equals(1)) {
                loadDatas();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
