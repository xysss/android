package com.example.activitytext;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FourthActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {        //重写back键
        Intent intent=new Intent();
        intent.putExtra("data_return","Hello FourthActivity");
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth_activity);

        Button button4=(Button)findViewById(R.id.button_4);
        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("data_return","Hello FourthActivity");
                setResult(RESULT_OK,intent);  //调用方法，返回处理结果
                finish();
            }
        });



    }
}
