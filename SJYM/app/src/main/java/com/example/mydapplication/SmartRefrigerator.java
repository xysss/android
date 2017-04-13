package com.example.mydapplication;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;

/**
 * Created by 44265 on 2016/7/18.
 */
public class SmartRefrigerator extends Application {
    private static SmartRefrigerator instance;
    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this,"LxABwaznYafrNh0pWKpnIKFp-gzGzoHsz","viwsmh3QmzIOWsBcQx6DQzFM");
        AVAnalytics.enableCrashReport(this.getApplicationContext(), true);
        AVOSCloud.setLastModifyEnabled(true);
        AVOSCloud.setDebugLogEnabled(true);
        //initial();
    }

    @Override
    protected void attachBaseContext(Context base){
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
//    public synchronized static SmartRefrigerator getInstance(){
//        if (null == instance) {
//            instance = new SmartRefrigerator();
//        }
//        return instance;
//    }
}
