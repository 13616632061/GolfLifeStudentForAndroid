package com.glorystudent.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.glorystudent.entity.ApplyCheckEntity;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflife.R;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gavin.J on 2017/5/25.
 */

public class ApplyCheckListAdapter extends AbsBaseAdapter<ApplyCheckEntity.ApplyTeamUserListBean> {

    private static final String TAG = "ApplyCheckListAdapter";

    public ApplyCheckListAdapter(Context context) {
        super(context, R.layout.item_apply_check_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, final ApplyCheckEntity.ApplyTeamUserListBean data) {
        viewHolder.setTextView(R.id.tv_apply_name, data.getName());
        viewHolder.setTextView(R.id.tv_apply_remarks, data.getRemarks());
        GlideUtil.loadCircleImageView(context, data.getCustomerphoto(), (ImageView) viewHolder.getView(R.id.iv_apply_head), R.drawable.pic_default_avatar);
        switch (data.getStatus()) {
            case 0://待同意
                viewHolder.getView(R.id.tv_apply_agree).setVisibility(View.VISIBLE);
                viewHolder.getView(R.id.tv_apply_agreed).setVisibility(View.GONE);
                viewHolder.getView(R.id.tv_apply_refused).setVisibility(View.GONE);
                break;
            case 1://已同意
                viewHolder.getView(R.id.tv_apply_agreed).setVisibility(View.VISIBLE);
                viewHolder.getView(R.id.tv_apply_agree).setVisibility(View.GONE);
                viewHolder.getView(R.id.tv_apply_refused).setVisibility(View.GONE);
                break;
            case 2://已拒绝
                viewHolder.getView(R.id.tv_apply_refused).setVisibility(View.VISIBLE);
                viewHolder.getView(R.id.tv_apply_agree).setVisibility(View.GONE);
                viewHolder.getView(R.id.tv_apply_agreed).setVisibility(View.GONE);
                break;
        }
        viewHolder.getView(R.id.tv_apply_agree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestAgree(data);
            }
        });
    }

    /**
     * 请求网络同意入队
     */
    private void requestAgree(final ApplyCheckEntity.ApplyTeamUserListBean data) {
        ((BaseActivity) context).showLoading();
        String json = "\"applyTeamUser\":{" + "\"id\":" + data.getId() + ",\"status\":" + 1 + "}";
        String requestJson = RequestUtil.getJson(context, json);
        Log.i(TAG, "requestAgree: " + requestJson);
        String url = "/api/APITeam/EditTeamUserApply";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {//正常
                        Toast.makeText(context, "操作成功", Toast.LENGTH_SHORT).show();
                        data.setStatus(1);
                        notifyDataSetChanged();
                    } else if (statuscode.equals("2")) {//暂无数据
                        Log.i(TAG, "parseDatas: " + statusmessage);
                    } else {
                        Log.i(TAG, "parseDatas: " + statusmessage);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ((BaseActivity) context).dismissLoading();
            }

            @Override
            public void requestFailed() {
                ((BaseActivity) context).dismissLoading();
                Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(context, url, requestJson);
    }
}
