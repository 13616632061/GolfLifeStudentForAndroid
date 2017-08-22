package com.glorystudent.fragment;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.adapter.GolfChatAdapter;
import com.glorystudent.entity.ContactsInfoEntity;
import com.glorystudent.entity.FriendEntity;
import com.glorystudent.entity.FriendsRequestEntity;
import com.glorystudent.entity.GolfFriendsEntity;
import com.glorystudent.entity.GroupEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.base.BaseFragment;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.golflife.FriendChatActivity;
import com.glorystudent.golflife.NewFriendActivity;
import com.glorystudent.golflife.R;
import com.glorystudent.util.Constants;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.widget.PullToRefreshLayout;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * 通讯录Fragment
 * Created by hyj on 2016/12/21.
 */
public class AddressBookFragment extends BaseFragment implements ExpandableListView.OnChildClickListener, TextWatcher {
    private final static String TAG = "AddressBookFragment";
    @Bind(R.id.elv)
    public ExpandableListView elv;

    private GolfChatAdapter golfChatAdapter;

    /**
     * 获取库Phone表字段
     **/
    private static final String[] PHONES_PROJECTION = new String[]{
            Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID};

    /**
     * 联系人显示名称
     **/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;

    /**
     * 电话号码
     **/
    private static final int PHONES_NUMBER_INDEX = 1;

    /**
     * 头像ID
     **/
    private static final int PHONES_PHOTO_ID_INDEX = 2;

    /**
     * 联系人的ID
     **/
    private static final int PHONES_CONTACT_ID_INDEX = 3;


    /**
     * 联系人名称
     **/
    private ArrayList<String> mContactsName = new ArrayList<String>();

    /**
     * 联系人手机号
     **/
    private ArrayList<String> mContactsNumber = new ArrayList<String>();

    /**
     * 联系人头像
     **/
    private ArrayList<Bitmap> mContactsPhonto = new ArrayList<Bitmap>();

