package com.glorystudent.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.dialog.Dialog;
import com.glorystudent.entity.ApplyListEntity;
import com.glorystudent.golflibrary.adapter.AbsMoreBaseAdapter;
import com.glorystudent.golflibrary.util.PhoneFormatCheckUtils;
import com.glorystudent.golflife.ApplyListActivity;
import com.glorystudent.golflife.R;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/4/27.
 */

public class ApplyListAdapter extends AbsMoreBaseAdapter<ApplyListEntity.ListsignupBean> {
    private static final String TAG = "ApplyListAdapter";
    private PopupWindow popupWindow;

    public ApplyListAdapter(Context context) {
        super(context, R.layout.item_apply_list_1, R.layout.item_apply_list_2, R.layout.item_apply_list_3);
    }

    @Override
    public void bindDatas(ViewHolder viewHolder, ApplyListEntity.ListsignupBean datas, int typeView) {
        switch (typeView) {
            case 0://报名成功
                initValue0(viewHolder, datas);
                break;
            case 1://已取消
                initValue1(viewHolder, datas);
                break;
            case 2://已拒绝
                initValue2(viewHolder, datas);
                break;
        }
    }

    /**
     * 已拒绝
     *
     * @param viewHolder
     * @param datas
     */
    private void initValue2(final ViewHolder viewHolder, final ApplyListEntity.ListsignupBean datas) {
        initPublicValue(viewHolder, datas);
        viewHolder.bindTextView(R.id.tv_apply_reject_reason, (String) datas.getSign_refuse());
        if (datas.isSign_ifallow()) {
            ((RadioButton) viewHolder.getView(R.id.rb_apply_allow)).setChecked(true);
        } else {
            ((RadioButton) viewHolder.getView(R.id.rb_apply_not_allow)).setChecked(true);
        }
        ((RadioGroup) viewHolder.getView(R.id.rg_apply_allow)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_apply_allow) {
                    //允许再次报名
                    Log.i(TAG, "onCheckedChanged: 允许");
                    allowSign(datas, true);
                } else if (checkedId == R.id.rb_apply_not_allow) {
                    //不允许再次报名
                    Log.i(TAG, "onCheckedChanged: 不允许");
                    allowSign(datas, false);
                }
            }
        });
    }

    /**
     * 允许和不允许再次报名
     *
     * @param datas
     * @param flag
     */
    private void allowSign(final ApplyListEntity.ListsignupBean datas, final boolean flag) {
        ((ApplyListActivity) context).showLoading();
        String json = "\"signup\":{" + "\"signup_id\":" + datas.getSignup_id() + "," + "\"sign_ifallow\":" + flag + "}";
        String requestJson = RequestUtil.getJson(context, json);
        Log.i(TAG, "onClick: " + requestJson);
        String url = "/api/APISignUp/AgreeSignUp";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        ((ApplyListActivity) context).dismissLoading();
                        datas.setSign_ifallow(flag);
                        notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailed() {
                ((ApplyListActivity) context).dismissLoading();
                Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(context, url, requestJson);
    }

    /**
     * 取消报名
     *
     * @param viewHolder
     * @param datas
     */
    private void initValue1(ViewHolder viewHolder, ApplyListEntity.ListsignupBean datas) {
        initPublicValue(viewHolder, datas);
        viewHolder.bindTextView(R.id.tv_apply_cancel_reason, (String) datas.getSign_refuse());
    }

    /**
     * 报名成功
     *
     * @param viewHolder
     * @param datas
     */
    private void initValue0(final ViewHolder viewHolder, final ApplyListEntity.ListsignupBean datas) {
        initPublicValue(viewHolder, datas);
        if (datas.getSign_sex().equals("2")) {
            viewHolder.bindTextView(R.id.tv_apply_sex, "女");
        } else {
            viewHolder.bindTextView(R.id.tv_apply_sex, "男");
        }
        //设置详情信息
        viewHolder.bindTextView(R.id.tv_apply_card_number, (String) datas.getSign_cardnumber());
        viewHolder.bindTextView(R.id.tv_apply_company, (String) datas.getSign_company());
        viewHolder.bindTextView(R.id.tv_apply_registration_fee, (String) datas.getSign_registrationfee());
        viewHolder.bindTextView(R.id.tv_apply_member, (String) datas.getSign_member());
        if (datas.isSign_ifbringguests()) {
            viewHolder.getView(R.id.ll_apply_guest).setVisibility(View.VISIBLE);
            viewHolder.bindTextView(R.id.tv_apply_guest_number, datas.getSign_number() + "名：");
            if (datas.getSign_list() != null && datas.getSign_list().size() > 0) {
                StringBuffer names = new StringBuffer("");
                for (int i = 0; i < datas.getSign_list().size(); i++) {
                    names.append(datas.getSign_list().get(i).getSign_name() + " ");
                }
                viewHolder.bindTextView(R.id.tv_apply_guest_names, names.toString());
            } else {
                viewHolder.bindTextView(R.id.tv_apply_guest_names, "");
            }
        } else {
            viewHolder.getView(R.id.ll_apply_guest).setVisibility(View.GONE);
        }
        //初始化签到信息
        if (datas.getSign_upstate().equals("1")) {
            viewHolder.getView(R.id.tv_apply_sign).setVisibility(View.VISIBLE);
            viewHolder.getView(R.id.tv_apply_cancel_sign).setVisibility(View.GONE);
        } else if (datas.getSign_upstate().equals("2")) {
            viewHolder.getView(R.id.tv_apply_sign).setVisibility(View.GONE);
            viewHolder.getView(R.id.tv_apply_cancel_sign).setVisibility(View.VISIBLE);
        }
        //拨打电话
        viewHolder.getView(R.id.ll_apply_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phoneStr = datas.getSign_phone();
                Log.i(TAG, "initValue0: " + phoneStr);
                if (phoneStr != null && !phoneStr.isEmpty()) {
                    if (PhoneFormatCheckUtils.isPhoneLegal(phoneStr)) {
                        //拨打电话
                        Dialog.getInstance().setOnShowDialogListener(new Dialog.OnShowDialogListener() {
                            @Override
                            public void onSure() {
                                //意图：想干什么事
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_CALL);
                                //url:统一资源定位符
                                //uri:统一资源标示符（更广）
                                intent.setData(Uri.parse("tel:" + phoneStr));
                                try {
                                    //开启系统拨号器
                                    context.startActivity(intent);
                                } catch (Exception e) {
                                    Toast.makeText(context, "请手动打开拨打电话权限", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancel() {

                            }
                        }).showDialog(context, "拨打电话", "你确定拨打该成员电话吗?", "拨打");
                    } else {
                        Toast.makeText(context, "手机号格式不正确", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "手机号不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //发送短信
        viewHolder.getView(R.id.ll_apply_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phoneStr = datas.getSign_phone();
                Log.i(TAG, "initValue0: " + phoneStr);
                if (phoneStr != null && !phoneStr.isEmpty()) {
                    if (PhoneFormatCheckUtils.isPhoneLegal(phoneStr)) {
                        //发送短信
                        Dialog.getInstance().setOnShowDialogListener(new Dialog.OnShowDialogListener() {
                            @Override
                            public void onSure() {
                                Uri smsToUri = Uri.parse("smsto:" + phoneStr);
                                Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                                try {
                                    //开启系统短信
                                    context.startActivity(intent);
                                } catch (Exception e) {
                                    Toast.makeText(context, "请手动打开发送短信权限", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancel() {

                            }
                        }).showDialog(context, "发送短信", "你确定给该成员发短信吗?", "发送");
                    } else {
                        Toast.makeText(context, "手机号格式不正确", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "手机号不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //拒绝报名
        viewHolder.getView(R.id.ll_apply_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopWindow(datas);
            }
        });
        viewHolder.getView(R.id.tv_apply_sign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //签到
                signUp(datas, "2");
            }
        });
        viewHolder.getView(R.id.tv_apply_cancel_sign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消签到
                signUp(datas, "1");
            }
        });
    }

    /**
     * 拒绝的对话框
     *
     * @param datas
     */
    public void showPopWindow(final ApplyListEntity.ListsignupBean datas) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popu_reject_layout, null);
        final TextView rejectInfo = (TextView) view.findViewById(R.id.et_reject_info);
        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        TextView sure = (TextView) view.findViewById(R.id.sure);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                ((ApplyListActivity) context).showLoading();
                final String text = rejectInfo.getText().toString().trim();
                if (text.isEmpty()) {
                    Toast.makeText(context, "拒绝原因不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String json = "\"wxpay\":{" + "\"sign_id\":" + datas.getSignup_id() + "}," + "\"signrefse\":" + "\"" + text + "\"" + "," + "\"type\":" + "\"2\"";
                String requestJson = RequestUtil.getJson(context, json);
                Log.i(TAG, "onClick: " + requestJson);
                String url = "/api/APIWXPay/WXPayReFundAPP";
                OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
                    @Override
                    public void parseDatas(String json) {
                        try {
                            JSONObject jo = new JSONObject(json);
                            String statuscode = jo.getString("statuscode");
                            String statusmessage = jo.getString("statusmessage");
                            if (statuscode.equals("1")) {
                                ((ApplyListActivity) context).dismissLoading();
                                datas.setSign_state("4");//已拒绝
                                datas.setSign_refuse(text);
                                notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void requestFailed() {
                        ((ApplyListActivity) context).dismissLoading();
                        Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
                    }
                }).getEntityData(context, url, requestJson);
            }
        });
        //显示popupwindow
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        popupWindow.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        popupWindow.setBackgroundDrawable(dw);
        // 在中间显示
        popupWindow.showAtLocation(view.findViewById(R.id.cancel), Gravity.CENTER, 0, 0);
    }

    /**
     * 签到和取消签到的方法 1为签到，2签到
     *
     * @param datas
     * @param state
     */
    private void signUp(final ApplyListEntity.ListsignupBean datas, final String state) {
        ((ApplyListActivity) context).showLoading();
        String json = "\"signup\":{" + "\"signup_id\":" + datas.getSignup_id() + "," + "\"sign_upstate\":" + "\"" + state + "\"" + "}";
        String requestJson = RequestUtil.getJson(context, json);
        Log.i(TAG, "onClick: " + requestJson);
        String url = "/api/APISignUp/EditSignUp";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        ((ApplyListActivity) context).dismissLoading();
                        datas.setSign_upstate(state);
                        notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailed() {
                ((ApplyListActivity) context).dismissLoading();
                Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(context, url, requestJson);
    }

    /**
     * 公共的赋值方法
     *
     * @param viewHolder
     * @param datas
     */
    private void initPublicValue(final ViewHolder viewHolder, ApplyListEntity.ListsignupBean datas) {
        if (datas.getSign_logo() != null) {
            viewHolder.bindImageView(R.id.iv_apply_head, (String) datas.getSign_logo(), R.drawable.pic_default_avatar);
        }
        viewHolder.bindTextView(R.id.tv_apply_name, datas.getSign_name());
        //折叠的监听
        ((CheckBox) viewHolder.getView(R.id.cb_apply_expand)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    viewHolder.getView(R.id.ll_apply_detail).setVisibility(View.VISIBLE);
                } else {
                    viewHolder.getView(R.id.ll_apply_detail).setVisibility(View.GONE);
                }
            }
        });
    }
}
