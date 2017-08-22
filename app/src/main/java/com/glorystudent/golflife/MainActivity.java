package com.glorystudent.golflife;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.database.MyDataBase;
import com.glorystudent.entity.ApplyFriendsEntity;
import com.glorystudent.entity.ApplyInfoEntity;
import com.glorystudent.entity.FriendEntity;
import com.glorystudent.entity.FriendsRequestEntity;
import com.glorystudent.entity.GolfFriendsEntity;
import com.glorystudent.entity.UserInformationEntity;
import com.glorystudent.entity.UserRequestEntity;
import com.glorystudent.fragment.ChatFragment;
import com.glorystudent.fragment.CoachHomeFragment;
import com.glorystudent.fragment.CoachMyFragment;
import com.glorystudent.fragment.CoachVideoFragment;
import com.glorystudent.fragment.StudentHomeFragment;
import com.glorystudent.fragment.StudentMyFragment;
import com.glorystudent.fragment.StudentVideoFragment;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.dialog.iosdialog.AlertDialog;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.util.Constants;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.google.gson.Gson;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 主入口
 * Created by hyj on 2016/12/14 08:10
 **/
public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private final static String TAG = "MainActivity";
    @Bind(R.id.rg)
    public RadioGroup rg;

    @Bind(R.id.tv_message_num)
    public TextView tv_message_num;

    private MyDataBase myDataBase;
    private SQLiteDatabase sqLiteDatabase;
    private int page = 1;//查询好友申请的页码

    private int count;
    private boolean switchPort;//true为教练端，false为客户端
    private Map<String, EMConversation> conversations;
    private final static int INVITED = 0x081;
    private final static int MESSAGE = 0x092;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INVITED:
                    String phonenumber = (String) msg.obj;
                    Cursor cursor4 = null;
                    if (sqLiteDatabase != null) {
                        cursor4 = sqLiteDatabase.rawQuery("select * from golffriends where phonenumber = ?", new String[]{phonenumber});
                    }
                    String nickname = null;
                    if (cursor4 != null) {
                        while (cursor4.moveToNext()) {
                            nickname = cursor4.getString(cursor4.getColumnIndex("username"));
                        }
                    }
                    if (nickname == null) {
                        getUserName(phonenumber);
                    } else {
                        if (SharedUtil.getBoolean(Constants.NOTIFICATION)) {
                            setNotificationBuilder("高尔夫人生", nickname + "申请添加您为好友");
                        }
                        setAddressBookState(phonenumber);
                    }
                    break;
                case MESSAGE:
                    //接收到消息
                    EMMessage message = (EMMessage) msg.obj;
                    String phone = message.getFrom();
                    Cursor cursor3 = sqLiteDatabase.rawQuery("select * from golffriends where phonenumber = ?", new String[]{phone});
                    String username = " ";
                    if (cursor3 != null) {
                        while (cursor3.moveToNext()) {
                            username = cursor3.getString(cursor3.getColumnIndex("username"));
                        }
                    }

                    if (phone.equals("00000")) {
                        username = "系统消息";
                    }

                    String type = message.getType().toString();
                    String subText = null;

                    switch (type) {
                        case "TXT":
                            String text = message.getBody().toString();
                            int position = text.indexOf("\"");
                            subText = text.substring(position + 1, text.length() - 1);
                            break;
                        case "IMAGE":
                            Map<String, Object> ext = message.ext();
                            if (ext != null && !ext.isEmpty()) {
                                subText = "[视频]";
                            } else {
                                subText = "[图片]";
                            }
                            break;
                        case "VOICE":
                            subText = "[语音]";
                            break;
                    }

                    if (SharedUtil.getBoolean(Constants.NOTIFICATION)) {
                        setNotificationBuilder(username, subText);
                    }

                    setUnRead();
                    EventBus.getDefault().post(message);
                    break;
            }
        }
    };

    /**
     * 获取用户信息
     */
    private void getUserName(String pNumber) {
        UserRequestEntity userRequestEntity = new UserRequestEntity();
        UserRequestEntity.UserBean userBean = new UserRequestEntity.UserBean();
        userBean.setPhonenumber(pNumber);
        userRequestEntity.setUser(userBean);
        String request = new Gson().toJson(userRequestEntity);
        String requestJson = RequestUtil.getRequestJson(this, request);
        String url = "/api/APIUser/QueryUsersBy";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        UserInformationEntity userInformationEntity = new Gson().fromJson(jo.toString(), UserInformationEntity.class);
                        String username = userInformationEntity.getListUsers().get(0).getUsername();
                        String phonenumber = userInformationEntity.getListUsers().get(0).getPhonenumber();
                        if (SharedUtil.getBoolean(Constants.NOTIFICATION)) {
                            setNotificationBuilder("高尔夫人生", username + "申请添加您为好友");
                            setAddressBookState(phonenumber);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailed() {

            }
        }).getEntityData(this, url, requestJson);
    }

    /**
     * 绑定activity的布局文件
     *
     * @return
     */
    @Override
    protected int getContentId() {
        return R.layout.activity_main;
    }

    /**
     * TODO 初始化
     * 初始化
     */
    @Override
    protected void init() {
        //创建需要用到的数据库
        myDataBase = new MyDataBase(this, null, 1);
        sqLiteDatabase = myDataBase.getReadableDatabase();
        SharedUtil.putBoolean(Constants.NOTIFICATION, true);
        getGolfFriends();
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {
            @Override
            public void onContactAdded(String s) {
            }

            @Override
            public void onContactDeleted(String s) {

            }

            @Override
            public void onContactInvited(String s, String s1) {
                Message message = new Message();
                message.what = INVITED;
                message.obj = s;
                handler.sendMessage(message);
            }

            @Override
            public void onFriendRequestAccepted(String s) {

            }

            @Override
            public void onFriendRequestDeclined(String s) {

            }
        });

        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                Log.d(TAG, "onMessageReceived: ---->收到消息");
                Message message = new Message();
                message.what = MESSAGE;
                message.obj = list.get(list.size() - 1);
                handler.sendMessage(message);
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> list) {
                Log.d(TAG, "onMessageChanged: ---->收到透传消息");
            }

            @Override
            public void onMessageRead(List<EMMessage> list) {
                Log.d(TAG, "onMessageChanged: ---->收到已读回执");
            }

            @Override
            public void onMessageDelivered(List<EMMessage> list) {
                Log.d(TAG, "onMessageChanged: ---->收到已送达回执");
            }

            @Override
            public void onMessageChanged(EMMessage emMessage, Object o) {
                Log.d(TAG, "onMessageChanged: ---->消息状态变动");
            }
        });

        getNewsFriends();

        count = 0;
        String phonenumber = SharedUtil.getString(Constants.PHONE_NUMBER);
        setUnRead();


        String v = SharedUtil.getString(Constants.SWITCH_VERSION);
        if (v.equals("客户端")) {
            switchPort = false;
        } else {
            switchPort = true;
        }

        rg.setOnCheckedChangeListener(this);
        rg.getChildAt(0).performClick();//模拟点击首页
    }

    /**
     * 查找新的朋友申请列表
     */
    private void getNewsFriends() {

        ApplyFriendsEntity applyFriendsEntity = new ApplyFriendsEntity();
        ApplyFriendsEntity.ApplyFriendsBean applyFriendsBean = new ApplyFriendsEntity.ApplyFriendsBean();
        applyFriendsEntity.setApplyfriends(applyFriendsBean);
        applyFriendsEntity.setPage(page);
        String json = new Gson().toJson(applyFriendsEntity);
        String requestJson = RequestUtil.getRequestJson(this, json);
        Log.i(TAG, "getNewsFriends: " + requestJson);
        String url = "/api/APIApplyFriends/QueryApplyFriends";
        Log.i(TAG, "getNewsFriends: " + Constants.BASE_URL + url);
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    int count = 0;
                    if (statuscode.equals("1")) {
                        ApplyInfoEntity applyInfoEntity = new Gson().fromJson(jo.toString(), ApplyInfoEntity.class);
                        List<ApplyInfoEntity.ListapplyfriendsBean> listapplyfriends = applyInfoEntity.getListapplyfriends();
//                        Map<Integer, Integer> map = new HashMap<>();
                        if (listapplyfriends != null) {
                            for (int i = 0; i < listapplyfriends.size(); i++) {
//                                map.put(listapplyfriends.get(i).getApplyuserid(), i);
                                if (listapplyfriends.get(i).getApplystatus().equals("0")) {
                                    count++;
                                }
                            }
                        }
//                        List<ApplyInfoEntity.ListusersBean> listusers = applyInfoEntity.getListusers();
//                        if (listusers != null) {
//                            for (ApplyInfoEntity.ListusersBean listuser : listusers) {
//                                ApplyInfoEntity.ListapplyfriendsBean listapplyfriendsBean = listapplyfriends.get(map.get(listuser.getUserid()));
//                                if (listapplyfriendsBean.getApplystatus().equals("0")) {
//                                    count++;
//                                }
//                            }
//                        }
                        if (count > 0) {
                            SharedUtil.putInt(Constants.NEW_FRIENDS_COUNT, count);
                            EventBus.getDefault().postSticky(EventBusMapUtil.getStringMap("newFriends", "1"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailed() {

            }
        }).getEntityData(this, url, requestJson);
    }

    private void setUnRead() {
        count = 0;
        conversations = EMClient.getInstance().chatManager().getAllConversations();
        Iterator<Map.Entry<String, EMConversation>> iterator = conversations.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, EMConversation> next = iterator.next();
            int unreadMsgCount = next.getValue().getUnreadMsgCount();
            count = count + unreadMsgCount;
        }
        if (count > 0) {
            tv_message_num.setText(count + "");
            tv_message_num.setVisibility(View.VISIBLE);
        } else {
            tv_message_num.setVisibility(View.GONE);
        }
    }

    /**
     * TODO 监听底部导航栏
     * 监听底部导航栏
     *
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //设置底部导航栏字体颜色
        RadioButton rb = (RadioButton) group.findViewWithTag("checked");
        if (rb != null) {
            rb.setTextColor(rb.getResources().getColor(R.color.colorWhite));
            rb.setTag(null);
        }
        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
        radioButton.setTag("checked");
        radioButton.setTextColor(radioButton.getResources().getColor(R.color.colorOrange));
        switch (checkedId) {
            case R.id.rb_home:
                //首页模块
                if (switchPort) {
                    showFragment(R.id.fragment, new CoachHomeFragment());
                } else {
                    showFragment(R.id.fragment, new StudentHomeFragment());
                }
                break;
            case R.id.rb_video:
                //视频模块
                if (SharedUtil.getBoolean(Constants.LOGIN_STATE)) {
                    if (switchPort) {
                        showFragment(R.id.fragment, new CoachVideoFragment());
                    } else {
                        showFragment(R.id.fragment, new StudentVideoFragment());
                    }
                } else {
                    showLogin();
                    rg.getChildAt(0).performClick();
                }
                break;
            case R.id.rb_chat:
                //消息模块
                if (SharedUtil.getBoolean(Constants.LOGIN_STATE)) {
                    showFragment(R.id.fragment, new ChatFragment());

                } else {
                    showLogin();
                    rg.getChildAt(0).performClick();

                }

                break;
            case R.id.rb_my:
                //我的模块
                if (SharedUtil.getBoolean(Constants.LOGIN_STATE)) {
                    if (switchPort) {
                        showFragment(R.id.fragment, new CoachMyFragment());
                    } else {
                        showFragment(R.id.fragment, new StudentMyFragment());
                    }
                } else {
                    showLogin();
                    rg.getChildAt(0).performClick();

                }
                break;
        }
    }

    /**
     * TODO 切换客户端/教练端
     */
    public void setCoachPort() {
        String v = SharedUtil.getString(Constants.SWITCH_VERSION);
        if (v.equals("客户端")) {
            switchPort = false;
        } else {
            switchPort = true;
        }
        rg.getChildAt(0).performClick();
    }


    @OnClick({R.id.iv_rec})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.iv_rec:
                //录制
                requestPermission(new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        true, new PermissionsResultListener() {
                    @Override
                    public void onPermissionGranted() {
                        Intent intent = new Intent(MainActivity.this, RecActivity.class);
                        startActivityForAnimation(intent, R.anim.activity_bottom_top, R.anim.activity_no_anim);
                        rg.findViewById(R.id.rb_video).performClick();
                    }

                    @Override
                    public void onPermissionDenied() {
                        Toast.makeText(MainActivity.this, "请赋予权限", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private long mExitTime = 0;

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 1000) {
//            Dialog.dialog_Exit(this);
            new AlertDialog(this).builder()
                    .setTitle("确认要退出么?")
                    .setCancelable(true)
                    .setPositiveButton("退出", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EventBus.getDefault().post("exit");
                        }
                    })
                    .setNegativeButton("取消", null).show();
            mExitTime = System.currentTimeMillis();
        }
    }

    private void getGolfFriends() {
        FriendsRequestEntity friendsRequestEntity = new FriendsRequestEntity();
        FriendsRequestEntity.FriendsBean friendsBean = new FriendsRequestEntity.FriendsBean();
        friendsRequestEntity.setFriends(friendsBean);
        String request = new Gson().toJson(friendsRequestEntity);
        String requestJson = RequestUtil.getRequestJson(this, request);
        String url = "/api/APIFriends/QueryFriends";
        Log.i(TAG, "getGolfFriends: " + requestJson);
        Log.i(TAG, "getGolfFriends: " + Constants.BASE_URL + url);
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
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
                                friendEntity.setPhoneNumber(listuser.getPhonenumber());

                                Cursor cursor = sqLiteDatabase.rawQuery("select * from golffriends where phonenumber = ?", new String[]{friendEntity.getPhoneNumber()});
                                if (cursor != null && cursor.getCount() > 0) {
                                    sqLiteDatabase.execSQL("update golffriends set username = ? where phonenumber = ?",
                                            new String[]{friendEntity.getName(), friendEntity.getPhoneNumber()});
                                } else {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("phonenumber", friendEntity.getPhoneNumber());//好友手机号
                                    contentValues.put("username", friendEntity.getName());//好友名称
                                    sqLiteDatabase.insert("golffriends", null, contentValues);
                                }
                                if (cursor != null) {
                                    cursor.close();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void requestFailed() {

            }
        }).getEntityData(this, url, requestJson);
    }

    private void setAddressBookState(String phonenumber) {
        boolean isExist = true;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from friends", null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String pNumber = cursor.getString(cursor.getColumnIndex("phonenumber"));
                if (phonenumber.equals(pNumber)) {
                    isExist = false;
                    break;
                }
            }
        }
        cursor.close();
        if (isExist) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("phonenumber", phonenumber);//申请的好友手机号
            sqLiteDatabase.insert("friends", null, contentValues);
        }

        Cursor cursor2 = sqLiteDatabase.rawQuery("select * from friends", null);
        int friendsCount = cursor2.getCount();
        cursor2.close();
        SharedUtil.putInt(Constants.NEW_FRIENDS_COUNT, friendsCount);
        EventBus.getDefault().postSticky(EventBusMapUtil.getStringMap("newFriends", "1"));
    }

    protected void setNotificationBuilder(String username, String body) {
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, MainActivity.class), 0);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(username)
                .setContentText(body)
                .setContentIntent(contentIntent)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true)
                .build();// getNotification()

        mNotifyMgr.notify(1, notification);
    }

    private void showLogin() {
        new AlertDialog(this).builder()
                .setTitle("此功能需要登录")
                .setMsg("是否去登录")
                .setCancelable(true)
                .setPositiveButton("去登录", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("取消", null).show();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBus(Map<Integer, Integer> map) {
        if (map.containsKey(11)) {
            if (map.get(11) == 1) {
                setUnRead();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
