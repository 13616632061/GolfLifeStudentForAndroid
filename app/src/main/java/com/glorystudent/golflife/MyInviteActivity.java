package com.glorystudent.golflife;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.glorystudent.dialog.SharePopupWindow;
import com.glorystudent.entity.ShareModel;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.util.Constants;
import com.glorystudent.util.ImageUtil;

import java.util.HashMap;

import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * 邀请球友
 * Created by hyj on 2016/12/21.
 */
public class MyInviteActivity extends BaseActivity implements PlatformActionListener {
    private OnekeyShare oks;
    private SharePopupWindow share;
    private String picPath;

    @Override
    protected int getContentId() {
        return R.layout.activity_my_invite;
    }

    @Override
    protected void init() {
        ShareSDK.initSDK(this);
    }

    @OnClick({R.id.back, R.id.tv_invite})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.tv_invite:
                //邀请
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                picPath = ImageUtil.saveBitmap(bitmap, System.currentTimeMillis() + "");
                share = new SharePopupWindow(this);
                share.setPlatformActionListener(this);
                ShareModel model = new ShareModel();
                model.setImageUrl(picPath);
                model.setText("点击下载高尔夫人生，开启高尔夫人生精彩生活");
                model.setTitle(SharedUtil.getString(Constants.NICKNAME) + "邀请您加入高尔夫人生");
                model.setUrl("http://www.pgagolf.cn/home/share?userid=" + SharedUtil.getString(Constants.USER_ID) + "&fromrode=0");
                share.initShareParams(model);
                share.showShareWindow();
                // 显示窗口 (设置layout在PopupWindow中显示的位置)
                share.showAtLocation(this.findViewById(R.id.back),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.7f;
                getWindow().setAttributes(lp);
                share.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });
                break;
        }
    }
    private void showShare() {
        oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(SharedUtil.getString(Constants.NICKNAME) + "邀请您加入高尔夫人生");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://www.pgagolf.cn/home/share?userid=" + SharedUtil.getString(Constants.USER_ID) + "&fromrode=0");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("点击下载高尔夫人生，开启高尔夫人生精彩生活");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
//        oks.setVideoUrl("http://www.pgagolf.cn/home/video?variable=" + video.getTeachingvideo_path());
        oks.setImageUrl("http://www.pgagolf.cn/home/share?userid=" + SharedUtil.getString(Constants.USER_ID) + "&fromrode=0");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://www.pgagolf.cn/home/share?userid=" + SharedUtil.getString(Constants.USER_ID) + "&fromrode=0");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
//        String picPath = ImageUtil.saveBitmap(bitmap, System.currentTimeMillis() + "");
//        oks.setImagePath(picPath);
        oks.show(this);
    }



    @Override
    public void onCancel(Platform arg0, int arg1) {
        Log.d("", "onCancel: ");
    }

    @Override
    public void onComplete(Platform plat, int action,
                           HashMap<String, Object> res) {

    }

    @Override
    public void onError(Platform arg0, int arg1, Throwable arg2) {

    }


}
