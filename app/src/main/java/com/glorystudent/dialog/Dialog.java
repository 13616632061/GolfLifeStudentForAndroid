package com.glorystudent.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.glorystudent.golflife.R;

import org.greenrobot.eventbus.EventBus;

/**
 * 弹窗
 * Created by hyj on 2017/2/16.
 */
public class Dialog {

    public OnShowDialogListener onShowDialogListener;

    /**
     * 获取Dialog实例对象
     * @return
     */
    public static Dialog getInstance() {
        return new Dialog();
    }

    /**
     * 设置Dialog的监听
     * @param onShowDialogListener
     * @return
     */
    public Dialog setOnShowDialogListener(OnShowDialogListener onShowDialogListener) {
        this.onShowDialogListener = onShowDialogListener;
        return this;
    }

    /**
     * 弹出dialog
     * @param context
     * @param title
     * @param meassage
     */
    public void showDialog(Context context, String title, String meassage, String sureStr) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(meassage);
        builder.setTitle(title);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setPositiveButton(sureStr,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        if (onShowDialogListener != null) {
                            onShowDialogListener.onSure();
                        }
                    }
                });
        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (onShowDialogListener != null) {
                            onShowDialogListener.onCancel();
                        }
                    }
                });
        builder.create().show();
    }

    /**
     * 弹出dialog
     * @param context
     * @param title
     * @param meassage
     */
    public void showDialogButton3(Context context, String title, String meassage, String cancel, String sureStr) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(meassage);
        builder.setTitle(title);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setPositiveButton(sureStr,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        if (onShowDialogListener != null) {
                            onShowDialogListener.onSure();
                        }
                    }
                });
        builder.setNegativeButton(cancel,
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (onShowDialogListener != null) {
                            onShowDialogListener.onCancel();
                        }
                    }
                });
        builder.create().show();
    }

    /**
     * 退出应用
     * @param context
     */
    public static void dialog_Exit(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("确定要退出吗?");
        builder.setTitle("提示");
        builder.setIcon(R.drawable.ic_launcher);
        builder.setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        EventBus.getDefault().post("exit");
                    }
                });
        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    public interface OnShowDialogListener{
        void onSure();
        void onCancel();
    }
}