    private List<GroupEntity> golf;
    private GroupEntity groupEntity;
    private List<FriendEntity> friendEntities1;
    private List<FriendEntity> friendEntities2;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case "com.glory.broadcast.RefreshFriend":
                    getGolfFriends();
                    break;
            }
        }
    };
    private IntentFilter intentFilter;

    private LocalBroadcastManager localBroadcastManager;
    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refresh_view;
    private boolean isRefresh;//true 是下拉刷新， false 是上拉加载
    private TextView tv_friends_number;
    private SQLiteDatabase sqLiteDatabase;
    private List<GolfFriendsEntity.ListusersBean> listusers;


    @Override
    protected int getContentId() {
        return R.layout.fragment_address_book;
    }

    @Override
    protected void init(View view) {
        EventBus.getDefault().register(this);
        refresh_view.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //下拉刷新回调
                isRefresh = true;
                refreshContactsFriends();
                getGolfFriends();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                //上拉加载回调
                isRefresh = false;

            }
        });
        showLoading();
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(getActivity().getDatabasePath("video.db"), null);
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.glory.broadcast.RefreshFriend");
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);
        golfChatAdapter = new GolfChatAdapter(getActivity());

        golf = new ArrayList<>();
        groupEntity = new GroupEntity();
        groupEntity.setGroupName("GolfLife好友");
        friendEntities1 = new ArrayList<>();
        getGolfFriends();
        groupEntity.setFriends(friendEntities1);
        golf.add(groupEntity);

        GroupEntity groupEntity2 = new GroupEntity();
        groupEntity2.setGroupName("通讯录好友");
        friendEntities2 = new ArrayList<>();
        //获取通讯录好友资料
        requestPermission(new String[]{android.Manifest.permission.READ_CONTACTS}, false, new BaseActivity.PermissionsResultListener() {
            @Override
            public void onPermissionGranted() {
                friendEntities2 = getContactsFriends();
            }

            @Override
            public void onPermissionDenied() {

            }
        });
        groupEntity2.setFriends(friendEntities2);
        golf.add(groupEntity2);

        golfChatAdapter.setDatas(golf);
        elv.setAdapter(golfChatAdapter);
        elv.setGroupIndicator(null);
        elv.expandGroup(0);//默认展开GolfLife好友
        elv.setOnChildClickListener(this);

        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.item_address_book_head, null);
        EditText search = (EditText) inflate.findViewById(R.id.search_address);
        tv_friends_number = (TextView) inflate.findViewById(R.id.tv_friends_number);
        search.addTextChangedListener(this);
        elv.addHeaderView(inflate);
        RelativeLayout rl_new_friend = (RelativeLayout) inflate.findViewById(R.id.rl_new_friend);
        rl_new_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedUtil.putInt(Constants.NEW_FRIENDS_COUNT, 0);
                tv_friends_number.setText("0");
                tv_friends_number.setVisibility(View.GONE);
                sqLiteDatabase.execSQL("delete from friends");
                EventBus.getDefault().post(EventBusMapUtil.getStringMap("newFriends", "4"));
                Intent intent = new Intent(getActivity(), NewFriendActivity.class);
                startActivity(intent);
            }
        });

        int friendCount = SharedUtil.getInt(Constants.NEW_FRIENDS_COUNT);
        if (friendCount > 0) {
            tv_friends_number.setText(friendCount + "");
            tv_friends_number.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        if (groupPosition == 0) {
            //GolfLife好友
            FriendEntity child = (FriendEntity) golfChatAdapter.getChild(groupPosition, childPosition);
            GolfFriendsEntity.ListusersBean user = listusers.get(childPosition);
            Intent intent = new Intent(getActivity(), FriendChatActivity.class);
            intent.putExtra("username", child.getName());
            intent.putExtra("phonenumber", child.getPhoneNumber());
            intent.putExtra("customerphoto", child.getCustomerphoto());
            startActivity(intent);
        }
        return true;
    }

    private void refreshContactsFriends() {
        friendEntities2.clear();
        List<FriendEntity> contactsFriends = getContactsFriends();
        friendEntities2.addAll(contactsFriends);
    }

    private List<FriendEntity> getContactsFriends() {
        List<ContactsInfoEntity> phoneContacts = getPhoneContacts();
        if (phoneContacts != null) {
            for (ContactsInfoEntity phoneContact : phoneContacts) {
                FriendEntity friendEntity = new FriendEntity();
                friendEntity.setName(phoneContact.getName());
                friendEntity.setPhoneNumber(phoneContact.getPhone());
                friendEntity.setAddState("1");
                friendEntities2.add(friendEntity);
            }
        }
        return friendEntities2;
    }

    /**
     * 得到手机通讯录联系人信息
     **/
    private List<ContactsInfoEntity> getPhoneContacts() {
        List<ContactsInfoEntity> phonelist = new ArrayList<>();
        ContentResolver resolver = getActivity().getContentResolver();
        try {
            // 获取手机联系人
            Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);

            if (phoneCursor != null) {
                while (phoneCursor.moveToNext()) {
                    //得到手机号码
                    String number = phoneCursor.getString(PHONES_NUMBER_INDEX);
                    String phoneNumber = number.replaceAll(" ", "");
                    //当手机号码为空的或者为空字段 跳过当前循环
                    if (TextUtils.isEmpty(phoneNumber))
                        continue;
                    //得到联系人名称
                    String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                    ContactsInfoEntity contactsInfoEntity = new ContactsInfoEntity();
                    contactsInfoEntity.setName(contactName);
                    contactsInfoEntity.setPhone(phoneNumber);
                    phonelist.add(contactsInfoEntity);
                }
                phoneCursor.close();
            }
            return phonelist;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "请去安全中心手动打开读取联系人权限", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private void contrastPhoneNumber() {
        if (friendEntities2 != null && friendEntities1 != null) {
            for (int i = 0; i < friendEntities2.size(); i++) {
                for (int j = 0; j < friendEntities1.size(); j++) {
                    if (friendEntities2.get(i).getPhoneNumber().equals(friendEntities1.get(j).getPhoneNumber())) {
                        friendEntities1.get(j).setAddState("2");
                        break;
                    }
                }
            }
        }
    }


    private void getGolfFriends() {
        FriendsRequestEntity friendsRequestEntity = new FriendsRequestEntity();
        FriendsRequestEntity.FriendsBean friendsBean = new FriendsRequestEntity.FriendsBean();
        friendsRequestEntity.setFriends(friendsBean);
        String request = new Gson().toJson(friendsRequestEntity);
        String requestJson = RequestUtil.getRequestJson(getActivity(), request);
        String url = "/api/APIFriends/QueryFriends";
        Log.i(TAG, "getGolfFriends: " + requestJson);
        Log.i(TAG, "getGolfFriends: " + Constants.BASE_URL + url);
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    Log.d(TAG, "parseDatas: --->" + json);
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                        friendEntities1.clear();
                        GolfFriendsEntity golfFriendsEntity = new Gson().fromJson(jo.toString(), GolfFriendsEntity.class);
                        listusers = golfFriendsEntity.getListusers();
                        if (listusers != null) {
                            for (GolfFriendsEntity.ListusersBean listuser : listusers) {
                                FriendEntity friendEntity = new FriendEntity();
                                friendEntity.setName(listuser.getUsername());
                                String remark = listuser.getRemarkName();
                                if (remark != null && !remark.isEmpty()) {
                                    friendEntity.setName(remark);
                                }
                                friendEntity.setRemarkname(remark);
                                friendEntity.setPhoneNumber(listuser.getPhonenumber());
                                friendEntity.setCustomerphoto(listuser.getCustomerphoto());
                                friendEntities1.add(friendEntity);

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
//                        contrastPhoneNumber();
                        golfChatAdapter.setDatas(golf);
                    } else if (statuscode.equals("2")) {
                        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                    } else {
                        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
            }
        }).getEntityData(getActivity(), url, requestJson);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString();
        if (text != null && !text.isEmpty()) {
            List<GroupEntity> golfFriends = new ArrayList<>();
            for (int i = 0; i < golf.size(); i++) {
                GroupEntity groupEntity2 = new GroupEntity();
                GroupEntity groupEntity = golf.get(i);
                groupEntity2.setGroupName(groupEntity.getGroupName());
                List<FriendEntity> friends = groupEntity.getFriends();
                List<FriendEntity> friends2 = new ArrayList<>();
                if (friends != null) {
                    for (FriendEntity friend : friends) {
                        if (friend.getName().contains(text)) {
                            friends2.add(friend);
                            Log.d(TAG, "afterTextChanged: 添加了");
                        }
                    }
                }
                groupEntity2.setFriends(friends2);
                golfFriends.add(groupEntity2);
            }
            golfChatAdapter.setDatas(golfFriends);
        } else {
            golfChatAdapter.setDatas(golf);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getEventBus(Map<Integer, Integer> map) {
        Log.d(TAG, "getEventBus: ---->刷新");
        if (map.containsKey(2)) {
            if (map.get(2) == 1) {
                getGolfFriends();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getStringEventBus(Map<String, String> map) {
        if (map.containsKey("newFriends")) {
            if (map.get("newFriends").equals("1")) {
                int friendCount = SharedUtil.getInt(Constants.NEW_FRIENDS_COUNT);
                if (friendCount > 0) {
                    if (tv_friends_number != null) {
                        tv_friends_number.setText(friendCount + "");
                        tv_friends_number.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
        sqLiteDatabase.close();
        EventBus.getDefault().unregister(this);
    }
}
