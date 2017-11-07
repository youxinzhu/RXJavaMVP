package com.yxz.mylibrary.entity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by yxz on 2017/10/19.
 */

public abstract class BaseListEntity<T> extends BaseEntity.BaseBean implements BaseEntity.IListBean{
    @Override
    public void setParam(Map<String, String> param) {
        this.param=param;
    }

    @Override
    public abstract Observable<HttpResult<List<T>>> getPage(int page);
}
