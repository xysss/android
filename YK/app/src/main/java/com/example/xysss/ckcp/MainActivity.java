package com.example.xysss.ckcp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.xysss.ckcp.sixitem.Gqck;
import com.example.xysss.ckcp.bx_item.Ckwp;
import com.example.xysss.ckcp.item.Cxcp;
import com.example.xysss.ckcp.item.Tscp;
import com.example.xysss.ckcp.Second;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
//import com.iflytek.cloud.SpeechConstant;
//import com.iflytek.cloud.SpeechUtility;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {

    private BaiduASRDigitalDialog mDialog = null;
    private DialogRecognitionListener mDialogListener = null;
    private String API_KEY = "DspU1hDgD8OfXziAfK9Humto";
    private String SECRET_KEY = "0415ab3f7f11e779badd594c01f5f6f4";

    //界面加载
    private DrawerLayout mDrawerLayout;
    private List<Fruit> fruitList = new ArrayList<>();
    private FruitAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;   //下拉刷新

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //悬浮窗按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.show();
            }
        });


        //启动服务
        Intent startIntent = new Intent(this, MyService1.class);
        startService(startIntent); // 启动服务


        initFruits();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);  //第二个参数为列数
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary); //设置下拉进度条颜色
        //下拉监听器
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //去网络请求数据并更新
                refreshFruits();
            }
        });
        yy();
    }

    private void yy() {

        if (mDialog == null) {
            Bundle params = new Bundle();
            //设置API_KEY
            params.putString(BaiduASRDigitalDialog.PARAM_API_KEY, API_KEY);
            params.putString(BaiduASRDigitalDialog.PARAM_SECRET_KEY, SECRET_KEY);
            //设置百度对话框主题
            params.putInt(BaiduASRDigitalDialog.PARAM_DIALOG_THEME, BaiduASRDigitalDialog.THEME_BLUE_LIGHTBG);
            //实例化百度语音识别
            mDialog = new BaiduASRDigitalDialog(this, params);
            //设置百度语音回调接口
            mDialogListener = new DialogRecognitionListener() {
                @Override
                public void onResults(Bundle mResults) {
                    ArrayList<String> rs = mResults != null ? mResults.getStringArrayList(RESULTS_RECOGNITION) : null;
                    if (rs != null && rs.size() > 0) {
                        //text_input.setText(rs.get(0));

                        //Toast.makeText(MainActivity.this, rs.get(0), Toast.LENGTH_SHORT).show();

                        if (rs.get(0).contains("空调")) {
                            ((Myapp) getApplication()).setValue("1");
                        }
                        else if (rs.get(0).contains("冷风")) {
                            ((Myapp) getApplication()).setValue("2");
                        }
                        else if (rs.get(0).contains("风速")) {
                            ((Myapp) getApplication()).setValue("3");
                        }
                        else if (rs.get(0).contains("定时")) {
                            ((Myapp) getApplication()).setValue("4");
                        }
                        else if (rs.get(0).contains("分类")) {
                            ((Myapp) getApplication()).setValue("5");
                        }
                        else if (rs.get(0).contains("白峰")) {
                            ((Myapp) getApplication()).setValue("6");
                        }

                        else if (rs.get(0).contains("门")) {
                            ((Myapp) getApplication()).setValue("7");
                        }

                        else if (rs.get(0).contains("开灯")) {
                            ((Myapp) getApplication()).setValue("8");
                        }
                        else if (rs.get(0).contains("关灯")) {
                            ((Myapp) getApplication()).setValue("9");
                        }
                        else if (rs.get(0).contains("电视")) {
                            ((Myapp) getApplication()).setValue("a");
                        }
                        else if (rs.get(0).contains("频道加")) {
                            ((Myapp) getApplication()).setValue("b");
                        }
                        else if (rs.get(0).contains("频道减")) {
                            ((Myapp) getApplication()).setValue("c");
                        }
                        else if (rs.get(0).contains("音量加")) {
                            ((Myapp) getApplication()).setValue("d");
                        }
                        else if (rs.get(0).contains("音量减")) {
                            ((Myapp) getApplication()).setValue("e");
                        }

                        else {
                            Toast.makeText(MainActivity.this, "抱歉！未能理解您的意思", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            };
            mDialog.setDialogRecognitionListener(mDialogListener);
        }
    }


    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000); //让线程沉睡2秒钟
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() { //切换回主线程
                    @Override
                    public void run() {
                        initFruits();  //调用方法生成新数据
                        adapter.notifyDataSetChanged();  //通知数据发生变化
                        swipeRefresh.setRefreshing(false); //隐藏刷新进度条
                    }
                });
            }
        }).start();
    }

    private void initFruits() {
        fruitList.clear(); //清空fruitList中的数据
        for (int i = 0; i < 1; i++) {
            Fruit apple = new Fruit("灯", R.drawable.deng);
            fruitList.add(apple);
            Fruit banana = new Fruit("门", R.drawable.men1);
            fruitList.add(banana);
            Fruit orange = new Fruit("电视", R.drawable.dianshi1);
            fruitList.add(orange);
            Fruit pear = new Fruit("空调", R.drawable.kongtiao1);
            fruitList.add(pear);
            Fruit pineapple = new Fruit("手势", R.drawable.ss1);
            fruitList.add(pineapple);
            Fruit strawberry = new Fruit("其他", R.drawable.qd);
            fruitList.add(strawberry);
        }
    }
}

