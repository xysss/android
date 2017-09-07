package com.example.xysss.kdspeak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

public class MainActivity extends AppCompatActivity {

    //private EditText edt;
    //private Button btn;
    private KqwSpeechCompound kqwSpeechCompound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SpeechUtility.createUtility(MainActivity.this, SpeechConstant.APPID + "=51e8ae25");
        //edt= (EditText) findViewById(R.id.et_text);
        //btn= (Button) findViewById(R.id.btn);
        kqwSpeechCompound=new KqwSpeechCompound(this);
        String bx="冰箱里面有";
        final String bx1;

        Intent intent101=getIntent();
        String data="nmnm";
        try {
            data=intent101.getStringExtra("abc");

        }catch (Exception e){
            Log.d("bbb",data);
        }

        //Log.d("bbb",data);
        //String []s=new String[]

        String []s=data.split(" ");
        for (int i = 0; i < s.length; i++) {
            if (s[i].equals("1")) {
                bx="冰箱里面有";
            }
            else if (s[i].equals("西"))
            {
                bx="已有西红柿232g，缺少鸡蛋";
                break;
            }

            else if (s[i].equals("建"))
            {
                bx="您近期蔬菜使用过少,建议多吃蔬菜";
                break;
            }

            else if (s[i].equals("做"))
            {
                bx="现在冰箱里可以做，糖醋里脊，青椒胡萝卜炒肉片，鱼香肉丝，青椒炒肉，" +
                        "胡萝卜炒肉片，青椒回锅肉，番茄肉丝汤，西红柿炖猪肉，凉拌西红柿，西红柿炒胡萝卜丝";
                break;
            }

            else if (s[i].equals("鱼"))
            {
                bx="已有猪肉75g、青椒250g、胡萝卜186g、食材齐全 ";
                break;
            }


             else if (s[i].equals("2")) {
                bx="快过期食品有，牛奶240克、3天后过期，猪肉75g,后天过期";
                int k=1;
                break;
//                for(int j=1;j<s.length;j++)
//                {
////                    if (s[j].equals("d")) {
////                        bx=bx+"猪肉480克、";
////                        k=1;
////                    }
//                    if (s[j].equals("e")) {
//                        bx=bx+"牛奶250克、后天过期";
//                        k=1;
//                        break;
//                    }
//                }
//                if (k==0){
//                    bx="冰箱里没有过期食品";
//                }
                //bx="过期食品有面包350g    猪肉340g";
                //break;
                //Log.d("a","矿泉水");
            }
            else if (s[i].equals("a")) {
                bx=bx+"矿泉水1瓶,";
                //Log.d("a","矿泉水");
            }
            else if (s[i].equals("b")) {
                bx=bx+"胡萝卜186克，";
                //Log.d("a","胡萝卜");
            }
            else if (s[i].equals("c")) {
                bx=bx+"西红柿232克，";
            }
            else if (s[i].equals("d")) {
                bx=bx+"猪肉75克、";
            }
            else if (s[i].equals("e")) {
                bx=bx+"牛奶240克、";

            }
            else if (s[i].equals("f1")) {
                bx=bx+"苹果160克、";
            }
            else if (s[i].equals("f2")) {
                bx=bx+"苹果255克、";
            }
            else if (s[i].equals("g")) {
                bx=bx+"青椒186克、";
            }
            else if (s[i].equals("z")) {
                bx="冰箱里现在什么都没有";
                break;
            }
        }
        bx1=bx;
        kqwSpeechCompound.speaking(bx1);

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                kqwSpeechCompound.speaking(bx1);
//            }
//        });
    }
}
