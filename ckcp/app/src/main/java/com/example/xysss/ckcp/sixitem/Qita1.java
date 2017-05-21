package com.example.xysss.ckcp.sixitem;

/**
 * Created by xysss on 2017/5/6.
 */

/**
 * Created by xysss on 2017/5/6.
 */
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

public class Qita1 extends AppCompatActivity {

    private List<food> mfoodList6 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ckwp);

        initFruits6();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.food_quick_view);
        //StaggeredGridLayoutManager layoutManager = new
        //        StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FoodAdapter adapter = new FoodAdapter(mfoodList6);
        recyclerView.setAdapter(adapter);
    }

    private void initFruits6() {
        hs6();

    }
    private void hs6(){
        String []s6=((Myapp) getApplication()).getValue().split(" ");
        for (int i=0;i<s6.length;i++)
        {
            if (s6[i].equals("e")) {
                food mb6 = new food("面包", "350g", "已过保", "3天");
                mfoodList6.add(mb6);
            }

        }
    }

}
