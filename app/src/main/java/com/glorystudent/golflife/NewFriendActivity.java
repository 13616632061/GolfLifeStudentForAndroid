package com.glorystudent.golflife;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.glorystudent.adapter.NewFriendRecyclerAdapter;
import com.glorystudent.adapter.RecycleViewDivider;
import com.glorystudent.entity.ApplyFriendsEntity;
import com.glorystudent.entity.ApplyInfoEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.DensityUtil;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.widget.PullToRefreshLayout;
import com.google.gson.Gson;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 新的朋友
 * Created by hyj on 2017/2/7.
 */
public class NewFriendActivity extends BaseActivity implements NewFriendRecyclerAdapter.OnRecyclerItemClickListener, OnSwipeMenuItemClickListener {
    private final static String TAG = "NewFriendActivity";
//    @Bind(R.id.lv_new_friend)
//    public ListView lv_new_friend;

    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refreshView;

    @Bind(R.id.swipe_menu_recycler_view)
    public SwipeMenuRecyclerView newFriendSwipeMenuRecyclerView;
    private boolean isRefresh = true;//true 是下拉刷新， false 是上拉加载
    private int page = 1;
    private NewFriendRecyclerAdapter newFriendRecyclerAdapter;

    @Override
    protected int getContentId() {
        return R.layout.activity_new_friend;
    }

    @Override
    protected void init() {
        refreshView.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //下拉刷新回调
                isRefresh = true;
                page = 1;
                loadDatas();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                //上拉加载回调
                isRefresh = false;
                page++;
                loadDatas();
            }
        });
        showLoading();
        newFriendSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        newFriendRecyclerAdapter = new NewFriendRecyclerAdapter(this);
        newFriendSwipeMenuRecyclerView.setAdapter(newFriendRecyclerAdapter);
        newFriendSwipeMenuRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, R.drawable.divider_mileage));
        // 设置菜单创建器。
        newFriendSwipeMenuRecyclerView.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(NewFriendActivity.this)
                        .setBackgroundDrawable(getResources().getColor(R.color.colorRed))
                        .setText("删除") // 文字。
                        .setTextColor(Color.WHITE) // 文字颜色。
                        .setTextSize(18) // 文字大小。
                        .setWidth(DensityUtil.dip2px(NewFriendActivity.this, 88))
                        .setHeight(DensityUtil.dip2px(NewFriendActivity.this, 56));
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
            }
        });
        // 设置菜单Item点击监听。
        newFriendSwipeMenuRecyclerView.setSwipeMenuItemClickListener(this);
        newFriendRecyclerAdapter.setOnRecyclerItemClickListener(this);
    }

    @Override
    protected void loadDatas() {
        ApplyFriendsEntity applyFriendsEntity = new ApplyFriendsEntity();
        ApplyFriendsEntity.ApplyFriendsBean applyFriendsBean = new ApplyFriendsEntity.ApplyFriendsBean();
        applyFriendsEntity.setApplyfriends(applyFriendsBean);
        applyFriendsEntity.setPage(page);
        String request = new Gson().toJson(applyFriendsEntity);
        String requestJson = RequestUtil.getRequestJson(this, request);
        String url = "/api/APIApplyFriends/QueryApplyFriends";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        refreshView.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                        ApplyInfoEntity applyInfoEntity = new Gson().fromJson(jo.toString(), ApplyInfoEntity.class);
                        List<ApplyInfoEntity.ListapplyfriendsBean> listapplyfriends = applyInfoEntity.getListapplyfriends();
                        if (listapplyfriends != null) {
                            if (isRefresh) {
                                newFriendRecyclerAdapter.setDatas(listapplyfriends);
                            } else {
                                newFriendRecyclerAdapter.addDatas(listapplyfriends);
                            }
                        }
                    } else if (statuscode.equals("2")) {
                        refreshView.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                    } else {
                        refreshView.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                refreshView.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                Toast.makeText(NewFriendActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
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
    public void onItemClick(int position) {
        ApplyInfoEntity.ListapplyfriendsBean datas = newFriendRecyclerAdapter.getDatas(position);
        if (!datas.getApplystatus().equals("2")) {//已拒绝则不能点击
            startFriendProfile(datas);
        }
    }

    /**
     * 侧滑菜单的点击事件
     *
     * @param closeable
     * @param adapterPosition
     * @param menuPosition
     * @param direction
     */
    @Override
    public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {

    }

    /**
     * 打开好友资料页面
     */
    private void startFriendProfile(ApplyInfoEntity.ListapplyfriendsBean datas) {
        Intent intent = new Intent(this, FriendProfileActivity.class);
        intent.putExtra("userid", datas.getApplyuserid());
        intent.putExtra("applystatus", datas.getApplystatus());//申请状态 0待审核，1通过，2拒绝
        intent.putExtra("applyfriends_type", datas.getApplyfriends_type());//申请方式 1通讯录查找，2扫码，3账号查找
        intent.putExtra("applyremark", datas.getApplyremark());//打招呼语
        intent.putExtra("applyfriends_id", datas.getApplyfriends_id());//申请信息id
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(Map<String, String> map) {
        if (map.containsKey("NewFriendActivity")) {
            if (map.get("NewFriendActivity").equals("refresh")) {
                page = 1;
                isRefresh = true;
                loadDatas();
            }
        }
    }
}
