package com.example.mydapplication;

import android.app.Activity;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.SaveCallback;

public class function extends Activity {
private Button a,b,c,d,e,f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        a=(Button)findViewById(R.id.one);
        b=(Button)findViewById(R.id.two);
        c=(Button)findViewById(R.id.three);
        d=(Button)findViewById(R.id.forth);
        e=(Button)findViewById(R.id.five);
        f=(Button)findViewById(R.id.six);


        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(function.this, ManageActivity.class);
                startActivity(i);
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.example.administrator.test");
                if(intent!=null)
                    startActivity(intent);
                else
                    Log.d("NOT EXIT", "app didn't installed");
            }

        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(function.this,Select_Food.class);
                startActivity(i);
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(function.this,find_food.class);
                startActivity(i);
            }
        });
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(function.this,Empty.class);
                startActivity(i);
            }
        });
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(function.this,Pass.class);
                startActivity(i);
            }
        });
        AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                AVInstallation.getCurrentInstallation().saveInBackground();
            }
        });
    }
}
