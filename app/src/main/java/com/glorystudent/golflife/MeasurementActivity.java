package com.glorystudent.golflife;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.glorystudent.adapter.MeasurementViewPagerAdapter;
import com.glorystudent.entity.TestRequestEntity2;
import com.glorystudent.entity.TestScoreEntity;
import com.glorystudent.fragment.MeasurementFragment;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.util.RequestUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 测评
 * Created by hyj on 2017/1/4.
 */
public class MeasurementActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private final static String TAG = "MeasurementActivity";
    @Bind(R.id.tv_pagecount)
    public TextView tv_pagecount;
    @Bind(R.id.vp_measurement)
    public ViewPager vp_measurement;
    @Bind(R.id.tv_page)
    public TextView tv_page;
    @Bind(R.id.btn_end)
    public TextView btn_end;
    private MeasurementViewPagerAdapter measurementViewPagerAdapter;
    public List<TestScoreEntity> scoreDatas;
    public Map<Integer,Boolean> isScoreMap;
    private int hole;//球洞数
    private int savePosition;//保存上一页的下标
    private int saveCurrentPosition;//保存当前页的下标
    private String testname;
    private int testType;
    private int playersUserID;

    @Override
    protected int getContentId() {
        return R.layout.activity_measurement;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        hole = intent.getIntExtra("hole", -1);
        testname = intent.getStringExtra("testname");
        testType = intent.getIntExtra("testType",0);
        playersUserID = intent.getIntExtra("playersUserID",0);
        tv_pagecount.setText("/" + hole);
        vp_measurement.setOffscreenPageLimit(hole);
        measurementViewPagerAdapter = new MeasurementViewPagerAdapter(getSupportFragmentManager(), hole);
        vp_measurement.setAdapter(measurementViewPagerAdapter);
        vp_measurement.addOnPageChangeListener(this);
        isScoreMap = new HashMap<>();
        scoreDatas = new ArrayList<>();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        saveCurrentPosition = position;
        if (position > 0) {
            savePosition = position - 1;
            btn_end.setVisibility(View.GONE);
            MeasurementFragment item = (MeasurementFragment) measurementViewPagerAdapter.getItem(position - 1);
            TestScoreEntity test = item.getTest();
            test.setPosition(savePosition);
            setScoreDatas(test);
        }
        if(position == hole - 1) {
            //最后一页
            savePosition = position;
            btn_end.setVisibility(View.VISIBLE);
        }
        tv_page.setText(position + 1 + "");
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private boolean isSaveed;
    public void setScoreDatas(TestScoreEntity testScoreEntity){
        if (testScoreEntity.getFairway() != -1 && testScoreEntity.getUpgreen() != -1
                && testScoreEntity.getFollowball() != -1 && testScoreEntity.getBunkers() != -1) {
            isSaveed = false;
            for (TestScoreEntity scoreData : scoreDatas) {
                if(scoreData.getPosition() == savePosition){
                    isSaveed = true;
                    scoreData.setPar(testScoreEntity.getPar());
                    scoreData.setFairway(testScoreEntity.getFairway());
                    scoreData.setUpgreen(testScoreEntity.getUpgreen());
                    scoreData.setBunkers(testScoreEntity.getBunkers());
                }
            }
            if (!isSaveed) {
                scoreDatas.add(testScoreEntity);
            }
            isScoreMap.put(savePosition, true);
        }else{
            isScoreMap.put(savePosition, false);
            if(savePosition != hole -1){
                dialog_Hint(this, testScoreEntity.getPosition());
            }
        }
    }
    private  void dialog_Hint(Context context, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("发现记录数据不完整球洞，记录完善的数据才能测评");
        builder.setTitle("提示");
        builder.setIcon(R.drawable.ic_launcher);
        builder.setPositiveButton("去完善",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        vp_measurement.setCurrentItem(position);
                    }
                });
        builder.setNegativeButton("等会儿再说",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    private boolean isEnd;
    @OnClick({R.id.back, R.id.btn_end})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.btn_end:
                //结束测评

                MeasurementFragment item = (MeasurementFragment) measurementViewPagerAdapter.getItem(savePosition);
                TestScoreEntity test = item.getTest();
                test.setPosition(savePosition);
                setScoreDatas(test);

                isEnd = true;//是否可以结束测试
                Iterator<Map.Entry<Integer, Boolean>> iterator = isScoreMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<Integer, Boolean> entry = iterator.next();
                    if (!entry.getValue()) {
                        //未填完整的数据
                        isEnd = false;
                        dialog_Hint(this, entry.getKey());
                        break;
                    }
                }
                if (isEnd) {
                    String requestString = getRequestString();
                    String request = RequestUtil.getRequestJson(this, requestString);
                    Intent intent = new Intent(this, TestResultActivity.class);
                    intent.putExtra("request", request);
                    intent.putExtra("type", "add");
                    startActivity(intent);
                }
                break;
        }
    }

    private String getRequestString() {
//        TestRequestEntity testRequestEntity = new TestRequestEntity();
//        TestRequestEntity.TestIntegrationBean  testIntegrationBean = new TestRequestEntity.TestIntegrationBean();
//        testIntegrationBean.setTest_holecount(hole);
//        testIntegrationBean.setTest_createtime(RequestUtil.getCurrentTime());
//
//        TestRequestEntity.TestplayerBean testplayerBean = new TestRequestEntity.TestplayerBean();
//        testplayerBean.setTest_id(0);
//        testplayerBean.setPlayers_userid(0);
//        testplayerBean.setPlayers_username(testname);
//        List<TestRequestEntity.ListtestholeBean> testholeList = new ArrayList<>();
//        for (int i = 0; i < scoreDatas.size(); i++) {
//            TestRequestEntity.ListtestholeBean listtestholeBean = new TestRequestEntity.ListtestholeBean();
//            listtestholeBean.setHole_number(i);
//            listtestholeBean.setShots(scoreDatas.get(i).getPar());
//            listtestholeBean.setFairway(scoreDatas.get(i).getFairway());
//            listtestholeBean.setParongreen(scoreDatas.get(i).getUpgreen());
//            listtestholeBean.setCut(scoreDatas.get(i).getFollowball() + "");
//            listtestholeBean.setBunker(scoreDatas.get(i).getBunkers() + "");
//            testholeList.add(listtestholeBean);
//        }
//        testplayerBean.setListtesthole(testholeList);
//        testIntegrationBean.setTestplayer(testplayerBean);
//        testRequestEntity.setTestIntegration(testIntegrationBean);
//        String json = new Gson().toJson(testRequestEntity);






        TestRequestEntity2 testEntity=new TestRequestEntity2();
        TestRequestEntity2.TestsBean testsBean=new TestRequestEntity2.TestsBean();
        testsBean.setPlayersUserName(testname);
        testsBean.setPlayersUserID(playersUserID);
        testsBean.setTestType(testType);
        testsBean.setTest_createtime(RequestUtil.getCurrentTime());
        List<TestRequestEntity2.TestsBean.TestHoleBean> testHoleList=new ArrayList<>();
        for (int i = 0; i < scoreDatas.size(); i++) {
            TestRequestEntity2.TestsBean.TestHoleBean testHoleBean = new TestRequestEntity2.TestsBean.TestHoleBean();
            testHoleBean.setHole_number(i);
            testHoleBean.setShots(scoreDatas.get(i).getPar());
            testHoleBean.setFairway(scoreDatas.get(i).getFairway());
            testHoleBean.setParongreen(scoreDatas.get(i).getUpgreen());
            testHoleBean.setCut(scoreDatas.get(i).getFollowball() + "");
            testHoleBean.setBunker(scoreDatas.get(i).getBunkers() + "");
            testHoleList.add(testHoleBean);
        }
        testsBean.setTestHole(testHoleList);
        testEntity.setTests(testsBean);
        String json = new Gson().toJson(testEntity);

        return json;
    }

    /**
     * 提供给Fragmnet自动跳转页面方法
     */
    public void setAutomaticJump(){
        if(saveCurrentPosition != hole - 1){
            vp_measurement.setCurrentItem(saveCurrentPosition + 1);
        }
    }
}
