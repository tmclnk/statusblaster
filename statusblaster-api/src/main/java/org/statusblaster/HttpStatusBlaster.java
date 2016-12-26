package org.statusblaster;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpStatusBlaster implements StatusBlaster {
	private String baseUrl;

	/**
	 * 
	 * @param baseUrl e.g. http://localhost:9376/ping
	 * @throws MalformedURLException
	 */
	public HttpStatusBlaster(final String baseUrl) throws MalformedURLException {
		this.baseUrl = baseUrl;
		if(! baseUrl.endsWith("/") ){
			this.baseUrl = baseUrl + "/";
		}
	}
	
	@Override
	public void blast(StatusMessage status) {
		URL url;
		try {
			url = new URL(baseUrl + status.getKey());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			
			System.out.println(conn.getResponseCode());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
