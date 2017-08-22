package com.glorystudent.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.glorystudent.fragment.LoginFragment;

/**
 * Created by hyj on 2017/3/27.
 */
public class LoginViewPagerAdapter extends FragmentPagerAdapter {
    public LoginViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return LoginFragment.getInstance(position);
    }

    @Override
    public int getCount() {
        return 1;
    }
}
