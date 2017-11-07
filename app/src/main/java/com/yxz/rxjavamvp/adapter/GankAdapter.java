package com.yxz.rxjavamvp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yxz.mylibrary.utils.ScreenUtils;
import com.yxz.rxjavamvp.R;
import com.yxz.rxjavamvp.entity.Gank;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yxz on 2017/10/24.
 */

public class GankAdapter extends RecyclerView.Adapter<GankAdapter.MyViewHolder>{
    private List<Gank> mList;
    Context context;
    int itemWidth;
    public GankAdapter(List list, Context context){
        mList = list;
        this.context = context;
        itemWidth = ScreenUtils.getScreenWidth();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img,parent,false);
        return new GankAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Glide.with(context).load(mList.get(position).getUrl())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        float scaleType =  (float) resource.getIntrinsicHeight()/(float)resource.getIntrinsicWidth();
                        int imageHeight = (int) (itemWidth * scaleType);
                        ViewGroup.LayoutParams lp = holder.imageView.getLayoutParams();
                        lp.width=itemWidth;
                        lp.height=imageHeight;
                        holder.imageView.setLayoutParams(lp);
                        //resource就是加载成功后的图片资源
                        holder.imageView.setImageDrawable(resource);
                    }
                });
//        Glide.with(context)
//                .load(mList.get(position).getUrl())
//                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
