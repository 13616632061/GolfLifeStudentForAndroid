package com.glorystudent.golflife;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.glorystudent.golflibrary.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 球队信息编辑页面
 * Created by Gavin.J on 2017/5/18.
 */

public class TeamInfoActivity extends BaseActivity {
    private static final int REQUEST_CITY_CODE = 0x123;
    private static final String TAG = "TeamInfoActivity";
    @Bind(R.id.back)
    ImageView back;
    //    @Bind(R.id.tv_cancel)
//    TextView cancel;
    @Bind(R.id.tv_activity_name)
    TextView activityName;
    //    @Bind(R.id.tv_edit_or_finish)
//    TextView editOrFinish;
//    @Bind(R.id.tv_city_name)
//    TextView cityName;
//    @Bind(R.id.tv_revise)
//    TextView tvRevise;
    @Bind(R.id.tv_introduction)
    TextView tvIntroduction;
    //    @Bind(R.id.et_introduction_edit)
//    EditText etIntroduction;
    private boolean editFlag = false;//false 完成，true 编辑
    private boolean isCaptain;//上个页面传过来的是否是队长
    //    private String regionText;//地区城市名
    private String summary;//球队简介
    //    private int cid;//地区id
    private int id;//球队id

    @Override
    protected int getContentId() {
        return R.layout.activity_team_info;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
//        id = intent.getIntExtra("id", -1);
//        isCaptain = intent.getBooleanExtra("isCaptain", false);
        summary = intent.getStringExtra("summary");
//        regionText = intent.getStringExtra("regionText");
        tvIntroduction.setText(summary);
//        cityName.setText(regionText);
//        if (isCaptain) {
//            editOrFinish.setVisibility(View.VISIBLE);
//        } else {
//            editOrFinish.setVisibility(View.GONE);
//        }
    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
//            case R.id.tv_cancel:
//                close();
//                break;
//            case R.id.tv_edit_or_finish:
//                //编辑或者完成
//                editOrFinish();
//                break;
//            case R.id.tv_revise:
//                //修改
//                //关闭输入法
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (imm != null) {
//                    imm.hideSoftInputFromWindow(tvIntroduction.getWindowToken(), 0);
//                }
//                startActivityForResult(new Intent(this, SelectTeamCityActivity.class), REQUEST_CITY_CODE);
//                break;
        }
    }

    /**
     * 关闭页面的提示弹窗
     */
//    private void close() {
//        new AlertDialog(this)
//                .builder()
//                .setTitle("退出本次编辑")
//                .setPositiveButton("退出", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        editOrFinish.performClick();
//                    }
//                })
//                .setNegativeButton("继续编辑", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                    }
//                })
//                .show();
//    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (back.getVisibility() == View.VISIBLE) {
//                finish();
//            } else {
//                close();
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    /**
     * 编辑或者完成的切换
     */
//    private void editOrFinish() {
//        if (editFlag) {
//            //点击后变为完成状态
//            editFlag = false;
//            editOrFinish.setText("编辑");
//            editOrFinish.setTextColor(getResources().getColor(R.color.colorWhite));
//            cancel.setVisibility(View.GONE);
//            back.setVisibility(View.VISIBLE);
////            tvRevise.setVisibility(View.GONE);
//            tvIntroduction.setVisibility(View.VISIBLE);
//            tvIntroduction.setText(etIntroduction.getText().toString());
//            etIntroduction.setVisibility(View.GONE);
//            etIntroduction.setSelection(etIntroduction.getText().length());
//            //关闭输入法
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            if (imm != null) {
//                imm.hideSoftInputFromWindow(tvIntroduction.getWindowToken(), 0);
//            }
//            if (!tvIntroduction.getText().toString().equals(summary) ) {
//                //数据发生变动,上传服务器更新
//                updateData();
//            }
//        } else {
//            //点击后变为编辑状态
//            editFlag = true;
//            editOrFinish.setText("完成");
//            editOrFinish.setTextColor(getResources().getColor(R.color.colorGreen2));
//            cancel.setVisibility(View.VISIBLE);
//            back.setVisibility(View.GONE);
////            tvRevise.setVisibility(View.VISIBLE);
//            etIntroduction.setVisibility(View.VISIBLE);
//            etIntroduction.setText(tvIntroduction.getText().toString());
//            tvIntroduction.setVisibility(View.GONE);
//            //打开输入法，光标定位在尾部
//            etIntroduction.requestFocus();
//            etIntroduction.setSelection(etIntroduction.getText().length());
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            if (imm != null) {
//                imm.showSoftInput(etIntroduction, 0);
//            }
//        }
//    }

    /**
     * 上传网络更新数据
     */
//    private void updateData() {
//        showLoading();
//        TeamRequestEntity teamRequestEntity = new TeamRequestEntity();
//        TeamRequestEntity.TeamBean teamBean = new TeamRequestEntity.TeamBean();
//        teamRequestEntity.setTeam(teamBean);
//        teamBean.setId(id);
//        teamBean.setSummary(tvIntroduction.getText().toString().trim());
////        teamBean.setRegionid(cid);
////        teamBean.setRegionText(cityName.getText().toString());
//        String json = new Gson().toJson(teamRequestEntity);
//        String requestJson = RequestUtil.getRequestJson(this, json);
//        Log.i(TAG, "updateData: " + requestJson);
//        String url = "/api/APITeam/EditTeamInfo";
//        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
//            @Override
//            public void parseDatas(String json) {
//                try {
//                    JSONObject jo = new JSONObject(json);
//                    String statuscode = jo.getString("statuscode");
//                    String statusmessage = jo.getString("statusmessage");
//                    if (statuscode.equals("1")) {//正常
//                        Toast.makeText(TeamInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
//                        EventBus.getDefault().post(EventBusMapUtil.getStringMap("TeamDetailActivity", "refresh"));
//                        EventBus.getDefault().post(EventBusMapUtil.getStringMap("TeamManagementActivity", "refresh"));
//                        finish();
//                    } else if (statuscode.equals("2")) {//暂无数据
//                        Log.i(TAG, "parseDatas: " + statusmessage);
//                    } else {
//                        Log.i(TAG, "parseDatas: " + statusmessage);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                dismissLoading();
//            }
//
//            @Override
//            public void requestFailed() {
//                dismissLoading();
//                Toast.makeText(TeamInfoActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
//            }
//        }).getEntityData(this, url, requestJson);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == REQUEST_CITY_CODE) {
//                if (data != null) {
//                    String name = data.getStringExtra("name");
////                    cid = data.getIntExtra("cid", -1);
////                    cityName.setText(name);
//
//                }
//            }
//        }
//    }
}
