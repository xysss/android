package com.smart;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.SaveCallback;
import com.easemob.chat.EMChat;

public class smartApplication extends Application{
    private static Context context;
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        EMChat.getInstance().init(this);
        AVOSCloud.initialize(this, "Nzr77Q3FqIkDqJqH0dYmfRbs-gzGzoHsz", "3V1iyVUcfYWNdDz4L2gjDL7c");
        AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            public void done(AVException e) {
                if (e == null) {
                    // 保存成功
                    Log.d("ID", "保存成功" + AVInstallation.getCurrentInstallation().getInstallationId());
                } else {
                    // 保存失败，输出错误信息
                }
            }
        });
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
