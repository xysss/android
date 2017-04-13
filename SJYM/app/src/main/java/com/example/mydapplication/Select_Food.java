package com.example.mydapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Select_Food extends AppCompatActivity {
    private RecyclerView mRecyclerView,m;
    private QVFoodAdapter mAdapter;
    private  QVFoodAdapter2 qvFoodAdapter2;
    private SQLiteDatabase mDatabase;
    static int i=0,j=0;
    private List<String>c=new ArrayList<String>();
    private String a[]=new String[15],b[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__food);
        init();
        //mRecyclerView=(RecyclerView)findViewById(R.id.select_class);
       // mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        m=(RecyclerView)findViewById(R.id.select_class);
        m.setLayoutManager(new LinearLayoutManager(this));
        a=loadQVFoodList1();
        b=loadQVFoodList();

      //mAdapter=new QVFoodAdapter(a);
     //   mRecyclerView.setAdapter(mAdapter);
        qvFoodAdapter2=new QVFoodAdapter2(b);
        m.setAdapter(qvFoodAdapter2);
    }
 void init(){
     Log.d("ppxxa",String.valueOf(j));
     if (j!=0)
         j++;
     else
         j=0;
 }
    public class QVFoodHolder extends RecyclerView.ViewHolder{
        public TextView foodclass;


        public QVFoodHolder(View itemView){
            super(itemView);

            foodclass=(TextView)itemView.findViewById(R.id.food_class);


        }
    }

//    public class QVFoodAdapter extends RecyclerView.Adapter<QVFoodHolder>{
//    //  public List<String> datas = null;
//    public String[] datas = null;
//        public QVFoodAdapter(String[] datas) {
//            this.datas = datas;
//        }
//        @Override
//        public QVFoodHolder onCreateViewHolder(ViewGroup parent,int viewType){
//            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.select_food,parent,false);
//            return new QVFoodHolder(view);
//        }
//        @Override
//        public void onBindViewHolder(QVFoodHolder holder,int position){
//         //   holder.foodclass.setText(datas.get(position));
//            holder.foodclass.setText(datas[position]);
//        }
//        @Override
//        public int getItemCount(){
//           // return datas.size();return datas.length;
//            return datas.length;
//        }
//    }
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

        mDatabase=new FoodBaseHelper(getApplication()).getReadableDatabase();
        Cursor cursor=mDatabase.query(FoodDbSchema.FoodTable.NAME,null,null,null,null,null,null);
        try{

            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                Log.d("pp",i+"&"+cursor.getString(cursor.getColumnIndex(FoodDbSchema.FoodTable.Cols.NAME)));
             a[i]=cursor.getString(cursor.getColumnIndex(FoodDbSchema.FoodTable.Cols.NAME));
             //   a[i]="123";
               // Log.d("pp",i+"&"+cursor.getString(cursor.getColumnIndex(FoodDbSchema.FoodTable.Cols.NAME)));

                j=1;
                Log.d("ppa",String.valueOf(j));
                          //  c.add(cursor.getString(cursor.getColumnIndex(FoodDbSchema.FoodTable.Cols.NAME)));
                          cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return  a;
    }
    public String[] loadQVFoodList(){

        String[]b=new String[10];
        if(j==0)
        return b;

        else if(j==1) {
        b[0] = "西红柿炒鸡蛋";
        b[1] = "黄瓜炒鸡蛋";
        b[2] = "青椒炒鸡蛋";
        b[3] = "青椒炒肉";

            b[4] = "鸡蛋羹";

            b[5] = "炖肉";
            b[6] = "黄瓜炒肉";
            b[7] = "凉拌黄瓜";
        return b;

    }
        else if(j==2) {
            b[0] = "西红柿炒鸡蛋";
            b[1] = "黄瓜炒鸡蛋";
            b[2] = "青椒炒鸡蛋";
            b[3] = "青椒炒肉";

            b[4] = "鸡蛋羹";

            b[5] = "炖肉";
            b[6] = "黄瓜炒肉";
            b[7] = "凉拌黄瓜";
            return b;

        }
        else  {
            b[0] = "青椒炒肉";
            b[1] = "黄瓜炒鸡蛋";
            b[2] = "蘑菇炒鸡蛋";
            b[3] = "凉拌西红柿";

            b[4] = "水果沙拉";
            b[5] = "炖肉";
            return b;

        }

    }
}
