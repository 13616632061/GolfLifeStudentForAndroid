package com.glorystudent.golflife;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.glorystudent.entity.ItemModle;
import com.glorystudent.entity.OptionModle;
import com.glorystudent.entity.TestModle;
import com.glorystudent.entity.TpiTestEntiy;
import com.glorystudent.entity.TpiWebEntiy;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.util.Constants;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.util.XmlParserHandler;
import com.glorystudent.view.CycleWheelView;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.OnClick;

/**
 * Created by lucy on 2017/7/20.
 * TPI测评
 */
public class TPIEvaluationActivity extends BaseActivity  {


    private CycleWheelView optionWheelView, testWheelView, itemWheelView;
    List<TestModle> provinceList = null;
    public static CycleWheelCallBack cycleWheelCallBack;
    private String testname;
    private int playersUserID;

    @Override
    protected int getContentId() {
        return R.layout.activity_tpievaluation;
    }
    @Override
    protected void init() {
        testname = getIntent().getStringExtra("testname");
        playersUserID = getIntent().getIntExtra("playersUserID",0);
        initProvinceDatas();
        initview();
    }

    @OnClick({R.id.back,R.id.bt_back,R.id.bt_preview})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.bt_back:
                //返回
                finish();
                break;
            case R.id.bt_preview:
                //预览页
                Intent intent=new Intent(this,EvaluationResultActivity.class);
                intent.putExtra("type","TPI");
                intent.putExtra("json",getRequestString());
                intent.putExtra("url",getWebUrlString());
                startActivity(intent);

