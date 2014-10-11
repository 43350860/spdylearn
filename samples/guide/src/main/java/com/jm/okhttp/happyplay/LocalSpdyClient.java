package com.jm.okhttp.happyplay;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.internal.http.HttpsURLConnectionImpl;

public class LocalSpdyClient {
	
	OkHttpClient okHttpClient = new OkHttpClient();
	
	static Object object = new Object();
	
	public void run() throws Exception{
		
		HttpURLConnection connection = okHttpClient.open(new URL("https://alipay-jm:8888/contributors.html"));
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		HttpsURLConnectionImpl connectionImpl = (HttpsURLConnectionImpl) connection;
		connectionImpl.setHostnameVerifier(new HostnameVerifier() {
			
			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
			
		});
		
		
		InputStream inputStream = connection.getInputStream();
		byte buff[] = new byte[1024];
		for (int i = -1; (i = inputStream.read(buff)) != -1;) {
			System.out.println(new String(buff,0,i));
		}
		inputStream.close();
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
