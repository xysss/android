
package com.example.mydapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManageActivity extends AppCompatActivity {

    private List<FoodItem> foodList=new ArrayList<>();
    private List<FoodItem> foodList1=new ArrayList<>();
    private View foodListView;
    private IntentFilter mFilter;
    private MangeReceiver mReceiver;
    private View progressView;
    private FloatingActionButton fab;
    private SQLiteDatabase mDatabase;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodList1.clear();
                showProgress(true);
                ScanFoodTask mScanFoodTask=new ScanFoodTask();
                mScanFoodTask.execute((Void) null);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

            }
        });
        foodListView=findViewById(R.id.food_list);
        progressView=findViewById(R.id.load_progress);
        showProgress(true);

        mFilter=new IntentFilter();
        mFilter.addAction("UPDATE_STATUS");
        mReceiver=new MangeReceiver();
        registerReceiver(mReceiver,mFilter);

        LoadFoodTask loadFood=new LoadFoodTask();
        loadFood.execute((Void) null);
        NotificationManager notification=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        PendingIntent pendingIntent3 = PendingIntent.getActivity(this, 0,
                new Intent(this,Pass.class), 0);
        // 通过Notification.Builder来创建通知，注意API Level
        // API16之后才支持
        Notification notify3 = new Notification.Builder(this)
                .setSmallIcon(R.drawable.detail)
                .setTicker("TickerText:" + "您有新短消息，请注意查收！")
                .setContentTitle("Notification Title")
                .setContentText("食品过期通知")
                .setContentIntent(pendingIntent3).setNumber(1).build(); // 需要注意build()是在API
        notification.notify(1, notify3);

    }

    class MangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            showProgress(true);
            LoadFoodTask loadFood=new LoadFoodTask();
            loadFood.execute((Void) null);
        }
    }
    private void praseJsonWithGson(String jsonData) {
        Gson gson = new Gson();
        foodList = gson.fromJson(jsonData, new TypeToken<List<FoodItem>>() {
        }.getType());
        for (FoodItem food : foodList) {
            Log.d("FOOD", food.getName());
        }
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            foodListView.setVisibility(show ? View.GONE : View.VISIBLE);
            foodListView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    foodListView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            foodListView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
    public class ScanFoodTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            fab.setEnabled(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection connection=null;
            try {
                URL url=new URL("http://123.206.54.102/avoshan.php");
                connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.getInputStream();
                if (connection.getResponseCode()!=HttpURLConnection.HTTP_OK) {
                    throw new IOException(connection.getResponseMessage());
                }
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                connection.disconnect();
            }
            return null;
        }

    }
    public class LoadFoodTask extends AsyncTask<Void,Void,String>{
        @Override
        protected void onPreExecute(){
            //showProgress(true);
        }
        @Override
        protected String doInBackground(Void...params){
            HttpURLConnection connection=null;
            try{
                URL url=new URL("http://123.206.54.102/getfoods.php");//http://123.206.21.230/SmartRefrigerator/getfoods.php
                connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                InputStream in = connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                StringBuilder response=new StringBuilder();
                String line;
                while((line=reader.readLine())!=null){
                    response.append(line);
                }
                String foodJson=response.toString();
                Log.d("JsonData",foodJson);
                praseJsonWithGson(foodJson);
//                SharedPreferences.Editor editor=getSharedPreferences("food_data",MODE_PRIVATE).edit();
                mContext= getApplicationContext();
                mDatabase=new FoodBaseHelper(mContext).getWritableDatabase();
                mDatabase.execSQL("delete from "+ FoodDbSchema.FoodTable.NAME);
                Date now=new Date();
                DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
                Date freshDate;
                String whetherBad,a,f,b="不足",c="充足",d="过期",e="即将过期",day;
                String whetherEnough;
               int  i=0,j;
                for (FoodItem food:foodList) {

                    a=food.getName();
                    f=food.getWeight();
                    j=Integer.parseInt(f);
                    ContentValues values=new ContentValues();
                    if(food.getWeight().equals("0")){
                     //   foodList.remove(i);

                        i++;
                    }
                    else

                    {    foodList1.add(food);
                        values.put(FoodDbSchema.FoodTable.Cols.NAME,food.getName());

                 //  long last=(freshDate.getTime()-now.getTime())/(1000*24*60*60);
                    if (a.equals("黄瓜")) {
                        freshDate=format.parse("2016-10-30");
                    }
                    else if (a.equals("青椒"))
                    {
                        freshDate=format.parse("2016-11-2");
                    }
                    else if (a.equals("苹果"))
                    {
                        freshDate=format.parse("2016-11-20");
                    }
                    else if (a.equals("啤酒"))
                    {
                        freshDate=format.parse("2017-1-29");

                    }
                    else if (a.equals("猪肉"))
                    {

                        freshDate=format.parse("2017-11-5");

                    }
                    else if (a.equals("鸡蛋"))
                    {
                        freshDate=format.parse("2016-9-4");

                    }
                    else
                    {
                        freshDate=format.parse("2016-9-28");

                    }
                    if (now.before(freshDate)){
                        //Nor bad yet.
                        long last=(freshDate.getTime()-now.getTime())/(1000*24*60*60);
                        if (last<=10){
                            whetherBad="即将过期";
                            day=String.valueOf(last+"天");
                        }
                        else{
                            whetherBad="未过期";
                            day=String.valueOf("数天");
                        }
                    }
                    else{
                        //bad
                        whetherBad="过期";
                        day="- -";
                    }
                    //editor.putString("whetherBad",whetherBad);
                    values.put(FoodDbSchema.FoodTable.Cols.WHETHERBAD,whetherBad);
                    values.put(FoodDbSchema.FoodTable.Cols.Day,day);
                    if (a.equals("黄瓜")) {
                        if(j<200)
                            values.put(FoodDbSchema.FoodTable.Cols.WHETHERENOUGH,b);
                        else
                            values.put(FoodDbSchema.FoodTable.Cols.WHETHERENOUGH,c);
                    }
                    else if (a.equals("青椒"))
                    {
                        if(j<200)
                        values.put(FoodDbSchema.FoodTable.Cols.WHETHERENOUGH,b);
                        else
                      values.put(FoodDbSchema.FoodTable.Cols.WHETHERENOUGH,c);
                    }
                    else if (a.equals("苹果"))
                    {
                        values.put(FoodDbSchema.FoodTable.Cols.WHETHERENOUGH,c);
                     //   values.put(FoodDbSchema.FoodTable.Cols.WHETHERBAD,e);
                    }
                    else if (a.equals("啤酒"))
                    {
                        values.put(FoodDbSchema.FoodTable.Cols.WHETHERENOUGH,c);
                      //  values.put(FoodDbSchema.FoodTable.Cols.WHETHERBAD,e);
                    }
                    else if (a.equals("猪肉"))
                    {
                        values.put(FoodDbSchema.FoodTable.Cols.WHETHERENOUGH,c);
                      //  values.put(FoodDbSchema.FoodTable.Cols.WHETHERBAD,d);
                    }
                    else if (a.equals("鸡蛋"))
                    {
                        values.put(FoodDbSchema.FoodTable.Cols.WHETHERENOUGH,b);
                     //   values.put(FoodDbSchema.FoodTable.Cols.WHETHERBAD,d);
                    }
                    else
                    {
                        values.put(FoodDbSchema.FoodTable.Cols.WHETHERENOUGH,b);
                        values.put(FoodDbSchema.FoodTable.Cols.WHETHERBAD,d);
                    }

                    mDatabase.insert(FoodDbSchema.FoodTable.NAME,null,values);
                }}
                //editor.clear();
                //editor.commit();
                return foodJson;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            showProgress(false);
            if(result!=null) {
                FoodAdapter adapter=new FoodAdapter(ManageActivity.this,R.layout.food_item,foodList1);
                ListView listView=(ListView)findViewById(R.id.food_list);
                listView.setAdapter(adapter);
            }
            else {
                Log.d("Receive False","NO INTERNET");
            }
        }


} protected void onDestroy(){
        super.onDestroy();
        //unregisterReceiver(mReceiver);
    }}
