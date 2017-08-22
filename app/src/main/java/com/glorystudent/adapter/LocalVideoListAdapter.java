package com.glorystudent.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.glorystudent.entity.VideoModelEntity;
import com.glorystudent.golflibrary.dialog.iosdialog.AlertDialog;
import com.glorystudent.golflife.R;
import com.glorystudent.golflife.UpLoadVideoActivity;
import com.glorystudent.golflife.VideoEditActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gavin.J on 2017/6/20.
 */

public class LocalVideoListAdapter extends BaseAdapter implements SectionIndexer {
    private Context context;
    private List<VideoModelEntity> datas;
    private boolean isChoose;
    private SQLiteDatabase sqLiteDatabase;
    private OnItemDeleteListener onItemDeleteListener;
    private int prePosition = -1;

    public LocalVideoListAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
        if (sqLiteDatabase == null) {
            sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(context.getDatabasePath("video.db"), null);
        }
    }

    public void setOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
        this.onItemDeleteListener = onItemDeleteListener;
    }

    public void setDatas(List<VideoModelEntity> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void setIsChoose(boolean isChoose) {
        this.isChoose = isChoose;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public VideoModelEntity getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_local_video_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //赋值
        VideoModelEntity item = getItem(position);
        viewHolder.tvTitle.setText(item.getTitle());
        viewHolder.tvContent.setText(item.getContent());
        viewHolder.tvDuration.setText(item.getDuration());
        Bitmap bitmap = BitmapFactory.decodeByteArray(item.getPicBytes(), 0, item.getPicBytes().length);
        viewHolder.ivVideoPic.setImageBitmap(bitmap);
//        Glide.with(context).load(item.getPicBytes()).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.ivVideoPic);

        int section = getSectionForPosition(position);
        if (position == getPositionForSection(section)) {
            viewHolder.tvLabelDate.setVisibility(View.VISIBLE);
            viewHolder.tvLabelDate.setText(item.getDate());
        } else {
            viewHolder.tvLabelDate.setVisibility(View.GONE);
        }
        if (isChoose) {
            viewHolder.llCheck.setVisibility(View.VISIBLE);
            viewHolder.cbCheck.setChecked(item.isSelectFlag());
        } else {
            viewHolder.llCheck.setVisibility(View.GONE);
        }
        if (item.isExpandFlag()) {
            viewHolder.ivExtend.setImageResource(R.drawable.btn_video_expand);
            viewHolder.llVisibility.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivExtend.setImageResource(R.drawable.btn_video_packup);
            viewHolder.llVisibility.setVisibility(View.GONE);
        }
        //展开点击事件
        viewHolder.ivExtend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isChoose) {
                    if (prePosition != -1 && prePosition != position) {
                        if (datas.get(prePosition).isExpandFlag()) {
                            datas.get(prePosition).setExpandFlag(false);
                        }
                    }
                    datas.get(position).setExpandFlag(!datas.get(position).isExpandFlag());
                    //记录上次点击的position
                    prePosition = position;
                    notifyDataSetChanged();
                }
            }
        });
        //编辑点击事件
        viewHolder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoEditActivity.class);
                ArrayList<Integer> ids = new ArrayList<>();
                ids.add(datas.get(position).getId());
                intent.putIntegerArrayListExtra("ids", ids);
                context.startActivity(intent);
            }
        });
        //上传点击事件
        viewHolder.tvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpLoadVideoActivity.class);
                ArrayList<Integer> ids = new ArrayList<>();
                ids.add(datas.get(position).getId());
                intent.putIntegerArrayListExtra("ids", ids);
                //单个上传时需要通知这个页面拿到视频id
                intent.putExtra("single", true);
                context.startActivity(intent);
            }
        });
        //分享点击事件
        viewHolder.tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //删除点击事件
        viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog(context).builder()
                        .setTitle("确定删除此视频")
                        .setPositiveButton("删除", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (onItemDeleteListener != null) {
                                    onItemDeleteListener.onDelete(getItem(position).getId());
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
        return convertView;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    /**
     * 指定sectionIndex第一次出现时的position
     *
     * @param sectionIndex
     * @return
     */
    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String date = datas.get(i).getDate();
            String dateStr = date.replaceAll("-", "");
            Integer dateInt = Integer.valueOf(dateStr);
            if (dateInt == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 每个item对应的section值
     *
     * @param position
     * @return
     */
    @Override
    public int getSectionForPosition(int position) {
        String date = datas.get(position).getDate();
        String dateStr = date.replaceAll("-", "");
        return Integer.valueOf(dateStr);
    }

    public static class ViewHolder {
        @Bind(R.id.tv_label_date)
        TextView tvLabelDate;
        @Bind(R.id.cb_check)
        CheckBox cbCheck;
        @Bind(R.id.ll_check)
        LinearLayout llCheck;
        @Bind(R.id.iv_video_pic)
        ImageView ivVideoPic;
        @Bind(R.id.tv_duration)
        TextView tvDuration;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.iv_extend)
        ImageView ivExtend;
        @Bind(R.id.tv_edit)
        TextView tvEdit;
        @Bind(R.id.tv_upload)
        TextView tvUpload;
        @Bind(R.id.tv_share)
        TextView tvShare;
        @Bind(R.id.tv_delete)
        TextView tvDelete;
        @Bind(R.id.ll_visibility)
        LinearLayout llVisibility;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemDeleteListener {
        void onDelete(int video_id);
    }
}
