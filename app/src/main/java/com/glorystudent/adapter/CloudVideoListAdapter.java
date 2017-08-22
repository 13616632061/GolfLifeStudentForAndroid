package com.glorystudent.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.glorystudent.entity.CloudVideoEntity;
import com.glorystudent.golflibrary.dialog.iosdialog.AlertDialog;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflife.CloudVideoEditActivity;
import com.glorystudent.golflife.DownLoadVideoActivity;
import com.glorystudent.golflife.R;
import com.glorystudent.util.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gavin.J on 2017/6/20.
 */

public class CloudVideoListAdapter extends BaseAdapter implements SectionIndexer {
    private Context context;
    private List<CloudVideoEntity.ListvideosBean> datas;
    private boolean isChoose;
    private OnItemDeleteListener onItemDeleteListener;
    private int prePosition = -1;//记录上次点击的位置

    public CloudVideoListAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
    }

    public void setOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
        this.onItemDeleteListener = onItemDeleteListener;
    }

    public void setDatas(List<CloudVideoEntity.ListvideosBean> datas) {
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
    public CloudVideoEntity.ListvideosBean getItem(int position) {
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
            convertView = View.inflate(context, R.layout.item_cloud_video_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //赋值
        CloudVideoEntity.ListvideosBean item = getItem(position);
        viewHolder.tvTitle.setText(item.getVideo_tag());
        if (item.getVideo_tips() != null) {
            viewHolder.tvContent.setText(Constants.LABEL[Integer.valueOf(item.getVideo_tips()) - 1]);
        }
        GlideUtil.loadImageView(context, item.getVideo_picpath(), viewHolder.ivVideoPic);

        int section = getSectionForPosition(position);
        if (position == getPositionForSection(section)) {
            viewHolder.tvLabelDate.setVisibility(View.VISIBLE);
            viewHolder.tvLabelDate.setText(formatStandardTime(item.getVideo_datetime()));
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
        final ViewHolder finalViewHolder = viewHolder;
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
                Intent intent = new Intent(context, CloudVideoEditActivity.class);
                ArrayList<CloudVideoEntity.ListvideosBean> selectedDatas = new ArrayList<>();
                selectedDatas.add(datas.get(position));
                intent.putExtra("selectedDatas", selectedDatas);
                context.startActivity(intent);
            }
        });
        //下载点击事件
        viewHolder.tvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DownLoadVideoActivity.class);
                ArrayList<CloudVideoEntity.ListvideosBean> listVideos = new ArrayList<>();
                listVideos.add(datas.get(position));
                intent.putExtra("listVideos", listVideos);
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
                                    onItemDeleteListener.onDelete(datas.get(position).getVideo_id());
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
        return convertView;
    }

    /**
     * 转换2017-01-20T00:00:00为2017-01-20
     */
    private String formatStandardTime(String dateTime) {
        String[] dt = dateTime.split("T");
        return dt[0];
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
            String date = datas.get(i).getVideo_datetime();
            String s = formatStandardTime(date);
            String dateStr = s.replaceAll("-", "");
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
        String date = datas.get(position).getVideo_datetime();
        String s = formatStandardTime(date);
        String dateStr = s.replaceAll("-", "");
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
        @Bind(R.id.tv_download)
        TextView tvDownload;
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
