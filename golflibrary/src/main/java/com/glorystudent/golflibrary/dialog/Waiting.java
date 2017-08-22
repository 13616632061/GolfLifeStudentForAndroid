package com.glorystudent.golflibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.glorystudent.golflibrary.R;


public  class Waiting extends Dialog {

    private final RotateAnimation refreshingAnimation;



    public Waiting(Context context) {
        super(context, R.style.Waiting);
        // 加载布局
        setContentView(R.layout.dialog_waiting_layout);
        // 设置Dialog参数
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        ImageView iv_center = (ImageView) findViewById(R.id.iv_center);
        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
                context, R.anim.rotating);
        refreshingAnimation.setInterpolator(new LinearInterpolator());
        iv_center.startAnimation(refreshingAnimation);
    }
}