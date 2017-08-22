package com.glorystudent.golflife;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.lazylibrary.util.ToastUtils;
import com.glorystudent.adapter.AccountListAdapter;
import com.glorystudent.adapter.RecycleViewDivider;
import com.glorystudent.dialog.Dialog;
import com.glorystudent.entity.request.BaseRequestEntity;
import com.glorystudent.entity.request.RequestBlankAccountDeleteEntity;
import com.glorystudent.entity.response.ResponseQueryBlanceListEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.DensityUtil;
import com.glorystudent.util.Constants;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Gavin.J on 2017/5/16.
 * 绑定账号列表
 */

public class SelectAccountActivity extends BaseActivity implements OnSwipeMenuItemClickListener, AccountListAdapter.OnRecyclerItemClickListener {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.rv_account_recycler_view)
    SwipeMenuRecyclerView rvAccountRecyclerView;
    @Bind(R.id.rl_add_account)
    RelativeLayout rlAddAccount;
    private AccountListAdapter accountListAdapter;
    private List<ResponseQueryBlanceListEntity.UserBanksBean> data=new ArrayList<>();
    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            SwipeMenuItem deleteItem = new SwipeMenuItem(SelectAccountActivity.this)
                    .setBackgroundDrawable(R.color.colorRed)
                    .setText("删除") // 文字。
                    .setTextColor(Color.WHITE) // 文字颜色。
                    .setTextSize(18) // 文字大小。
                    .setWidth(DensityUtil.dip2px(SelectAccountActivity.this, 88))
                    .setHeight(DensityUtil.dip2px(SelectAccountActivity.this, 60));
            swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
        }
    };

    @Override
    protected int getContentId() {
        return R.layout.activity_select_account;
    }

    @Override
    protected void init() {
        // 设置菜单创建器。
        rvAccountRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        rvAccountRecyclerView.setSwipeMenuItemClickListener(this);
        rvAccountRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvAccountRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, R.drawable.divider_mileage));
        accountListAdapter = new AccountListAdapter(this,data);
        accountListAdapter.setOnRecyclerItemClickListener(this);
        rvAccountRecyclerView.setAdapter(accountListAdapter);
    }

    @Override
    protected void loadDatas() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        requestQueryBalanceList();
    }

    @OnClick({R.id.back, R.id.rl_add_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl_add_account:
                //跳转添加账户操作
                startActivity(new Intent(this, AddAccountActivity.class));
                break;
        }
    }

    /**
     * 左滑菜单的点击事件
     *
     * @param closeable
     * @param adapterPosition
     * @param menuPosition
     * @param direction
     */
    @Override
    public void onItemClick(final Closeable closeable, final int adapterPosition, int menuPosition, int direction) {
        Dialog.getInstance().setOnShowDialogListener(new Dialog.OnShowDialogListener() {
            @Override
            public void onSure() {
                requestDeleteBlance(data.get(adapterPosition).getID());
                closeable.smoothCloseRightMenu();
            }

            @Override
            public void onCancel() {

            }
        }).showDialog(this, "提示", "确认删除这个账户", "删除");
    }

    /**
     * item的点击事件
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        Intent intent=new Intent(this,WithdrawalsActivity.class);
        intent.putExtra("data",data.get(position));
        setResult(Activity.RESULT_OK,intent);
        finish();
    }


    /**
     * 获取绑定提现账户列表
     */
    private void requestQueryBalanceList(){
        data.clear();
        Map<String,Object> map=new BaseRequestEntity(this).getRequestMap();
        String json=new Gson().toJson(map);
        OkGo.<String>post(Constants.BASE_URL+"/api/APIUserBankInfo/QueryUserBank")
                .params("request",json)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if(!TextUtils.isEmpty(s)){
                            ResponseQueryBlanceListEntity entity=new Gson().fromJson(s,ResponseQueryBlanceListEntity.class);
                            if(entity.getStatuscode()==1){
                                for (ResponseQueryBlanceListEntity.UserBanksBean userBean:entity.getUserBanks()
                                     ) {
                                    data.add(userBean);
                                }
                                accountListAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }
    private void requestDeleteBlance( int bankId){
        Map<String,Object> map=new BaseRequestEntity(this).getRequestMap();
        map.put("UserBank",new RequestBlankAccountDeleteEntity(bankId));
        String json=new Gson().toJson(map);
        OkGo.<String>post(Constants.BASE_URL+"/api/APIUserBankInfo/DeleteUserBank")
                .params("request",json)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        ResponseQueryBlanceListEntity entity=new Gson().fromJson(s,ResponseQueryBlanceListEntity.class);
                        if(entity.getStatuscode()==1){
                            requestQueryBalanceList();
                        }else {
                            ToastUtils.showToast(SelectAccountActivity.this,entity.getStatusmessage());
                        }
                    }
                });

    }
}
