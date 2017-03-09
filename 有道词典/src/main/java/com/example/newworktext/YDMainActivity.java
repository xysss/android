package com.example.newworktext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.List;

import static android.R.id.edit;
import static android.R.id.inputExtractEditText;
import static android.R.id.message;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView responseText;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendRequest = (Button) findViewById(R.id.send_request);
        editText = (EditText) findViewById(R.id.edit);
        responseText = (TextView) findViewById(R.id.response_text);
        sendRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_request) {
            sendRequestWithWithHttpURLConnection();
        }
    }

    private void sendRequestWithWithHttpURLConnection() {
        //开启线程发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    String inputText =editText.getText().toString();
                    Log.d("asd",inputText);
                    String youdaokey = "http://fanyi.youdao.com/openapi.do?keyfrom=1343025yll&key=1141821383&type=data&doctype=json&version=1.1&q=";
                    String youdaoURL =youdaokey+inputText;
                    URL url = new URL(youdaoURL);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    Log.d("asdas",youdaoURL);
                    //下面对获取到的输入流进行读取

                    reader = new BufferedReader(new InputStreamReader(in,
                            "UTF-8"), 8192);
                    // reader=new BufferedReader(new InputStreamReader(in));

                    String str = "";
                    String line;
                    while ((line = reader.readLine()) != null) {
                        str += line;
                    }
//                    showResponse(str);

                   showResponse(JSONWithJSONObject(str));

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private String JSONWithJSONObject(String jsonData) {
        String ZH = "";
        String message = null;
        try {
            JSONArray jsonArray = new JSONArray("[" + jsonData + "]");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                String translation=jsonObject.getString("translation");
//                String basic=jsonObject.getString("basic");
//                String web=jsonObject.getString("web");
//                ZH+=translation+basic+web;

                if (jsonObject != null) {
                    String errorCode = jsonObject.getString("errorCode");
                    if (errorCode.equals("20")) {
                        Toast.makeText(getApplicationContext(), "要翻译的文本过长", Toast.LENGTH_SHORT);
                    } else if (errorCode.equals("30 ")) {
                        Toast.makeText(getApplicationContext(), "无法进行有效的翻译", Toast.LENGTH_SHORT);
                    } else if (errorCode.equals("40")) {
                        Toast.makeText(getApplicationContext(), "不支持的语言类型", Toast.LENGTH_SHORT);
                    } else if (errorCode.equals("50")) {
                        Toast.makeText(getApplicationContext(), "无效的key", Toast.LENGTH_SHORT);
                    } else {
                        // 要翻译的内容
                        String query = jsonObject.getString("query");
                        message = query;
                        // 翻译内容
                        String translation = jsonObject.getString("translation");
                        message += "\t" + translation;
                        // 有道词典-基本词典
                        if (jsonObject.has("basic")) {
                            JSONObject basic = jsonObject.getJSONObject("basic");
                            if (basic.has("phonetic")) {
                                String phonetic = basic.getString("phonetic");
                                message += "\n\t" + phonetic;
                            }
                            if (basic.has("phonetic")) {
                                String explains = basic.getString("explains");
                                message += "\n\t" + explains;
                            }
                        }

                    }
                    //responseText.setText(message);
                } else {
                    Toast.makeText(getApplicationContext(), "提取异常", Toast.LENGTH_SHORT);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return message;

    }

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //进行UI操作，将结果显示到界面上
                responseText.setText(response);

                Log.d("XXX",response);
            }
        });
    }
}
