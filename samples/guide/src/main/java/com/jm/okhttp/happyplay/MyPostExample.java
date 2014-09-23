package com.jm.okhttp.happyplay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.squareup.okhttp.OkHttpClient;

/**
 * 
 * 自己练习用的post
 * @author jm
 *
 */
public class MyPostExample {

	OkHttpClient client = new OkHttpClient();
	
	String post(URL url,byte[] body) {
		try {
			HttpURLConnection connection = client.open(url);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			
			OutputStream outputStream = connection.getOutputStream();
			outputStream.write(body);
			outputStream.flush();
			
			InputStream inputStream = connection.getInputStream();
			try {
				return readFirstLine(inputStream);
			} finally {
				inputStream.close();
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	void run() throws IOException {
		byte body[] = bowlingJson("Jesse", "Jake").getBytes("UTF-8");
		String post = post(new URL("http://www.roundsapp.com/post"), body);
		System.out.println(post);
	}
	
	String bowlingJson(String player1, String player2) {
	    return "{'winCondition':'HIGH_SCORE',"
	        + "'name':'Bowling',"
	        + "'round':4,"
	        + "'lastSaved':1367702411696,"
	        + "'dateStarted':1367702378785,"
	        + "'players':["
	        + "{'name':'" + player1 + "','history':[10,8,6,7,8],'color':-13388315,'total':39},"
	        + "{'name':'" + player2 + "','history':[6,10,5,10,10],'color':-48060,'total':41}"
	        + "]}";
	  }
	
	String readFirstLine(InputStream in) throws Exception {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
		StringBuilder stringBuilder = new StringBuilder();
		for (String line = bufferedReader.readLine();  line != null && !line.isEmpty(); line = bufferedReader.readLine()) {
			stringBuilder.append(line).append("\n");
		}
		return stringBuilder.toString();
	}

}
