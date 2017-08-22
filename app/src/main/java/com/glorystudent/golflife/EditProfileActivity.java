package com.glorystudent.golflife;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.golflibrary.widget.pickerscrollview.PickerScrollView;
import com.glorystudent.golflibrary.widget.pickerscrollview.Pickers;
import com.glorystudent.util.Constants;
import com.glorystudent.util.RequestUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.thinkcool.circletextimageview.CircleTextImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 编辑个人资料
 * Created by hyj on 2016/12/22.
 */
public class EditProfileActivity extends BaseActivity implements PickerScrollView.onSelectListener, View.OnClickListener {
    private final static String TAG = "EditProfileActivity";
    @Bind(R.id.iv_head_portrait)
    public CircleTextImageView iv_head_portrait;

    @Bind(R.id.take_nickname)
    public TextView take_nickname;

    @Bind(R.id.take_account)
    public TextView take_account;

    @Bind(R.id.take_sex)
    public TextView take_sex;

    @Bind(R.id.take_golfage)
    public TextView take_golfage;

    @Bind(R.id.take_address)
    public TextView take_address;


    private Bitmap head_portrait_bitmap;
    private PopupWindow window;

    private Button bt_scrollchoose; // 滚动选择器按钮
    private PickerScrollView pickerscrlllview; // 滚动选择器
    private List<Pickers> list; // 滚动选择器数据
    private String[] id;
    private String[] name;

    private Button bt_yes; // 确定按钮
    private RelativeLayout picker_rel; // 选择器布局
    private int size = 30;//设置最高球龄15年
    private String saveGolfAge;//保存球龄
    @Override
    protected int getContentId() {
        return R.layout.activity_edit_profile;
    }

    @Override
    protected void init() {

        String head_pic_url = SharedUtil.getString(Constants.HEAD_PORTRAIT);
        if(head_pic_url != null){
            GlideUtil.loadCircleImageView(this, head_pic_url, iv_head_portrait);
        }else{
            iv_head_portrait.setImageResource(R.drawable.pic_default_avatar);
        }

        //设置昵称
        String nickname = SharedUtil.getString(Constants.NICKNAME);
        if(nickname != null){
            take_nickname.setText(nickname);
        }else{
            take_nickname.setText(Constants.DEFAULT_USERNAME + SharedUtil.getString(Constants.PHONE_NUMBER));
        }

        //设置账号
        String account = SharedUtil.getString(Constants.PHONE_NUMBER);
        take_account.setText(account);

        //设置性别
        String sex = SharedUtil.getString(Constants.SEX);

        if(sex.equals("2")){
            take_sex.setText("女");
        }else{
            take_sex.setText("男");
        }

        //设置球龄
        String age = SharedUtil.getString(Constants.VETERAN);
        take_golfage.setText(age + "年");

        //设置地区
        String address = SharedUtil.getString(Constants.ADDRESS);
        if(address != null){
            take_address.setText(address);
        }
    }

    @OnClick({R.id.back, R.id.set_head_portrait, R.id.set_nickname, R.id.set_sex, R.id.set_veteran, R.id.set_region})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.set_head_portrait:
                //修改头像
                Intent intent = new Intent(this, HeadPortraitActivity.class);
                startActivity(intent);
                if (head_portrait_bitmap != null) {
                    EventBus.getDefault().postSticky(head_portrait_bitmap);
                }
                break;
            case R.id.set_nickname:
                //修改昵称
                Intent intentNickname = new Intent(this, EditNicknameActivity.class);
                intentNickname.putExtra("nickname", take_nickname.getText().toString());
                startActivityForResult(intentNickname, 0x001);
                break;
            case R.id.set_sex:
                //修改性别
                Intent intentSex = new Intent(this, EditSexActivity.class);
                intentSex.putExtra("sex", take_sex.getText().toString());
                startActivityForResult(intentSex, 0x003);
                break;
            case R.id.set_veteran:
                //修改球龄
                showPopwindow();
                break;
            case R.id.set_region:
                //修改地区
                startActivity(new Intent(this, SelectProvincesActivity.class));
                break;
        }
    }

    //接收头像
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getHeadPic(Bitmap bitmap) {
        iv_head_portrait.setImageBitmap(bitmap);
        head_portrait_bitmap = bitmap;
    }


    //接收修改头像之后，更改头像
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getHeadPortrait(Bitmap bitmap){
        iv_head_portrait.setImageBitmap(bitmap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //修改昵称返回值
        if(requestCode == 0x001 && resultCode == 0x002 && data != null){
            String nickname = data.getStringExtra("nickname");
            take_nickname.setText(nickname);
        }
        //修改性别返回值
        if (requestCode == 0x003 && resultCode == 0x004 && data != null) {
            String sex = data.getStringExtra("sex");
            if(sex.equals("2")){
            take_sex.setText("女");
        }else{
            take_sex.setText("男");
        }
    }
    }

    /**
     * 显示popupWindow
     */
    private void showPopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popu_golfage_layout, null);
        TextView tv_cancel = (TextView) view.findViewById(R.id.cancel);
        TextView tv_sure = (TextView) view.findViewById(R.id.sure);
        tv_cancel.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        pickerscrlllview = (PickerScrollView) view.findViewById(R.id.pickerscrlllview);
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        window.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(EditProfileActivity.this.findViewById(R.id.set_veteran),
                Gravity.BOTTOM, 0, 0);

        list = new ArrayList<Pickers>();
        id = new String[size + 1];
        name = new String[size + 1];
        for (int i = 0; i <= size; i++) {
            id[i] = i + "";
            name[i] = i + "年";
        }
        for (int i = 0; i < name.length; i++) {
            list.add(new Pickers(name[i], id[i]));
        }
        // 设置数据，默认选择第一条
        pickerscrlllview.setData(list);
        pickerscrlllview.setSelected(0);
        saveGolfAge = "0";
        pickerscrlllview.setOnSelectListener(this);
    }

    @Override
    public void onSelect(Pickers pickers) {
        saveGolfAge = pickers.getShowId();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                //取消
                window.dismiss();
                break;
            case R.id.sure:
                //确定
                showWaiting();
                String golfAgeJson = "\"golfage\":" + "\"" + saveGolfAge +"\"";
                String editUserInfo = RequestUtil.getEditUserInfo(this, golfAgeJson);
                OkGo.post(Constants.BASE_URL + "/api/APIUser/EditUser")
                        .tag(this)//
                        .params("request", editUserInfo)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                try {
                                    JSONObject jo = new JSONObject(s);
                                    String statuscode = jo.getString("statuscode");
                                    String statusmessage = jo.getString("statusmessage");
                                    if(statuscode.equals("1")){
                                        SharedUtil.putString(Constants.VETERAN, saveGolfAge);
                                        take_golfage.setText(saveGolfAge + "年");
                                        Toast.makeText(EditProfileActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(EditProfileActivity.this, "保存失败，错误码:" + statusmessage, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                dismissWaiting();
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                dismissWaiting();
                                Toast.makeText(EditProfileActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                window.dismiss();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void refreshAddress(String address){
        if (!address.equals(Constants.CLOSE) && !address.equals("nickname")) {
            take_address.setText(address);
            EventBus.getDefault().cancelEventDelivery(address);
        }
    }
}
