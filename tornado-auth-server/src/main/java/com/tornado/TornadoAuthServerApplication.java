package com.tornado;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.tornado.common.api.prop.TornadoProperties;

@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties(TornadoProperties.class)
@MapperScan("com.tornado.authserver.mapper")
public class TornadoAuthServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(TornadoAuthServerApplication.class, args);
	}
}