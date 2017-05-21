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

public class Yinpin1 extends AppCompatActivity {

    private List<food> mfoodList3 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ckwp);

        initFruits3();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.food_quick_view);
        //StaggeredGridLayoutManager layoutManager = new
        //        StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FoodAdapter adapter = new FoodAdapter(mfoodList3);
        recyclerView.setAdapter(adapter);
    }

    private void initFruits3() {
        hs3();

    }
    private void hs3(){
        String []s3=((Myapp) getApplication()).getValue().split(" ");
        for (int i=0;i<s3.length;i++)
        {

            if (s3[i].equals("a")) {
                food sh3 = new food("矿泉水", "1瓶", "未过保", "310天");
                mfoodList3.add(sh3);
            }
        }
    }

}
