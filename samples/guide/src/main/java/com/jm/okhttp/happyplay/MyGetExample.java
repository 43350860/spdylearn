package com.jm.okhttp.happyplay;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.squareup.okhttp.OkHttpClient;

public class MyGetExample {
	
	OkHttpClient mClient = new OkHttpClient();
	
	InputStream mInputStream = null;
	
	String mUrl = "https://raw.github.com/square/okhttp/master/README.md";
	
	void run() throws Exception {
		HttpURLConnection connection = mClient.open(new URL(mUrl));
		exchangeData(connection);
		mInputStream.close();
	}

	private void exchangeData(HttpURLConnection connection) throws IOException,
			UnsupportedEncodingException {
		
		/*if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new IOException("Unexcepted HTTP response "+connection.getResponseCode()+" "+connection.getResponseMessage());
		}*/
		
    	mInputStream = connection.getInputStream();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte body[] = new byte[1024];
		for (int count = -1; (count = mInputStream.read(body)) != -1; ) {
			outputStream.write(body, 0, count);
		}
		
		String content = new String(outputStream.toByteArray(),"utf-8");
		System.out.println("=================================================");
		System.out.println(content);
		
		//mInputStream.close();
		outputStream.close();
	}

	public static void main(String[] args) throws Exception {
		new MyGetExample().run();
	}
	
}
