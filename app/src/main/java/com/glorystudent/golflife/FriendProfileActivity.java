package com.glorystudent.golflife;

import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.entity.ApplyFriendsEntity;
import com.glorystudent.entity.FriendsProfileEntity;
import com.glorystudent.entity.FriendsRequestEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.dialog.iosdialog.ActionSheetDialog;
import com.glorystudent.golflibrary.dialog.iosdialog.AlertDialog;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.util.Constants;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.google.gson.Gson;
import com.thinkcool.circletextimageview.CircleTextImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 好友资料
 * Created by hyj on 2017/2/6.
 */
public class FriendProfileActivity extends BaseActivity {
    private final static String TAG = "FriendProfileActivity";

    @Bind(R.id.iv_head_img)
    public CircleTextImageView iv_head_img;

    @Bind(R.id.tv_new_friend)
    public TextView tv_new_friend;

    @Bind(R.id.tv_activity_name)
    public TextView tv_activity_name;

    @Bind(R.id.iv_sex_man)
    public ImageView iv_sex_man;
    @Bind(R.id.iv_sex_lady)
    public ImageView iv_sex_lady;

    @Bind(R.id.iv_vip)
    public ImageView iv_vip;

    @Bind(R.id.tv_username)
    public TextView tv_username;

    @Bind(R.id.tv_veteran)
    public TextView tv_veteran;

    @Bind(R.id.tv_age)
    public TextView tv_age;

    @Bind(R.id.tv_address)
    public TextView tv_address;

    @Bind(R.id.iv_more)
    public ImageView iv_more;

    @Bind(R.id.rl_all_profile)
    public RelativeLayout rl_all_profile;

    @Bind(R.id.ll_information)
    public LinearLayout ll_information;

    @Bind(R.id.ll_apply_friend)
    public LinearLayout ll_apply_friend;

    @Bind(R.id.tv_greet)
    public TextView tv_greet;

    @Bind(R.id.tv_source)
    public TextView tv_source;

    @Bind(R.id.tv_no_add)
    public TextView tv_no_add;

    @Bind(R.id.ll_foot)
    public LinearLayout ll_foot;

    @Bind(R.id.ll_add_foot)
    public LinearLayout ll_add_foot;

    private FriendsProfileEntity.UserBean user;//个人信息实体
    private int applyfriends_id;//申请信息id
    private String applyremark;//申请的打招呼语
    private String applyfriends_type;//申请方式 1电话簿查找，2扫码，3队内查找  //新的朋友传过来的
    private String applystatus;//申请状态 0待审核，1通过，2拒绝
    private String applyType;//添加好友时的来源
    private int userid;//好友id
    private String phoneNumber;//手机号
    private String json;//请求json
    private String showRemark;//显示的用户备注


    @Override
    protected int getContentId() {
        return R.layout.activity_friend_profile;
    }

    @Override
    protected void init() {
//        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        showLoading();
        Intent intent = getIntent();
        userid = intent.getIntExtra("userid", -1);
        phoneNumber = intent.getStringExtra("phonenumber");
        applystatus = intent.getStringExtra("applystatus");
        applyfriends_type = intent.getStringExtra("applyfriends_type");
        applyfriends_id = intent.getIntExtra("applyfriends_id", -1);
        applyremark = intent.getStringExtra("applyremark");
        applyType = intent.getStringExtra("applyType");
        if (user != null) {
            if (user.isIsMe()) {//本人
                showMe();//显示本人资料
            } else if (user.isIsFriend()) {//是否是好友
                showFriendProfile();//显示好友资料
            } else {//非好友
                showNotFriend();//显示非好友资料
            }
            initValue();
            dismissLoading();
        } else {
            requestDatas();
        }
    }

