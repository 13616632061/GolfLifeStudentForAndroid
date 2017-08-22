package com.glorystudent.golflife;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.glorystudent.golflibrary.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 测评
 * Created by hyj on 2017/1/4.
 */
public class AssessmentActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private PopupWindow window;
    @Bind(R.id.tv_start)
    public TextView tv_start;

    @Bind(R.id.rg_choose_hole)
    public RadioGroup rg_choose_hole;

    @Bind(R.id.tv_player)
    public TextView tv_player;

    private boolean isStart = false;//是否可以开始测评
    private int hole = 18;//默认18洞
    private int testType;
    private int playersUserID;

    @Override
    protected int getContentId() {
        return R.layout.activity_assessment;
    }

    @Override
    protected void init() {
        rg_choose_hole.getChildAt(1).performClick();
        rg_choose_hole.setOnCheckedChangeListener(this);
    }

    @OnClick({R.id.back, R.id.tv_start, R.id.tv_player})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.tv_player:
                //填写
                Intent intent = new Intent(this, FillPlayerActivity.class);
                startActivityForResult(intent, 0x033);
                break;
            case R.id.tv_start:
                //开始
                if (!tv_player.getText().toString().isEmpty() && !tv_player.getText().toString().equals("点击填写")) {
                    Intent startIntent = new Intent(this, MeasurementActivity.class);
                    startIntent.putExtra("testType",testType);
                    startIntent.putExtra("hole", hole);
                    startIntent.putExtra("testname", tv_player.getText().toString());
                    startIntent.putExtra("playersUserID", playersUserID);
                    startActivity(startIntent);
                    isStart = false;
                }else{
                    showPopwindow();
                }
                break;
        }
    }

    /**
     * 显示popupWindow
     */
    private void showPopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popu_layout_start, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00ffffff);
        window.setBackgroundDrawable(dw);
        //获取自身的长宽高
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupHeight = view.getMeasuredHeight();
        int popupWidth = view.getMeasuredWidth();
        int[] location = new int[2];
        tv_start.getLocationOnScreen(location);
        //在控件上方显示
        window.showAtLocation(AssessmentActivity.this.findViewById(R.id.tv_start), Gravity.NO_GRAVITY, (location[0]) - popupWidth / 2, location[1] - popupHeight - 20);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_nine_hole:
                //9洞传1 ，18洞传2，tpi测试传3
                testType = 1;
                //9洞
                hole = 9;
                break;
            case R.id.rb_eighteen_hole:
                //9洞传1 ，18洞传2，tpi测试传3
                testType = 2;
                //18洞
                hole = 18;
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0x033 && resultCode == 0x044 && data != null){
            String playername = data.getStringExtra("playername");
            playersUserID = data.getIntExtra("playersUserID",0);
            tv_player.setText(playername);
        }
    }
}
