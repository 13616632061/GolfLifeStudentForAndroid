package com.glorystudent.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.glorystudent.golflibrary.base.BaseFragment;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflife.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 图片浏览
 * Created by hyj on 2017/3/22.
 */
public class ImageBrowsingFragment extends BaseFragment {
    public static ImageBrowsingFragment getInstance(String picurl) {
        ImageBrowsingFragment imageBrowsingFragment = new ImageBrowsingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("picurl", picurl);
        imageBrowsingFragment.setArguments(bundle);
        return imageBrowsingFragment;
    }

    @Bind(R.id.iv_img)
    public ImageView iv_img;

    @Override
    protected int getContentId() {
        return R.layout.fragment_image_browsing;
    }

    @Override
    protected void getBundle(Bundle bundle) {
        String picurl = (String) bundle.get("picurl");
        if (picurl != null) {
            load(picurl);
        }
    }

    private void load(String picurl) {
        GlideUtil.loadImageView(getActivity(), picurl, iv_img);
    }

    @OnClick({R.id.iv_img})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.iv_img:
                //点击了
                getActivity().finish();
                break;
        }
    }
}
