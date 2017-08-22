package com.glorystudent.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.glorystudent.adapter.OpinionUserListAdapter;
import com.glorystudent.entity.ContractUserEntity;
import com.glorystudent.entity.FriendsRequestEntity;
import com.glorystudent.entity.OpinionUserEntity;
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
 * 意向学员
 * Created by hyj on 2017/1/6.
 */
public class OpinionParticipantsFragment extends BaseFragment implements TextWatcher, OkGoRequest.OnOkGoUtilListener, AdapterView.OnItemClickListener {
    private final static String TAG = "OpinionPartFragment";
    @Bind(R.id.opinion_lv)
    public PullableNoUpListView opinion_lv;
    private OpinionUserListAdapter opinionUserListAdapter;
    private List<OpinionUserEntity.ListusersBean> listusers;
    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refresh_view;
    private boolean isRefresh;//true 是下拉刷新， false 是上拉加载
    private List<ContractUserEntity.ListfriendsBean> listfriends;

    @Bind(R.id.ll_empty)
    public LinearLayout ll_empty;

    @Override
    protected int getContentId() {
        return R.layout.fragment_opinion_participants;
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
        opinionUserListAdapter = new OpinionUserListAdapter(getActivity());
        opinion_lv.setAdapter(opinionUserListAdapter);
        View head = LayoutInflater.from(getActivity()).inflate(R.layout.item_search_list, null);
        EditText et_search = (EditText) head.findViewById(R.id.search_address);
        et_search.addTextChangedListener(this);
        opinion_lv.addHeaderView(head);
        opinion_lv.setOnItemClickListener(this);
    }

    @Override
    protected void loadDatas() {
        String url = "/api/APIFriends/QueryFriends";
        FriendsRequestEntity friendsRequestEntity = new FriendsRequestEntity();
        FriendsRequestEntity.FriendsBean friendsBean = new FriendsRequestEntity.FriendsBean();
        friendsBean.setStudentstatus(2);
        friendsRequestEntity.setFriends(friendsBean);
        String request = new Gson().toJson(friendsRequestEntity);
        String requestJson = RequestUtil.getRequestJson(getActivity(), request);
        new OkGoRequest().setOnOkGoUtilListener(this).getEntityData(getActivity(), url, requestJson);
    }

    @Override
    public void parseDatas(String json) {
        try {
            JSONObject jo = new JSONObject(json);
            String statuscode = jo.getString("statuscode");
            String statusmessage = jo.getString("statusmessage");
            if(statuscode.equals("1")){
                ll_empty.setVisibility(View.GONE);
                listusers = new ArrayList<>();
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                OpinionUserEntity opinionStudentEntity = new Gson().fromJson(jo.toString(), OpinionUserEntity.class);
                listfriends = opinionStudentEntity.getListfriends();
                listusers = opinionStudentEntity.getListusers();
                for (int i = 0; i < listusers.size(); i++) {
                    for (int j = 0; j < listfriends.size(); j++) {
                        if (listusers.get(i).getUserid() == listfriends.get(j).getFriend_userid()) {
                            if (listfriends.get(j).getFriend_username() == null) {
                                listfriends.get(j).setFriend_username(listusers.get(i).getUsername());
                            }
                            String remark = listfriends.get(j).getRemark();
                            if (remark != null) {
                                listusers.get(i).setUsername(remark);
                            }
                        }
                    }
                }

                if (listusers != null) {
                    opinionUserListAdapter.setDatas(listusers);
                }
            }else if(statuscode.equals("2")){
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

    private List<OpinionUserEntity.ListusersBean> changedDatas;
    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString();
        changedDatas = new ArrayList<>();
        for (OpinionUserEntity.ListusersBean listuser : listusers) {
            if(listuser.getUsername().contains(text)){
                changedDatas.add(listuser);
            }
        }
        opinionUserListAdapter.setDatas(changedDatas);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OpinionUserEntity.ListusersBean datas = opinionUserListAdapter.getDatas(position - 1);
        int userid = datas.getUserid();
        Intent intent = new Intent(getActivity(), StudentProfileActivity.class);

        for (int i = 0; i < listfriends.size(); i++) {
            if (listfriends.get(i).getFriend_userid() == userid) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("friend", listfriends.get(i));
                intent.putExtras(bundle);
                break;
            }
        }
        intent.putExtra("userid", userid);
        intent.putExtra("type", 0);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getBus(Map<Integer, Integer> map){
        if (map.get(1).equals(1)) {
            loadDatas();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
