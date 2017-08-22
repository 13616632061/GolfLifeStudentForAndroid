package com.glorystudent.golflife;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.glorystudent.entity.AddContractRequestEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.PhoneFormatCheckUtils;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 添加学员
 * Created by hyj on 2017/1/6.
 */
public class AddStudentActivity extends BaseActivity implements OkGoRequest.OnOkGoUtilListener {
    private final static String TAG = "AddStudentActivity";

    @Bind(R.id.et_student_name)
    public EditText et_student_name;
    @Bind(R.id.et_student_number)
    public EditText et_student_number;


    @Override
    protected int getContentId() {
        return R.layout.activity_add_student;
    }



    @OnClick({R.id.back, R.id.btn_add, R.id.ll_address_friend, R.id.ll_golf_friend})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.btn_add:
                //确定添加
                String name = et_student_name.getText().toString();
                String number = et_student_number.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(AddStudentActivity.this, "学员名称不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (number.isEmpty()) {
                    Toast.makeText(AddStudentActivity.this, "请填写手机号码", Toast.LENGTH_SHORT).show();
                    break;
                } else if(!PhoneFormatCheckUtils.isPhoneLegal(number)){
                    Toast.makeText(AddStudentActivity.this, "请填写正确的手机号码", Toast.LENGTH_SHORT).show();
                    break;
                }
                //合约学员
                AddContractRequestEntity addContractRequestEntity = new AddContractRequestEntity();
                AddContractRequestEntity.ContractTraineeBean contractTraineeBean = new AddContractRequestEntity.ContractTraineeBean();
                contractTraineeBean.setContracttraineename(name);
                contractTraineeBean.setPhonenumber(number);
                addContractRequestEntity.setContractTrainee(contractTraineeBean);
                String json = new Gson().toJson(addContractRequestEntity);
                String reqestJson = RequestUtil.getRequestJson(this, json);
                String url = "/api/APIContractTrainees/AddContractTrainees";
                OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(this).getEntityData(this, url, reqestJson);

                break;
            case R.id.ll_address_friend:
                //通讯录好友添加
                startActivityForResult(new Intent(this, AddressFriendsActivity.class), 0x341);
                break;
            case R.id.ll_golf_friend:
                //GolfLife好友添加
                startActivityForResult(new Intent(this, FriendsListActivity.class), 0x341);
                break;
        }
    }



//    /**
//     * 显示popupWindow
//     */
//    private void showPopwindow() {
//        // 利用layoutInflater获得View
//        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.popu_golfage_layout, null);
//        TextView tv_cancel = (TextView) view.findViewById(R.id.cancel);
//        TextView tv_sure = (TextView) view.findViewById(R.id.sure);
//        tv_cancel.setOnClickListener(this);
//        tv_sure.setOnClickListener(this);
//        pickerscrlllview = (PickerScrollView) view.findViewById(R.id.pickerscrlllview);
//        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
//        window = new PopupWindow(view,
//                WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.WRAP_CONTENT);
//        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
//        window.setFocusable(true);
//        // 实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0xffffffff);
//        window.setBackgroundDrawable(dw);
//        // 设置popWindow的显示和消失动画
//        window.setAnimationStyle(R.style.mypopwindow_anim_style);
//        // 在底部显示
//        window.showAtLocation(AddStudentActivity.this.findViewById(R.id.btn_add),
//                Gravity.BOTTOM, 0, 0);
//        List<PackageEntity.ListPackageBean> listPackage = packageEntity.getListPackage();
//
//        list = new ArrayList<Pickers>();
//        id = new String[listPackage.size()];
//        name = new String[listPackage.size()];
//
//        for (int i = 0; i < listPackage.size(); i++) {
//            id[i] = String.valueOf(listPackage.get(i).getPackageid());
//            name[i] = listPackage.get(i).getPackagename();
//        }
//
//        for (int i = 0; i < name.length; i++) {
//            list.add(new Pickers(name[i], id[i]));
//        }
//        // 设置数据，默认选择第一条
//        pickerscrlllview.setData(list);
//        pickerscrlllview.setOnSelectListener(this);
//        pickerscrlllview.setSelected(1);
//    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.cancel:
//                //关闭
//                window.dismiss();
//                break;
//            case R.id.sure:
//                //确定
//                tv_package.setText(showConetnt);
//                window.dismiss();
//                break;
//        }
//    }

    @Override
    public void parseDatas(String json) {
        try {
            JSONObject jo = new JSONObject(json);
            String statuscode = jo.getString("statuscode");
            String statusmessage = jo.getString("statusmessage");
            if(statuscode.equals("1")){
                et_student_name.setText("");
                et_student_number.setText("");
                Toast.makeText(AddStudentActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(AddStudentActivity.this, "添加失败，错误码:" + statusmessage, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestFailed() {

    }

    /**
     * 点击EditText其他地方，隐藏软键盘
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
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


    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x341 && resultCode == 0x342 && data != null) {
            String username = data.getStringExtra("username");
            String phonenumber = data.getStringExtra("phonenumber");
            et_student_name.setText(username);
            et_student_number.setText(phonenumber);
        }
    }
}
