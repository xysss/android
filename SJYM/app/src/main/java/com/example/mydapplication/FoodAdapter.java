package com.example.mydapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;


public class FoodAdapter extends ArrayAdapter<FoodItem> {
    private int resourceId;
    public FoodAdapter(Context context, int textViewResourceId,List<FoodItem> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        FoodItem food=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView foodName=(TextView)view.findViewById(R.id.food_name);
        TextView foodWeight=(TextView)view.findViewById(R.id.food_weight);
        TextView foodDate=(TextView)view.findViewById(R.id.food_date);
        if(food.getWeight().equals("0")){}
        else {
            foodName.setText(food.getName());
            if (food.getName().equals("黄瓜")) {
                foodWeight.setText(food.getWeightString() + "克");
                foodDate.setText("2016-10-30");
            } else if (food.getName().equals("青椒")) {
                foodWeight.setText(food.getWeightString() + "克");
                foodDate.setText("2016-11-2");
            } else if (food.getName().equals("苹果")) {
                foodWeight.setText(food.getWeightString() + "克");
                foodDate.setText("2016-11-20");
            } else if (food.getName().equals("啤酒")) {
                foodWeight.setText(food.getWeightString() + "瓶");
                foodDate.setText("2017-1-29");
            } else if (food.getName().equals("矿泉水")) {
                foodWeight.setText(food.getWeightString() + "瓶");
                foodDate.setText("2017-16-28");
            } else if (food.getName().equals("猪肉")) {
                foodWeight.setText(food.getWeightString() + "克");
                foodDate.setText("2016-12-28");

            } else if (food.getName().equals("鸡蛋")) {
                foodWeight.setText(food.getWeightString() + "克");
                foodDate.setText("2017-3-20");
            } else {
                foodWeight.setText(food.getWeightString() + "克");
                foodDate.setText("2016-10-24");
            }
        }
  //      foodDate.setText(food.getDate());
        return view;
    }
}