    private void requestDatas() {
        if (userid != -1) {
            json = "\"user\":{" + "\"userid\":" + userid + "}";
        } else if (phoneNumber != null) {
            json = "\"user\":{" + "\"phonenumber\":\"" + phoneNumber + "\"}";
        }
        String requestJson = RequestUtil.getJson(this, json);
        String url = "/api/APIFriends/QueryFriend";
        Log.i(TAG, "loadDatas: " + requestJson);
        Log.i(TAG, "loadDatas: " + Constants.BASE_URL + url);
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        FriendsProfileEntity friendsProfileEntity = new Gson().fromJson(jo.toString(), FriendsProfileEntity.class);
                        user =  friendsProfileEntity.getUser();
                        if (user != null) {
                            if (user.isIsMe()) {//本人
                                showMe();//显示本人资料
                            } else if (user.isIsFriend()) {//是否是好友
                                showFriendProfile();//显示好友资料
                            } else {//非好友
                                showNotFriend();//显示非好友资料
                            }
                            initValue();
                        } else {
                            Toast.makeText(FriendProfileActivity.this, "搜索不到此用户", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(FriendProfileActivity.this, "搜索不到此用户", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(FriendProfileActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);

    }

    /**
     * 初始赋值
     */
    private void initValue() {
        userid = user.getUserid();
        phoneNumber = user.getPhonenumber();
        //设置头像
        GlideUtil.loadCircleImageView(FriendProfileActivity.this, user.getCustomerphoto(), iv_head_img, R.drawable.icon_chat_golffriend);
        //设置备注或者用户名显示
        String username = user.getUsername();
        String remarkName = user.getRemarkName();
        if (remarkName != null && !remarkName.isEmpty()) {
            showRemark = remarkName;
        } else {
            showRemark = username;
        }
        tv_username.setText(showRemark);
        //设置性别显示
        String sex = user.getGender();
        if (sex.equals("1")) {
            iv_sex_man.setVisibility(View.VISIBLE);
            iv_sex_lady.setVisibility(View.GONE);
        } else if (sex.equals("2")) {
            iv_sex_man.setVisibility(View.GONE);
            iv_sex_lady.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示非好友资料
     */
    private void showNotFriend() {
        if (applystatus != null && applystatus.equals("0")) {
            //待审核
            ll_add_foot.removeAllViews();
            View inflate = LayoutInflater.from(this).inflate(R.layout.item_friend_profile_foot2, null);
            TextView tv_refuse = (TextView) inflate.findViewById(R.id.tv_refuse);
            TextView tv_agreed = (TextView) inflate.findViewById(R.id.tv_add);
            tv_refuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkFriend("2");
                }
            });
            tv_agreed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkFriend("1");
                }
            });
            ll_add_foot.addView(inflate);

            ll_information.setVisibility(View.GONE);
            tv_no_add.setVisibility(View.GONE);
            iv_more.setVisibility(View.GONE);
            ll_apply_friend.setVisibility(View.VISIBLE);

            tv_greet.setText(applyremark);
            //申请方式 1电话簿查找，2扫码，3队内查找
            switch (applyfriends_type) {
                case "1":
                    tv_source.setText("电话簿查找");
                    break;
                case "2":
                    tv_source.setText("扫码");
                    break;
                case "3":
                    tv_source.setText("队内查找");
                    break;
                default:
                    tv_source.setText("");
                    break;
            }
        } else {
            //都属于添加好友范畴
            ll_add_foot.removeAllViews();
            final View inflate = LayoutInflater.from(this).inflate(R.layout.item_friend_profile_foot, null);
            TextView tv_add = (TextView) inflate.findViewById(R.id.tv_add);
            tv_add.setText("添加好友");
            tv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //添加好友
                    Intent intent = new Intent(FriendProfileActivity.this, VerifyInformationActivity.class);
                    intent.putExtra("userid", user.getUserid());
                    intent.putExtra("applyType", applyType);
                    startActivity(intent);
                }
            });
            ll_add_foot.addView(inflate);

            ll_information.setVisibility(View.GONE);
            iv_more.setVisibility(View.GONE);
            ll_apply_friend.setVisibility(View.GONE);
            tv_no_add.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示本人资料
     */
    private void showMe() {
        tv_no_add.setVisibility(View.GONE);
        ll_apply_friend.setVisibility(View.GONE);
        iv_more.setVisibility(View.GONE);
        ll_information.setVisibility(View.VISIBLE);

        //设置球龄
        String golfage = user.getGolfage();
        if (golfage != null) {
            tv_veteran.setText(golfage);
        } else {
            tv_veteran.setText("");
        }
        //设置地址
        String chinacity_name = user.getChinacity_name();
        tv_address.setText(chinacity_name);
        //设置为我的资料
        tv_activity_name.setText("我的资料");
    }

    /**
     * 显示好友资料
     */
    public void showFriendProfile() {
        ll_add_foot.removeAllViews();
        View inflate = LayoutInflater.from(this).inflate(R.layout.item_friend_profile_foot, null);
        TextView tv_send = (TextView) inflate.findViewById(R.id.tv_add);
        tv_send.setText("发消息");
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendProfileActivity.this, FriendChatActivity.class);
//                String username = null;//发送到聊天界面的好友名字
//                String remarkName = user.getRemarkName();
//                if (remarkName != null && !remarkName.isEmpty()) {
//                    username = remarkName;
//                } else {
//                    username = user.getUsername();
//                }
                intent.putExtra("username", showRemark);
                intent.putExtra("phonenumber", user.getPhonenumber());
                intent.putExtra("customerphoto", user.getCustomerphoto());
                EventBus.getDefault().post(EventBusMapUtil.getStringMap("FriendChatActivity", showRemark));
                startActivity(intent);
            }
        });
        ll_add_foot.addView(inflate);
        tv_no_add.setVisibility(View.GONE);
        ll_apply_friend.setVisibility(View.GONE);
        ll_information.setVisibility(View.VISIBLE);
        iv_more.setVisibility(View.VISIBLE);
        //赋值
        //设置球龄
        String golfage = user.getGolfage();
        if (golfage != null) {
            tv_veteran.setText(golfage);
        } else {
            tv_veteran.setText("");
        }
        //设置地址
        String chinacity_name = user.getChinacity_name();
        tv_address.setText(chinacity_name);
