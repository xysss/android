package com.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.smart.R;

public class BindingEquipmentDialog extends Dialog {

    public TextView bindingEquipmentTextView;
    public ProgressBar bindingEquipmentProgressBar;
    private static BindingEquipmentDialog bindingEquipmentDialog;
    private BindingEquipmentDialog(Context c) {
        super(c);
    }


    public synchronized static BindingEquipmentDialog getInstance(Context c){
        if(bindingEquipmentDialog==null)
        {
            bindingEquipmentDialog = new BindingEquipmentDialog(c);
        }
        return bindingEquipmentDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.bangdingshebei_layout);
        bindingEquipmentTextView = (TextView) findViewById(R.id.bangdingshebei_tishikuang_TextView_widget);
        bindingEquipmentProgressBar = (ProgressBar) findViewById(R.id.bangdingshebei_tishikuang_ProgressBar_widget);
    }
}
