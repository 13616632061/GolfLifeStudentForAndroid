package com.glorystudent.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.glorystudent.entity.ItemMenuEntity;
import com.glorystudent.entity.response.ResponseApplyforRecordQuery;
import com.glorystudent.golflife.R;
import com.glorystudent.util.TimeUtil;

import java.util.List;

/**
 * Created by billlamian on 17-8-4.
 * 查询提现申请记录
 */

public class ApplyforRecordAdapter extends BaseQuickAdapter<ItemMenuEntity<ResponseApplyforRecordQuery.ApplyCashInfosBean>,BaseViewHolder> {
    public ApplyforRecordAdapter(@LayoutRes int layoutResId, @Nullable List<ItemMenuEntity<ResponseApplyforRecordQuery.ApplyCashInfosBean>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemMenuEntity<ResponseApplyforRecordQuery.ApplyCashInfosBean> item) {
        String date=item.getData().getApplyDate();

        helper.setText(R.id.item_applyfor_record_status,getStatusTxt(item.getData().getApplyState()));
        helper.setText(R.id.item_applyfor_record_time, TimeUtil.getEventTime(date));
        helper.setText(R.id.item_applyfor_record_money,-item.getData().getApplyMoney()+"");



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
