package com.glorystudent.golflife;

import android.content.Intent;
import android.support.v4.view.ViewPager;

import com.glorystudent.adapter.ImageBrowsingViewPagerAdapter;
import com.glorystudent.golflibrary.base.BaseActivity;

import java.util.List;

import butterknife.Bind;

/**
 * 图片浏览
 * Created by hyj on 2017/3/22.
 */
public class ImageBrowsingActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @Bind(R.id.vp_img)
    public ViewPager vp_img;

    private ImageBrowsingViewPagerAdapter imageBrowsingViewPagerAdapter;

    @Override
    protected int getContentId() {
        return R.layout.activity_image_browsing;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        List<String> picList = intent.getStringArrayListExtra("picList");
        if (picList != null) {
            imageBrowsingViewPagerAdapter = new ImageBrowsingViewPagerAdapter(getSupportFragmentManager(), picList);
            vp_img.setAdapter(imageBrowsingViewPagerAdapter);
            vp_img.addOnPageChangeListener(this);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
