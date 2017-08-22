package com.glorystudent.golflife;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.lazylibrary.util.ToastUtils;
import com.glorystudent.entity.request.BaseRequestEntity;
import com.glorystudent.entity.request.RequestApplyforDetailQueryEntity;
import com.glorystudent.entity.request.RequestCancelApplyforEntity;
import com.glorystudent.entity.response.BaseResponseEntity;
import com.glorystudent.entity.response.ResponseApplyforDetailQuery;
import com.glorystudent.entity.response.ResponseApplyforRecordQuery;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.dialog.iosdialog.AlertDialog;
import com.glorystudent.util.Constants;
import com.glorystudent.util.TimeUtil;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 提现详情
 * Created by Gavin.J on 2017/5/16.
 */
public class ApplyforDetailActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.actionbar)
    LinearLayout actionbar;
    @Bind(R.id.apply_topbar_noapply)
    TextView applyTopbarNoapply;
    @Bind(R.id.applyfor_detail_number)
    TextView applyforDetailNumber;
    @Bind(R.id.applyfor_detail_type)
    TextView applyforDetailType;
    @Bind(R.id.applyfor_detail_paymoney)
    TextView applyforDetailPaymoney;
    @Bind(R.id.applyfor_detail_pay_type)
    TextView applyforDetailPayType;
    @Bind(R.id.applyfor_detail_date)
    TextView applyforDetailDate;
    @Bind(R.id.applyfor_detail_balance)
    TextView applyforDetailBalance;
    @Bind(R.id.applyfor_detail_message)
    TextView applyforDetailMessage;

    private ResponseApplyforRecordQuery.ApplyCashInfosBean bean;

    @Override
    protected int getContentId() {
        return R.layout.activity_applyfor_detail;
    }

    @Override
    protected void init() {
        super.init();
        bean= (ResponseApplyforRecordQuery.ApplyCashInfosBean) getIntent().getSerializableExtra("data");
//        request(getIntent().getIntExtra("id",0));

        applyforDetailNumber.setText(bean.getID()+"");
        applyforDetailType.setText("提现");
        applyforDetailPaymoney.setText(bean.getApplyMoney()+"");
        applyforDetailPayType.setText("支付宝");
        applyforDetailDate.setText(TimeUtil.getEventTime(bean.getApplyDate()));
        applyforDetailMessage.setText(bean.getRemark());

        if(bean.getApplyState()==1 || bean.getApplyState()==2){
            applyTopbarNoapply.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.back,R.id.apply_topbar_noapply})
    void btnOnClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.apply_topbar_noapply:
                showDialog();
                break;
        }
    }


    /**
     * 取消申请
     */
    private void request(){
        Map<String,Object> map=new BaseRequestEntity(this).getRequestMap();
        map.put("ApplyCash",new RequestCancelApplyforEntity(bean.getID(),bean.getApplyState()));
        String json=new Gson().toJson(map);

        OkGo.<String>post(Constants.BASE_URL+"/api/APIApplyCash/UpdateApplyCash")
                .params("request",json)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if(!TextUtils.isEmpty(s)){
                            BaseResponseEntity entity=new Gson().fromJson(s,BaseResponseEntity.class);
                            ToastUtils.showToast(ApplyforDetailActivity.this,entity.getStatusmessage());
                            if(entity.getStatuscode()==1){
                                request(bean.getID());

                            }
                        }
                    }
                });
    }




    private void showDialog(){
        AlertDialog dialog=new AlertDialog(this).builder()
                .setMsg("是否取消提现")
                .setCancelable(true)
                .setNegativeButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        request();
                    }
                })
                .setPositiveButton("否", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        dialog.show();

    }



    private void request(int applyforId){
        Map<String,Object> map=new BaseRequestEntity(this).getRequestMap();
        map.put("ApplyCash",new RequestApplyforDetailQueryEntity(applyforId));
        String json=new Gson().toJson(map);

        OkGo.<String>post(Constants.BASE_URL+"/api/APIApplyCash/GetApplyCash")
                .params("request",json)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if(!TextUtils.isEmpty(s)){
                            ResponseApplyforDetailQuery entity=new Gson().fromJson(s,ResponseApplyforDetailQuery.class);
                            if(entity.getStatuscode()==1){
                                applyforDetailNumber.setText(entity.getApplyCash().getID());
                                applyforDetailType.setText("提现");
                                applyforDetailPaymoney.setText(entity.getApplyCash().getApplyMoney()+"");
                                applyforDetailPayType.setText("支付宝");
                                applyforDetailDate.setText(TimeUtil.getEventTime(entity.getApplyCash().getApplyDate()));
                                applyforDetailMessage.setText(entity.getApplyCash().getRemark());
                                if(bean.getApplyState()==1 || bean.getApplyState()==2){
                                    applyTopbarNoapply.setVisibility(View.VISIBLE);
                                }else {
                                    applyTopbarNoapply.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                    }
                });
    }

    private String getStatusTxt(int status){

        switch (status){
            case 0:
                return "申请中";
            case 1:
                return "审核中";
            case 2:
                return "审核成功";
            case 3:
                return "审核失败";
            case 4:
                return "用户取消提现";
            case 5:
                return "转账中";
            case 6:
                return "转账失败";
            case 7:
                return "成功";
        }
        return "未知";
//        /// 申请中
//        /// </summary>
//        public static int Apply = 0;
//
//        /// <summary>
//        /// 审核中
//        /// </summary>
//        public static int Auditing = 1;
//
//        /// <summary>
//        /// 审核成功
//        /// </summary>
//        public static int AuditOK = 2;
//
//        /// <summary>
//        /// 审核失败
//        /// </summary>
//        public static int AuditNotOK = 3;
//
//        /// <summary>
//        /// 用户取消提现
//        /// </summary>
//        public static int UserCencel = 4;
//
//        /// <summary>
//        /// 转账中
//        /// </summary>
//        public static int Transfering = 5;
//
//        /// <summary>
//        /// 转账失败
//        /// </summary>
//        public static int TransferErr = 6;
//
//        /// <summary>
//        /// 成功
//        /// </summary>
//        public static int Success = 7;
    }

}
