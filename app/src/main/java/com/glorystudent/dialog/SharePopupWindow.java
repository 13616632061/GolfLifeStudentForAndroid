package com.glorystudent.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.glorystudent.adapter.ShareAdapter;
import com.glorystudent.entity.ShareModel;
import com.glorystudent.golflife.R;
import com.glorystudent.util.Constants;
import com.glorystudent.util.ImageUtil;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * TODO<分享工具>
 *
 * @data: 2014-7-21 下午2:45:38
 * @version: V1.0
 */
public class SharePopupWindow extends PopupWindow {
    private Context context;
    private PlatformActionListener platformActionListener;
    private ShareParams shareParams;

    public SharePopupWindow(Context cx) {
        this.context = cx;
    }

    public PlatformActionListener getPlatformActionListener() {
        return platformActionListener;
    }

    public void setPlatformActionListener(
            PlatformActionListener platformActionListener) {
        this.platformActionListener = platformActionListener;
    }

    public void showShareWindow() {
        View view = LayoutInflater.from(context).inflate(R.layout.share_layout, null);
        GridView gridView = (GridView) view.findViewById(R.id.share_gridview);
        ShareAdapter adapter = new ShareAdapter(context);
        gridView.setAdapter(adapter);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        // 取消按钮
        btn_cancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // 销毁弹出框
                dismiss();
            }
        });
        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.FILL_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        gridView.setOnItemClickListener(new ShareItemClickListener(this));
    }

    private class ShareItemClickListener implements OnItemClickListener {
        private PopupWindow pop;

        public ShareItemClickListener(PopupWindow pop) {
            this.pop = pop;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            share(position);
            pop.dismiss();
        }
    }

    /**
     * 分享
     *
     * @param position
     */
    private void share(int position) {
        if (position == 2) {
            Glide.with(context).load(shareParams.getImageUrl()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    try {
                        String picPath = ImageUtil.saveBitmap(resource, System.currentTimeMillis() + "");
                        showShare(context, QQ.NAME, true, shareParams.getTitle(), shareParams.getText(), shareParams.getUrl(), picPath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {

                }
            });
        } else if (position == 4) {
            qzone();
        } else if (position == 5) {
            shortMessage();
        } else if (position == 0) {
            Glide.with(context).load(shareParams.getImageUrl()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    try {
                        String picPath = ImageUtil.saveBitmap(resource, System.currentTimeMillis() + "");
                        showShare(context, Wechat.NAME, true, shareParams.getTitle(), shareParams.getText(), shareParams.getUrl(), picPath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {

                }
            });

        } else if (position == 1) {
            Glide.with(context).load(shareParams.getImageUrl()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    try {
                        String picPath = ImageUtil.saveBitmap(resource, System.currentTimeMillis() + "");
                        showShare(context, WechatMoments.NAME, true, shareParams.getTitle(), shareParams.getText(), shareParams.getUrl(), picPath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {

                }
            });
        }
    }

    /**
     * 初始化分享参数
     *
     * @param shareModel
     */
    public void initShareParams(ShareModel shareModel) {
        if (shareModel != null) {
            ShareParams sp = new ShareParams();
            sp.setShareType(Platform.SHARE_TEXT);
            sp.setShareType(Platform.SHARE_WEBPAGE);
            sp.setTitle(shareModel.getTitle());
            sp.setText(shareModel.getText());
            sp.setUrl(shareModel.getUrl());
            sp.setImageUrl(shareModel.getImageUrl());
            shareParams = sp;
        }
    }

    /**
     * 获取平台
     *
     * @param position
     * @return
     */
    private String getPlatform(int position) {
        String platform = "";
        switch (position) {
            case 0:
                platform = "Wechat";
                break;
            case 1:
                platform = "WechatMoments";
                break;
            case 2:
                platform = "QQ";
                break;
        }
        return platform;
    }

    /**
     * 分享到QQ空间
     */
    private void qzone() {
        ShareParams sp = new ShareParams();
        sp.setTitle(shareParams.getTitle());
        sp.setTitleUrl(shareParams.getUrl()); // 标题的超链接
        sp.setText(shareParams.getText());
        sp.setImageUrl(shareParams.getImageUrl());
        sp.setComment("我对此分享内容的评论");
        sp.setSite(shareParams.getTitle());
        sp.setSiteUrl(shareParams.getUrl());
        Platform qzone = ShareSDK.getPlatform(context, "QZone");
        qzone.setPlatformActionListener(platformActionListener); // 设置分享事件回调 //
        // 执行图文分享
        qzone.share(sp);
    }

    /**
     * 分享到朋友圈
     */
    private void wechatMoments() {
        ShareParams sp = new ShareParams();
        sp.setTitle(shareParams.getTitle());
        sp.setTitleUrl(shareParams.getUrl()); // 标题的超链接
        sp.setText(shareParams.getText());
        sp.setImageUrl(shareParams.getImageUrl());
        sp.setComment("我对此分享内容的评论");
        sp.setSite(shareParams.getTitle());
        sp.setSiteUrl(shareParams.getUrl());
        Platform wechatMoments = ShareSDK.getPlatform(context, "WechatMoments");
        wechatMoments.setPlatformActionListener(platformActionListener); // 设置分享事件回调 //
        // 执行图文分享
        wechatMoments.share(sp);
    }

    /**
     * 分享到微信
     */
    private void wechat() {
        // 微信分享的配置注册信息.
        HashMap<String, Object> wxMap = new HashMap<String, Object>();
        wxMap.put("Id", "4");
        wxMap.put("SortId", "4");
        wxMap.put("AppId", "wxd2ec5fc5fab63695");
        wxMap.put("AppSecret", "5275c0374acde6a637d15560dffc6314");
        wxMap.put("BypassApproval", "false");
        wxMap.put("Enable", "true");
        ShareSDK.setPlatformDevInfo(Wechat.NAME, wxMap);
        Wechat.ShareParams sp = new Wechat.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setTitle(shareParams.getTitle());
        sp.setTitleUrl(shareParams.getUrl()); // 标题的超链接
        sp.setText(shareParams.getText());
        sp.setImageUrl(shareParams.getImageUrl());
        sp.setComment("我对此分享内容的评论");
        sp.setSite(shareParams.getTitle());
        sp.setSiteUrl(shareParams.getUrl());
        Platform wechat = ShareSDK.getPlatform(context, Wechat.NAME);
        wechat.setPlatformActionListener(platformActionListener); // 设置分享事件回调 //
        // 执行图文分享
        wechat.share(sp);
    }

    /**
     * 演示调用ShareSDK执行分享
     *
     * @param context
     * @param platformToShare 指定直接分享平台名称（一旦设置了平台名称，则九宫格将不会显示）
     * @param showContentEdit 是否显示编辑页
     */
    public  void showShare(Context context, String platformToShare, boolean showContentEdit,
                                 final String titile, final String mShareContent, final String url, final String logopath) {
        if (platformToShare.equals(Wechat.NAME)) {
            // 获取缓存微信分享的秘钥key值
            String wxAppId = Constants.WX_APPID;
            // 获取缓存微信分享的秘钥key值
            String wxAppSecret = Constants.WX_AppSecret;//动态获取
            if (wxAppId != null && !wxAppId.equals("") && wxAppId.length() != 0
                    && wxAppSecret != null && !wxAppSecret.equals("") && wxAppSecret.length() != 0) {
                // 微信分享的配置注册信息.
                HashMap<String, Object> wxMap = new HashMap<String, Object>();
                wxMap.put("Id", "4");
                wxMap.put("SortId", "4");
                wxMap.put("AppId", wxAppId);
                wxMap.put("AppSecret", wxAppSecret);
                wxMap.put("BypassApproval", "false");
                wxMap.put("Enable", "true");
                ShareSDK.setPlatformDevInfo(Wechat.NAME, wxMap);
            }
        }else if(platformToShare.equals(WechatMoments.NAME)) {

            // 获取缓存微信分享的秘钥key值
            String wxAppId = Constants.WX_APPID;
            // 获取缓存微信分享的秘钥key值
            String wxAppSecret = Constants.WX_AppSecret;//动态获取
            if (wxAppId != null && !wxAppId.equals("") && wxAppId.length() != 0
                    && wxAppSecret != null && !wxAppSecret.equals("") && wxAppSecret.length() != 0) {
                // 微信朋友圈分享的配置注册信息.
                HashMap<String, Object> wxMomentsMap = new HashMap<String, Object>();
                wxMomentsMap.put("Id", "5");
                wxMomentsMap.put("SortId", "5");
                wxMomentsMap.put("AppId", wxAppId);
                wxMomentsMap.put("AppSecret", wxAppSecret);
                wxMomentsMap.put("BypassApproval", "false");
                wxMomentsMap.put("Enable", "true");
                ShareSDK.setPlatformDevInfo(WechatMoments.NAME, wxMomentsMap);
            }

        } else if (platformToShare.equals(QQ.NAME)) {
            String qqAppId = Constants.QQ_APPID;
            String qqAppKey = Constants.QQ_APPKEY;
            if (qqAppId != null && qqAppKey != null && !qqAppId.isEmpty() && !qqAppKey.isEmpty()) {
                HashMap<String, Object> wxMomentsMap = new HashMap<String, Object>();
                wxMomentsMap.put("Id", "7");
                wxMomentsMap.put("SortId", "7");
                wxMomentsMap.put("AppId", qqAppId);
                wxMomentsMap.put("AppKey", qqAppKey);
                wxMomentsMap.put("ShareByAppClient", "true");
                wxMomentsMap.put("Enable", "true");
                ShareSDK.setPlatformDevInfo(QQ.NAME, wxMomentsMap);
            }
        }

//        ShareSDK.initSDK(context, "1a11d52921447");








        OnekeyShare oks = new OnekeyShare();
        oks.setSilent(!showContentEdit);
        if (platformToShare != null) {
            oks.setPlatform(platformToShare);
        }
//        //ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC  第二个是SKYBLUE
        oks.setTheme(OnekeyShareTheme.CLASSIC);
//        // 令编辑页面显示为Dialog模式
        oks.setDialogMode();
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//		oks.setTitle(context.getString(R.string.app_name));
        oks.setTitle(titile);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(mShareContent);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数

        oks.setImagePath(logopath);// 确保SDcard下面存在此张图片
        // imageUrl是图片的网络路径，新浪微博、人人网、QQ空间和Linked-In支持此字段
        oks.setImageUrl(shareParams.getImageUrl());
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(context.getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                // 改写twitter分享内容中的text字段，否则会超长，
                // 因为twitter会将图片地址当作文本的一部分去计算长度
                if ("Twitter".equals(platform.getName())) {
                    paramsToShare.setText(mShareContent);
                }
            }
        });
        // 启动分享
        oks.show(context);
    }

    private void qq() {
        ShareParams sp = new ShareParams();
        sp.setTitle(shareParams.getTitle());
        sp.setTitleUrl(shareParams.getUrl()); // 标题的超链接
        sp.setText(shareParams.getText());
        sp.setImageUrl(shareParams.getImageUrl());
        sp.setComment("我对此分享内容的评论");
        sp.setSite(shareParams.getTitle());
        sp.setSiteUrl(shareParams.getUrl());
        Platform qq = ShareSDK.getPlatform(context, "QQ");
        qq.setPlatformActionListener(platformActionListener);
        qq.share(sp);
    }

    /**
     * 分享到短信
     */
    private void shortMessage() {
        ShareParams sp = new ShareParams();
        sp.setAddress("");
        sp.setText(shareParams.getText() + "这是网址《" + shareParams.getUrl() + "》很给力哦！");
        Platform circle = ShareSDK.getPlatform(context, "ShortMessage");
        circle.setPlatformActionListener(platformActionListener); // 设置分享事件回调
        // 执行图文分享
        circle.share(sp);
    }
}