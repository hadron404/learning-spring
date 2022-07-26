package com.example.quickstartfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
// 0.启动feign
@EnableFeignClients
public class QuickstartFeignApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickstartFeignApplication.class, args);
	}

}
