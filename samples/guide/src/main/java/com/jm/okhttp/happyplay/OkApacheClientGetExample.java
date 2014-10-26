package com.jm.okhttp.happyplay;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import com.squareup.okhttp.apache.OkApacheClient;

public class OkApacheClientGetExample {

	// static String mUrl =
	// "https://raw.github.com/square/okhttp/master/README.md";

	static String mUrl = "http://alipay-jm:8888/contributors.html";

	public static void main(String[] args) throws IOException,
			InterruptedException {

		Thread thread1 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println("开始=====1");
					OkApacheClient okApacheClient = new OkApacheClient();
					HttpGet httpGet = new HttpGet(mUrl);
					HttpResponse execute = okApacheClient.execute(httpGet);
					HttpEntity entity = execute.getEntity();
					System.out.println("================>"
							+ EntityUtils.toString(entity));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		Thread thread2 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					System.out.println("����=====2");
					OkApacheClient okApacheClient2 = new OkApacheClient();
					HttpGet httpGet2 = new HttpGet(mUrl);
					HttpResponse execute2 = okApacheClient2.execute(httpGet2);
					HttpEntity entity2 = execute2.getEntity();
					System.out.println("================>" + EntityUtils.toString(entity2));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		thread1.start();
		thread2.start();
	}

}
