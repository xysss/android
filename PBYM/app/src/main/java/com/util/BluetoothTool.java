package com.util;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BluetoothTool {

    //蓝牙适配器
    public final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    //搜索到的远程设备集合
    public List<BluetoothDevice> discoveredDevices = new ArrayList<BluetoothDevice>();
    //蓝牙通讯线程
    public BluetoothCommunThread communThread;

    private static BluetoothTool mBluetooth = null;

    private  BluetoothTool() {

    }

    public synchronized static  BluetoothTool getInstance()                //获取BluetoothTool类实例
    {
        if(mBluetooth==null){
            mBluetooth=new BluetoothTool();
        }
        return mBluetooth;
    }

    public boolean connectDevice()         //开始搜寻远端蓝牙设备
    {
        discoveredDevices.clear();	//清空存放设备的集合
        if(!bluetoothAdapter.isEnabled())
        {
            bluetoothAdapter.enable();	//打开蓝牙
        }
        bluetoothAdapter.startDiscovery();
        Tools.Log("打开蓝牙，搜索");
        return true;
    }

    public boolean disconnectDevice()   //断开蓝牙
    {
        bluetoothAdapter.cancelDiscovery();
        return false;
    }

}
