package com.glorystudent.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.TextView;

import com.glorystudent.golflibrary.base.BaseFragment;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.golflife.MainActivity;
import com.glorystudent.golflife.MyAttestationActivity;
import com.glorystudent.golflife.MyCollectActivity;
import com.glorystudent.golflife.MyCourseActivity;
import com.glorystudent.golflife.MyEventActivity;
import com.glorystudent.golflife.MyInviteActivity;
import com.glorystudent.golflife.MyPactActivity;
import com.glorystudent.golflife.MyQRcodeActivity;
import com.glorystudent.golflife.MyScoreActivity;
import com.glorystudent.golflife.MySetActivity;
import com.glorystudent.golflife.MyTeamActivity;
import com.glorystudent.golflife.MyWalletActivity;
import com.glorystudent.golflife.R;
import com.glorystudent.util.Constants;
import com.thinkcool.circletextimageview.CircleTextImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 学员端我的模块
 * Created by hyj on 2016/12/21.
 */
public class StudentMyFragment extends BaseFragment {
    private final static String TAG = "StudentMyFragment";
    @Bind(R.id.my_head_portrait)
    public CircleTextImageView my_head_portrait;
    @Bind(R.id.my_nickname)
    public TextView my_nickname;

    @Override
    protected int getContentId() {
        return R.layout.fragment_stu_my;
    }

    @Override
    protected void init(View view) {
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
            String username = Constants.DEFAULT_USERNAME + SharedUtil.getString(Constants.PHONE_NUMBER);
            my_nickname.setText(username);
            SharedUtil.putString(Constants.NICKNAME, username);
        }
    }

    @OnClick({R.id.my_tv_event, R.id.my_tv_pact, R.id.my_tv_course, R.id.my_tv_score,
            R.id.my_tv_collect, R.id.my_tv_attestation, R.id.my_tv_invite, R.id.my_tv_set,
            R.id.iv_my_qrcode, R.id.my_head_portrait, R.id.my_tv_wallet, R.id.my_tv_team})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.my_head_portrait:
                //我
                Intent intent2 = new Intent(getActivity(), MySetActivity.class);
                startActivityForResult(intent2, 0x886);
                break;
            case R.id.iv_my_qrcode:
                //我的二维码
                startActivity(new Intent(getActivity(), MyQRcodeActivity.class));
                break;
            case R.id.my_tv_event:
                //我的活动
                startActivity(new Intent(getActivity(), MyEventActivity.class));
                break;
            case R.id.my_tv_pact:
                //我的预约
                startActivity(new Intent(getActivity(), MyPactActivity.class));
                break;
            case R.id.my_tv_course:
                //我的课程
                startActivity(new Intent(getActivity(), MyCourseActivity.class));
                break;
            case R.id.my_tv_score:
                //我的记分卡
                startActivity(new Intent(getActivity(), MyScoreActivity.class));
                break;
            case R.id.my_tv_wallet:
                //我的钱包
                startActivity(new Intent(getActivity(), MyWalletActivity.class));
                break;
            case R.id.my_tv_team:
                //我的球队
                startActivity(new Intent(getActivity(), MyTeamActivity.class));
                break;
            case R.id.my_tv_collect:
                //我的收藏
                startActivity(new Intent(getActivity(), MyCollectActivity.class));
                break;
            case R.id.my_tv_attestation:
                //教练认证
                startActivity(new Intent(getActivity(), MyAttestationActivity.class));
                break;
            case R.id.my_tv_invite:
                //邀请球友
                startActivity(new Intent(getActivity(), MyInviteActivity.class));
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
