package com.example.xysss.bxbxbx;

/**
 * Created by xysss on 2017/5/7.
 */

import android.app.Application;
public class Myapp extends Application {

    private String value="z";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {

        this.value = value;
    }

}
