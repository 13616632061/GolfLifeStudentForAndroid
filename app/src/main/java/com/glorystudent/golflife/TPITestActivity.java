package com.glorystudent.golflife;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.glorystudent.golflibrary.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lucy on 2017/7/20.
 */
public class TPITestActivity extends BaseActivity {


    @Bind(R.id.tv_player)
    TextView tv_player;
    @Bind(R.id.tv_start)
    TextView tv_start;
    private PopupWindow window;

    private boolean isStart = false;//是否可以开始测评
    private int playersUserID;

    @Override
    protected int getContentId() {
        return R.layout.activity_tpitest;
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
                    Intent startIntent = new Intent(this, TPIEvaluationActivity.class);
                    startIntent.putExtra("testname", tv_player.getText().toString());
                    startIntent.putExtra("playersUserID",playersUserID);
                    startActivity(startIntent);
//                    startActivity(new Intent(this, TPIEvaluationActivity.class));
                    isStart = false;
                } else {
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
        window.showAtLocation(TPITestActivity.this.findViewById(R.id.tv_start), Gravity.NO_GRAVITY, (location[0]) - popupWidth / 2, location[1] - popupHeight - 20);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x033 && resultCode == 0x044 && data != null) {
            String playername = data.getStringExtra("playername");
            playersUserID = data.getIntExtra("playersUserID",0);
            tv_player.setText(playername);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
