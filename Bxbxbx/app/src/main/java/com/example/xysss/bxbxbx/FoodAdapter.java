package com.example.xysss.bxbxbx;

/**
 * Created by xysss on 2017/5/7.
 */

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private List<food> mfoodList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        //CardView cardView1;
        TextView foodName;
        TextView foodName1;
        TextView foodName2;
        TextView foodName3;

        public ViewHolder(View view) {
            super(view);
            //cardView1 = (CardView) view;
            foodName = (TextView) view.findViewById(R.id.food_name);
            foodName1 = (TextView) view.findViewById(R.id.food_name1);
            foodName2 = (TextView) view.findViewById(R.id.food_name2);
            foodName3 = (TextView) view.findViewById(R.id.food_name3);
        }
    }

    public FoodAdapter(List<food> foodList) {
        mfoodList = foodList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        food food = mfoodList.get(position);
        holder.foodName.setText(food.getName());
        holder.foodName1.setText(food.getName1());
        holder.foodName2.setText(food.getName2());
        holder.foodName3.setText(food.getName3());
    }

    @Override
    public int getItemCount() {
        return mfoodList.size();
    }

}