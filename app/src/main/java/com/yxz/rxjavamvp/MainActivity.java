package com.yxz.rxjavamvp;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.yxz.mylibrary.BaseRxActivity;
import com.yxz.mylibrary.entity.HttpResult;
import com.yxz.mylibrary.utils.LogUtils;
import com.yxz.mylibrary.utils.helper.RxException;
import com.yxz.mylibrary.utils.helper.RxSchedulers;
import com.yxz.rxjavamvp.adapter.MainAdapter;
import com.yxz.rxjavamvp.adapter.MyAdapter;
import com.yxz.rxjavamvp.adapter.decoration.MainItemDecoration;
import com.yxz.rxjavamvp.adapter.decoration.MyItemDecoration;
import com.yxz.rxjavamvp.api.GankApi;
import com.yxz.rxjavamvp.entity.Gank;
import com.yxz.rxjavamvp.entity.MainItem;
import com.yxz.rxjavamvp.ui.ListActivity;
import com.yxz.rxjavamvp.ui.UploadImgActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class MainActivity extends BaseRxActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.test_recycler_view)
    RecyclerView recyclerView;
    MainAdapter mAdaoter;
    List<MainItem> listData = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        recyclerView.setHasFixedSize(true); // 设置固定大小
        initRecyclerLayoutManager(recyclerView); // 初始化布局
        initRecyclerAdapter(recyclerView); // 初始化适配器
        initItemDecoration(recyclerView); // 初始化装饰
        initItemAnimator(recyclerView); // 初始化动画效果
//        getData();
    }
    private void initRecyclerLayoutManager(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void initRecyclerAdapter(RecyclerView recyclerView) {
        listData.add(new MainItem("瀑布流图片布局","通过glide获取图片宽高比计算item高度。实际开发中最好通过接口获取宽高比"));
        listData.add(new MainItem("上传图片","ceshi上传图片"));
        mAdaoter = new MainAdapter(listData);
        recyclerView.setAdapter(mAdaoter);
        mAdaoter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
              switch (position){
                  case 0:
                      Intent intent = new Intent(MainActivity.this, ListActivity.class);
                      startActivity(intent);
                      break;
                  case 1:
                      Intent intent1 = new Intent(MainActivity.this, UploadImgActivity.class);
                      startActivity(intent1);
                      break;

              }
            }
        });
    }
    private void initItemDecoration(RecyclerView recyclerView) {
        recyclerView.addItemDecoration(new MainItemDecoration(this));
    }
    private void initItemAnimator(RecyclerView recyclerView) {
        recyclerView.setItemAnimator(new DefaultItemAnimator()); // 默认动画
    }
    @OnClick(R.id.fab)
    public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("", null).show();
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
//    private void getData(){
//        addRxDestroy(GankApi.getInstance().service.getGankData("福利",1)
//                        .compose(RxSchedulers.<HttpResult<List<Gank>>>io_main())
//                        .compose(this.<List<Gank>>handleResult())
//                        .subscribe(new Consumer<List<Gank>>() {
//                            @Override
//                            public void accept(List<Gank> ganks) throws Exception {
//                                LogUtils.d(ganks);
//                                listData.addAll(ganks);
//                                mAdaoter.notifyDataSetChanged();
//                            }
//                        },new RxException<>(new Consumer<Throwable>() {
//                            @Override
//                            public void accept(Throwable e) throws Exception {
//                                e.printStackTrace();
//                            }
//                        }))
//        );
//    }
}
