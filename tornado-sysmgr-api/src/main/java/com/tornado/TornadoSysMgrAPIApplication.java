package com.tornado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import com.tornado.common.api.prop.TornadoProperties;


@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableConfigurationProperties(TornadoProperties.class)
public class TornadoSysMgrAPIApplication {
	public static void main(String[] args) {
		SpringApplication.run(TornadoSysMgrAPIApplication.class, args);
	}
}
