package com.glorystudent.adapter;

import android.content.Context;
import android.widget.TextView;

import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflife.R;

import java.util.Map;

/**
 * Created by Gavin.J on 2017/5/17.
 */

public class TransactionListAdapter extends AbsBaseAdapter<Map<String, Object>> {
    public TransactionListAdapter(Context context) {
        super(context, R.layout.item_money_transaction_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, Map<String, Object> data) {
        viewHolder.setTextView(R.id.tv_money_name, (String) data.get("name"));
        viewHolder.setTextView(R.id.tv_money_date, (String) data.get("time"));
        if ((Integer) data.get("state") == 1) {
            viewHolder.setTextView(R.id.tv_money_amount, "+" + data.get("money"));
            ((TextView) viewHolder.getView(R.id.tv_money_amount)).setTextColor(context.getResources().getColor(R.color.colorOrange));
        } else {
            viewHolder.setTextView(R.id.tv_money_amount, "-" + data.get("money"));
            ((TextView) viewHolder.getView(R.id.tv_money_amount)).setTextColor(context.getResources().getColor(R.color.colorBlack));
        }
    }
}
