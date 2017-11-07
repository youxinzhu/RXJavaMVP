package com.yxz.rxjavamvp.ui;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yxz.mylibrary.BaseRxActivity;
import com.yxz.mylibrary.OnBooleanListener;
import com.yxz.mylibrary.entity.HttpResult;
import com.yxz.mylibrary.entity.HttpResult2;
import com.yxz.mylibrary.utils.UriToPathUtil;
import com.yxz.mylibrary.utils.helper.MultipartBodyHelp;
import com.yxz.mylibrary.utils.helper.RxException;
import com.yxz.mylibrary.utils.helper.RxSchedulers;
import com.yxz.rxjavamvp.R;
import com.yxz.rxjavamvp.api.GankApi;
import com.yxz.rxjavamvp.entity.Gank;

import org.reactivestreams.Subscription;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.jar.Manifest;

import butterknife.BindView;
import io.reactivex.FlowableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;

public class UploadImgActivity extends BaseRxActivity {
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.imageView3)
    ImageView imageView1;
    @BindView(R.id.textView)
    TextView textView1;
    @BindView(R.id.textView2)
    TextView textView2;
    Uri imageUri;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_upload_img;
    }

    @Override
    protected void initView() {
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");//相片类型
                startActivityForResult(intent, 1);
            }
        });
    }
    private void getData(Uri uri){
        MediaType Image = MediaType.parse("image/jpeg; charset=utf-8");
        String[] paths = new String[]{UriToPathUtil.getImageAbsolutePath(this,uri)};
        MultipartBody body =  MultipartBodyHelp.filesToMultipartBody("file",paths,Image);
//        File file = url.getPath();
        addRxDestroy(GankApi.getInstance()
//                .service.region("http://192.168.100.60:8080/PartyAffairsService/dwt/api/region","wmfw_dwtapi_V2.21")
                .service.requestUploadWork("http://192.168.100.29:8080/PartyAffairsService/dwt/api/upload","wmfw_dwtapi_V2.21",body)
                .compose(RxSchedulers.<HttpResult2<String>>Flowableio_main())
                .map(new Function<HttpResult2<String>, String>() {
                    @Override
                    public String apply(@NonNull HttpResult2<String> stringHttpResult) throws Exception {
                        return stringHttpResult.getData();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String stringHttpResult) throws Exception {

                    }
                }, new RxException<Throwable>(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }))
        );




    }
    @Override
    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            try {
                /**
                 * 该uri是上一个Activity返回的
                 */
                final Uri uri = data.getData();
                permissionRequests(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, new OnBooleanListener() {
                    @Override
                    public void onClick(boolean bln) {
                        if (bln){
                            getData(uri);
                        }
                    }
                });

                Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                imageView1.setImageBitmap(bit);

//                bit.recycle();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("tag",e.getMessage());
                Toast.makeText(this,"程序崩溃",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Log.i("liang", "失败");
        }

    }

}
