package com.listeners;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class ClientApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ClientApplication.class, args);
		System.out.println("* * * 시 작 * * *");
	}
}
