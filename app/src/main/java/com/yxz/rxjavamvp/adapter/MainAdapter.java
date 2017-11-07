package com.yxz.rxjavamvp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yxz.rxjavamvp.R;
import com.yxz.rxjavamvp.entity.MainItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yxz on 2017/10/25.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> implements View.OnClickListener{
    List<MainItem> list;
    private OnItemClickListener mOnItemClickListener = null;
    public MainAdapter(List list){
        this.list = list;
    }
    @Override
    public MainAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        view.setOnClickListener(this);

        return new MainAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainAdapter.MyViewHolder holder, int position) {
        holder.textView1.setText(list.get(position).getTitle());
        holder.textView2.setText(list.get(position).getDescription());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView1)
        TextView textView1;
        @BindView(R.id.textViwe2)
        TextView textView2;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            itemView.setOnClickListener();
        }
    }
    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }
}
