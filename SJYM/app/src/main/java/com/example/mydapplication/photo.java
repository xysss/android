package com.example.mydapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.BufferOverflowException;

public class photo extends AppCompatActivity {
private EditText edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        edit=(EditText)findViewById(R.id.edit);
    }
    protected void onDestroy(){
        super.onDestroy();
        save("dsadd");
    }
    public void save(String a)
    {
        FileOutputStream out=null;
        BufferedWriter writer=null;
        try{
            out=openFileOutput("data", Context.MODE_PRIVATE);
              writer=new BufferedWriter(new OutputStreamWriter(out));
            writer.write(a);
        }catch (IOException e){e.printStackTrace();}
        finally {

        }

    }

}
