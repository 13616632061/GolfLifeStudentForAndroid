package com.glorystudent.golflibrary.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.glorystudent.golflibrary.R;
import com.glorystudent.golflibrary.dialog.Loading;
import com.glorystudent.golflibrary.dialog.Waiting;
import com.glorystudent.golflibrary.util.ScreenUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by hyj on 2016/9/26 0026.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private android.support.v4.app.FragmentManager fragmentManager;
    //当前正在展示的Fragment
    private BaseFragment showFragment;
    private Loading mLoading;
    private Waiting waiting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //让布局向上移来显示软键盘
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //取消软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(getContentId());
//        Resources res = getResources();
//        Drawable drawable = res.getDrawable(R.drawable.bkcolor);
//        this.getWindow().setBackgroundDrawable(drawable);
        ButterKnife.bind(this);
        //获得fragmentManager对象
        fragmentManager = getSupportFragmentManager();
        if (isOpenStatus()) {
            //设置沉浸式状态栏
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            //获得状态栏的高度
            int height = ScreenUtil.getStatusHeight(this);
            if (height != -1) {
                //设置padding
                View view = findViewById(R.id.actionbar);
                if (view != null) {
                    view.setPadding(0, height, 0, 0);
                }
            }
        }
        EventBus.getDefault().register(this);
        mLoading = new Loading(this);
        waiting = new Waiting(this);
        init();
        loadDatas();
    }

    /**
     * 显示隐藏Fragment
     */
    protected void showFragment(int resid, BaseFragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //隐藏正在显示的Fragment
        if (showFragment != null) {
            fragmentTransaction.hide(showFragment);
        }
        //展示需要显示的Fragment对象
        Fragment mFragment = fragmentManager.findFragmentByTag(fragment.getClass().getName());
        if (mFragment != null) {
            fragmentTransaction.show(mFragment);
            showFragment = (BaseFragment) mFragment;
        } else {
            fragmentTransaction.add(resid, fragment, fragment.getClass().getName());
            showFragment = fragment;
        }
        fragmentTransaction.commit();
    }


    /**
     * 获得activity显示的布局
     *
     * @return
     */
    protected abstract int getContentId();


    /**
     * 加载数据
     */
    protected void loadDatas() {

    }

    /**
     * 初始化
     */
    protected void init() {

    }

    @Subscribe
    public void getMessage(String exit) {
        Log.d("BaseActivity", "getMessage: --->退出登录");
        if (exit.equals("exit")) {
            finish();
        }
    }

    /**
     * 设置Activity过场动画
     *
     * @param intent    意图
     * @param animInId  进来的动画
     * @param animOutId 出去的动画
     */
    public void startActivityForAnimation(Intent intent, int animInId, int animOutId) {
        startActivity(intent);
        overridePendingTransition(animInId, animOutId);
    }

    /**
     * 是否沉浸式状态栏
     */
    public boolean isOpenStatus() {
        return true;
    }

    public void showLoading() {
        // 添加Loading
        mLoading.show();
    }

    public void dismissLoading() {
        mLoading.dismiss();
    }

    public void showWaiting() {
        waiting.show();
    }

    public void dismissWaiting() {
        waiting.dismiss();
    }

    //权限授予回调
    private PermissionsResultListener mListener;
    //拒绝权限后是否关闭界面或APP
    private boolean mIsNeedFinish;
    //界面传递过来的权限列表,用于二次申请
    private String[] mPermissionsList;
    //申请标记值
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    //手动开启权限requestCode
    public static final int SETTINGS_REQUEST_CODE = 200;

    /**
     * 权限允许或拒绝对话框
     *
     * @param permissions  需要申请的权限
     * @param isNeedFinish 如果必须的权限没有允许的话，是否需要finish当前 Activity
     * @param callback     回调对象
     */
    protected void requestPermission(final String[] permissions, final boolean isNeedFinish, final PermissionsResultListener callback) {
        if (permissions == null || permissions.length == 0) {
            return;
        }
        mIsNeedFinish = isNeedFinish;
        mListener = callback;
        mPermissionsList = permissions;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //获取未通过的权限列表
            ArrayList<String> newPermissions = checkEachSelfPermission(permissions);
            if (newPermissions.size() > 0) {// 是否有未通过的权限
                requestEachPermissions(newPermissions.toArray(new String[newPermissions.size()]));
            } else {// 权限已经都申请通过了
                if (mListener != null) {
                    mListener.onPermissionGranted();
                }
            }
        } else {
            if (mListener != null) {
                mListener.onPermissionGranted();
            }
        }
    }

    /**
     * 申请权限前判断是否需要声明
     *
     * @param permissions
     */
    private void requestEachPermissions(String[] permissions) {
        if (shouldShowRequestPermissionRationale(permissions)) {// 需要再次声明
            showRationaleDialog(permissions);
        } else {
            ActivityCompat.requestPermissions(BaseActivity.this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    /**
     * 弹出声明的 Dialog
     *
     * @param permissions
     */
    private void showRationaleDialog(final String[] permissions) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage("为了应用可以正常使用，请您点击确认赋予权限")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(BaseActivity.this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (mIsNeedFinish) finish();
                    }
                })
                .setCancelable(false)
                .show();
    }


    /**
     * 再次申请权限时，是否需要声明
     *
     * @param permissions
     * @return
     */
    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 检察每个权限是否申请
     *
     * @param permissions
     * @return newPermissions.size > 0 表示有权限需要申请
     */
    private ArrayList<String> checkEachSelfPermission(String[] permissions) {
        ArrayList<String> newPermissions = new ArrayList<String>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                newPermissions.add(permission);
            }
        }
        return newPermissions;
    }

    /**
     * 申请权限结果的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS && permissions != null) {
            // 获取被拒绝的权限列表
            ArrayList<String> deniedPermissions = new ArrayList<>();
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissions.add(permission);
                }
            }
            if (deniedPermissions != null && deniedPermissions.size() > 0) {
                // 存在权限被拒绝，
                if (mIsNeedFinish) {
                    showPermissionSettingDialog();
                } else {
                    if (mListener != null) {
                        mListener.onPermissionDenied();
                    }
                }
            } else {
                // 不存在权限被拒绝
                if (mListener != null) {
                    mListener.onPermissionGranted();
                }
            }
        }
    }

    /**
     * 手动开启权限弹窗
     */
    private void showPermissionSettingDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage("必需的权限被拒绝")
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                        String packageName = getPackageName();
                        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                        intent.setData(Uri.parse("package:" + packageName));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivityForResult(intent, SETTINGS_REQUEST_CODE);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        })
                .setCancelable(false)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果需要跳转系统设置页后返回自动再次检查和执行业务 如果不需要则不需要重写onActivityResult
        if (requestCode == SETTINGS_REQUEST_CODE) {
            requestPermission(mPermissionsList, mIsNeedFinish, mListener);
        }
    }

    /**
     * 权限的接口回调
     */
    public interface PermissionsResultListener {
        void onPermissionGranted();

        void onPermissionDenied();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        dismissLoading();
    }

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
}
