package com.example.xysss.ckcp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
//import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.xysss.ckcp.bx_item.Ckwp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Main2Activity extends AppCompatActivity {

//    private BufferedReader reader;
//    private PrintWriter writer;
//    private Socket socket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //sockett();

        //因为引用的包过多，实现多包问题
        // MultiDex.install(this);

        //动页面的进度条刷新
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent1 = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(intent1);
                Main2Activity.this.finish();
            }
        }, 3000);

    }
}


//    public  void sockett()
//    {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    socket=new Socket("192.168.43.164",7777);
//                    reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                    writer=new PrintWriter(socket.getOutputStream(),true);
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            while(true)
//                            {
//                                try
//                                {
//                                    String line = reader.readLine();
//                                    Log.d("111",line);
//                                    //writer.println("contnt");//发送数据
//                                    ((Myapp) getApplication()).setValue(line);//赋值操作
//
//                                    Intent intent=new Intent(Main2Activity.this, Ckwp.class);
//                                    //intent.putExtra("hh",bx);
//
//                                    PendingIntent pi=PendingIntent.getActivity(Main2Activity.this,0,intent,0);
//
//                                    NotificationManager manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//                                    Notification notification=new NotificationCompat.Builder(Main2Activity.this).setContentTitle("新通知来了")
//                                            .setContentText("冰箱物品有变更")
//                                            .setWhen(System.currentTimeMillis())
//                                            .setSmallIcon(R.mipmap.ic_launcher)
//                                            .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
//
//                                            .setContentIntent(pi) //点击事件
//                                            .setAutoCancel(true)  //取消通知
//                                            .setDefaults(android.support.v4.app.NotificationCompat.DEFAULT_ALL)   //默认效果
//                                            .setPriority(android.support.v4.app.NotificationCompat.PRIORITY_MAX)//优先级最高
//                                            .build();
//                                    manager.notify(1,notification);
//
//                                }
//                                catch(IOException ie){}
//                            }
//                        }
//                    }).start();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }

