package com.clients.rest;

import java.net.URI;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RestClient {
	public static void main (String[] args) {
		try {
			URI uri = new URI("http://localhost:8080/rest");
			
			CloseableHttpClient client = HttpClientBuilder.create().disableContentCompression().build();
			
			HttpPost httpPost = new HttpPost(uri);
			HashMap<String, Object> param = new HashMap<String, Object>();
			
			param.put("name", "abc");
			param.put("age", 31);
			param.put("school", "university");
			
			ObjectMapper paramMapper = new ObjectMapper();
			String json = paramMapper.writeValueAsString(param);
			
			httpPost.setHeader("Content-Type", "application/json");
			httpPost.setEntity(new StringEntity(json, "UTF-8"));
			
			CloseableHttpResponse response = client.execute(httpPost);
			
			String content = EntityUtils.toString(response.getEntity(), "UTF-8");
			
			if(response.getStatusLine().getStatusCode() == HttpServletResponse.SC_OK) {
				System.out.println(" ● 성공 >> " + content);
			} else {
				System.out.println(" ● 실패 >> " + content);
			}
		} catch(Exception e) {
			System.out.println("● ● ● ● ● ● ● ● ● ● ● ● ● 에 러 에 러  ● ● ● ● ● ● ● ● ● ● ● ● ● ●");
			e.printStackTrace();
		}
		
	}
}