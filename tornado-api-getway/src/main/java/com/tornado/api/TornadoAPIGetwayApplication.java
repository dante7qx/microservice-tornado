package com.tornado.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.tornado.api.filter.VisitLogFilter;
import com.tornado.api.prop.TornadoProperties;

@SpringBootApplication
@EnableFeignClients
@EnableZuulProxy
@EnableConfigurationProperties(TornadoProperties.class)
public class TornadoAPIGetwayApplication {
	
	@Bean
	public VisitLogFilter visitLogFilter() {
		return new VisitLogFilter();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TornadoAPIGetwayApplication.class, args);
	}
}
