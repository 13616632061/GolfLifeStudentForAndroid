package com.glorystudent.golflife;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.adapter.StudentProfileListAdapter;
import com.glorystudent.dialog.Dialog;
import com.glorystudent.entity.AddContractRequestEntity;
import com.glorystudent.entity.ApplyContractEntity;
import com.glorystudent.entity.ContractEditRequestEntity;
import com.glorystudent.entity.ContractInfoEntity;
import com.glorystudent.entity.ContractTraineeEntity;
import com.glorystudent.entity.PackageEntity;
import com.glorystudent.entity.SubscribeRequestEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflibrary.widget.circleview.CircleImageView;
import com.glorystudent.golflibrary.widget.pickerscrollview.PickerScrollView;
import com.glorystudent.golflibrary.widget.pickerscrollview.Pickers;
import com.glorystudent.util.Constants;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.util.ImageUtil;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.thinkcool.circletextimageview.CircleTextImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 学员资料
 * Created by hyj on 2017/2/14.
 */
public class StudentProfileActivity extends BaseActivity implements View.OnClickListener, PickerScrollView.onSelectListener {
    private final static String TAG = "StudentProfileActivity";
    private static final int RESULT_CAMERA_ONLY = 100;
    private static final int RESULT_CAMERA_CROP_PATH_RESULT = 301;
    private static final int CROP_PHOTO = 0x1112;
    private int userid;
    private String phNumber;
    private String username;

    @Bind(R.id.iv_head_img)
    public CircleTextImageView iv_head_img;

    @Bind(R.id.ll_add_foot)
    public LinearLayout ll_add_foot;

    @Bind(R.id.tv_address)
    public TextView tv_address;

    @Bind(R.id.et_username)
    public EditText et_username;

    @Bind(R.id.iv_sex)
    public ImageView iv_sex;

    @Bind(R.id.tv_phone_number)
    public TextView tv_phone_number;

    @Bind(R.id.lv_contract)
    public ListView lv_contract;

    @Bind(R.id.et_remark)
    public EditText et_remark;

    @Bind(R.id.sv)
    public ScrollView sv;

    private ContractTraineeEntity.ListContractUserExtBean userExt;
    private int friend_userid;
    private String customerphoto;
    private PopupWindow window;
    private PackageEntity packageEntity;
    private PopupWindow buywindow;
    private String showConetnt;
    private String showId;
    private StudentProfileListAdapter studentProfileListAdapter;
    private PickerScrollView pickerscrlllview;
    private PopupWindow contractWindow;
    private int packageID;
    private String saveUsername;
    private String saveRemark;
    private PopupWindow photoWindow;
    private Uri imageUri;
    private Uri imageCropUri;
    public Bitmap mbitmap;
    private int stuid;
    private PopupWindow deletewindow;
    private PopupWindow coursewindow;
    private PopupWindow timeQuantumWindow;

    @Override
    protected int getContentId() {
        return R.layout.activity_student_profile;
    }

    @Override
    protected void init() {
        et_username.setFocusable(false);
        et_remark.setFocusable(false);
        Intent intent = getIntent();
        userid = intent.getIntExtra("userid", -1);
        int type = intent.getIntExtra("type", -1);
        Bundle bundle = intent.getExtras();
        userExt = (ContractTraineeEntity.ListContractUserExtBean) bundle.getSerializable("ContractuserExt");
        if (userExt != null) {
            friend_userid = userExt.getContracttraineeid();
            stuid = userExt.getContracttraineeuserid();
            setFriendProfile(userExt);
        }
        if (type == 1) {
            //是好友关系的学员
            View inflate = LayoutInflater.from(this).inflate(R.layout.item_contract_details_foot1, null);
            TextView tv_send = (TextView) inflate.findViewById(R.id.tv_send);
            tv_send.setOnClickListener(this);
            ll_add_foot.addView(inflate);
        } else {
            //不是好友关系的学员
            View inflate = LayoutInflater.from(this).inflate(R.layout.item_contract_details_foot2, null);
            TextView tv_send = (TextView) inflate.findViewById(R.id.tv_add);
            tv_send.setOnClickListener(this);
            ll_add_foot.addView(inflate);
        }
        showLoading();
        studentProfileListAdapter = new StudentProfileListAdapter(this);
        lv_contract.setAdapter(studentProfileListAdapter);
        studentProfileListAdapter.setOnAppointmentListener(new StudentProfileListAdapter.OnAppointmentListener() {
            @Override
            public void onClick(ContractInfoEntity.ListContractCourseDetailBean data) {
                Log.d(TAG, "onClick: ---------------------" + data.getState());
                int state = data.getState();
                switch (state) {
                    case 0:
                        //待审核
                        showTimeQuantum(new String[]{"合约待审核"});
                        break;
                    case 1:
                        Intent intent1 = new Intent(StudentProfileActivity.this, AppointmentCourseActivity.class);
                        intent1.putExtra("studentid", stuid);
                        String text = et_username.getText().toString();
                        intent1.putExtra("coachid", data.getCoachid());
                        intent1.putExtra("coursedetailid", data.getCcoursedetailid());
                        intent1.putExtra("coursedetailname", data.getCcoursedetailname());
                        intent1.putExtra("username",text);
                        startActivity(intent1);
                        break;
                    case 2:
                        //已过期
                        showTimeQuantum(new String[]{"合约已过期"});
                        break;
                    case 3:
                        //已完成
                        showTimeQuantum(new String[]{"合约已完成"});
                        break;
                    case 4:
                        //无效
                        showTimeQuantum(new String[]{"合约无效"});
                        break;
                    case 5:
                        //已驳回
                        showTimeQuantum(new String[]{"合约已驳回"});
                        break;
                }
            }
        });

        if (userid != -1) {
            load();
        }

        String path = ImageUtil.getSDCardPath();
        File file = new File(path + "/temp_"+ System.currentTimeMillis()+ ".png");
        imageUri = Uri.fromFile(file);
        File cropFile = new File(path + "/temp_crop_" + System.currentTimeMillis() + ".png");
        imageCropUri = Uri.fromFile(cropFile);
    }


