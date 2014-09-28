package com.jm.okhttp.happyplay;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.OkHttpClient;

public class LocalSpdyClient {
	
	OkHttpClient okHttpClient = new OkHttpClient();
	
	static Object object = new Object();
	
	public void run() throws Exception{
		
		HttpURLConnection connection = okHttpClient.open(new URL("http://alipay-jm:8888"));
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.connect();
		
		ConnectionPool connPool = ConnectionPool.getDefault();
		
		OutputStream outputStream = connection.getOutputStream();
		outputStream.write("中华人民共和国".getBytes("UTF-8"));
		outputStream.flush();
	}
	
	public static void main(String[] args) throws Exception {
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				LocalSpdyClient spdyClient = new LocalSpdyClient();
				try {
					spdyClient.run();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
		
		try {
			synchronized (object) {
				object.wait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
