package com.util;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.activity.MainActivity;

import org.bitlet.weupnp.Main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 蓝牙通讯线程
 */
public class  BluetoothCommunThread extends Thread {

	private Handler serviceHandler;		//与Service通信的Handler
	private BluetoothSocket socket;
	private ReadWriteLock rwl = new ReentrantReadWriteLock();
	public volatile boolean isRun = true;	//运行标志位
	List<Integer> list =new ArrayList<Integer>();
	WaitEvent state=new WaitEvent();

	/**
	 * 构造函数
	 * @param handler 用于接收消息
	 * @param socket
	 */
	public BluetoothCommunThread(Handler handler, BluetoothSocket socket) {
		this.serviceHandler = handler;
		this.socket = socket;

	}

	//发送数据
	public void sendMessageHandle(String msg)
	{
		rwl.writeLock().lock();// 取到写锁
		try{
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (socket == null)
			{
				Log.d("socket","没有连接");
				return;
			}
			try {
				OutputStream os = socket.getOutputStream();
				os.write(msg.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}finally {
			rwl.writeLock().unlock();// 释放写锁
		}


	}
	//读取数据
		//@Override
		public void run1() {

			Log.i("OK","BEGIN mConnectedThread");
			InputStream mmInStream = null;
			byte[] buffer = new byte[1024];

			int len = 0;
			int bytes=-1;
			int i = 0;

			while (true) {
				try {
					mmInStream = socket.getInputStream();

					Log.i("OK", "Read from the InputStream...");
					if ((bytes = mmInStream.read(buffer)) > -1) {
						Log.d("Tread","NO 4");
						byte[] buf_data = new byte[1024];
						for (int a = 0; a < bytes; a++) {
							buf_data[a] = buffer[a];
							i = a;
						}

					if (i == 3) {
						len = buffer[2] + 10;
					}
					Log.i("OK", "Read from the InputStream, data is" + buf_data[i]);
					if (i == len) {
						serviceHandler.obtainMessage(BluetoothTools.MESSAGE_READ_OBJECT, len, -1, buffer).sendToTarget();
						len = 0;
						i = 0;
					}
				}
				} catch (IOException e) {

					Log.d("OK","disconnected", e);
					try {
						mmInStream.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
			}
		}
	@Override
		public void  run() {

		byte[] buffer = new byte[1024];
		int bytes;
		InputStream mmInStream = null;

		while (true) {
			if (!isRun) {
					break;
				}
			try {
				mmInStream = socket.getInputStream();
				while(mmInStream.available()==0){

				}
				rwl.readLock().lock();// 取到读锁
				try{
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if ((bytes = mmInStream.read(buffer)) > 0) {
						byte[] buf_data = new byte[bytes];

						for (int i = 0; i < bytes; i++) {
							buf_data[i] = buffer[i];
						}
						String s =new String(buf_data);

						Log.d("接收到的原始数据为:", s);

						Message msg = serviceHandler.obtainMessage();
						Bundle bundle = new Bundle();
						bundle.putString("foodId", s);

						msg.setData(bundle);
						msg.what = BluetoothTools.MESSAGE_READ_OBJECT;
						msg.sendToTarget();

					}
				}finally {
					rwl.readLock().unlock();// 释放读锁
				}

			} catch (IOException e) {

				e.printStackTrace();
				break;
			}
		}

	}

}
