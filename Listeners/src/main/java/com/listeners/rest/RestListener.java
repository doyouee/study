package com.listeners.rest;

import java.util.HashMap;
import java.util.Map;

import javax.jws.WebService;
import javax.servlet.http.HttpServlet;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.listeners.rest.testDto;

//@WebService
@RestController
public class RestListener extends HttpServlet{
//public class testController {
		
//		@PostMapping("/rest")
		@RequestMapping(value = "/rest" , method = RequestMethod.POST)
		public Map<String, Object> testListener(@RequestBody testDto test) {
			System.out.println("rest rest rest");
			Map<String, Object> result = new HashMap<String, Object>();
			if(test != null) {
				System.out.println(test.toString());
				result.put("status", "success");
				result.put("value", test.toString());
			} else {
				result.put("status", "fail");
			}
			return result;
		}
}