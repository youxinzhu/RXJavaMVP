package com.yxz.rxjavamvp.api;

import com.yxz.mylibrary.entity.HttpResult;
import com.yxz.mylibrary.entity.HttpResult2;
import com.yxz.rxjavamvp.entity.Gank;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by yxz on 2017/10/19.
 */

public interface GankApiService {

    @GET("data/福利/10/{page}")
    Flowable<HttpResult<List<Gank>>> getMeizhiData(
            @Path("page") int page);

    @GET("random/data/福利/10")
    Flowable<HttpResult<List<Gank>>> getRandMeizhiData();

    @GET("data/{gank}/10/{page}")
    Observable<HttpResult<List<Gank>>> getGankData(@Path("gank") String gank,
                                                   @Path("page") int page);

    @GET("search/query/{query}/category/{category}/count/10/page/{page}")
    Flowable<HttpResult<List<Gank>>> getSearch(@Path("query") String query, @Path("category") String category,
                                               @Path("page") int page);

    @GET("data/福利/10/{page}")
    Flowable<HttpResult<List<Gank>>> getMeizhiList(@Path("page") int page);


    @POST
    Flowable<HttpResult2<String>> requestUploadWork(@Url String url,  @Query("token")String token ,@Body MultipartBody body);

    @Multipart
    @POST
    Flowable<HttpResult2<String>> requestUploadWork(@Url String url,@PartMap Map<String, RequestBody> params,
                                                   @Part List<MultipartBody.Part> partts);
    @GET
    Flowable<HttpResult2<String>> region(@Url String url, @Query("token")String token);
}
