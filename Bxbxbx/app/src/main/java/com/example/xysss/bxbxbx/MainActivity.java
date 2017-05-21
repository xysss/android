package com.example.xysss.bxbxbx;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private MyService.DownloadBinder downloadBinder;
    private static TextView xianshi;



    private ServiceConnection connection = new ServiceConnection() {
        //解绑
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        //绑定服务
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (MyService.DownloadBinder) service;
            //downloadBinder.sendMessage();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        RelativeLayout bindlj = (RelativeLayout) findViewById(R.id.binding_lj);
//        RelativeLayout binding_equipment = (RelativeLayout) findViewById(R.id.binding_bd);
//        RelativeLayout chakansw = (RelativeLayout) findViewById(R.id.chakansw);

        xianshi = (TextView) findViewById(R.id.xianshi);
        Button bd=(Button)findViewById(R.id.bangding);
        Button ck=(Button)findViewById(R.id.chakan);

        bd.setOnClickListener(this);  //绑定设备点击事件
        ck.setOnClickListener(this);    //查看物品点击事件

        Intent startIntent = new Intent(this, MyService.class);
        startService(startIntent); // 启动服务
        bindService(startIntent, connection, BIND_AUTO_CREATE); // 绑定服务

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection); // 解绑服务  防止内存泄漏
    }

    public static void refreshEquipment(){        //刷新设备连接显示
        xianshi.setText("连接成功");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //case R.id.binding_state://连接状态

            case R.id.chakan:
                //查看物品
                Intent ckwp = new Intent(MainActivity.this, Ckwp.class);
                startActivity(ckwp);
            case R.id.bangding:
                Toast.makeText(MainActivity.this,"绑定成功",Toast.LENGTH_SHORT).show();
            default:
                break;
        }
    }
}
