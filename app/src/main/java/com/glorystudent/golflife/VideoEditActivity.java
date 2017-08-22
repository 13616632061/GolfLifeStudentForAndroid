package com.glorystudent.golflife;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.dialog.iosdialog.AlertDialog;
import com.glorystudent.util.EventBusMapUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 视频编辑
 * Created by hyj on 2017/1/13.
 */
public class VideoEditActivity extends BaseActivity {


    @Bind(R.id.tv_take_label)
    public TextView tv_take_label;

    @Bind(R.id.et_video_name)
    public EditText et_video_name;

    @Bind(R.id.ll_hsv)
    public LinearLayout ll_hsv;

    private SQLiteDatabase sqLiteDatabase;
    private ArrayList<Integer> ids;

    @Override
    protected int getContentId() {
        return R.layout.activity_video_edit;
    }

    @Override
    protected void init() {
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(getDatabasePath("video.db"), null);
        Intent intent = getIntent();
        ids = intent.getIntegerArrayListExtra("ids");
        if (ids != null) {
            for (int i = 0; i < ids.size(); i++) {
                Cursor cursor = sqLiteDatabase.rawQuery("select * from videoModel where id = ?", new String[]{ids.get(i) + ""});
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        if (ids.size() == 1) {
                            String title = cursor.getString(cursor.getColumnIndex("title"));
                            String content = cursor.getString(cursor.getColumnIndex("content"));
                            if (title != null && !title.isEmpty()) {
                                et_video_name.setText(title);
                                et_video_name.setSelection(title.length());
                            }
                            if (content != null && !content.isEmpty()) {
                                tv_take_label.setText(content);
                            }
                        }
                        byte[] in = cursor.getBlob(cursor.getColumnIndex("picBytes"));
                        View inflate = LayoutInflater.from(this).inflate(R.layout.item_edit_video_pic, null);
                        ImageView iv_video_pic = (ImageView) inflate.findViewById(R.id.iv_video_pic);
                        Bitmap bitmap = null;
                        if (in != null) {
                            bitmap = BitmapFactory.decodeByteArray(in, 0, in.length);
                            iv_video_pic.setImageBitmap(bitmap);
                            ll_hsv.addView(inflate);
                        }
                    }
                    cursor.close();
                }
            }
        }
    }

    @OnClick({R.id.back, R.id.add_label, R.id.tv_sure})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                close();
                break;
            case R.id.add_label:
                //全部标签
                Intent intent = new Intent(this, LabelActivity.class);
                startActivityForResult(intent, 0x876);
                break;
            case R.id.tv_sure:
                //确定
                saveToDatabase();
                break;
        }
    }

    private void saveToDatabase() {
        String nameStr = et_video_name.getText().toString().trim();
        if (nameStr != null && !nameStr.isEmpty()) {
            String labelStr = tv_take_label.getText().toString().trim();
            if (labelStr != null && !labelStr.isEmpty()) {
                if (ids != null) {
                    for (int i = 0; i < ids.size(); i++) {
                        sqLiteDatabase.execSQL("update videoModel set title = ?,content = ? where id = ?",
                                new String[]{nameStr, labelStr, ids.get(i) + ""});
                    }
                }
            } else {
                for (int i = 0; i < ids.size(); i++) {
                    sqLiteDatabase.execSQL("update videoModel set title = ? where id = ?",
                            new String[]{nameStr, ids.get(i) + ""});
                }
            }
            Toast.makeText(VideoEditActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent();
//        intent.putExtra("ids", ids);
//        setResult(0x138, intent);
            EventBus.getDefault().post(EventBusMapUtil.getStringMap("LocalVideoFragment", "refresh"));
            finish();
        } else {
            Toast.makeText(this, "请输入视频名字", Toast.LENGTH_SHORT).show();
        }
    }

    private void close() {
        new AlertDialog(this).builder()
                .setTitle("确认退出编辑")
                .setPositiveButton("退出", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setNegativeButton("继续编辑", null)
                .show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            close();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x876 && resultCode == RESULT_OK) {
            String label = data.getStringExtra("label");
            tv_take_label.setText(label);
        }
    }
}
