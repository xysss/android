package com.example.activitytext;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);     //当前活动创建菜单
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                Toast.makeText(this," You click Add",Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this,"You click Remove", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {    //重写方法
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    String returnedData=data.getStringExtra("data_return");   //得到返回数据
                    Log.d("FirstActivity",returnedData);
                }
                break;
            default:
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        Button button1=(Button) findViewById(R.id.button_1);    //获取布局文件中定义的元素
        button1.setOnClickListener(new View.OnClickListener(){  //注册监听器
            @Override
            public void onClick(View view){
              Intent intent=new Intent(FirstActivity.this,ThirdActivity.class);//显示构建
           //Intent intent=new Intent("com.example.activitytext.ACTTION_START"); //隐式构建
            //intent.addCategory("com.example.activitytext.MY_CATEGORY");         //自定义category
            startActivity(intent);//隐式action，category同时匹配才启动第二个活动
        }
    });

        Button buttonBD=(Button) findViewById(R.id.button_BD);   //自定百度按钮
        buttonBD.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);   //指定action
                intent.setData(Uri.parse("http://www.baidu.com"));//将URI对象传递进去
                startActivity(intent);

            }
        });

        Button buttonBH=(Button)findViewById(R.id.button_bh);  //拨号按钮
        buttonBH.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_DIAL);    //系统内置动作
                intent.setData(Uri.parse("tel:10086"));          //协议Tel
                startActivity(intent);
            }
        });

        Button buttonCZ=(Button)findViewById(R.id.button_CZ);   //传递参数
        buttonCZ.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String data="Hello SecondActivity";
                Intent intent=new Intent(FirstActivity.this,SecondActivity.class);  //显示构建
                intent.putExtra("extra_data",data);     //传递data数据
                startActivity(intent);
            }
        });

        Button buttonHD=(Button)findViewById(R.id.button_HD);  //返回参数
        buttonHD.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FirstActivity.this,FourthActivity.class);
                startActivityForResult(intent,1); //启动活动，但能返回结果给上一个活动，唯一请求码1
            }
        });


    }
}
