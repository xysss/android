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

public class Shucai extends AppCompatActivity {
    private List<food> mfoodList2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ckwp);

        initFruits2();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.food_quick_view);
        //StaggeredGridLayoutManager layoutManager = new
        //        StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FoodAdapter adapter = new FoodAdapter(mfoodList2);
        recyclerView.setAdapter(adapter);
    }

    private void initFruits2() {
        hs2();
    }
    private void hs2(){
        String []s2=((Myapp) getApplication()).getValue().split(" ");
        for (int i=0;i<s2.length;i++)
        {
            if (s2[i].equals("b")){
                food hlb1 = new food("胡萝卜", "100g", "未过保", "25天");
                mfoodList2.add(hlb1);
            }


            if (s2[i].equals("g")) {
                food qj1 = new food("青椒", "200g", "未过保", "10天");
                mfoodList2.add(qj1);
            }

            if (s2[i].equals("c")) {
                food xhs1 = new food("西红柿", "200g", "未过保", "16天");
                mfoodList2.add(xhs1);
            }
        }
    }

}

