package com.example.xysss.im;

/**
 * Created by xysss on 2017/3/24.
 */

public class Msg {

    public static final int TYPE_RECEIVED = 0;//表示这是收到的消息

    public static final int TYPE_SENT = 1;  //表示这是发送的消息

    private String content;  //消息内容

    private int type;  //消息类型

    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }


}
