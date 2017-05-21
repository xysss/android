package com.example.xysss.ckcp;

/**
 * Created by xysss on 2017/5/5.
 */

import android.app.Application;
public class Myapp extends Application {

    private String value=" ";


    public String getValue() {
        return value;
    }

    public void setValue(String value) {

        this.value = value;
    }

}
