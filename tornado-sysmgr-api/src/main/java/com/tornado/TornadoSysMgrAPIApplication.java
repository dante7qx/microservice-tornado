package com.tornado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TornadoSysMgrAPIApplication {
	public static void main(String[] args) {
		SpringApplication.run(TornadoSysMgrAPIApplication.class, args);
	}
}
