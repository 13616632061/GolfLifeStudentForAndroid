package com.glorystudent.golflife;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.glorystudent.adapter.AddressFriendsListAdapter;
import com.glorystudent.entity.ContactsInfoEntity;
import com.glorystudent.entity.FriendEntity;
import com.glorystudent.golflibrary.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 通讯录好友
 * Created by hyj on 2017/2/13.
 */
public class AddressFriendsActivity extends BaseActivity implements AdapterView.OnItemClickListener, TextWatcher {
    @Bind(R.id.lv_address_friends)
    public ListView lv_address_friends;
    public AddressFriendsListAdapter friendsListAdapter;

    /**获取库Phon表字段**/
    private static final String[] PHONES_PROJECTION = new String[] {
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Photo.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID };

    /**联系人显示名称**/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;

    /**电话号码**/
    private static final int PHONES_NUMBER_INDEX = 1;

    /**头像ID**/
    private static final int PHONES_PHOTO_ID_INDEX = 2;

    /**联系人的ID**/
    private static final int PHONES_CONTACT_ID_INDEX = 3;


    /**联系人名称**/
    private ArrayList<String> mContactsName = new ArrayList<String>();

    /**联系人头像**/
    private ArrayList<String> mContactsNumber = new ArrayList<String>();

    /**联系人头像**/
    private ArrayList<Bitmap> mContactsPhonto = new ArrayList<Bitmap>();
    private List<FriendEntity> friendEntities2;

    @Override
    protected int getContentId() {
        return R.layout.activity_address_friends;
    }

    @Override
    protected void init() {
        friendsListAdapter = new AddressFriendsListAdapter(this);
        lv_address_friends.setAdapter(friendsListAdapter);
        lv_address_friends.setOnItemClickListener(this);
        View headView = LayoutInflater.from(this).inflate(R.layout.item_search_list, null);
        EditText et_search = (EditText) headView.findViewById(R.id.search_address);
        et_search.addTextChangedListener(this);
        lv_address_friends.addHeaderView(headView);

        List<ContactsInfoEntity> phoneContacts = getPhoneContacts();
        friendEntities2 = new ArrayList<>();
        if (phoneContacts != null) {
            for (ContactsInfoEntity phoneContact : phoneContacts) {
                FriendEntity friendEntity = new FriendEntity();
                friendEntity.setName(phoneContact.getName());
                friendEntity.setPhoneNumber(phoneContact.getPhone());
                friendEntities2.add(friendEntity);
            }
            friendsListAdapter.setDatas(friendEntities2);
        }
    }

    /**得到手机通讯录联系人信息**/
    private List<ContactsInfoEntity> getPhoneContacts() {
        List<ContactsInfoEntity> phonelist = new ArrayList<>();
        ContentResolver resolver = getContentResolver();
        try {
            // 获取手机联系人
            Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,PHONES_PROJECTION, null, null, null);
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
            Toast.makeText(this, "请去安全中心手动打开读取联系人权限", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FriendEntity datas = friendsListAdapter.getDatas(position - 1);
        Intent intent = new Intent();
        intent.putExtra("username", datas.getName());
        intent.putExtra("phonenumber", datas.getPhoneNumber());
        setResult(0x342, intent);
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String str = s.toString();
        List<FriendEntity> datas = new ArrayList<>();
        for (FriendEntity friendEntity : this.friendEntities2) {
            if (friendEntity.getName().contains(str)) {
                datas.add(friendEntity);
            }
        }
        friendsListAdapter.setDatas(datas);
    }



    @OnClick({R.id.back})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
        }
    }
}
