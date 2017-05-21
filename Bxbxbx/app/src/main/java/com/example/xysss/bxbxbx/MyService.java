package com.example.xysss.bxbxbx;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.UUID;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyService extends Service {

//    private TextView tv_msg;
//    private LinearLayout ly_device;
//    private Button bt_search, bt_send;
    private BluetoothSocket BTSocket;
    private BluetoothAdapter BTAdapter;
    private BluetoothDevice device;
    private StringBuilder sb;
    private BufferedReader reader;
    private PrintWriter writer;
    static private Socket socket = null;

    String s1="";

    public MyService() {
    }

    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyService", "onDestroy executed");

        //注销广播
        unregisterReceiver(BTReceive);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket=new Socket("192.168.43.164",7777);
                    reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    writer=new PrintWriter(socket.getOutputStream(),true);

//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            while (true)
//                            {
//                                try {
//                                    String line = reader.readLine();
//                                    Log.d("111",line);
//                                    ((Myapp) getApplication()).setValue(line);//赋值操作
//
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        sb = new StringBuilder();
        //show("客户端:检查BT");
        checkBT(this);
        //show("客户端:注册接收者");
        registerBTReceiver();

        //开始搜索蓝牙
        BTAdapter.startDiscovery();

    }

    /**
     * UI文本输出
     *
     * @param msg
     */
//    public void show(String msg) {
//        sb.append(msg + "\n");
//        tv_msg.setText(sb.toString());
//    }

    /**
     * 检查蓝牙
     */
    public void checkBT(Context context) {
        BTAdapter = BluetoothAdapter.getDefaultAdapter();
        if (BTAdapter != null) {
            if (!BTAdapter.isEnabled()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                // 设置蓝牙可见性，最多300秒
                intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);  //使得蓝牙处于可发现模式
                context.startActivity(intent);
            }
        } else {
            Log.d("Myservice","本地设备驱动异常!");
        }
    }

    /**
     * 注册广播
     */
    public void registerBTReceiver() {
        // 设置广播信息过滤
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);  //当你匹配到附近蓝牙设备时发送
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);  //当你开始搜索附近蓝牙设备时发送
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);  //当你结束搜索附近蓝牙设备时发送
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);  //当你蓝牙开启或者关闭的时候发送
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);  //当你蓝牙设备匹配状态发生变化时发送
        // 注册广播接收器，接收并处理搜索结果
        registerReceiver(BTReceive, intentFilter);
    }

    /**
     * 广播接收者
     */
    private BroadcastReceiver BTReceive = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //打印Action，调试使用
            //show(action);
            //找到设备通知  ACTION_FOUND,设备已配对通知  ACTION_BOND_STATE_CHANGED
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.d("MyService", device.getName());

                if(device.getName().equals("HC-05")) //HC-05蓝牙设备号
                {
                    Log.d("搜寻到———命中———",device.getName());
                    bondBT(device.getName());
                }

                //show("客户端:找到的BT名:" + device.getName());
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                // 获取蓝牙设备的连接状态
                int connectState = device.getBondState();
                // 已配对
                if (connectState == BluetoothDevice.BOND_BONDED) {
                    try {
                        Log.d("客户端","开始连接:");
                        clientThread clientConnectThread = new clientThread();
                        clientConnectThread.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                Log.d("没找到","HC-05");
            }
        }
    };

    /**
     * 绑定蓝牙
     *
     * @param deviceName
     */
    private void bondBT(String deviceName) {
        if (device.getName().equalsIgnoreCase(deviceName)) {
            Log.d("客户端","配对蓝牙开始");
            // 搜索蓝牙设备的过程占用资源比较多，一旦找到需要连接的设备后需要及时关闭搜索
            BTAdapter.cancelDiscovery();
            // 获取蓝牙设备的连接状态
            int connectState = device.getBondState();

            switch (connectState) {
                // 未配对
                case BluetoothDevice.BOND_NONE:
                    //show("客户端:开始配对");
                    try {
                        Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
                        createBondMethod.invoke(device);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                // 已配对
                case BluetoothDevice.BOND_BONDED:
                    try {
                        Log.d("客户端","开始连接:");

                        //xianshi.setText("连接成功");
                        //MainActivity.refreshEquipment();    //刷新绑定设备显示

                        clientThread clientConnectThread = new clientThread();

                        clientConnectThread.start();

                        //发送数据
                        //sendMessage();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }


    /**
     * 开启客户端
     */
    private class clientThread extends Thread {
        public void run() {
            try {
                //创建一个Socket连接：只需要服务器在注册时的UUID号
                //此处必须使用Android的SSP（协议栈默认）的UUID：
                BTSocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                //连接
                //Log.d("客户端","开始连接:");
                BTSocket.connect();
                Log.d("客户端","连接成功");
                //启动接受数据
                Log.d("客户端","启动接受数据");


                readThread mreadThread = new readThread();
                mreadThread.start();
            } catch (IOException e) {
                Log.d("客户端3",":连接服务端异常！断开连接重新试一试");
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取数据
     */
    private class readThread extends Thread {
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;
            InputStream is = null;
            try {
                is = BTSocket.getInputStream();
                Log.d("客户端:","获得输入流");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            while (true) {
                try {
                    if ((bytes = is.read(buffer)) > 0) {
                        byte[] buf_data = new byte[bytes];
                        for (int i = 0; i < bytes; i++) {
                            buf_data[i] = buffer[i];
                        }
                        String s = new String(buf_data);

                        if (s.equals("Y")||s.equals(" Y")||s.equals("Y ")||s.equals(" Y "))
                        {
                            ((Myapp) getApplication()).setValue(s1);
                            writer.println(s1);
                            writer.flush();
                            s1="";
                        }
                        else
                        s1+=s;
                        deleteDatabase(s);
                        Log.d("客户端:读取数据了",s);
                    }
                } catch (IOException e) {
                    try {
                        is.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    //post请求

    /**
     * 发送数据
     */
    public void sendMessage() {
        if (BTSocket == null) {
            Toast.makeText(this, "没有连接", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            OutputStream os = BTSocket.getOutputStream();
            os.write("1".getBytes());
            os.flush();
            Log.d("客户端:发送信息成功","11");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

