package com.example.mydapplication;

import android.app.NotificationManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Pass extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private QVFoodAdapter mAdapter;
    private SQLiteDatabase mDatabase;
    private List<QVFoodItem> mqv_list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass);
        mRecyclerView=(RecyclerView)findViewById(R.id.food_quick_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadQVFoodList();
        mAdapter=new QVFoodAdapter(mqv_list);
        mRecyclerView.setAdapter(mAdapter);
        NotificationManager notification=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification.cancel(1);
    }
    public class QVFoodHolder extends RecyclerView.ViewHolder{
        public TextView mName;
        public TextView mWhetherBad;
        public TextView mWhetherday;
        public QVFoodHolder(View itemView){
            super(itemView);
            mName=(TextView)itemView.findViewById(R.id.qv_food_name);
             mWhetherBad=(TextView)itemView.findViewById(R.id.qv_food_whether_bad);
            mWhetherday=(TextView)itemView.findViewById(R.id.qv_food_day);
        }
    }
    public class QVFoodAdapter extends RecyclerView.Adapter<QVFoodHolder>{
        private List<QVFoodItem> mQVFoodItems;
        public QVFoodAdapter(List<QVFoodItem> QVFoodItems){
            mQVFoodItems=QVFoodItems;
        }
        @Override
        public QVFoodHolder onCreateViewHolder(ViewGroup parent,int viewType){
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.qv_food_item1,parent,false);
            return new QVFoodHolder(view);
        }
        @Override
        public void onBindViewHolder(QVFoodHolder holder,int position){
            QVFoodItem food=mQVFoodItems.get(position);
            holder.mName.setText(food.getName());
            holder.mWhetherday.setText(food.getDay());
             holder.mWhetherBad.setText(food.getWhetherBad());
        }
        @Override
        public int getItemCount(){
            return mQVFoodItems.size();
        }
    }
    public void loadQVFoodList(){
        mqv_list=new ArrayList<>();
        mDatabase=new FoodBaseHelper(getApplication()).getReadableDatabase();
        Cursor cursor=mDatabase.query(FoodDbSchema.FoodTable.NAME,null,null,null,null,null,null);
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                QVFoodItem food=new QVFoodItem();
                food.setName(cursor.getString(cursor.getColumnIndex(FoodDbSchema.FoodTable.Cols.NAME)));
                 food.setWhetherBad(cursor.getString(cursor.getColumnIndex(FoodDbSchema.FoodTable.Cols.WHETHERBAD)));
                food.setDay(cursor.getString(cursor.getColumnIndex(FoodDbSchema.FoodTable.Cols.Day)));
                mqv_list.add(food);
                cursor.moveToNext();
            }




        }finally {
            cursor.close();
        }

    }


}
