package com.yxz.rxjavamvp.ui;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yxz.mylibrary.BaseRxActivity;
import com.yxz.mylibrary.entity.HttpResult;
import com.yxz.mylibrary.utils.LogUtils;
import com.yxz.mylibrary.utils.helper.RxException;
import com.yxz.mylibrary.utils.helper.RxSchedulers;
import com.yxz.rxjavamvp.R;
import com.yxz.rxjavamvp.adapter.GankAdapter;
import com.yxz.rxjavamvp.adapter.MyAdapter;
import com.yxz.rxjavamvp.adapter.decoration.MyItemDecoration;
import com.yxz.rxjavamvp.api.GankApi;
import com.yxz.rxjavamvp.entity.Gank;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class ListActivity extends BaseRxActivity {
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    MyAdapter mAdapter;
    List<Gank> list = new ArrayList<>();
    int page = 1;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    protected void initView() {
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        //RecyclerView滑动过程中不断请求layout的Request，不断调整item见的间隙，并且是在item尺寸显示前预处理，因此解决RecyclerView滑动到顶部时仍会出现移动问题
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MyItemDecoration(this));
        recyclerView.setPadding(0, 0, 0, 0);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                layoutManager.invalidateSpanAssignments();
            }
        });
        mAdapter = new MyAdapter(list,this);
        recyclerView.setAdapter(mAdapter);
//        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        //设置 Header 为 Material风格
        MaterialHeader refreshHeader = new MaterialHeader(this).setShowBezierWave(true)
                .setColorSchemeColors(R.color.colorPrimary,R.color.colorAccent,R.color.warning_stroke_color,R.color.colorPrimaryDark);
        refreshLayout.setRefreshHeader(refreshHeader);
        refreshLayout.setPrimaryColorsId(R.color.colorPrimary,R.color.colorAccent,R.color.warning_stroke_color,R.color.colorPrimaryDark);
//设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        page = 1;
        getData(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                getData(true);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page ++;
                getData(false);
            }
        });
    }
    //清除来自数据库缓存或者已有数据。
    private void getData(final boolean clean){
        addRxDestroy(GankApi.getInstance().service.getGankData("福利",page)
                .compose(RxSchedulers.<HttpResult<List<Gank>>>io_main())
                .compose(this.<List<Gank>>handleResult())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                })
                .doOnNext(new Consumer<List<Gank>>() {
                    @Override
                    public void accept(List<Gank> ganks) throws Exception {
                        if(!clean) saveData(ganks);//保存数据操作
                    }
                })
                .flatMap(new Function<List<Gank>, ObservableSource<Gank>>() {
                    @Override
                    public ObservableSource<Gank> apply(@NonNull List<Gank> ganks) throws Exception {
                        return Observable.fromIterable(ganks);
                    }
                })
                .toSortedList(new Comparator<Gank>() {
                    @Override
                    public int compare(Gank gank1, Gank gank2) {
                        return gank1.getPublishedAt().compareTo(gank1.getPublishedAt());
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                })
                .subscribe(new Consumer<List<Gank>>() {
                    @Override
                    public void accept(List<Gank> ganks) throws Exception {
                        LogUtils.d(ganks);
                        if(clean){
                            list.clear();
                            list.addAll(ganks);
                            mAdapter.notifyDataSetChanged();
                            refreshLayout.finishRefresh(true);
                        }else{
                            if(ganks.size()>0){
                                refreshLayout.finishLoadmore(true);
                                list.addAll(ganks);
                                mAdapter.notifyItemRangeInserted((page-1)*10,list.size());//ju
                            }else{
                                refreshLayout.finishLoadmore(false);
                                refreshLayout.setLoadmoreFinished(true);
                            }


                        }

                    }
                },new RxException<>(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        e.printStackTrace();
                        if(clean){
                            refreshLayout.finishRefresh(false);
                        }else{
                            refreshLayout.finishLoadmore(false);
                        }
                    }
                }))
        );
    }
    private void saveData(List<Gank> ganks) {
        //保存数据库
    }
}
