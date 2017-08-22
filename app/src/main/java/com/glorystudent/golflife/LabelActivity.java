package com.glorystudent.golflife;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.util.Constants;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 标签
 * Created by hyj on 2017/1/13.
 */
public class LabelActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private final static String TAG = "LabelActivity";

    private String[] datas;

    @Bind(R.id.lv_label)
    public ListView lv_label;

    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected int getContentId() {
        return R.layout.activity_label;
    }

    @Override
    protected void init() {

        datas = Constants.LABEL;

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datas);
        lv_label.setAdapter(arrayAdapter);
        lv_label.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = arrayAdapter.getItem(position);
        Intent intent = new Intent();
        intent.putExtra("labelnumber", position + 1);
        intent.putExtra("label", item);
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick({R.id.back})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
