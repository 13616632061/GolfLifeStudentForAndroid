package com.glorystudent.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.entity.ApplyFriendsEntity;
import com.glorystudent.entity.ApplyInfoEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflife.R;
import com.glorystudent.util.Constants;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyj on 2017/2/7.
 */
public class NewFriendRecyclerAdapter extends RecyclerView.Adapter<NewFriendRecyclerAdapter.ViewHolder> {

    private final static String TAG = "NewFriendAdapter";
    private Context context;
    private List<ApplyInfoEntity.ListapplyfriendsBean> datas;
    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    public NewFriendRecyclerAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
    }

    public void setDatas(List<ApplyInfoEntity.ListapplyfriendsBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void addDatas(List<ApplyInfoEntity.ListapplyfriendsBean> datas) {
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public ApplyInfoEntity.ListapplyfriendsBean getDatas(int position) {
        return datas.get(position);
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @Override
    public NewFriendRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(context, R.layout.item_new_friend_list, null));
    }

    @Override
    public void onBindViewHolder(NewFriendRecyclerAdapter.ViewHolder holder, final int position) {
        GlideUtil.loadCircleImageView(context, datas.get(position).getApplyphoto(), holder.image, R.drawable.pic_default_avatar);
        holder.tvUserName.setText(datas.get(position).getApplyname());
        holder.tvRemark.setText(datas.get(position).getApplyremark());
        String applystatus = datas.get(position).getApplystatus();
        switch (applystatus) {
            case "0":
                //待审核
                holder.tvAgree.setVisibility(View.VISIBLE);
                holder.tvAgreed.setVisibility(View.GONE);
                holder.tvAgree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        agreeApplyFriend(datas.get(position));
                    }
                });
                break;
            case "1":
                //已添加
                holder.tvAgree.setVisibility(View.GONE);
                holder.tvAgreed.setVisibility(View.VISIBLE);
                holder.tvAgreed.setText("已同意");
                break;
            case "2":
                //已拒绝
                holder.tvAgree.setVisibility(View.GONE);
                holder.tvAgreed.setVisibility(View.VISIBLE);
                holder.tvAgreed.setText("已拒绝");
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecyclerItemClickListener != null) {
                    onRecyclerItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private ImageView image;
        private TextView tvUserName;
        private TextView tvRemark;
        private TextView tvAgree;
        private TextView tvAgreed;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.iv_header);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_username);
            tvRemark = (TextView) itemView.findViewById(R.id.tv_remark);
            tvAgree = (TextView) itemView.findViewById(R.id.tv_add_book_friend);
            tvAgreed = (TextView) itemView.findViewById(R.id.tv_friend);
            this.itemView = itemView;
        }
    }

    public interface OnRecyclerItemClickListener {
        void onItemClick(int position);
    }

    /**
     * 同意申请请求
     *
     * @param data
     */
    private void agreeApplyFriend(final ApplyInfoEntity.ListapplyfriendsBean data) {
        ((BaseActivity) context).showLoading();
        ApplyFriendsEntity applyFriendsEntity = new ApplyFriendsEntity();
        ApplyFriendsEntity.ApplyFriendsBean applyFriendsBean = new ApplyFriendsEntity.ApplyFriendsBean();
        applyFriendsBean.setApplyfriends_id(data.getApplyfriends_id());
//        applyFriendsBean.setApplyfriends_type(data.getApplyfriends_type());
//        applyFriendsBean.setApplyuserid(data.getUserid());
//        applyFriendsBean.setAnsweruserid(data.getAnsweruserid());
//        applyFriendsBean.setApplytype("1");
        applyFriendsBean.setApplystatus("1");//1 同意 ，2 拒绝
        applyFriendsEntity.setApplyfriends(applyFriendsBean);
        String request = new Gson().toJson(applyFriendsEntity);
        String requestJson = RequestUtil.getRequestJson(context, request);
        String url = "/api/APIApplyFriends/EditApplyFriends";
        Log.i(TAG, "onClick: ---->" + requestJson);
        Log.i(TAG, "onClick: ---->" + Constants.BASE_URL + url);
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    Log.d(TAG, "parseDatas: --->" + json);
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
                        data.setApplystatus("1");
                        notifyDataSetChanged();
//                        localBroadcastManager.sendBroadcast(intent);
//                        EMMessage message = EMMessage.createTxtSendMessage(context.getResources().getString(R.string.chat_begin), finalData.getPhonenumber());
//                        EMClient.getInstance().chatManager().sendMessage(message);
                    } else {
                        Toast.makeText(context, statusmessage, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ((BaseActivity) context).dismissLoading();
            }

            @Override
            public void requestFailed() {
                Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
                ((BaseActivity) context).dismissLoading();
            }
        }).getEntityData(context, url, requestJson);
    }
}
