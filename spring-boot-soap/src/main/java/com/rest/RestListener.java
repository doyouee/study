package com.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//@WebService
@RestController
public class RestListener extends HttpServlet{
//public class testController {
		
//		@PostMapping("/rest")
		@RequestMapping(value = "/rest" , method = RequestMethod.GET)
		public Map<String, Object> testListener(@RequestBody TestDto test) {
			System.out.println("rest rest rest");
			Map<String, Object> result = new HashMap<String, Object>();
			if(test != null) {
				System.out.println(test.toString());
				result.put("status", "success!!!");
				result.put("value", test.toString());
			} else {
				result.put("status", "fail!!!");
			}
			return result;
		}
}
