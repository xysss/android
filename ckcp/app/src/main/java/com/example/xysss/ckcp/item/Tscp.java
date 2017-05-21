package com.example.xysss.ckcp.item;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xysss.ckcp.Myapp;
import com.example.xysss.ckcp.R;


public class Tscp extends AppCompatActivity {
    private RecyclerView m;
    private  QVFoodAdapter2 qvFoodAdapter2;
    private String b[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tscp);
        //init();
        //m.setLayoutManager(new LinearLayoutManager(this));
        m=(RecyclerView)findViewById(R.id.select_class);
        m.setLayoutManager(new LinearLayoutManager(this));
        b=loadQVFoodList();

        qvFoodAdapter2=new QVFoodAdapter2(b);
        m.setAdapter(qvFoodAdapter2);
    }
//    void init(){
//        Log.d("ppxxa",String.valueOf(j));
//}

    public class QVFoodHolder extends RecyclerView.ViewHolder{
        public TextView foodclass;

        public QVFoodHolder(View itemView){
            super(itemView);

            foodclass=(TextView)itemView.findViewById(R.id.food_class);
        }
    }

    public class QVFoodAdapter2 extends RecyclerView.Adapter<QVFoodHolder>{
        public String[] datas = null;
        public QVFoodAdapter2(String[] datas) {
            this.datas = datas;
        }
        @Override
        public QVFoodHolder onCreateViewHolder(ViewGroup parent,int viewType){
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.select_food,parent,false);
            return new QVFoodHolder(view);
        }
        @Override
        public void onBindViewHolder(QVFoodHolder holder,int position){
            holder.foodclass.setText(datas[position]);
        }
        @Override
        public int getItemCount(){
            return datas.length;
        }
    }

    public String[] loadQVFoodList(){
        String[]b=new String[10];

        if(((Myapp) getApplication()).getValue().contains("g")&&
                ((Myapp) getApplication()).getValue().contains("d"))//猪肉 青椒
        {
            b[0] = "青椒肉丝";
            b[1] = "虎皮青椒";
            b[2] = "青椒回锅肉";
            b[3] = "糖醋青椒";

        }
        if(((Myapp) getApplication()).getValue().contains("b")&&
                ((Myapp) getApplication()).getValue().contains("d"))//d猪肉 b胡萝卜
        {
            b[0] = "胡萝卜炒肉片";
            b[1] = "胡萝卜炖猪肉";
            b[2] = "水煮胡萝卜";
            b[3] = "鱼香肉丝";
        }
        if(((Myapp) getApplication()).getValue().contains("c")&&
                ((Myapp) getApplication()).getValue().contains("d"))//d猪肉 c西红柿
        {
            b[0] = "番茄肉丝汤";
            b[1] = "肉末番茄";
            b[2] = "柿子烤肉";
            b[3] = "西红柿炖猪肉";

        }
        if(((Myapp) getApplication()).getValue().contains("g")&&
                ((Myapp) getApplication()).getValue().contains("d")&&
                ((Myapp) getApplication()).getValue().contains("b")&&
                ((Myapp) getApplication()).getValue().contains("c"))
        {
            b[0] = "糖醋里脊";
            b[1] = "青椒胡萝卜炒肉片";
            b[2] = "鱼香肉丝";
            b[3] = "青椒炒肉";
            b[4] = "胡萝卜炒肉片";
            b[5] = "青椒回锅肉";
            b[6] = "番茄肉丝汤";
            b[7] = "西红柿炖猪肉";
            b[8] = "凉拌西红柿";
            b[9] = "西红柿炒胡萝卜丝";
        }
        return b;
    }
}
