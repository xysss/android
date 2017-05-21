package com.example.xysss.ckcp.bx_item;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.xysss.ckcp.Myapp;
import com.example.xysss.ckcp.R;

import java.util.ArrayList;
import java.util.List;
public class Ckwp extends AppCompatActivity {

    private List<food> mfoodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ckwp);

        initFruits();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.food_quick_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FoodAdapter adapter = new FoodAdapter(mfoodList);
        recyclerView.setAdapter(adapter);
    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent); //这一句必须的，否则Intent无法获得最新的数据
//    }
    @Override
    protected void onStart() {
        super.onStart();
        //initFruits();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //initFruits();
    }

    private void initFruits() {
        hs7();
    }

    private void hs7() {
        String []s=((Myapp) getApplication()).getValue().split(" ");
        for (int i = 0; i < s.length; i++) {
            if (s[i].equals("a")) {
                food sh = new food("矿泉水", "1瓶", "未过保", "310天");
                mfoodList.add(sh);
            }
            if (s[i].equals("b")) {
                food hlb = new food("胡萝卜", "100g", "未过保", "25天");
                mfoodList.add(hlb);
            }
            if (s[i].equals("c")) {
                food xhs = new food("西红柿", "200g", "未过保", "16天");
                mfoodList.add(xhs);
            }
            if (s[i].equals("d")) {
                food zr = new food("猪肉", "100g", "未过保", "7天");
                mfoodList.add(zr);
            }
            if (s[i].equals("e")) {
                food mb = new food("面包", "350g", "已过保", "3天");
                mfoodList.add(mb);
            }

            if (s[i].equals("f1")){
                food pg1 = new food("苹果", "100g", "未过保", "26天");
                mfoodList.add(pg1);
            }
            if (s[i].equals("f2")){
                food pg2 = new food("苹果", "200g", "未过保", "26天");
                mfoodList.add(pg2);
            }

            if (s[i].equals("g")) {
                food qj = new food("青椒", "200g", "未过保", "10天");
                mfoodList.add(qj);
            }

            else if (s[i].equals("z")){
                food kong = new food("空", "空", "空", "空");
                mfoodList.add(kong);
            }
        }
    }
}
