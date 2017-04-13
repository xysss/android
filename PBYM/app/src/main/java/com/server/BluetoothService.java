package com.server;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import com.activity.MainActivity;
import com.smart.smartApplication;
import com.util.BindingEquipmentDialog;
import com.util.BluetoothClientConnThread;
import com.util.BluetoothCommunThread;
import com.util.BluetoothTool;
import com.util.BluetoothTools;
import com.util.Tools;
import com.util.WaitEvent;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import java.util.List;


public class BluetoothService extends Service {

    private BluetoothTool mBluetooth;
    BluetoothDevice deviceTarget = null;
    public static BindingEquipmentDialog dialog;
    WaitEvent state=new WaitEvent();
    public static List<String> list =new ArrayList<String>();
    public static List<String> listBackup =new ArrayList<String>();
    private String ch="";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //控制信息广播的接收器
    private BroadcastReceiver controlReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothTools.ACTION_START_DISCOVERY.equals(action)) {
                //开始搜索
                Log.d("startDiscovery", "OK");
                mBluetooth.connectDevice();

            } else if (BluetoothTools.ACTION_STOP_SERVICE.equals(action)) {
                //停止后台服务
                if (mBluetooth.communThread != null) {
                    mBluetooth.communThread.isRun = false;
                }
                stopSelf();

            } else if (BluetoothTools.ACTION_DATA_TO_SERVICE.equals(action)) {
                //获取数据
                String data = intent.getStringExtra(BluetoothTools.DATA);

            }
        }
    };
    //蓝牙搜索广播的接收器
    private BroadcastReceiver discoveryReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //获取广播的Action
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {

                Log.d("ACTIONDISCOVERYSTARTED", "开始搜索");//开始搜索
                MainActivity.refreshState("开始搜索");
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //发现远程蓝牙设备
                //获取设备
                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //  discoveredDevices.add(bluetoothDevice);

                if(bluetoothDevice.getName().equals("HC-05")) //HC-05蓝牙设备号
                {
                    Tools.Log("搜寻到———命中———" + bluetoothDevice.getName());
                    MainActivity.refreshState("--目标命中--"+bluetoothDevice.getName());

                    deviceTarget=bluetoothDevice;
                    mBluetooth.disconnectDevice();//停止搜索
                    new BluetoothClientConnThread(handler, deviceTarget).start();

                }else{
                    Tools.Log("Name" + bluetoothDevice.getName() + "———未命中———");
                }

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //搜索结束
                if (deviceTarget==null) {
                    //若未找到设备，则发动未发现设备广播
                    Intent foundIntent = new Intent(BluetoothTools.ACTION_NOT_FOUND_SERVER);

                    MainActivity.refreshState("未搜索到设备");
                    sendBroadcast(foundIntent);
                }
            }
        }
    };
    //广播接收器
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothTools.ACTION_NOT_FOUND_SERVER.equals(action)) {
                //未发现设备
                Tools.Log("未发现设备,重新搜索");
                mBluetooth.connectDevice();

            } else if (BluetoothTools.ACTION_CONNECT_SUCCESS.equals(action)) {
                //连接成功

                MainActivity.refreshState("连接成功");
                dialog.cancel();

                MainActivity.bindingEquipmentEnable = true;
                MainActivity.refreshEquipment();                    //刷新绑定设备显示


            } else if (BluetoothTools.ACTION_DATA_TO_APP.equals(action)) {

                try{
                    //接收数据
                    String data=intent.getStringExtra("DATA").trim();

                    //   调试时应该在Activity上显示
                    //可选择进行数据库匹配，存取\
                    Tools.Log("接收到数据--" + data);


                    ////////////////////////////做了一次修改2016-9-7  if(data.equals(" ")){
                    data=data.trim();

                    if(data.length()>=1){
                        if(data.length()>1){
                            //处理接收的数据为a100 b200 c322 e154 f436 g34 之类的字符
                            String[] str1=data.split(" ");

                            for(int i=0;i<str1.length;i++){
                                list.add(str1[i]);
                            }

                        }else if(!data.equals("z")){
                            list.add(data);
                        }

                    }
                    if(data.equals("z")){
                        MainActivity.AA=-1;
                    }
                    if(list.size()<25&&MainActivity.XX.equals("1")&&!data.equals("z")){

                        if (WaitEvent.SUCCESS != state.waitSignal(300)){
                            mBluetooth.communThread.sendMessageHandle(MainActivity.XX);
                        }
                        Log.d("发送数据","List.size:"+String.valueOf(list.size()));
                        // Log.d("第几次发送",String.valueOf(MainActivity.AA));
                        //空格未处理，想在接收数据的地方，进行处理。明天见
                    }else{

                        for(int i=0;i<list.size();i++)
                        {
                            for(int j=list.size()-1;j>i;j--){
                                if(list.get(i).equals(list.get(j))){
                                    list.remove(j);
                                }
                            }
                        }
                        MainActivity.XX="3";
                        Log.d("进行remove操作","12312");
                    }

                    if (MainActivity.XX.equals("3")){
                        for(int i=0;i<MainActivity.AA;i++){
                            Log.d("第一次MainActivity","数数");
                        }

                        if(MainActivity.AA==list.size()){
                            Log.d("个数相同，数据比对", "asd");
                            for(int i=0;i<listBackup.size();i++){
                                int j=0;
                                for(;j<MainActivity.AA;j++){
                                    if(listBackup.get(i).equals(list.get(j))){
                                        break;
                                    }else if(j==MainActivity.AA-1){
                                        MainActivity.AA=-2;

                                    }
                                }
                            }
//        if(MainActivity.BB==-3&&list.size()==1){
//            list.clear();
//        }
                            if(MainActivity.AA==-2){
                                dropData();
                                Thread.sleep(2000);
                                for(int i=0;i<list.size();i++){
                                    //可进行入库存取
                                    Log.d("存取list",list.get(i) );
                                    //Id入库
                                    ch=ch+list.get(i)+"/";
                                }
                                postAction(ch);
                                ch="";
                                notificationavos("1"); //物品变更推送
                                MainActivity.AA=list.size();
                                listBackup.clear();
                                listBackup.addAll(list);
                                for(int i=0;i<MainActivity.AA;i++){
                                    Log.d("2个kankan","数数");
                                }
                                MainActivity.XX="1";
                                // ch="";
                                MainActivity.refreshState("初始态");

                            }
                            list.clear();
                            MainActivity.XX="1";
                        }else{
                            Log.d("冰箱经历开关门且数据变更", "");

                            Log.d("去删除数据库", "");
                            dropData();
                            Thread.sleep(2000);

                            for(int i=0;i<list.size();i++){
                                //可进行入库存取
                                Log.d("入库存取list",list.get(i) );
                                //Id入库
                                ch=ch+list.get(i)+"/";
                                Tools.Log(ch);
                            }
                            postAction(ch);
                            ch="";
                            MainActivity.refreshState("更新完成");
                            notificationavos("1"); //物品变更推送
                            MainActivity.refreshState("推送完成");

                            MainActivity.AA=list.size();
                            listBackup.clear();
                            listBackup.addAll(list);
                            for(int i=0;i<MainActivity.AA;i++){
                                Log.d("kankan","数数");
                            }
                            MainActivity.XX="1";
                            list.clear();
                            ch="";
                            MainActivity.refreshState("初始态");
                        }

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }else if (BluetoothTools.ACTION_CONNECT_DISCONNECT.equals(action)) {                       //蓝牙断开连接广播

                MainActivity.bluetooth.bluetoothAdapter.disable();    //断开蓝牙
                stopSelf();
            }
        }
    };


    private void postAction(final String Id) {
        // TODO Auto-generated method stub

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                String connectURL = MainActivity.URL+"test1.php";
                HttpPost httpResult = new HttpPost(connectURL);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("Id",Id));
                try{
                    UrlEncodedFormEntity entity;
                    entity = new UrlEncodedFormEntity(params,"utf-8");
                    httpResult.setEntity(entity);

                    HttpResponse httpResponse = new DefaultHttpClient().execute(httpResult);

                    // Message message = new Message();
                    if(httpResponse.getStatusLine().getStatusCode() == 200){
                        Log.d("数据库完成","OK--OK");
                    }
                    else {
                        Log.d("更新数据库失败", "OK--OK");
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void notificationavos(final String data){

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                String connectURL = MainActivity.URL + "notificationavos.php";
                HttpPost httpResult = new HttpPost(connectURL);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("Data",data));

                try{
                    UrlEncodedFormEntity entity;
                    entity = new UrlEncodedFormEntity(params,"utf-8");
                    httpResult.setEntity(entity);

                    HttpResponse httpResponse = new DefaultHttpClient().execute(httpResult);

                    Message message = new Message();
                    if(httpResponse.getStatusLine().getStatusCode() == 200){
                        //进行通知推送 1 变更 0 过期 -1 不足
                        Log.d("物品变更推送成功", "");
                        //  MainActivity.refreshState("物品变更推送成功"); 不能刷新UI线程
                    }
                    else {
                        Log.d("冰箱经历开关门", "");
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //这里用get进行URL的访问
    private void dropData() {
        // TODO Auto-generated method stub

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                String connectURL = MainActivity.URL+"dropData.php";
                HttpGet httpResult = new HttpGet(connectURL);

                try{

                    HttpResponse httpResponse = new DefaultHttpClient().execute(httpResult);

                    // Message message = new Message();
                    if(httpResponse.getStatusLine().getStatusCode() == 200){
                        Log.d("dropData完成","OK--OK");
                    }
                    else {
                        Log.d("dropData数据库失败", "OK--OK");
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }



    //接收其他线程消息的Handler
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case BluetoothTools.MESSAGE_CONNECT_SUCCESS:
                    //连接成功
                    //开启通讯线程
                    mBluetooth.communThread = new BluetoothCommunThread(handler, (BluetoothSocket)msg.obj);
                    mBluetooth.communThread.start();

                    //发送连接成功消息
                    Intent connSuccIntent = new Intent(BluetoothTools.ACTION_CONNECT_SUCCESS);
                    sendBroadcast(connSuccIntent);
                    break;

                case BluetoothTools.MESSAGE_CONNECT_ERROR:
                    //连接错误
                    //发送连接错误广播
                    Intent errorIntent = new Intent(BluetoothTools.ACTION_CONNECT_ERROR);
                    sendBroadcast(errorIntent);
                    break;

                case BluetoothTools.MESSAGE_READ_OBJECT:
                    //读取到数据
                    //发送数据广播（包含数据对象）
                    Intent dataIntent = new Intent(BluetoothTools.ACTION_DATA_TO_APP);
                    Bundle bundle = msg.getData();
                    String data = bundle.getString("foodId");
//					byte[] readBuf = (byte[]) msg.obj;//这是把缓冲区给了readBuf吗？
//					String data = new String(readBuf, 0,msg.arg1);

                    dataIntent.putExtra(BluetoothTools.DATA, data);
                    sendBroadcast(dataIntent);

                    break;
            }

            super.handleMessage(msg);
        }

    };


    @Override
    public void onCreate() {

        dialog =BindingEquipmentDialog.getInstance(smartApplication.getContext());

        mBluetooth=BluetoothTool.getInstance();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothTools.ACTION_NOT_FOUND_SERVER);
        intentFilter.addAction(BluetoothTools.ACTION_CONNECT_SUCCESS);
        intentFilter.addAction(BluetoothTools.ACTION_FOUND_DEVICE);
        intentFilter.addAction(BluetoothTools.ACTION_CONNECT_ERROR);
        intentFilter.addAction(BluetoothTools.ACTION_DATA_TO_APP);
        intentFilter.addAction(BluetoothTools.ACTION_CONNECT_DISCONNECT);

        IntentFilter discoveryFilter = new IntentFilter();
        discoveryFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        discoveryFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        discoveryFilter.addAction(BluetoothDevice.ACTION_FOUND);

        //controlReceiver的IntentFilter
        IntentFilter controlFilter = new IntentFilter();
        controlFilter.addAction(BluetoothTools.ACTION_START_DISCOVERY);
        controlFilter.addAction(BluetoothTools.ACTION_SELECTED_DEVICE);
        controlFilter.addAction(BluetoothTools.ACTION_STOP_SERVICE);
        controlFilter.addAction(BluetoothTools.ACTION_DATA_TO_SERVICE);

        registerReceiver(controlReceiver, controlFilter);
        registerReceiver(discoveryReceiver, discoveryFilter);
        registerReceiver(broadcastReceiver, intentFilter);

        super.onCreate();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(controlReceiver);
        unregisterReceiver(discoveryReceiver);
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}
