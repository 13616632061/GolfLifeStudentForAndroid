package com.glorystudent.golflife;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.glorystudent.adapter.EventFragmentAdapter;
import com.glorystudent.dialog.Dialog;
import com.glorystudent.fragment.MyJoinEventFragment;
import com.glorystudent.fragment.MyReleasedEventFragment;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.util.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/18.
 */

public class MyEventActivity extends BaseActivity {
    @Bind(R.id.event_tab)
    TabLayout eventTab;
    @Bind(R.id.event_vp)
    ViewPager eventVp;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.tv_release_event)
    TextView tvReleaseEvent;
    private List<String> tabList;
    private List<Fragment> fragmentList;

    @Override
    protected int getContentId() {
        return R.layout.activity_my_event;
    }

    @Override
    protected void init() {
        tabList = new ArrayList<>();
        tabList.add("我参加的");
        tabList.add("我发布的");
        eventTab.addTab(eventTab.newTab().setText(tabList.get(0)));
        eventTab.addTab(eventTab.newTab().setText(tabList.get(1)));
        fragmentList = new ArrayList<>();
        fragmentList.add(new MyJoinEventFragment());
        fragmentList.add(new MyReleasedEventFragment());
        EventFragmentAdapter eventFragmentAdapter = new EventFragmentAdapter(this.getSupportFragmentManager(), tabList, fragmentList);
        eventVp.setAdapter(eventFragmentAdapter);
        eventVp.setOffscreenPageLimit(1);
        eventTab.setupWithViewPager(eventVp);
        eventTab.setTabsFromPagerAdapter(eventFragmentAdapter);
    }

    @OnClick({R.id.back, R.id.tv_release_event})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_release_event:
                if (SharedUtil.getBoolean(Constants.LOGIN_STATE)) {//需要登陆
//                    if (SharedUtil.getString(Constants.GROUP_ID) != null && !SharedUtil.getString(Constants.GROUP_ID).isEmpty()) {
                        //必须为教练才能使用
                        Intent intent = new Intent(this, EventReleasedActivity.class);
                        intent.putExtra("add", true);
                        startActivity(intent);
//                    } else {
//                        Dialog.getInstance().setOnShowDialogListener(new Dialog.OnShowDialogListener() {
//                            @Override
//                            public void onSure() {
//                                startActivity(new Intent(MyEventActivity.this, MyAttestationActivity.class));
//                            }
//
//                            @Override
//                            public void onCancel() {
//
//                            }
//                        }).showDialog(this, "此功能只有教练才能使用", "是否申请教练", "前往");
//                    }
                } else {
                    Dialog.getInstance().setOnShowDialogListener(new Dialog.OnShowDialogListener() {
                        @Override
                        public void onSure() {
                            startActivity(new Intent(MyEventActivity.this, LoginActivity.class));
                            finish();
                        }

                        @Override
                        public void onCancel() {

                        }
                    }).showDialog(this, "此功能需要登陆", "是否去登陆", "去登陆");
                }
                break;
        }
    }
}
