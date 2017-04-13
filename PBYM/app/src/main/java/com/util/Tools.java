package com.util;

import android.util.Log;
import android.widget.Toast;

import com.smart.smartApplication;

public class Tools {
    public static void Log(String output){
        Log.w("Tools", output);
    }

    public static void showToast(String message) {
        Toast.makeText(smartApplication.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
