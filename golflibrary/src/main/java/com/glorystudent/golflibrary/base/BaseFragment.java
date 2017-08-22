package com.glorystudent.golflibrary.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.glorystudent.golflibrary.R;
import com.glorystudent.golflibrary.dialog.Loading;
import com.glorystudent.golflibrary.dialog.Waiting;
import com.glorystudent.golflibrary.util.ScreenUtil;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by hyj on 2016/9/26 0026.
 */
public abstract class BaseFragment extends Fragment {
    public static Context fragmentContext;
    private Loading mLoading;
    private Waiting waiting;
    private android.support.v4.app.FragmentManager fragmentManager;
    //当前正在展示的Fragment
    private BaseFragment showFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentId(), container, false);
        fragmentContext = getContext();
        return view;
    }

    public static Context getFragmentContext() {
        return fragmentContext;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        //获得fragmentManager对象
        fragmentManager = getChildFragmentManager();
        //查找actionbar控件设置paddingtop
        BaseActivity activity = (BaseActivity) getActivity();
        //判断是否开启沉浸状态栏
        if (activity.isOpenStatus()) {
            View actionbarview = view.findViewById(R.id.actionbar);
            if (actionbarview != null) {
                //获得状态栏的高度
                int height = ScreenUtil.getStatusHeight(getActivity());
                actionbarview.setPadding(0, height, 0, 0);
            }
        }
        mLoading = new Loading(getActivity());
        waiting = new Waiting(getActivity());
        init(view);
        bindListener();
        loadDatas();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            getBundle(bundle);
        }
    }

    protected void getBundle(Bundle bundle) {

    }

    /**
     * 绑定监听
     */
    protected void bindListener() {

    }

    /**
     * 加载数据
     */
    protected void loadDatas() {

    }

    /**
     * 初始化
     */
    protected void init(View view) {

    }

    protected abstract int getContentId();

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

    /**
     * 显示隐藏Fragment
     */
    protected void showFragment(int resid, BaseFragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //隐藏正在暂时的Fragment
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

    //权限授予回调
    private BaseActivity.PermissionsResultListener mListener;
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
    protected void requestPermission(final String[] permissions, final boolean isNeedFinish, final BaseActivity.PermissionsResultListener callback) {
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
            ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    /**
     * 弹出声明的 Dialog
     *
     * @param permissions
     */
    private void showRationaleDialog(final String[] permissions) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示")
                .setMessage("为了应用可以正常使用，请您点击确认赋予权限")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_CODE_ASK_PERMISSIONS);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (mIsNeedFinish) ;
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
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
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
            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
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
                if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示")
                .setMessage("必需的权限被拒绝")
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        AppUtils.getAppDetailsSettings(BaseActivity.this, SETTINGS_REQUEST_CODE);
                        String packageName = getActivity().getPackageName();
                        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                        intent.setData(Uri.parse("package:" + packageName));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivityForResult(intent, SETTINGS_REQUEST_CODE);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
}
