package com.example.xysss.im;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private List<Msg> msgList = new ArrayList<Msg>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    private BufferedReader reader;
    private PrintWriter writer;
    static private Socket socket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMsgs(); // 初始化消息数据
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        send.setOnClickListener(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket=new Socket("123.206.49.137",7777);
                    reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    writer=new PrintWriter(socket.getOutputStream(),true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while(true)
                            {
                                try
                                {
                                    String line = reader.readLine()+ '\n';
                                    show(line, Msg.TYPE_RECEIVED);
                                }
                                catch(IOException ie){}
                            }
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initMsgs() {
        Msg msg1 = new Msg("Hello guy.", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("Hello. Who is that?", Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3 = new Msg("This is Tom. Nice talking to you. ", Msg.TYPE_RECEIVED);
        msgList.add(msg3);
    }

    private void show(final String response, final int aa)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Msg msg = new Msg(response, aa);//新建对象
                msgList.add(msg);//添加到msgList列表中
                adapter.notifyItemInserted(msgList.size() - 1); // 当有新消息时，刷新ListView中的显示
                msgRecyclerView.scrollToPosition(msgList.size() - 1); // 将ListView定位到最后一行

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.send:
                String content = inputText.getText().toString();
                writer.println(content);//发送数据
                show(content,Msg.TYPE_SENT);
            default:
                break;
        }
    }
}