    //从相册里选择图片
    private void intoPhotoAlbum(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, CROP_PHOTO);
    }
    //拍照
    private void takeCameraOnly() {
        try {
            Intent intent = null;
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//action is capture
            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true);
            startActivityForResult(intent, RESULT_CAMERA_ONLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //共同调用裁剪
    public void cropImg(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 700);
        intent.putExtra("outputY", 700);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageCropUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, RESULT_CAMERA_CROP_PATH_RESULT);
    }

    protected void load() {
        /**
         * 获取合约
         */
        String request2 = "{\"usersid\":" + userid + ",\"finish\":" + "0}";
        final String requestJson2 = RequestUtil.getRequestJson(this, request2);
        Log.d(TAG, "load: --->" + requestJson2);
        String url2 = "/api/APIContract/QueryContractCourseListBy";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    dismissLoading();
                    Log.d(TAG, "parseDatas: --->查询课程明细" + json);
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        ContractInfoEntity contractCourseEntity = new Gson().fromJson(jo.toString(), ContractInfoEntity.class);
                        List<ContractInfoEntity.ListcontractsBean> listcontract = contractCourseEntity.getListcontracts();
                        if (listcontract != null && listcontract.size() > 0) {
                            for (int i = 0; i < listcontract.size(); i++) {
                                int contractid = listcontract.get(i).getContractid();
                                int state = listcontract.get(i).getStatus();
                                List<ContractInfoEntity.ListContractCourseDetailBean> listContractCourseDetail = contractCourseEntity.getListContractCourseDetail();
                                if (listContractCourseDetail != null && listContractCourseDetail.size() > 0) {
                                    List<ContractInfoEntity.ListContractCourseDetailBean> listContractCourseDetailBeen = sortContractcourseList(listContractCourseDetail, contractid, state);
                                    listcontract.get(i).setListContractDetail(listContractCourseDetailBeen);
                                }
                            }
                            studentProfileListAdapter.setDatas(listcontract);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailed() {
                dismissWaiting();
            }
        }).getEntityData(this, url2, requestJson2);
    }

    private List<ContractInfoEntity.ListContractCourseDetailBean> sortContractcourseList(List<ContractInfoEntity.ListContractCourseDetailBean> listcontractcourse, int contractid, int state) {
        List<ContractInfoEntity.ListContractCourseDetailBean> newlistcontractcourse = new ArrayList<>();
        for (int i = 0; i < listcontractcourse.size(); i++) {
            if (listcontractcourse.get(i).get_ContractID() == contractid) {
                listcontractcourse.get(i).setState(state);
                newlistcontractcourse.add(listcontractcourse.get(i));
            }
        }
        return newlistcontractcourse;
    }



    private void setFriendProfile(ContractTraineeEntity.ListContractUserExtBean contractuser) {
        saveUsername = userExt.getContracttraineename();
        et_username.setText(userExt.getContracttraineename());
        phNumber = contractuser.getPhonenumber();
        username = contractuser.getContracttraineename();
        userid = contractuser.getContracttraineeid();
        String sex = contractuser.getContracttraineegender();
        customerphoto = contractuser.getCustomerphoto();
        GlideUtil.loadCircleImageView(this, customerphoto, iv_head_img, R.drawable.icon_chat_golffriend);
        String chinacity_name = contractuser.getChinacityname();
        if (sex != null && sex.equals("1")) {
            iv_sex.setImageResource(R.drawable.tag_man);
        } else if (sex != null && sex.equals("2")) {
            iv_sex.setImageResource(R.drawable.tag_lady);
        } else {
            iv_sex.setImageResource(R.drawable.tag_man);
        }
        tv_address.setText(chinacity_name);
        tv_phone_number.setText(phNumber);
        String remark = contractuser.getRemark();
        if (remark != null) {
            et_remark.setText(remark);
            saveRemark = remark;
        } else {
            saveRemark = "";
        }

    }

    @OnClick({R.id.back, R.id.iv_more, R.id.iv_edit_name, R.id.tv_phone_number, R.id.iv_remark, R.id.iv_head_img})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.iv_more:
                //更多
                showPopwindow();
                break;
            case R.id.iv_edit_name:
                et_username.setFocusable(true);
                et_username.setFocusableInTouchMode(true);
                et_username.requestFocus();
                et_username.setSelection(et_username.getText().length());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                //如果开启
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
                imm.showSoftInput(et_username,InputMethodManager.SHOW_FORCED);
                break;
            case R.id.tv_phone_number:
                //拨打电话
                Dialog.getInstance().setOnShowDialogListener(new Dialog.OnShowDialogListener() {
                    @Override
                    public void onSure() {
                        //意图：想干什么事
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        //url:统一资源定位符
                        //uri:统一资源标示符（更广）
                        intent.setData(Uri.parse("tel:" + phNumber));
                        try {
                            //开启系统拨号器
                            startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(StudentProfileActivity.this, "请手动打开拨打电话权限", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                }).showDialog(this, "拨打电话", "你确定拨打该学员电话吗?", "拨打");
                break;
            case R.id.iv_remark:
                //对学员的备注
                et_remark.setFocusable(true);
                et_remark.setFocusableInTouchMode(true);
                et_remark.requestFocus();
                et_remark.setSelection(et_remark.getText().length());
                InputMethodManager imm2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                //如果开启
                imm2.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
                imm2.showSoftInput(et_username,InputMethodManager.SHOW_FORCED);
                break;
            case R.id.iv_head_img:
                showPhotoPopwindow();
                break;
        }
    }






    /**
     * 显示popupWindow
     */
    private void showPopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popu_student_profile_layout, null);
        Button remark = (Button) view.findViewById(R.id.remark);
        Button delete = (Button) view.findViewById(R.id.delete);
        Button add_contract = (Button) view.findViewById(R.id.add_contract);

        Button cancel = (Button) view.findViewById(R.id.cancel);
        add_contract.setOnClickListener(this);
        delete.setOnClickListener(this);
        remark.setOnClickListener(this);
        cancel.setOnClickListener(this);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x80000000);
        window.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(StudentProfileActivity.this.findViewById(R.id.iv_more), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_appointment:
                //约TA上课
                Intent intent1 = new Intent(this, AppointmentCourseActivity.class);
                intent1.putExtra("studentid", friend_userid);
                String text = et_username.getText().toString();
                intent1.putExtra("username",text);
                startActivity(intent1);
                break;
            case R.id.tv_add:
                //添加好友
                Intent intent = new Intent(this, VerifyInformationActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("phonenumber", phNumber);
                intent.putExtra("userid", userid);
                startActivity(intent);
                break;
            case R.id.tv_send:
                //发消息
                Intent intent3 = new Intent(this, FriendChatActivity.class);
                intent3.putExtra("phonenumber", phNumber);
                intent3.putExtra("username", et_username.getText().toString());
                intent3.putExtra("customerphoto",customerphoto);
                startActivity(intent3);
                break;
            case R.id.delete:
                //删除学员
                window.dismiss();
                AddContractRequestEntity addContractRequestEntity = new AddContractRequestEntity();
                AddContractRequestEntity.ContractTraineeBean contractTraineeBean = new AddContractRequestEntity.ContractTraineeBean();
                contractTraineeBean.setContracttraineeid(userid);
                addContractRequestEntity.setContractTrainee(contractTraineeBean);
                String request2 = new Gson().toJson(addContractRequestEntity);
                String requestJson2 = RequestUtil.getRequestJson(this, request2);
                String url = "/api/APIContractTrainees/DeleteContractTrainees";
                OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
                    @Override
                    public void parseDatas(String json) {
                        try {
                            JSONObject jo = new JSONObject(json);
                            String statuscode = jo.getString("statuscode");
                            String statusmessage = jo.getString("statusmessage");
                            if (statuscode.equals("1")) {
                                window.dismiss();
                                EventBus.getDefault().post(EventBusMapUtil.getIntMap(1,1));
                                finish();
                            } else if (statuscode.equals("-140")) {
                                Toast.makeText(StudentProfileActivity.this, statusmessage, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void requestFailed() {

                    }
                }).getEntityData(this, url, requestJson2);
                break;
            case R.id.add_contract:
                //添加合约
                window.dismiss();
                String request3 = RequestUtil.getEmptyParameter(this);
                OkGo.post(Constants.BASE_URL + "/api/APIPackage/QueryPackageList")
                        .tag(this)
                        .params("request", request3)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                try {
                                    JSONObject jo = new JSONObject(s);
                                    String statuscode = jo.getString("statuscode");
                                    String statusmessage = jo.getString("statusmessage");
                                    if(statuscode.equals("1")){
                                        packageEntity = new Gson().fromJson(s, PackageEntity.class);
                                        List<PackageEntity.ListPackageBean> listPackage = packageEntity.getListPackage();
                                        if (listPackage != null) {
                                            showContractPopwindow();
                                        }
                                    }else{
                                        Toast.makeText(StudentProfileActivity.this, "获取套餐失败，错误码:" + statusmessage, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                dismissWaiting();
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                dismissWaiting();
                                Toast.makeText(StudentProfileActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            case R.id.remark:
                //备注
                window.dismiss();
                sv.scrollTo(0,0);
                et_username.setFocusable(true);
                et_username.setFocusableInTouchMode(true);
                et_username.requestFocus();
                et_username.setSelection(et_username.getText().length());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                //如果开启
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
                imm.showSoftInput(et_username,InputMethodManager.SHOW_FORCED);
                break;
            case R.id.cancel:
                //取消
                window.dismiss();
                break;
        }
    }

    /**
     * 显示设为购课学员popupWindow
     */
    private void showBuyPopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popu_golfage_layout, null);
        TextView tv_cancel = (TextView) view.findViewById(R.id.cancel);
        TextView tv_sure = (TextView) view.findViewById(R.id.sure);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消
                buywindow.dismiss();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplyContractEntity applyContractEntity = new ApplyContractEntity();
                ApplyContractEntity.ApplyContractBean applyContractBean = new ApplyContractEntity.ApplyContractBean();
                applyContractBean.setPackageid(showId);
                applyContractBean.setUsertel(phNumber);
                applyContractEntity.setApplycontract(applyContractBean);
                applyContractEntity.setUsername(et_username.getText().toString());
                String json = new Gson().toJson(applyContractEntity);
                String reqestJson = RequestUtil.getRequestJson(StudentProfileActivity.this, json);
                Log.d(TAG, "onClick: -->" + reqestJson);
                String url = "/api/APIApplyContract/ApplyContract";
                OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
                    @Override
                    public void parseDatas(String json) {
                        try {
                            JSONObject jo = new JSONObject(json);
                            String statuscode = jo.getString("statuscode");
                            String statusmessage = jo.getString("statusmessage");
                            if (statuscode.equals("1")) {
                                EventBus.getDefault().post(EventBusMapUtil.getIntMap(1,1));
                                Toast.makeText(StudentProfileActivity.this, "购课成功", Toast.LENGTH_SHORT).show();
                                buywindow.dismiss();
                            }else{
                                Toast.makeText(StudentProfileActivity.this, "未知原因，购课失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void requestFailed() {

                    }
                }).getEntityData(StudentProfileActivity.this, url, reqestJson);
            }
        });
        PickerScrollView pickerscrlllview = (PickerScrollView) view.findViewById(R.id.pickerscrlllview);
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        buywindow = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        buywindow.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        buywindow.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        buywindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        buywindow.showAtLocation(StudentProfileActivity.this.findViewById(R.id.iv_more),
                Gravity.BOTTOM, 0, 0);
        List<PackageEntity.ListPackageBean> listPackage = packageEntity.getListPackage();

        List<Pickers> list = new ArrayList<Pickers>();
        String[] id = new String[listPackage.size()];
        String[] name = new String[listPackage.size()];

        for (int i = 0; i < listPackage.size(); i++) {
            id[i] = String.valueOf(listPackage.get(i).getPackageid());
            name[i] = listPackage.get(i).getPackagename();
        }

        for (int i = 0; i < name.length; i++) {
            list.add(new Pickers(name[i], id[i]));
        }
        // 设置数据，默认选择第一条
        if (name.length > 0) {
            showConetnt = name[0];
            showId = id[0];
        }
        pickerscrlllview.setData(list);
        pickerscrlllview.setOnSelectListener(this);
        pickerscrlllview.setSelected(0);
    }

    @Override
    public void onSelect(Pickers pickers) {
        showConetnt = pickers.getShowConetnt();
        showId = pickers.getShowId();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    et_username.setFocusable(false);
                    et_remark.setFocusable(false);
                    Log.d(TAG, "dispatchTouchEvent: --->1");
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    Log.d(TAG, "dispatchTouchEvent: --->2" + !saveUsername.equals(username));
                    String username = et_username.getText().toString();
                    if (saveUsername != null && !saveUsername.equals(username) && username != null && !username.isEmpty()) {
                        saveUsername(username);
                    } else {
                        et_username.setText(saveUsername);
                    }

                    Log.d(TAG, "dispatchTouchEvent: --->3");
                    String remark = et_remark.getText().toString();
                    Log.d(TAG, "dispatchTouchEvent: --->4");
                    if (saveRemark != null && !saveRemark.equals(remark) && remark != null && !remark.isEmpty()) {
                        saveRemark(remark);
                    } else {
                        et_remark.setText(saveRemark);
                    }

                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }


    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    /**
     * 显示合约套餐popupWindow
     */
    private void showContractPopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popu_golfage_layout, null);
        TextView tv_cancel = (TextView) view.findViewById(R.id.cancel);
        TextView tv_sure = (TextView) view.findViewById(R.id.sure);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contractWindow.dismiss();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contractWindow.dismiss();
                ContractEditRequestEntity contractEditRequestEntity = new ContractEditRequestEntity();
                contractEditRequestEntity.setContractTraineeID(userid);
                contractEditRequestEntity.setPackageID(packageID);
                contractEditRequestEntity.setContractUserID(userExt.getContracttraineeuserid());
                String request = new Gson().toJson(contractEditRequestEntity);
                String requestJson = RequestUtil.getRequestJson(StudentProfileActivity.this, request);
                String url = "/api/APIContract/AddApplyContract";
                OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
                    @Override
                    public void parseDatas(String json) {
                        try {
                            JSONObject jo = new JSONObject(json);
                            String statuscode = jo.getString("statuscode");
                            String statusmessage = jo.getString("statusmessage");
                            if (statuscode.equals("1")) {
                                Log.d(TAG, "parseDatas: ---->" + json);
                                ContractInfoEntity contractCourseEntity = new Gson().fromJson(jo.toString(), ContractInfoEntity.class);
                                List<ContractInfoEntity.ListcontractsBean> listcontract = contractCourseEntity.getListcontracts();
                                if (listcontract != null && listcontract.size() > 0) {
                                    for (int i = 0; i < listcontract.size(); i++) {
                                        int contractid = listcontract.get(i).getContractid();
                                        int state = listcontract.get(i).getStatus();
                                        List<ContractInfoEntity.ListContractCourseDetailBean> listContractCourseDetail = contractCourseEntity.getListContractCourseDetail();
                                        if (listContractCourseDetail != null && listContractCourseDetail.size() > 0) {
                                            List<ContractInfoEntity.ListContractCourseDetailBean> listContractCourseDetailBeen = sortContractcourseList(listContractCourseDetail, contractid, state);
                                            listcontract.get(i).setListContractDetail(listContractCourseDetailBeen);
                                        }
                                    }
                                    EventBus.getDefault().post(EventBusMapUtil.getIntMap(1,1));
                                    studentProfileListAdapter.setDatas(listcontract);
                                }
                                Toast.makeText(StudentProfileActivity.this, "添加套餐成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(StudentProfileActivity.this, "添加套餐失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void requestFailed() {

                    }
                }).getEntityData(StudentProfileActivity.this, url, requestJson);
            }
        });
        pickerscrlllview = (PickerScrollView) view.findViewById(R.id.pickerscrlllview);
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        contractWindow = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        contractWindow.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        contractWindow.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        contractWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        contractWindow.showAtLocation(StudentProfileActivity.this.findViewById(R.id.iv_more),
                Gravity.BOTTOM, 0, 0);
        List<PackageEntity.ListPackageBean> listPackage = packageEntity.getListPackage();

        List<Pickers>  list = new ArrayList<Pickers>();
        String[] id = new String[listPackage.size()];
        String[] name = new String[listPackage.size()];

        for (int i = 0; i < listPackage.size(); i++) {
            id[i] = String.valueOf(listPackage.get(i).getPackageid());
            name[i] = listPackage.get(i).getPackagename();
        }
        if (id != null && id.length > 0) {
            packageID = Integer.valueOf(id[0]);
        }
        for (int i = 0; i < name.length; i++) {
            list.add(new Pickers(name[i], id[i]));
        }
        // 设置数据，默认选择第一条
        pickerscrlllview.setData(list);
        pickerscrlllview.setOnSelectListener(new PickerScrollView.onSelectListener() {
            @Override
            public void onSelect(Pickers pickers) {
                packageID =  Integer.valueOf(pickers.getShowId());
            }
        });
        pickerscrlllview.setSelected(1);
    }


    /**
     * 显示popupWindow
     */
    private void showPhotoPopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popu_photo_layout, null);
        Button photograph = (Button) view.findViewById(R.id.photograph);
        Button photo_album = (Button) view.findViewById(R.id.photo_album);
        Button cancel = (Button) view.findViewById(R.id.cancel);
        photograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoWindow.dismiss();
                //拍照
                takeCameraOnly();
            }
        });
        photo_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoWindow.dismiss();
                //从相册里获取
                intoPhotoAlbum();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoWindow.dismiss();
            }
        });
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        photoWindow = new PopupWindow(view,
                 WindowManager.LayoutParams.MATCH_PARENT,
                 WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        photoWindow.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x80000000);
        photoWindow.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        photoWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        photoWindow.showAtLocation(StudentProfileActivity.this.findViewById(R.id.iv_more),
                Gravity.BOTTOM, 0, 0);
    }

    private void saveUsername(String username) {
        AddContractRequestEntity addContractRequestEntity = new AddContractRequestEntity();
        AddContractRequestEntity.ContractTraineeBean contractTraineeBean = new AddContractRequestEntity.ContractTraineeBean();
        contractTraineeBean.setContracttraineename(username);
        contractTraineeBean.setContracttraineeid(userid);
        addContractRequestEntity.setContractTrainee(contractTraineeBean);
        String request = new Gson().toJson(addContractRequestEntity);
        String requestJson = RequestUtil.getRequestJson(this, request);
        String url = "/api/APIContractTrainees/EditContractTrainees";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                Log.d(TAG, "parseDatas: --->" + json);
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        saveUsername = et_username.getText().toString();
                        EventBus.getDefault().post(EventBusMapUtil.getIntMap(1,1));
                        sv.scrollTo(0, 0);
                        Toast.makeText(StudentProfileActivity.this, "修改学员名称成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(StudentProfileActivity.this, "修改学员名称失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailed() {
                Toast.makeText(StudentProfileActivity.this, "修改学员名称失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
    }


    private void saveHeadImg(String imgUrl) {
        AddContractRequestEntity addContractRequestEntity = new AddContractRequestEntity();
        AddContractRequestEntity.ContractTraineeBean contractTraineeBean = new AddContractRequestEntity.ContractTraineeBean();
        contractTraineeBean.setCustomerphoto(imgUrl);
        contractTraineeBean.setContracttraineeid(userid);
        addContractRequestEntity.setContractTrainee(contractTraineeBean);
        String request = new Gson().toJson(addContractRequestEntity);
        String requestJson = RequestUtil.getRequestJson(this, request);
        String url = "/api/APIContractTrainees/EditContractTrainees";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                Log.d(TAG, "parseDatas: --->" + json);
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        saveUsername = et_username.getText().toString();
                        EventBus.getDefault().post(EventBusMapUtil.getIntMap(1,1));
                        sv.scrollTo(0, 0);
                        Toast.makeText(StudentProfileActivity.this, "修改头像成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(StudentProfileActivity.this, "修改头像失败,错误码：" + statusmessage, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dismissWaiting();
            }

            @Override
            public void requestFailed() {
                dismissWaiting();
                Toast.makeText(StudentProfileActivity.this, "修改学员名称失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
    }


    private void saveRemark(String remark){
        Log.d(TAG, "saveRemark: 执行了保 ");
        AddContractRequestEntity addContractRequestEntity = new AddContractRequestEntity();
        AddContractRequestEntity.ContractTraineeBean contractTraineeBean = new AddContractRequestEntity.ContractTraineeBean();
        contractTraineeBean.setRemark(remark);
        contractTraineeBean.setContracttraineeid(userid);
        addContractRequestEntity.setContractTrainee(contractTraineeBean);
        String request = new Gson().toJson(addContractRequestEntity);
        String requestJson = RequestUtil.getRequestJson(this, request);
        String url = "/api/APIContractTrainees/EditContractTrainees";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                Log.d(TAG, "parseDatas: --->" + json);
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        saveRemark = et_remark.getText().toString();
                        EventBus.getDefault().post(EventBusMapUtil.getIntMap(1,1));
                        Toast.makeText(StudentProfileActivity.this, "修改学员备注成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(StudentProfileActivity.this, "修改学员备注失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailed() {
                Toast.makeText(StudentProfileActivity.this, "修改学员备注失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        switch (requestCode) {
            case CROP_PHOTO:
                if(data!=null){
                    Uri uri = data.getData();
                    cropImg(uri);//得到Uri进行裁剪
                }
                break;

            case RESULT_CAMERA_ONLY: {
                cropImg(imageUri);//得到Uri进行裁剪
            }
            break;
            case RESULT_CAMERA_CROP_PATH_RESULT: {
                Bundle extras = data.getExtras();
                try {
                    mbitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageCropUri));
                    showWaiting();
                    OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
                        @Override
                        public void parseDatas(String json) {
                            Log.d(TAG, "parseDatas: --->" + json);
                            try {
                                JSONObject jo = new JSONObject(json);
                                String statuscode = jo.getString("statuscode");
                                String statusmessage = jo.getString("statusmessage");
                                if(statuscode.equals("1")){
                                    iv_head_img.setImageBitmap(mbitmap);
                                    String failePath = jo.getString("failePath");
                                    saveHeadImg(failePath);
                                }else{
                                    dismissWaiting();
                                    Toast.makeText(StudentProfileActivity.this, "修改头像失败，错误码:" + statusmessage, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void requestFailed() {
                            dismissWaiting();
                        }
                    }).upImgDatas(this, mbitmap);//开始上传
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(ContractInfoEntity.ListContractCourseDetailBean bean) {
        if (bean != null) {
            int state = bean.getState();
            switch (state) {
                case 0:
                    //待审核
                    showTimeQuantum(new String[]{"合约待审核"});
                    break;
                case 1:
                    showCoursePopwindow(bean);
                    break;
                case 2:
                    //已过期
                    showTimeQuantum(new String[]{"合约已过期"});
                    break;
                case 3:
                    //已完成
                    showTimeQuantum(new String[]{"合约已完成"});
                    break;
                case 4:
                    //无效
                    showTimeQuantum(new String[]{"合约无效"});
                    break;
                case 5:
                    //已驳回
                    showTimeQuantum(new String[]{"合约已驳回"});
                    break;
            }
        }
    }


    /**
     * 显示取消预约的popupWindow
     */
    private void showTimeQuantum(String[] times) {

        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popu_time_quantum, null);
        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        TextView sure = (TextView) view.findViewById(R.id.sure);
        LinearLayout ll_add_view = (LinearLayout) view.findViewById(R.id.ll_add_view);
        for (int i = 0; i < times.length; i++) {
            TextView textView = new TextView(this);
            textView.setText(times[i]);
            textView.setTextSize(14);
            textView.setTextColor(getResources().getColor(R.color.colorBlack));
            textView.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;
            textView.setLayoutParams(layoutParams);
            ll_add_view.addView(textView);
        }



        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeQuantumWindow.dismiss();
            }
        });


        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        timeQuantumWindow = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        timeQuantumWindow.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        timeQuantumWindow.setBackgroundDrawable(dw);
        // 在底部显示
        RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.rl);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        timeQuantumWindow.showAtLocation(findViewById(R.id.iv_more), Gravity.CENTER, 0, 0);
    }


    /**
     * 显示取消预约的popupWindow
     */
    private void showDeleteAppointment(ContractInfoEntity.ListContractCourseDetailBean data) {
        String times = data.getAppointments().getTimeDefinition();
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popu_delete_appointment, null);
        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        TextView sure = (TextView) view.findViewById(R.id.sure);
        String[] split;
        if (times.contains(",")) {
            split = times.split(",");
        } else {
            split = new String[]{times};
        }
        LinearLayout ll_add_view = (LinearLayout) view.findViewById(R.id.ll_add_view);
        for (int i = 0; i < split.length; i++) {
            TextView textView = new TextView(this);
            textView.setText(split[i]);
            textView.setTextSize(14);
            textView.setTextColor(getResources().getColor(R.color.colorBlack));
            textView.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;
            textView.setLayoutParams(layoutParams);
            ll_add_view.addView(textView);
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletewindow.dismiss();
            }
        });

        sure.setTag(data);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContractInfoEntity.ListContractCourseDetailBean tag = (ContractInfoEntity.ListContractCourseDetailBean) v.getTag();
                deleteAppointment(tag.getAppointments().getAppointmentid(), tag.getAppointments().getCcoursedetailid());
            }
        });


        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        deletewindow = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        deletewindow.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        deletewindow.setBackgroundDrawable(dw);
        // 在底部显示
        RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.rl);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        deletewindow.showAtLocation(findViewById(R.id.iv_more), Gravity.CENTER, 0, 0);
    }

    /**
     * 显示popupWindow
     */
    private void showCoursePopwindow(ContractInfoEntity.ListContractCourseDetailBean data) {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.popu_course_details, null);
        CircleImageView iv_img = (CircleImageView) view.findViewById(R.id.iv_img);
        iv_head_img.setDrawingCacheEnabled(true);
        Bitmap obmp = Bitmap.createBitmap(iv_head_img.getDrawingCache());
        iv_img.setImageBitmap(obmp);
        iv_head_img.setDrawingCacheEnabled(false);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_name.setText(username);
        TextView tv_age = (TextView) view.findViewById(R.id.tv_age);
        TextView tv_veteran = (TextView) view.findViewById(R.id.tv_veteran);
        tv_age.setText(data.getAppointments().getAppontimentage() + "岁");
        tv_veteran.setText("球龄" + data.getAppointments().getAppontimengolfage() + "年");

        TextView tv_course_content = (TextView) view.findViewById(R.id.tv_course_content);
        tv_course_content.setText(data.getAppointments().getCcoursedetailname());
        TextView tv_class_type = (TextView) view.findViewById(R.id.tv_class_type);
        tv_class_type.setText(data.getAppointments().getPackagename());
        TextView tv_play_information = (TextView) view.findViewById(R.id.tv_play_information);
        tv_play_information.setText(data.getAppointments().getPositionsname());
        TextView tv_class_time = (TextView) view.findViewById(R.id.tv_class_time);
        String times = data.getAppointments().getTimeDefinition();
        String[] split;
        if (times.contains(",")) {
            split = times.split(",");
            tv_class_time.setText(split[0] + "...");
        } else {
            split = new String[]{times};
            tv_class_time.setText(split[0]);
        }
        //显示时间段
        tv_class_time.setTag(split);
        tv_class_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] tag = (String[]) v.getTag();
                showTimeQuantum(tag);
            }
        });

        TextView tv_remind = (TextView) view.findViewById(R.id.tv_remind);
        tv_remind.setTag(data.getAppointments().getAppointmentid());
        //提醒签课
        tv_remind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int appointmentid = (int) v.getTag();
                SubscribeRequestEntity subscribeRequestEntity = new SubscribeRequestEntity();
                SubscribeRequestEntity.AppointmentBean appointmentBean = new SubscribeRequestEntity.AppointmentBean();
                appointmentBean.setAppointmentid(appointmentid);
                subscribeRequestEntity.setAppointment(appointmentBean);
                String request = new Gson().toJson(subscribeRequestEntity);
                String requestJson = RequestUtil.getRequestJson(StudentProfileActivity.this, request);
                String url = "/api/APISignClass/QuerySignClass";
                OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
                    @Override
                    public void parseDatas(String json) {
                        Log.d(TAG, "parseDatas: --->" + json);
                        try {
                            JSONObject jo = new JSONObject(json);
                            String statuscode = jo.getString("statuscode");
                            String statusmessage = jo.getString("statusmessage");
                            if (statuscode.equals("1")) {
                                Toast.makeText(StudentProfileActivity.this, "提醒成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(StudentProfileActivity.this, "提醒失败，错误码："  + statusmessage, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void requestFailed() {
                        Toast.makeText(StudentProfileActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                    }
                }).getEntityData(StudentProfileActivity.this, url, requestJson);
            }
        });


        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        TextView tv_contact_him = (TextView) view.findViewById(R.id.tv_contact_him);
        final String appointmenttel = data.getAppointments().getAppointmenttel();
        cancel.setTag(data);
        //取消
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContractInfoEntity.ListContractCourseDetailBean tag = (ContractInfoEntity.ListContractCourseDetailBean) v.getTag();
                showDeleteAppointment(tag);
            }
        });
        //联系他
        tv_contact_him.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coursewindow.dismiss();
                //联系他
                Dialog.getInstance().setOnShowDialogListener(new Dialog.OnShowDialogListener() {
                    @Override
                    public void onSure() {
                        //意图：想干什么事
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        //url:统一资源定位符
                        //uri:统一资源标示符（更广）
                        intent.setData(Uri.parse("tel:" + appointmenttel));
                        try {
                            //开启系统拨号器
                            startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(StudentProfileActivity.this, "请手动打开拨打电话权限", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                }).showDialog(StudentProfileActivity.this, "拨打电话", "你确定拨打该学员电话吗?", "拨打");
            }
        });

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        coursewindow = new PopupWindow(view,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        coursewindow.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        coursewindow.setBackgroundDrawable(dw);
        // 在底部显示
        RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.rl);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        coursewindow.showAtLocation(findViewById(R.id.iv_more), Gravity.CENTER, 0, 0);

        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        coursewindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    private void deleteAppointment(int appointmentid, int ccoursedetailid) {
        SubscribeRequestEntity subscribeRequestEntity = new SubscribeRequestEntity();
        SubscribeRequestEntity.AppointmentBean appointmentBean = new SubscribeRequestEntity.AppointmentBean();
        appointmentBean.setAppointmentid(appointmentid);
        appointmentBean.setCcoursedetailid(ccoursedetailid);
        subscribeRequestEntity.setAppointment(appointmentBean);
        String request = new Gson().toJson(subscribeRequestEntity);
        String requestJson = RequestUtil.getRequestJson(this, request);
        String url = "/api/APIAppointment/DeleteAppointment";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        deletewindow.dismiss();
                        coursewindow.dismiss();
                        load();
                        Toast.makeText(StudentProfileActivity.this, "取消预约成功", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailed() {

            }
        }).getEntityData(StudentProfileActivity.this, url, requestJson);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBus(Map<String, String> map) {
        if (map.containsKey("StudentProfileActivity")) {
            if (map.get("StudentProfileActivity").equals("1")) {
                load();
            }
        }
    }
}
