package com.example.xysss.ckcp.sixitem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.xysss.ckcp.Myapp;
import com.example.xysss.ckcp.R;
import com.example.xysss.ckcp.bx_item.FoodAdapter;
import com.example.xysss.ckcp.bx_item.food;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xysss on 2017/5/6.
 */

public class Yinpin1 extends AppCompatActivity implements View.OnClickListener {

    private List<food> mfoodList3 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dianshi);

        Button dsk = (Button) findViewById(R.id.dsk);
        dsk.setOnClickListener(this);

        Button yld = (Button) findViewById(R.id.yld);
        yld.setOnClickListener(this);

        Button ylx = (Button) findViewById(R.id.ylx);
        ylx.setOnClickListener(this);

        Button pds = (Button) findViewById(R.id.pds);
        pds.setOnClickListener(this);

        Button pdx = (Button) findViewById(R.id.pdx);
        pdx.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.dsk:
                ((Myapp) getApplication()).setValue("a");//赋值操作
                Log.d("赋值","a");
                break;
            case R.id.pds:
                ((Myapp) getApplication()).setValue("b");//赋值操作
                Log.d("赋值","b");
                break;
            case R.id.pdx:
                ((Myapp) getApplication()).setValue("c");//赋值操作
                Log.d("赋值","c");
                break;
            case R.id.yld:
                ((Myapp) getApplication()).setValue("d");//赋值操作
                Log.d("赋值","d");
                break;
            case R.id.ylx:
                ((Myapp) getApplication()).setValue("e");//赋值操作
                Log.d("赋值","e");
                break;
            default:break;
        }
    }
}