                break;
        }
    }

    // testIt当前选中主问题条目  optionIt当前选中小问题条目  itemIt当前选中答案条目
    private int testIt=0;
    private int optionIt=0;
    private int itemIt=0;
    //当前选中小问题集合
    private List<OptionModle> optionList=new ArrayList<>();
    //当前选中答案集合
    private List<ItemModle> itemList=new ArrayList<>();
    private void initview() {

        Log.i("asa",SharedUtil.getString(Constants.USER_ID));
        testWheelView = (CycleWheelView) findViewById(R.id.cycleWheelView1);
        testWheelView.setLabels(provinceList);//传入数据
        testWheelView.setSelection(0);//开始位置
        testWheelView.setSolid(Color.WHITE,Color.WHITE);//未选中 背景颜色 和选中背景颜色
        testWheelView.setLabelSelectColor(getResources().getColor(R.color.colorYellow4));//选中文字颜色
        //传入：布局，文字，图片，旋转图片，标识
        testWheelView.setWheelItemLayout(R.layout.item_cyclewheel_custom, R.id.tv_label_item_wheel_custom,R.id.iv_cyclewhell,R.id.iv_rotate,1);
        //滑动时改变下一级数据 列表从头开始
        testWheelView.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, String label) {
                testIt=position;
                optionList=provinceList.get(position).getList();
                itemList=optionList.get(0).getList();
                optionWheelView.setLabels1(optionList);
                itemWheelView.setLabels2(itemList);
                optionWheelView.setSelection(0);
                itemWheelView.setSelection(0);
            }
        });


        optionWheelView = (CycleWheelView) findViewById(R.id.cycleWheelView);
        optionWheelView.setLabels1(provinceList.get(0).getList());
        optionWheelView.setSelection(0);
        optionWheelView.setSolid(Color.WHITE,Color.WHITE);
        optionWheelView.setLabelSelectColor(getResources().getColor(R.color.colorYellow4));
        optionWheelView.setWheelItemLayout(R.layout.item_cyclewheel_custom2, R.id.tv_label_item_wheel_custom,R.id.iv_cyclewhell,R.id.iv_rotate,2);
        //滑动时改变下一级数据 列表从头开始
        optionWheelView.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() {


            @Override
            public void onItemSelected(int position, String label) {
                optionIt = position;
                itemList=optionList.get(position).getList();
                itemWheelView.setLabels2(itemList);
                itemWheelView.setSelection(0);
            }
        });


        itemWheelView = (CycleWheelView) findViewById(R.id.cycleWheelView2);
        itemWheelView.setLabels2(provinceList.get(testIt).getList().get(0).getList());
        itemWheelView.setSelection(0);
        itemWheelView.setSolid(Color.WHITE,Color.WHITE);
        itemWheelView.setLabelSelectColor(getResources().getColor(R.color.colorYellow4));
        itemWheelView.setWheelItemLayout(R.layout.item_cyclewheel_custom2, R.id.tv_label_item_wheel_custom,R.id.iv_cyclewhell,R.id.iv_rotate,3);
        itemWheelView.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, String label) {
                itemIt=position;
            }
        });

        cycleWheelCallBack = new CycleWheelCallBack() {
            @Override
            public void itemClickCallback(int possition) {
                //只有点击选中条目 才响应点击事件
                if (itemIt==possition){
                    //选中条目存储
                    for (int i=0;i<optionList.get(optionIt).getList().size();i++){
                        if(i==itemIt){
                            optionList.get(optionIt).getList().get(itemIt).setFlag(true);
                        }else {
                            optionList.get(optionIt).getList().get(i).setFlag(false);
                        }
                    }
                    optionWheelView.notifyAdapter();
                    //改变实体类中数据
                    optionList.get(optionIt).setFlag(true);
                    for (int i=0;i<optionList.size();i++){
                        if (!optionList.get(i).isFlag()){
                            return;
                        }else if (i==optionList.size()-1){
                            provinceList.get(testIt).setFlag(true);
                            testWheelView.notifyAdapter();
                        }
                    }

                }

            }

        };
    }



    public interface CycleWheelCallBack {
        /**
         * 选项点击后接口回调
         */
        void itemClickCallback(int possition);
    }

    //访问接口字符串
    public String getRequestString(){
        Map<String,Integer> stringMap=new HashMap<>();
        TpiTestEntiy tpiTestEntiy=new TpiTestEntiy();
        TpiTestEntiy.TestTPIBean testTPIBean=new TpiTestEntiy.TestTPIBean();
        for (int i=0;i<provinceList.size();i++){
            for (int j=0;j<provinceList.get(i).getList().size();j++){
                int selectedId=0;
                for (int y=0;y<provinceList.get(i).getList().get(j).getList().size();y++){
                    if (provinceList.get(i).getList().get(j).getList().get(y).isFlag()){
                        selectedId=y+1;
                    }
                }
                stringMap.put(provinceList.get(i).getList().get(j).getField(),selectedId);
            }
        }
        //9洞传1 ，18洞传2，tpi测试传3
        testTPIBean.setTestType(3);
        testTPIBean.setPlayersUserName(testname);
        testTPIBean.setPlayersUserID(playersUserID);
        testTPIBean.setTestTPI(stringMap);
        tpiTestEntiy.setTests(testTPIBean);
        String json = new Gson().toJson(tpiTestEntiy);
        return json;
    }

    //访问web页字符串
    public String getWebUrlString(){
        Map<String,Integer> stringMap=new HashMap<>();
        TpiWebEntiy tpiWebEntiy=new TpiWebEntiy();
        for (int i=0;i<provinceList.size();i++){
            for (int j=0;j<provinceList.get(i).getList().size();j++){
                int selectedId=0;
                for (int y=0;y<provinceList.get(i).getList().get(j).getList().size();y++){
                    if (provinceList.get(i).getList().get(j).getList().get(y).isFlag()){
                        selectedId=y+1;
                    }
                }
                stringMap.put(provinceList.get(i).getList().get(j).getField(),selectedId);
            }
        }
        tpiWebEntiy.setPlayersUserName(testname);
        tpiWebEntiy.setTestCreateTime(RequestUtil.getCurrentTime());
        tpiWebEntiy.setTestTPI(stringMap);

        String json = new Gson().toJson(tpiWebEntiy);
        return json;
    }


    /**
     * 解析xml数据
     */
    protected void initProvinceDatas()
    {
        AssetManager asset = getAssets();
        try {
            InputStream input = asset.open("tpi_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();

        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }

}
