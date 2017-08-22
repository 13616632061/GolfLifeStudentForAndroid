package com.glorystudent.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.glorystudent.dialog.Dialog;
import com.glorystudent.golflibrary.base.BaseFragment;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.golflife.EditProfileActivity;
import com.glorystudent.golflife.MainActivity;
import com.glorystudent.golflife.MyAttestationActivity;
import com.glorystudent.golflife.MyEventActivity;
import com.glorystudent.golflife.MyInviteActivity;
import com.glorystudent.golflife.MyQRcodeActivity;
import com.glorystudent.golflife.MyScoreActivity;
import com.glorystudent.golflife.MySetActivity;
import com.glorystudent.golflife.MyTeamActivity;
import com.glorystudent.golflife.MyWalletActivity;
import com.glorystudent.golflife.R;
import com.glorystudent.golflife.TeachingCenterActivity;
import com.glorystudent.util.Constants;
import com.thinkcool.circletextimageview.CircleTextImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 教练端我的模块
 * Created by hyj on 2016/12/21.
 */
public class CoachMyFragment extends BaseFragment {
    private final static String TAG = "CoachMyFragment";
    @Bind(R.id.my_head_portrait)
    public CircleTextImageView my_head_portrait;
    @Bind(R.id.my_nickname)
    public TextView my_nickname;
    private String groupid;

    @Bind(R.id.tv_attestation)
    public TextView tv_attestation;

    @Bind(R.id.my_coach_state)
    public ImageView my_coach_state;

    @Override
    protected int getContentId() {
        return R.layout.fragment_coach_my;
    }

    @Override
    protected void init(View view) {
        groupid = SharedUtil.getString(Constants.GROUP_ID);
        if (groupid != null) {
            tv_attestation.setText("已认证");
            my_coach_state.setVisibility(View.VISIBLE);
        } else {
            tv_attestation.setText("未认证");
            my_coach_state.setVisibility(View.GONE);
        }
        EventBus.getDefault().register(this);
        String head_pic_url = SharedUtil.getString(Constants.HEAD_PORTRAIT);
        if (head_pic_url != null) {
            my_head_portrait.setImageResource(R.drawable.pic_default_avatar);
            GlideUtil.loadCircleImageView(getActivity(), head_pic_url, my_head_portrait, R.drawable.pic_head_portrait);
        } else {
            my_head_portrait.setImageResource(R.drawable.pic_head_portrait);
        }

        String nickname = SharedUtil.getString(Constants.NICKNAME);
        if (nickname != null) {
            my_nickname.setText(nickname);
        } else {
            my_nickname.setText(Constants.DEFAULT_USERNAME + SharedUtil.getString(Constants.PHONE_NUMBER));
        }
    }

    @OnClick({R.id.my_head_portrait, R.id.my_tv_event, R.id.iv_my_qrcode, R.id.my_tv_score, R.id.my_tv_school,
            R.id.my_tv_attestation, R.id.my_tv_invite, R.id.my_tv_set, R.id.my_tv_wallet, R.id.my_tv_team})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.my_head_portrait:
                //我
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
                break;
            case R.id.iv_my_qrcode:
                //我的二维码
                startActivity(new Intent(getActivity(), MyQRcodeActivity.class));
                break;
            case R.id.my_tv_event:
                //我的活动
                startActivity(new Intent(getActivity(), MyEventActivity.class));
                break;
            case R.id.my_tv_score:
                //我的记分卡
                startActivity(new Intent(getActivity(), MyScoreActivity.class));
                break;
            case R.id.my_tv_attestation:
                //认证信息
                startActivity(new Intent(getActivity(), MyAttestationActivity.class));
                break;
            case R.id.my_tv_school:
                //所属教学中心
                if (groupid != null) {
                    startActivity(new Intent(getActivity(), TeachingCenterActivity.class));
                } else {
                    Dialog.getInstance().setOnShowDialogListener(new Dialog.OnShowDialogListener() {
                        @Override
                        public void onSure() {
                            startActivity(new Intent(getActivity(), MyAttestationActivity.class));
                        }

                        @Override
                        public void onCancel() {

                        }
                    }).showDialog(getActivity(), "此功能只有教练才能使用", "是否申请教练", "前往");
                }
                break;
            case R.id.my_tv_invite:
                //邀请球友
                startActivity(new Intent(getActivity(), MyInviteActivity.class));
                break;
            case R.id.my_tv_wallet:
                //我的钱包
                startActivity(new Intent(getActivity(), MyWalletActivity.class));
                break;
            case R.id.my_tv_team:
                //我的球队
                startActivity(new Intent(getActivity(), MyTeamActivity.class));
                break;
            case R.id.my_tv_set:
                //设置
                Intent intent = new Intent(getActivity(), MySetActivity.class);
                startActivityForResult(intent, 0x886);
                break;
        }
    }

    //接收修改头像之后，更改头像
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getHeadPortrait(Bitmap bitmap) {
        my_head_portrait.setImageBitmap(bitmap);
    }

    //接收修改昵称之后，更改昵称
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 50)
    public void getNickName(String nickname) {
        my_nickname.setText(SharedUtil.getString(Constants.NICKNAME));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x886 && resultCode == 0x887) {
            MainActivity activity = (MainActivity) getActivity();
            activity.setCoachPort();//调用父类方法，切换成教练端
        }
    }
}
