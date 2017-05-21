package com.example.xysss.ckcp.sixitem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.xysss.ckcp.Myapp;
import com.example.xysss.ckcp.R;
import com.example.xysss.ckcp.bx_item.FoodAdapter;
import com.example.xysss.ckcp.bx_item.food;

import java.util.ArrayList;
import java.util.List;

public class Shuiguo extends AppCompatActivity {
    private List<food> mfoodList1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ckwp);

        initFruits1();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.food_quick_view);
        //StaggeredGridLayoutManager layoutManager = new
        //        StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FoodAdapter adapter = new FoodAdapter(mfoodList1);
        recyclerView.setAdapter(adapter);
    }

    private void initFruits1() {
        hs1();

    }
    private void hs1(){
        String []s1=((Myapp) getApplication()).getValue().split(" ");
        for (int i=0;i<s1.length;i++)
        {
            if(s1[i].equals("f1"))
            {
                food pg1 = new food("苹果","100g","未过保","30天");
                mfoodList1.add(pg1);
            }
            if(s1[i].equals("f2"))
            {
                food pg1 = new food("苹果","200g","未过保","30天");
                mfoodList1.add(pg1);
            }
        }
    }

}