//        String usertype = listUsersBean.getUsertype();
//        if (usertype.equals("1")) {
//            iv_vip.setVisibility(View.VISIBLE);
//        } else {
//            iv_vip.setVisibility(View.GONE);
//        }
    }

    /**
     * 更改申请状态
     *
     * @param status
     */
    private void checkFriend(final String status) {
        showLoading();
        ApplyFriendsEntity applyFriendsEntity = new ApplyFriendsEntity();
        ApplyFriendsEntity.ApplyFriendsBean applyFriendsBean = new ApplyFriendsEntity.ApplyFriendsBean();
        applyFriendsBean.setApplyfriends_id(applyfriends_id);
//        applyFriendsBean.setApplyfriends_type(finalData.getApplyfriends_type());
//        applyFriendsBean.setApplyuserid(finalData.getUserid());
//        applyFriendsBean.setAnsweruserid(finalData.getAnsweruserid());
        applyFriendsBean.setApplystatus(status);
//        applyFriendsBean.setApplytype("1");
        applyFriendsEntity.setApplyfriends(applyFriendsBean);
        String request = new Gson().toJson(applyFriendsEntity);
        String requestJson = RequestUtil.getRequestJson(this, request);
        String url = "/api/APIApplyFriends/EditApplyFriends";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        if (status.equals("1")) {
                            Toast.makeText(FriendProfileActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent("com.glory.broadcast.RefreshFriend");
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("addfriend", finalData);
//                            intent.putExtras(bundle);
//                            localBroadcastManager.sendBroadcast(intent);
//                            EMMessage message = EMMessage.createTxtSendMessage(getResources().getString(R.string.chat_begin), finalData.getPhonenumber());
//                            EMClient.getInstance().chatManager().sendMessage(message);
                            finish();
                        } else {
                            Toast.makeText(FriendProfileActivity.this, "已拒绝该好友", Toast.LENGTH_SHORT).show();
                        }
                        EventBus.getDefault().post(EventBusMapUtil.getStringMap("NewFriendActivity", "refresh"));
                        finish();
                    } else {
                        Toast.makeText(FriendProfileActivity.this, statusmessage, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(FriendProfileActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
    }

    @OnClick({R.id.back, R.id.iv_more})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                EventBus.getDefault().post(EventBusMapUtil.getStringMap("FriendChatActivity", showRemark));
                finish();
                break;
            case R.id.iv_more:
                //菜单
                showAlertDialog();
                break;
        }
    }

    /**
     * 菜单对话框
     */
    private void showAlertDialog() {
        new ActionSheetDialog(this).builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("备注", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        startRemarkInfo();
                    }
                })
                .addSheetItem("删除此人", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        new AlertDialog(FriendProfileActivity.this).builder()
                                .setTitle("确定删除此人")
                                .setPositiveButton("删除", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        deleteFriend();
                                    }
                                })
                                .setNegativeButton("取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                    }
                                })
                                .show();
                    }
                }).show();
    }

    /**
     * 打开备注页面
     */
    private void startRemarkInfo() {
        Intent intent = new Intent(this, RemarkInfoActivity.class);
        intent.putExtra("friend_userid", user.getUserid());
        String remarkName = user.getRemarkName();
        if (remarkName != null && !remarkName.isEmpty()) {
            intent.putExtra("remarkName", remarkName);
        }
        startActivity(intent);
    }

    /**
     * 删除好友
     */
    private void deleteFriend() {
        showLoading();
        FriendsRequestEntity friendsRequestEntity = new FriendsRequestEntity();
        FriendsRequestEntity.FriendsBean friendsBean = new FriendsRequestEntity.FriendsBean();
        friendsBean.setFriend_userid(user.getUserid());
        friendsRequestEntity.setFriends(friendsBean);
        String request = new Gson().toJson(friendsRequestEntity);
        String requestJson = RequestUtil.getRequestJson(this, request);
        String url = "/api/APIFriends/DeleteFriends";
        Log.i(TAG, "deleteFriend: " + requestJson);
        Log.i(TAG, "deleteFriend: " + Constants.BASE_URL + url);
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        Toast.makeText(FriendProfileActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(EventBusMapUtil.getIntMap(2, 1));
                        //聊天界面若打开则关闭
                        EventBus.getDefault().post(EventBusMapUtil.getStringMap("FriendChatActivity", "close"));
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dismissLoading();
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(FriendProfileActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            EventBus.getDefault().post(EventBusMapUtil.getStringMap("FriendChatActivity", showRemark));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBus(Map<String, String> map) {
        if (map.containsKey("FriendProfileActivity")) {
            if (map.get("FriendProfileActivity").equals("refresh")) {
                requestDatas();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getEvent(Map<String, FriendsProfileEntity.UserBean> map) {
        if (map.containsKey("FriendProfileActivity")) {
            if (map.get("FriendProfileActivity") instanceof FriendsProfileEntity.UserBean) {
                FriendsProfileEntity.UserBean user = map.get("FriendProfileActivity");
                this.user = user;
                user = null;
            }
        }
    }
}
