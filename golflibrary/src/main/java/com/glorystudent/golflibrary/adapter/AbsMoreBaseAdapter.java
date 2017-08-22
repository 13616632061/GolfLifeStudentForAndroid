package com.glorystudent.golflibrary.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.glorystudent.golflibrary.util.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyj on 2016/9/27 0027.
 */
public abstract class AbsMoreBaseAdapter<T extends AbsMoreBaseAdapter.OnType> extends BaseAdapter {
    protected Context context;
    protected List<T> datas;
    private int[] resid;//如果布局返回类型为0，则会采用resid[0]所对应的布局

    public AbsMoreBaseAdapter(Context context, int... resid) {
        this.resid = resid;
        this.context = context;
        this.datas = new ArrayList<>();
    }
    public void setDatas(List<T> datas){
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    public void addDatas(List<T> datas){
        this.datas.addAll(datas);
        this.notifyDataSetChanged();
    }

    public void addOneData(T datas){
        this.datas.add(datas);
        this.notifyDataSetChanged();
    }

    public T getData(int position) {
        return datas.get(position);
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView != null){
            viewHolder = (ViewHolder) convertView.getTag();
        }else{
            convertView = LayoutInflater.from(context).inflate(resid[getItemViewType(position)],null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        bindDatas(viewHolder, datas.get(position), getItemViewType(position));
        return convertView;
    }
    public abstract void bindDatas(ViewHolder viewHolder, T datas, int typeView);
    public class ViewHolder{
        
        SparseArray<View> sparseArray = new SparseArray<>();
        View layoutView;
        public ViewHolder(View layoutView){
            this.layoutView = layoutView;
        }

        public View getView(int id){
            View view = sparseArray.get(id);
            if(view == null){
                view = layoutView.findViewById(id);
                sparseArray.put(id, view);
            }
            return view;
        }
        public ViewHolder bindTextView(int id, String value){
            TextView textView = (TextView) this.getView(id);
            textView.setText(value);
            return this;
        }
        public ViewHolder bindImageView(int id, String url, int defualid){
            ImageView iv = (ImageView) this.getView(id);
//            Glide.with(context).load(url)
//                                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                .placeholder(defualid)
//                                .into(iv);
            GlideUtil.loadImageView(context, url, iv);
            return this;
        }
    }

    @Override
    public int getItemViewType(int position) {
        OnType onType = datas.get(position);
        return onType.getType();
    }

    @Override
    public int getViewTypeCount() {
        return resid.length;
    }

    public interface OnType{
        int getType();
    }
    public void clear(){
        datas.clear();
        this.notifyDataSetChanged();
    }
}
