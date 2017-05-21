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

public class Roulei1 extends AppCompatActivity {

    private List<food> mfoodList4 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ckwp);

        initFruits4();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.food_quick_view);
        //StaggeredGridLayoutManager layoutManager = new
        //        StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FoodAdapter adapter = new FoodAdapter(mfoodList4);
        recyclerView.setAdapter(adapter);
    }

    private void initFruits4() {
        hs4();
    }
    private void hs4(){
        String []s4=((Myapp) getApplication()).getValue().split(" ");
        for (int i=0;i<s4.length;i++)
        {

            if (s4[i].equals("d")) {
                food zr = new food("猪肉", "100g", "未过保", "7天");
                mfoodList4.add(zr);
            }

        }
    }

}

