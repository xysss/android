package com.example.xysss.ckcp;

/**
 * Created by xysss on 2017/4/12.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.xysss.ckcp.sixitem.Qita1;

import com.example.xysss.ckcp.sixitem.Roulei1;
import com.example.xysss.ckcp.sixitem.Shucai;
import com.example.xysss.ckcp.sixitem.Shuiguo;

import com.example.xysss.ckcp.sixitem.Xuegao1;
import com.example.xysss.ckcp.sixitem.Yinpin1;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder>{

    //private  final String TAG = "FruitAdapter";

    private Context mContext;

    private List<Fruit> mFruitList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView fruitImage;
        TextView fruitName;

        //TextView fruitName0;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
            //fruitName0 = (TextView) view.findViewById(R.id.fruit_name0);
        }
    }

    public FruitAdapter(List<Fruit> fruitList) {
        mFruitList = fruitList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.fruit_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                switch (fruit.getImageId()) {
                    case R.drawable.deng:
                        //((Myapp) getApplication()).setValue("0");//赋值操作
                        //((Myapp) getApplication()).getValue();
                        Intent intent1= new Intent(mContext, Shuiguo.class);
                        mContext.startActivity(intent1);
                        break;
                    case R.drawable.men1:
                        Intent intent2 = new Intent(mContext, Shucai.class);
                        //intent2.putExtra("ll","2");
                        mContext.startActivity(intent2);
                        break;
                    case R.drawable.dianshi1:
                        Intent intent3 = new Intent(mContext, Yinpin1.class);
                        //intent3.putExtra("ll","3");
                        mContext.startActivity(intent3);
                        break;
                    case R.drawable.kongtiao1:
                        Intent intent4 = new Intent(mContext, Roulei1.class);
                        //intent4.putExtra("ll","4");
                        mContext.startActivity(intent4);
                        break;
                    case R.drawable.ss1:
                        Intent intent5 = new Intent(mContext, Xuegao1.class);
                        intent5.putExtra("ll","5");
                        mContext.startActivity(intent5);
                        break;
//                    case R.drawable.qd:
//                        Intent intent6 = new Intent(mContext, Qita1.class);
//                        intent6.putExtra("ll","6");
//                        mContext.startActivity(intent6);
//                        break;
                }
            }
        });
        return holder;
        //return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit = mFruitList.get(position);
        holder.fruitName.setText(fruit.getName());
        Glide.with(mContext).load(fruit.getImageId()).into(holder.fruitImage);
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}
