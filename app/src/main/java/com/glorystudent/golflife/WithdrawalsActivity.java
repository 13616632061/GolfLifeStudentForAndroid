package com.glorystudent.golflife;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lazylibrary.util.ToastUtils;
import com.glorystudent.entity.request.BaseRequestEntity;
import com.glorystudent.entity.request.RequestBlankApplyForEntity;
import com.glorystudent.entity.response.BaseResponseEntity;
import com.glorystudent.entity.response.ResponseQueryBlanceListEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.dialog.iosdialog.AlertDialog;
import com.glorystudent.util.Constants;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 提现页面
 * Created by Gavin.J on 2017/5/16.
 */
public class WithdrawalsActivity extends BaseActivity {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.iv_already_head)
    ImageView alreadyHead;
    @Bind(R.id.ll_select_account)
    LinearLayout selectAccountLayout;
    @Bind(R.id.ll_already_account)
    LinearLayout alreadyAccountLayout;
    @Bind(R.id.et_money_input)
    EditText moneyInput;
    @Bind(R.id.tv_all_take_out)
    TextView allTakeOut;
    @Bind(R.id.tv_already_account)
    TextView alreadyAccount;
    @Bind(R.id.tv_sure)
    TextView sure;

    @Bind(R.id.apply_topbar_apply)
    TextView txtApplyForRecord;

    private Map<String, String> data;
    private String remainingSum;

    private final int REQUESTR_CODE_SELECT_ACCOUNT=99;

    private ResponseQueryBlanceListEntity.UserBanksBean userBanksBean;//当前选中账户
    @Override
    protected int getContentId() {
        return R.layout.activity_withdrawals;
    }

    @Override
    protected void init() {


        Intent intent = getIntent();
        remainingSum = intent.getStringExtra("money");
        moneyInput.setHint("余额￥" + remainingSum);


    }

    @Override
    protected void loadDatas() {


    }

    @OnClick({R.id.back, R.id.ll_select_account, R.id.ll_already_account, R.id.tv_all_take_out, R.id.tv_sure,R.id.apply_topbar_apply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.ll_select_account:
                //选择账号
                Intent intent=new Intent(this,SelectAccountActivity.class);
                startActivityForResult(intent,REQUESTR_CODE_SELECT_ACCOUNT);
                break;
            case R.id.ll_already_account:
                //已选账号
                Intent intent1=new Intent(this,SelectAccountActivity.class);
                startActivityForResult(intent1,REQUESTR_CODE_SELECT_ACCOUNT);
                break;
            case R.id.tv_all_take_out:
                moneyInput.setText(""+remainingSum);
                //全部提现
                if (!checkAccount()) {
                    return;
                }
//                startActivity(new Intent(this, WithdrawalsDetailActivity.class));
                String money=moneyInput.getText().toString().trim();

                float applyForMoney=TextUtils.isEmpty(money)?0.00f:Float.parseFloat(money);
                if(applyForMoney<=0){
                    ToastUtils.showToast(this,"提现金额必须大于0");
                    return;
                }
                showApplyforDialog(applyForMoney,userBanksBean);
                break;
            case R.id.tv_sure:
                //确认提现
                if (!checkAccount()) {
                    return;
                }
                if (!checkWithdrawalAmount()) {
                    return;
                }
                String moneys=moneyInput.getText().toString().trim();
                float applyForMoneys=TextUtils.isEmpty(moneys)?0.00f:Float.parseFloat(moneys);
                if(applyForMoneys<=0){
                    ToastUtils.showToast(this,"提现金额必须大于0");
                    return;
                }
                showApplyforDialog(applyForMoneys,userBanksBean);
                break;
            case R.id.apply_topbar_apply://提现记录
                startActivity(new Intent(this,ApplyforRecordActivity.class));
                break;
        }
    }

    /**
     * 检查账户是否为空
     */
    private boolean checkAccount() {
        if (userBanksBean == null) {
            Toast.makeText(this, "账户不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 检查提现金额
     */
    private boolean checkWithdrawalAmount() {
        String moneyStr = moneyInput.getText().toString().trim();
        if (moneyStr.isEmpty()) {
            Toast.makeText(this, "提现金额不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Double.parseDouble(moneyStr) > Double.parseDouble(remainingSum)) {
            Toast.makeText(this, "提现金额不能大于余额", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void showApplyforDialog(final float applyforMoney, final ResponseQueryBlanceListEntity.UserBanksBean userBanksBean){
        AlertDialog alertDialog=new AlertDialog(this).builder()
                .setTitle("提示")
                .setMsg("确认提现 "+applyforMoney+"元 到账户 "+userBanksBean.getAccountNum())
                .setCancelable(true)
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setPositiveButton("提现", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestApplyFor(userBanksBean.getID(),Float.parseFloat(moneyInput.getText().toString().trim()));
                    }
                });
        alertDialog.show();

    }

    /**
     * 提现申请
     * @param bankId
     * @param applyForMoney
     */
    private void requestApplyFor(int bankId,float applyForMoney){
        Map<String,Object> map=new BaseRequestEntity(this).getRequestMap();
        map.put("ApplyCash",new RequestBlankApplyForEntity(bankId,applyForMoney));
        String json=new Gson().toJson(map);

        OkGo.<String>post(Constants.BASE_URL+"/api/APIApplyCash/AddApplyCash")
                .params("request",json)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if(!TextUtils.isEmpty(s)){
                            BaseResponseEntity entity=new Gson().fromJson(s,BaseResponseEntity.class);
                            if(entity.getStatuscode()==1){//提现成功
                                startActivity(new Intent(WithdrawalsActivity.this, WithdrawalsDetailActivity.class));
                            }
                            ToastUtils.showToast(WithdrawalsActivity.this,entity.getStatusmessage());
                        }
                    }
                });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }


    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(Map<String, Map<String, String>> map) {
        if (map.containsKey("WithdrawalsActivity")) {
            Map<String, String> data = map.get("WithdrawalsActivity");
            updateUI(data);
        }
    }

    /**
     * 更新显示
     *
     * @param data
     */
    private void updateUI(Map<String, String> data) {
        selectAccountLayout.setVisibility(View.GONE);
        alreadyAccountLayout.setVisibility(View.VISIBLE);
        this.data = data;
        alreadyAccount.setText(data.get("name") + "(" + data.get("account") + ")");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            if(requestCode==REQUESTR_CODE_SELECT_ACCOUNT){//选择提现账号
                userBanksBean= (ResponseQueryBlanceListEntity.UserBanksBean) data.getSerializableExtra("data");
                alreadyAccount.setText(userBanksBean.getAccountName()+"("+userBanksBean.getAccountNum()+")");
                alreadyHead.setImageResource(R.drawable.icon_zhifubao);
                alreadyAccount.setTextColor(ContextCompat.getColor(this,R.color.colorBlack));

            }
        }
    }
}
