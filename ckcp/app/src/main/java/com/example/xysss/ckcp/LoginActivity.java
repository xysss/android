package com.example.xysss.ckcp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xysss.ckcp.bx_item.Ckwp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginActivity extends AppCompatActivity {

    private EditText accountEdit;

    private EditText passwordEdit;

    private Button login;

//    private  BufferedReader reader;
//    private  PrintWriter writer;
//    private  Socket socket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //sockett();

        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                // 如果账号是admin且密码是123456，就认为登录成功
                if (account.equals("admin") && password.equals("123456")) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "密码或账号不正确",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    public  void sockett()
//    {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    socket=new Socket("192.168.1.111",7777);
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
//                                    Intent intent=new Intent(LoginActivity.this, Ckwp.class);
//                                    //intent.putExtra("hh",bx);
//
//                                    PendingIntent pi=PendingIntent.getActivity(LoginActivity.this,0,intent,0);
//
//                                    NotificationManager manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//                                    Notification notification=new NotificationCompat.Builder(LoginActivity.this).setContentTitle("新通知来了")
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
}
