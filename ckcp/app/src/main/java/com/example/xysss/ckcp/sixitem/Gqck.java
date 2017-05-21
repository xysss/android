package com.example.xysss.ckcp.sixitem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.xysss.ckcp.Myapp;
import com.example.xysss.ckcp.R;
import com.example.xysss.ckcp.bx_item.FoodAdapter;
import com.example.xysss.ckcp.bx_item.food;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xysss on 2017/5/6.
 */

public class Gqck extends AppCompatActivity {

    private List<food> mfoodList7 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ckwp);

        initFruits7();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.food_quick_view);
        //StaggeredGridLayoutManager layoutManager = new
        //        StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FoodAdapter adapter = new FoodAdapter(mfoodList7);
        recyclerView.setAdapter(adapter);
    }

    private void initFruits7() {
        hs7();

    }
    private void hs7(){
        String []s7=((Myapp) getApplication()).getValue().split(" ");
        for (int i=0;i<s7.length;i++)
        {
            if (s7[i].equals("e")) {
                food mianbao = new food("面包","500g", "已过保", "3天");
                mfoodList7.add(mianbao);
            }
            if (s7[i].equals("d")) {
                food pijiu7 = new food("啤酒", "两罐", "未过保", "5天");
                mfoodList7.add(pijiu7);
            }
            else if (s7[i].equals("z")){
                food kong7 = new food("无", "无", "无", "无");
                mfoodList7.add(kong7);
            }

        }
    }

}
