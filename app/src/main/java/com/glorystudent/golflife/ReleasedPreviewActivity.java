package com.glorystudent.golflife;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.glorystudent.entity.ReleasedRequestEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.util.TimeUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/10.
 */

public class ReleasedPreviewActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.cb_preview_banner)
    ConvenientBanner banner;
    @Bind(R.id.iv_preview_pic)
    ImageView pic;
    @Bind(R.id.tv_preview_event_name)
    TextView eventName;
    @Bind(R.id.tv_preview_cost)
    TextView cost;
    @Bind(R.id.tv_preview_sign_up_number)
    TextView signUpNumber;
    @Bind(R.id.tv_preview_all_number)
    TextView allNumber;
    @Bind(R.id.tv_preview_event_time)
    TextView eventTime;
    @Bind(R.id.tv_preview_kickoff_time)
    TextView kickoffTime;
    @Bind(R.id.iv_preview_call)
    ImageView call;
    @Bind(R.id.tv_preview_event_address)
    TextView eventAddress;
    @Bind(R.id.iv_preview_location)
    ImageView location;
    @Bind(R.id.tv_preview_event_info)
    TextView eventInfo;
    private ReleasedRequestEntity entity;
    private ArrayList<String> imageList;

    @Override
    protected int getContentId() {
        return R.layout.activity_event_preview;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        imageList = intent.getStringArrayListExtra("imageList");
        entity = EventReleasedActivity.releasedRequestEntity;
        if (entity != null) {
            initDatas();
        }
    }

    private void initDatas() {
        if (entity.getEventactivity().getEventactivity_name() == null) {
            eventName.setText("");
        } else {
            eventName.setText(entity.getEventactivity().getEventactivity_name());
        }
        if (entity.getEventactivity().getEventactivity_costtype() == null || entity.getEventactivity().getEventactivity_costtype().equals("1")) {
            cost.setText("免费活动");
        } else {
            cost.setText("￥" + entity.getEventactivity().getEventactivity_cost().setScale(2));
        }
        if (entity.getEventactivity().getEventactivity_address() == null) {
            eventAddress.setText("");
        } else {
            eventAddress.setText(entity.getEventactivity().getEventactivity_address());
        }
        if (entity.getEventactivity().getEventactivity_detail() == null) {
            eventInfo.setText("");
        } else {
            eventInfo.setText(entity.getEventactivity().getEventactivity_detail());
        }
        if (entity.getEventactivity().getEventactivity_kickofftime() == null) {
            kickoffTime.setText("0年0月0日 0:00");
        } else {
            kickoffTime.setText(TimeUtil.getReleasedTime(TimeUtil.getDateFromUploading(entity.getEventactivity().getEventactivity_kickofftime()), "").trim());
        }
        String defaultTime = "0年0月0日";
        String beginTimeStr = "";
        String endTimeStr = "";
        if (entity.getEventactivity().getEventactivity_begintime() == null) {
            beginTimeStr = defaultTime;
        } else {
            beginTimeStr = TimeUtil.getPreviewTime(entity.getEventactivity().getEventactivity_begintime());
        }
        if (entity.getEventactivity().getEventactivity_endtime() == null) {
            endTimeStr = defaultTime;
        } else {
            endTimeStr = TimeUtil.getPreviewTime(entity.getEventactivity().getEventactivity_endtime());
        }
        eventTime.setText(beginTimeStr + "-" + endTimeStr);
        if (imageList.size() > 1) {
            showBanner();
        } else {
            showImage();
        }
    }

    private void showImage() {
        pic.setVisibility(View.VISIBLE);
        banner.setVisibility(View.GONE);
        GlideUtil.loadImageView(this, imageList.get(0), pic);
    }

    private void showBanner() {
        banner.setVisibility(View.VISIBLE);
        pic.setVisibility(View.GONE);
        banner.setPages(new CBViewHolderCreator<loaclViewHolder>() {
            @Override
            public loaclViewHolder createHolder() {
                return new loaclViewHolder();
            }
        }, imageList)
                .setPageIndicator(new int[]{R.drawable.shape_min_whitecircle, R.drawable.shape_min_orangecircle})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .startTurning(3000);
    }

    /**
     * TODO 自动轮播类
     * 自动轮播需要的类
     */
    public class loaclViewHolder implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            GlideUtil.loadImageView(context, data, imageView);
        }
    }

    @OnClick(R.id.back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

//    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
//    public void getEvent(Map<String, ReleasedRequestEntity> map) {
//        if (map.containsKey("ReleasedPreviewActivity")) {
//            ReleasedRequestEntity releasedRequestEntity = map.get("ReleasedPreviewActivity");
//            entity = releasedRequestEntity;
//            releasedRequestEntity = null;
//        }
//    }
}
