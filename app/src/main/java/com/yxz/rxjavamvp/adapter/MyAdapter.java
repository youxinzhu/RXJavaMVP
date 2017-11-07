package com.yxz.rxjavamvp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yxz.mylibrary.utils.LogUtils;
import com.yxz.mylibrary.utils.ScreenUtils;
import com.yxz.rxjavamvp.MyAplication;
import com.yxz.rxjavamvp.R;
import com.yxz.rxjavamvp.entity.Gank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yxz on 2017/10/20.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private List<Gank> mList;
    Map<Integer,Integer> imageHeightMap;
    Context context;
    int itemWidth;
    public MyAdapter(List<Gank> dataModels,Context context) {
        if (dataModels == null) {
            throw new IllegalArgumentException("DataModel must not be null");
        }
        mList = dataModels;
        imageHeightMap = new HashMap<>();
        itemWidth = ScreenUtils.getScreenWidth()/2;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img,parent,false);
        return new MyAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (!this.imageHeightMap.containsKey(position)){
                //当首次加载图片时，调用 loadImageFirst()，保存图片高度
            loadImageFirst(holder.imageView,position);
        }else{
            ViewGroup.LayoutParams lp = holder.imageView.getLayoutParams();
            lp.width=itemWidth;
            lp.height=imageHeightMap.get(position);
            holder.imageView.setLayoutParams(lp);
            Glide.with(context).load(mList.get(position).getUrl())
                    .into(holder.imageView);

        }
    }
    private void loadImageFirst(final View view, final int position){

        Glide.with(context).load(mList.get(position).getUrl())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        float scaleType =  (float) resource.getIntrinsicHeight()/(float)resource.getIntrinsicWidth();
                        int imageHeight = (int) (itemWidth * scaleType);
                        imageHeightMap.put(position,imageHeight);
                        ViewGroup.LayoutParams lp = view.getLayoutParams();
                        lp.width=itemWidth;
                        lp.height=imageHeight;
                        view.setLayoutParams(lp);
                        //resource就是加载成功后的图片资源
                        ((ImageView)view).setImageDrawable(resource);
                    }
                });

    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
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
