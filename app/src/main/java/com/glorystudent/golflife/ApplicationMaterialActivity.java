package com.glorystudent.golflife;

import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.entity.AddTeamRequestEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.dialog.iosdialog.AlertDialog;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.util.Constants;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 入队申请资料页面
 * Created by Gavin.J on 2017/5/19.
 */

public class ApplicationMaterialActivity extends BaseActivity {
    private static final int REQUEST_SEX_CODE = 0x123;
    private static final String TAG = "AddTeamActivity";
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.tv_save)
    TextView save;
    @Bind(R.id.et_real_name)
    EditText realName;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.tv_phone_number)
    TextView phoneNumber;
    @Bind(R.id.et_cha_dian)
    EditText chaDian;
    private int teamId;
    private AddTeamRequestEntity addTeamRequestEntity;

    @Override
    protected int getContentId() {
        return R.layout.activity_application_material;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        teamId = intent.getIntExtra("id", -1);
        phoneNumber.setText(SharedUtil.getString(Constants.PHONE_NUMBER));
    }

    @OnClick({R.id.back, R.id.tv_save, R.id.tv_sex})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                close();
                break;
            case R.id.tv_save:
                //保存并提交
                checkInputData();
                break;
            case R.id.tv_sex:
                String str = tvSex.getText().toString();
                Intent intent = new Intent(this, SelectSexActivity.class);
                intent.putExtra("sex", str);
                startActivityForResult(intent, REQUEST_SEX_CODE);
                break;
        }
    }

    /**
     * 检查输入数据
     */
    private void checkInputData() {
        addTeamRequestEntity = new AddTeamRequestEntity();
        addTeamRequestEntity.setApplyTeamUser(new AddTeamRequestEntity.ApplyTeamUserBean());
        String nameStr = realName.getText().toString().trim();
        if (nameStr.isEmpty()) {
            Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String phoneStr = phoneNumber.getText().toString().trim();
//        if (phoneStr.isEmpty()) {
//            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
//            return;
//        } else if (!PhoneFormatCheckUtils.isPhoneLegal(phoneStr)) {
//            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
//            return;
//        }
        String chaDianStr = chaDian.getText().toString().trim();
        if (chaDianStr.isEmpty()) {
            Toast.makeText(this, "差点不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String sexStr = tvSex.getText().toString();
        if (sexStr.equals("男")) {
            addTeamRequestEntity.getApplyTeamUser().setSex('M');
        } else {
            addTeamRequestEntity.getApplyTeamUser().setSex('F');
        }
        addTeamRequestEntity.getApplyTeamUser().setTeamId(teamId);
        addTeamRequestEntity.getApplyTeamUser().setHandicap(chaDianStr);
        addTeamRequestEntity.getApplyTeamUser().setName(nameStr);
        addTeamRequestEntity.getApplyTeamUser().setTelphone(phoneStr);
        submit();
    }

    /**
     * 提交申请
     */
    private void submit() {
        showLoading();
        String json = new Gson().toJson(addTeamRequestEntity);
        String requestJson = RequestUtil.getRequestJson(this, json);
        Log.i(TAG, "submit: " + requestJson);
        String url = "/api/APITeam/AddTeam";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {//正常
                        Toast.makeText(ApplicationMaterialActivity.this, "提交申请成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (statuscode.equals("2")) {//暂无数据
                        Log.i(TAG, "parseDatas: " + statusmessage);
                    } else {
                        Log.i(TAG, "parseDatas: " + statusmessage);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(ApplicationMaterialActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
    }

    private void close() {
        new AlertDialog(this)
                .builder()
                .setTitle("退出本次申请")
                .setCancelable(true)
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setNegativeButton("继续申请", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            close();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SEX_CODE) {
                if (data != null) {
                    String sex = data.getStringExtra("sex");
                    tvSex.setText(sex);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
