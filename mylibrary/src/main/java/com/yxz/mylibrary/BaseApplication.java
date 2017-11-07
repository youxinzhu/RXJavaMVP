package com.yxz.mylibrary;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

/**
 * Created by yxz on 2017/10/19.
 */

public class BaseApplication extends Application {
    public static BaseApplication instance;

    public static Context getAppContext() {
        return instance;
    }

    public static Resources getAppResources() {
        return instance.getResources();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
