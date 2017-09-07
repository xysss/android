package com.example.xysss.ckcp.sixitem;

import android.icu.text.LocaleDisplayNames;
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

public class Roulei1 extends AppCompatActivity implements View.OnClickListener {

    private List<food> mfoodList4 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kongtiao);

        Button ktk = (Button) findViewById(R.id.ktk);
        ktk.setOnClickListener(this);

        Button lf = (Button) findViewById(R.id.lf);
        lf.setOnClickListener(this);

        Button  fs= (Button) findViewById(R.id.fs);
        fs.setOnClickListener(this);

        Button ds = (Button) findViewById(R.id.ds);
        ds.setOnClickListener(this);

        Button fl = (Button) findViewById(R.id.fl);
        fl.setOnClickListener(this);

        Button bf = (Button) findViewById(R.id.bf);
        bf.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ktk:
                ((Myapp) getApplication()).setValue("1");//赋值操作
                Log.d("赋值","1");
                Log.d("aa",((Myapp) getApplication()).getValue());
                break;
            case R.id.lf:
                ((Myapp) getApplication()).setValue("2");//赋值操作
                Log.d("赋值","2");
                break;
            case R.id.fs:
                ((Myapp) getApplication()).setValue("3");//赋值操作
                Log.d("赋值","3");
                break;
            case R.id.ds:
                ((Myapp) getApplication()).setValue("4");//赋值操作
                Log.d("赋值","4");
                break;
            case R.id.fl:
                ((Myapp) getApplication()).setValue("5");//赋值操作
                Log.d("赋值","5");
                break;
            case R.id.bf:
                ((Myapp) getApplication()).setValue("6");//赋值操作
                Log.d("赋值","6");
                break;
            default:break;
        }
    }
}
