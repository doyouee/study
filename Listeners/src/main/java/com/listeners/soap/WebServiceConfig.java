package com.listeners.soap;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) { // ServletRegistrationBean 을 이용해서 bean 등록
		MessageDispatcherServlet servlet = new MessageDispatcherServlet(); // MessageDispatcherServlet : 웹 서비스 메시지의 단순화된 전달을 위한 서블릿
		servlet.setApplicationContext(applicationContext); // springframework 서블릿을 springbean으로 등록

		System.out.println("messageDispatcherServlet");
		
		return new ServletRegistrationBean(servlet, "/javainuse/ws/*"); // /javainuse/ws/wsdl/helloworld.wsdl
	}

	@Bean(name="helloworld")
	public Wsdl11Definition defaultWsdl11Definition() { // Wsdl11Definition : wsdl 정의 인터페이스
		SimpleWsdl11Definition wsdl11Definition = new SimpleWsdl11Definition();
		wsdl11Definition.setWsdl(new ClassPathResource("/wsdl/helloworld.wsdl"));

		System.out.println("defaultWsdl11Definition");
		
		return wsdl11Definition;
	}
}
