package com.glorystudent.golflife;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.glorystudent.adapter.FriendsListAdapter;
import com.glorystudent.entity.FriendEntity;
import com.glorystudent.entity.FriendsRequestEntity;
import com.glorystudent.entity.GolfFriendsEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.widget.PullToRefreshLayout;
import com.glorystudent.widget.PullableNoUpListView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * golf好友列表
 * Created by hyj on 2017/2/13.
 */
public class FriendsListActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private final static String TAG = "FriendsListActivity";
    @Bind(R.id.lv_golf_friend)
    public PullableNoUpListView lv_golf_friend;
    private List<FriendEntity> friendEntities2;
    private FriendsListAdapter friendsListAdapter;
    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refresh_view;
    private boolean isRefresh;//true 是下拉刷新， false 是上拉加载

    @Override
    protected int getContentId() {
        return R.layout.activity_friends_list;
    }

    @Override
    protected void init() {
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
                loadDatas();
            }
        });
        showLoading();
        friendsListAdapter = new FriendsListAdapter(this);
        lv_golf_friend.setAdapter(friendsListAdapter);
        lv_golf_friend.setOnItemClickListener(this);
    }

    @Override
    protected void loadDatas() {
        getGolfFriends();
    }

    private void g1etGolfFriends() {
        friendEntities2 = new ArrayList<>();
        FriendsRequestEntity friendsRequestEntity = new FriendsRequestEntity();
        FriendsRequestEntity.FriendsBean friendsBean = new FriendsRequestEntity.FriendsBean();
        friendsRequestEntity.setFriends(friendsBean);
        String request = new Gson().toJson(friendsRequestEntity);
        String requestJson = RequestUtil.getRequestJson(this, request);
        String url = "/api/APIFriends/QueryFriends";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                        GolfFriendsEntity golfFriendsEntity = new Gson().fromJson(jo.toString(), GolfFriendsEntity.class);
                        if (golfFriendsEntity != null) {
                            List<GolfFriendsEntity.ListusersBean> listusers = golfFriendsEntity.getListusers();
                            for (GolfFriendsEntity.ListusersBean listuser : listusers) {
                                FriendEntity friendEntity = new FriendEntity();
                                friendEntity.setName(listuser.getUsername());
                                friendEntity.setPhoneNumber(listuser.getPhonenumber());
                                friendEntity.setCustomerphoto(listuser.getCustomerphoto());
                                friendEntity.setUserid(listuser.getUserid());

                                friendEntities2.add(friendEntity);
                            }
                            friendsListAdapter.setDatas(friendEntities2);
                        }
                    } else {
                        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailed() {
                Toast.makeText(FriendsListActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
            }
        }).getEntityData(this, url, requestJson);
    }

    private void getGolfFriends() {
        friendEntities2 = new ArrayList<>();
        FriendsRequestEntity friendsRequestEntity = new FriendsRequestEntity();
        FriendsRequestEntity.FriendsBean friendsBean = new FriendsRequestEntity.FriendsBean();
        friendsRequestEntity.setFriends(friendsBean);
        String request = new Gson().toJson(friendsRequestEntity);
        String requestJson = RequestUtil.getRequestJson(this, request);
        String url = "/api/APIFriends/QueryFriends";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                        friendEntities2.clear();
                        GolfFriendsEntity golfFriendsEntity = new Gson().fromJson(jo.toString(), GolfFriendsEntity.class);
//                            List<GolfFriendsEntity.ListfriendsBean> listfriends = golfFriendsEntity.getListfriends();
//                            Map<Integer, String> map = new HashMap<>();
//                            for (GolfFriendsEntity.ListfriendsBean listfriend : listfriends) {
//                                map.put(listfriend.getFriend_userid(), listfriend.getRemark());
//                            }

                        List<GolfFriendsEntity.ListusersBean> listusers = golfFriendsEntity.getListusers();
                        if (listusers != null) {
                            for (GolfFriendsEntity.ListusersBean listuser : listusers) {
                                String remark = listuser.getRemarkName();
                                FriendEntity friendEntity = new FriendEntity();
                                friendEntity.setName(listuser.getUsername());
                                if (remark != null && !remark.isEmpty()) {
                                    friendEntity.setName(remark);
                                }
                                friendEntity.setRemarkname(remark);
                                friendEntity.setPhoneNumber(listuser.getPhonenumber());
                                friendEntity.setCustomerphoto(listuser.getCustomerphoto());
                                friendEntities2.add(friendEntity);
                            }
                            friendsListAdapter.setDatas(friendEntities2);
                        }
                    } else if (statuscode.equals("2")) {
                        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                    } else {
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
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                Toast.makeText(FriendsListActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
    }

    @OnClick({R.id.back})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FriendEntity datas = friendsListAdapter.getDatas(position);
        Intent intent = new Intent();
        intent.putExtra("userid", datas.getUserid());
        intent.putExtra("username", datas.getName());
        intent.putExtra("phonenumber", datas.getPhoneNumber());
        intent.putExtra("customerphoto", datas.getCustomerphoto());
        setResult(0x342, intent);
        finish();
    }
}
