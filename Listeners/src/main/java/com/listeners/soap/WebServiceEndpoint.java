package com.listeners.soap;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.javainuse.InputSOATest;
import com.javainuse.ObjectFactory;
import com.javainuse.OutputSOATest;

@Endpoint
public class WebServiceEndpoint {

	private static final String NAMESPACE_URI = "http://javainuse.com";
//	private static final String NAMESPACE_URI = "http://soapListener.soap.com";

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "inputSOATest")
	@ResponsePayload
	public OutputSOATest hello(@RequestPayload InputSOATest request) {
		OutputSOATest response = null;
		try {
			System.out.println("WebServiceEndpoint.hello()");
			String outputString = "Hello " + request.getTest() + "!";

			ObjectFactory factory = new ObjectFactory();
			response = factory.createOutputSOATest();
			response.setResult(outputString);

			System.out.println("WebServiceEndpoint");
			return response;
		} catch (Exception e) {
			System.out.println("error " + e.toString());
		}

		return response;
		
	}
}