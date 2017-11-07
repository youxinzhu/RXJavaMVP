package com.yxz.mylibrary;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.yxz.mylibrary.utils.ToastUtils;

import butterknife.ButterKnife;

/**
 * Created by yxz on 2017/10/19.
 */

public abstract class BaseCoreActivity extends AppCompatActivity{
    private OnBooleanListener onPermissionListener;
    /**
     * 权限请求
     * @param permission Manifest.permission.CAMERA
     * @param onBooleanListener 权限请求结果回调，true-通过  false-拒绝
     */
    public void permissionRequests(String permission, OnBooleanListener onBooleanListener){
        onPermissionListener=onBooleanListener;
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                //权限已有
                onPermissionListener.onClick(true);
            } else {
                //没有权限，申请一下
                ActivityCompat.requestPermissions(this,
                        new String[]{permission},
                        1);
            }
        }else{
            onPermissionListener.onClick(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限通过
                if(onPermissionListener!=null){
                    onPermissionListener.onClick(true);
                }
            } else {
                //权限拒绝
                if(onPermissionListener!=null){
                    onPermissionListener.onClick(false);
                }
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.getLayoutId());
        ButterKnife.bind(this);
        initView();
        ActivityManager.getInstance().pushActivity(this);
    }
    protected abstract int getLayoutId();

    protected abstract void initView();

    @Override
    protected void onDestroy() {
//        ActivityManager.getInstance().popActivity(this);
        super.onDestroy();
    }
}
