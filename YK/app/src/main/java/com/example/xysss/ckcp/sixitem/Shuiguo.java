package com.example.xysss.ckcp.sixitem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.xysss.ckcp.Myapp;
import com.example.xysss.ckcp.R;
import com.example.xysss.ckcp.bx_item.FoodAdapter;
import com.example.xysss.ckcp.bx_item.food;

import java.util.ArrayList;
import java.util.List;

public class Shuiguo extends AppCompatActivity implements View.OnClickListener{
    private List<food> mfoodList1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ckwp);

        Button dk = (Button) findViewById(R.id.dk);
        dk.setOnClickListener(this);

        Button dg = (Button) findViewById(R.id.dg);
        dg.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dk:
                ((Myapp) getApplication()).setValue("8");//赋值操作
                Log.d("赋值","8");
                break;
            case R.id.dg:
                ((Myapp) getApplication()).setValue("9");//赋值操作
                Log.d("赋值","9");
                break;
               default: break;
        }
    }
}

