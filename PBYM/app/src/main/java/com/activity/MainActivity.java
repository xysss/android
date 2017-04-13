package com.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.PushService;
import com.easemob.chat.EMChatManager;
import com.server.BluetoothService;
import com.smart.R;
import com.smart.smartApplication;
import com.util.BindingEquipmentDialog;
import com.util.BluetoothTool;
import com.util.BluetoothTools;
import com.util.Tools;


public class MainActivity extends FragmentActivity implements View.OnClickListener, DialogInterface.OnDismissListener, DialogInterface.OnCancelListener {


    public static boolean databaseHave = false;

    private  SharedPreferences pref;
    private SharedPreferences.Editor editor;


    public static String URL = "http://123.206.54.102/";
    public static String XX="1";  //控制与硬件的通信字符为 1，后面的代码中将其设置为3。即不在与硬件进行通信
    public static String sss="z"; //置空
    public static int AA=-1;//冰箱开关门物品变更的判断
    public static int BB=-1;//冰箱开关门物品变更的判断

    private RelativeLayout bindingEquipmentLayout;                   //绑定设备按钮
    private RelativeLayout guanchawuping;
    private static TextView bindingEquipmentTextView;                 //绑定设备||解除设备
    private static TextView mbinding_state;
    public static boolean foundEquipment = false;                     //是否找到设备
    public static BindingEquipmentDialog dialog;               //绑定设备Dialog提示框

    public static BluetoothTool bluetooth;                    //蓝牙对象

    public static Boolean bindingEquipmentEnable = false;           //是否绑定设备
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_fragment);

        pref = getSharedPreferences("UserState", MODE_PRIVATE);

        PushService.subscribe(this, "public", MainActivity.class);
        AVInstallation.getCurrentInstallation().saveInBackground();
       // PushService.unsubscribe(context, "protected");
        //退订之后需要重新保存 Installation

        initView();
        initEvent();


    }

    /**
     * 初始化设置
     */
    private void initView() {
        bindingEquipmentLayout = (RelativeLayout) findViewById(R.id.binding_equipment_widget);
        bindingEquipmentTextView = (TextView) findViewById(R.id.binding_equipment_textView_widget);
        mbinding_state = (TextView) findViewById(R.id.binding_state);
        guanchawuping= (RelativeLayout) findViewById(R.id.chakansxt);
    }

    /**
     * 事件监听
     */
    private void initEvent() {

        bindingEquipmentLayout.setOnClickListener(this);
        guanchawuping.setOnClickListener(this);
    }



    public static void refreshEquipment(){        //刷新设备连接显示

          if(MainActivity.bindingEquipmentEnable){
                        bindingEquipmentTextView.setText("解除设备");
                    }else{
                        bindingEquipmentTextView.setText("绑定设备");
                    }

    }

    public static void refreshState(String arg){        //刷新冰箱状态显示显示

        mbinding_state.setText(arg);
    }

    /**
     * 判断哪个要显示，及设置按钮图片
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.binding_equipment_widget:
                if (!MainActivity.bindingEquipmentEnable) {
                    if (bluetooth == null) {
                        //获取蓝牙实例
                        bluetooth = BluetoothTool.getInstance();
                        //判断本设备是否支持蓝牙ble，并连接本地蓝牙设备
                    }
                    dialog = BindingEquipmentDialog.getInstance(this);          //显示提示框
                    dialog.setOnDismissListener(this);                //监听Dialog是否被手动关闭
                    dialog.setOnCancelListener(this);
                    dialog.show();

                    //本地蓝牙打开
                    if (bluetooth != null) {
                        Intent startService = new Intent(smartApplication.getContext(), BluetoothService.class);
                        startService(startService);

                        bluetooth.connectDevice();
                    }
                } else {

                    MainActivity.bindingEquipmentEnable = false;

                    Intent connectDeviceIntent = new Intent(BluetoothTools.ACTION_CONNECT_DISCONNECT); //          //发送断开蓝牙广播
                    sendBroadcast(connectDeviceIntent);
                }
                break;
            case R.id.chakansxt:
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.example.administrator.test");
                startActivity(intent);
                break;
        }
    }
    @Override
    public void onDismiss(DialogInterface dialog) {
        //如果用户将对话框关闭，则看情况关闭进行的程序
        if(!foundEquipment){
            bluetooth.disconnectDevice();	//关闭蓝牙扫描
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if(!foundEquipment){
            bluetooth.disconnectDevice();	//关闭蓝牙扫描
        }
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                isExit = false;
            }

        }
    };
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void exit() {
        if (!isExit) {
            isExit = true;
            Tools.showToast("再按一次退出程序");
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(1, 2000);
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Tools.Log("Main销毁");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Tools.Log("Main开始");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Tools.Log("Main睡眠");
    }
}
