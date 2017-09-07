package com.example.xysss.ckcp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Second extends AppCompatActivity {

    private static final int REQUEST_UI = 1;

    private Button button_start;//开始按钮
    private EditText text_input;//语音识别对话框
    private BaiduASRDigitalDialog mDialog = null;
    private DialogRecognitionListener mDialogListener = null;
    private String API_KEY = "DspU1hDgD8OfXziAfK9Humto";
    private String SECRET_KEY = "0415ab3f7f11e779badd594c01f5f6f4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        button_start = (Button) findViewById(R.id.button_start);
        text_input = (EditText) findViewById(R.id.text_input);

        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
            }
        });

        if (mDialog == null) {
//            if (mDialog != null) {
//                mDialog.dismiss();
//            }
            Bundle params = new Bundle();
            //设置API_KEY
            params.putString(BaiduASRDigitalDialog.PARAM_API_KEY, API_KEY);
            params.putString(BaiduASRDigitalDialog.PARAM_SECRET_KEY, SECRET_KEY);
            //设置百度对话框主题
            params.putInt(BaiduASRDigitalDialog.PARAM_DIALOG_THEME, BaiduASRDigitalDialog.THEME_BLUE_LIGHTBG);
            //实例化百度语音识别
            mDialog = new BaiduASRDigitalDialog(this, params);
            //设置百度语音回调接口
            mDialogListener = new DialogRecognitionListener() {
                @Override
                public void onResults(Bundle mResults) {
                    ArrayList<String> rs = mResults != null ? mResults.getStringArrayList(RESULTS_RECOGNITION) : null;
                    if (rs != null && rs.size() > 0) {
                        text_input.setText(rs.get(0));
                        Toast.makeText(Second.this, rs.get(0), Toast.LENGTH_SHORT).show();
                    }
                }
            };
            mDialog.setDialogRecognitionListener(mDialogListener);
        }

        //界面

    }

}
