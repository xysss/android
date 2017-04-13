package com.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * 蓝牙客户端连接线程
 *
 */
public class BluetoothClientConnThread extends Thread{

	private Handler serviceHandler;		//用于向客户端Service回传消息的handler
	private BluetoothDevice serverDevice;	//服务器设备
	private BluetoothSocket socket;		//通信Socket
	private BluetoothTool bluetoothTool;
	/**
	 * 构造函数
	 * @param handler
	 * @param serverDevice
	 */
	public BluetoothClientConnThread(Handler handler, BluetoothDevice serverDevice) {
		this.serviceHandler = handler;
		this.serverDevice = serverDevice;
		bluetoothTool=BluetoothTool.getInstance();
	}


	@Override
	public void run() {

		BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
		try {
			Method method = serverDevice.getClass().getMethod("createRfcommSocket",new Class[] { int.class });
			  socket = (BluetoothSocket) method.invoke(serverDevice, Integer.valueOf(1));

			//socket = serverDevice.createRfcommSocketToServiceRecord(BluetoothTools.PRIVATE_UUID);
			BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
			Log.d("ACTION_SELECTED_DEVICE", "命中设备,进入Thread处理");
			socket.connect();

		} catch (Exception ex) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//发送连接失败消息
			serviceHandler.obtainMessage(BluetoothTools.MESSAGE_CONNECT_ERROR).sendToTarget();
			Log.d("MESSAGE_CONNECT_ERROR", "连接FAIL");
			return;
		}
		//发送连接成功消息，消息的obj参数为连接的socket
		Message msg = serviceHandler.obtainMessage();
		msg.what = BluetoothTools.MESSAGE_CONNECT_SUCCESS;
		msg.obj = socket;
		msg.sendToTarget();
		Log.d("MESSAGE_CONNECT_SUCCESS", "socket连接success");
	}
}
