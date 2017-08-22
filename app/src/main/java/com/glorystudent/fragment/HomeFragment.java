package com.glorystudent.fragment;

import android.view.View;

import com.glorystudent.golflibrary.base.BaseFragment;
import com.glorystudent.golflife.R;

/**
 * 首页模块（客户端与教练端合并）
 * Created by hyj on 2016/12/14.
 */
public class HomeFragment extends BaseFragment{
    private final static String TAG = "HomeFragment";
    private int switchClient = 0;//0为客户端，1为教练端
    @Override
    protected int getContentId() {
        return R.layout.fragment_home;
    }

    /**
     * 初始化
     * @param view
     */
    @Override
    protected void init(View view) {
        switchFragment(0);
    }

    private void switchFragment(int switchClient){
        switch (switchClient) {
            case 0:
                //切换为客户端
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_fragment, new StudentHomeFragment())
                        .commit();
                break;
            case 1:
                //切换为教练端
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_fragment, new CoachHomeFragment())
                        .commit();
                break;
        }
    }
}
