package com.example.xysss.ckcp.item;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.xysss.ckcp.R;

public class Cxcp extends AppCompatActivity {
    private EditText editText;
    private  TextView textView;
    private RecyclerView mRecyclerView,m;
    private QVFoodAdapter mAdapter;
    private  QVFoodAdapter2 qvFoodAdapter2;
    private SQLiteDatabase mDatabase;
    private String a[]=new String[15],b[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cxcp);
        Button button=(Button)findViewById(R.id.button3);
        Button button1=(Button)findViewById(R.id.button);
        textView=(EditText)findViewById(R.id.editText);
        mRecyclerView=(RecyclerView)findViewById(R.id.select_class);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        m=(RecyclerView)findViewById(R.id.select_class1);
        m.setLayoutManager(new LinearLayoutManager(this));
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
                a= load7();
                b= load8();
                mAdapter = new QVFoodAdapter(a);
                mRecyclerView.setAdapter(mAdapter);
                qvFoodAdapter2 = new QVFoodAdapter2(b);
                m.setAdapter(qvFoodAdapter2);

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("dd",textView.getText().toString());
                if (textView.getText().toString().equals("可乐鸡翅"))
                {
                    a=loadQVFoodList1();
                    b=loadQVFoodList();
                } else if (textView.getText().toString().equals("西红柿炒鸡蛋"))
                {  a= load1();
                    b= load2();
                }
                else if(textView.getText().toString().equals("鱼香肉丝"))
                {
                    a= load3();
                    b= load4();
                }
                else
                {
                    a= load5();
                    b= load6();
                }
                mAdapter = new QVFoodAdapter(a);
                mRecyclerView.setAdapter(mAdapter);
                qvFoodAdapter2 = new QVFoodAdapter2(b);
                m.setAdapter(qvFoodAdapter2);
            }
        });

    }
    public class QVFoodHolder extends RecyclerView.ViewHolder{
        public TextView foodclass;


        public QVFoodHolder(View itemView){
            super(itemView);

            foodclass=(TextView)itemView.findViewById(R.id.food_class);

        }
    }

    public class QVFoodAdapter extends RecyclerView.Adapter<QVFoodHolder>{
        public String[] datas = null;
        public QVFoodAdapter(String[] datas) {
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
    public String[] loadQVFoodList1(){
        String[]b=new String[10];
        b[0]=" 1.鸡翅洗净，葱姜切片";
        b[1]=" 2.锅内烧开水，放入鸡翅再次煮滚捞出，沥干水分";
        b[2]=" 3.锅内放少许油烧热，放入鸡翅煎至两面双黄";
        b[3]=" 4.倒入可乐，老抽，加入葱姜，盐，料酒烧开转小火至汤汁浓稠即可";
        return  b;
    }
    public String[] loadQVFoodList(){

        String[]b=new String[10];
        b[0]="缺少食材：";
        b[1]="1.鸡翅500g";
        b[2]="2.可乐300ml";

        return b;
    }
    public String[] load1(){

        String[]b=new String[10];
        b[0]="1.西红柿清洗干净,鸡蛋打入碗中,打散备用";
        b[1]="2.热锅凉油8成热放入鸡蛋炒，炒散烧熟";
        b[2]="3.炒熟的鸡蛋出锅备用";
        b[3]="4.另起锅放少许油，6成热放入西红柿翻炒。";
        b[4]="5.加入糖和盐翻炒均匀即可出锅。";

        return b;
    }
    public String[] load2(){

        String[]b=new String[10];
        b[0]="已有食材：";
        b[1]="1.西红柿 200g";
        b[2]="缺少食材：";
        b[3]="2.鸡蛋 100g";
        return b;
    }
    public String[] load3(){

        String[]b=new String[10];
        b[0]="1.把里脊肉切成细丝，用生抽1汤匙，胡椒粉适量，料酒1汤匙，水淀粉1汤匙抓匀腌制10分钟。";
        b[1]="2.胡萝卜、青椒切丝。准备葱姜蒜丝。";
        b[2]="3在微波容器中加入2汤匙植物油，加入姜蒜拌一下，放入微波炉高火1分钟。";
        b[3]="4.取出，加入肉丝拌匀。。";
        b[4]="5.加入胡萝卜丝、青椒丝，加1汤匙蚝油，1汤匙豆瓣酱，1汤匙甜面酱，1汤匙醋，1/2汤匙白糖，搅拌均匀。";

        return b;
    }
    public String[] load4(){

        String[]b=new String[10];

        b[0]="已有食材：";
        b[1]="1.猪肉100g";
        b[2]="2.青椒100g";
        b[3]="3.胡萝卜100g";

        return b;
    }
    public String[] load5(){

        String[]b=new String[10];
        b[0]="暂无信息";

        return b;
    }
    public String[] load6(){

        String[]b=new String[10];
        return b;
    }
    public String[] load7(){

        String[]b=new String[10];
        return b;
    }
    public String[] load8(){

        String[]b=new String[10];
        return b;
    }
}
