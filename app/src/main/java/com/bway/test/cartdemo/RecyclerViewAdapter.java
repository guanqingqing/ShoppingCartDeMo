package com.bway.test.cartdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Data.DataBean> list;
    private Context context;
    private final MainActivity ac;

    public RecyclerViewAdapter(List<Data.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
        ac = (MainActivity) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Data.DataBean dataBean = list.get(position);
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).cb.setChecked(dataBean.isChecked());
            Glide.with(context).load(dataBean.getImage_url()).into(((ViewHolder) holder).iv);
            ((ViewHolder) holder).tv.setText(dataBean.getTitle());

            ((ViewHolder) holder).cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //先判断是否选中
                    Data.DataBean currentDataBean = list.get(position);
                    boolean currentChecked = currentDataBean.isChecked();
                    currentDataBean.setChecked(!currentChecked);
                    notifyDataSetChanged();

                    if (((ViewHolder) holder).cb.isChecked()) {
                        //去判断其它按钮是否都选中
                        ac.setCb(isAllChecked());

                    } else {
                        //如果点击的按钮取消掉，即未选中状态，则去判断全选按钮是否是选中状态，如果是则取消选中
                        ac.setCb(false);
                    }
                }
            });
        }
    }

    private boolean isAllChecked() {
        for (int i = 0; i < list.size(); i++) {
            Data.DataBean dataBeans = list.get(i);
            if (!dataBeans.isChecked()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox cb;
        private ImageView iv;
        private TextView tv;

        public ViewHolder(View view) {
            super(view);
            cb = itemView.findViewById(R.id.checkbox);
            iv = itemView.findViewById(R.id.imageview);
            tv = itemView.findViewById(R.id.tv);
        }
    }

    public void selectedAll() {
        for (int i = 0; i < list.size(); i++) {
            Data.DataBean dataBean = list.get(i);
            dataBean.setChecked(true);
        }
        notifyDataSetChanged();
    }

    public void cancleAll() {
        for (int i = 0; i < list.size(); i++) {
            Data.DataBean dataBean = list.get(i);
            dataBean.setChecked(false);
        }
        notifyDataSetChanged();
    }
}
