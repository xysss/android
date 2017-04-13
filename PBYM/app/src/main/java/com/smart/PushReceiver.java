package com.smart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.activity.MainActivity;
import com.server.BluetoothService;
import com.util.BluetoothTool;
import com.util.WaitEvent;

public class PushReceiver extends BroadcastReceiver {
    WaitEvent state=new WaitEvent();
    private BluetoothTool mBluetooth;
    @Override
    public void onReceive(Context context, Intent intent) {
        mBluetooth=BluetoothTool.getInstance();
        try {
            if (intent.getAction().equals("com.pushReceiver.action")) {

                MainActivity.refreshState("接到推送");

                MainActivity.AA=-1;

                state.Init();
                BluetoothService.list.clear();

                mBluetooth.communThread.sendMessageHandle("1");

            }

        } catch (Exception e) {
                 e.printStackTrace();
        }
    }


}
