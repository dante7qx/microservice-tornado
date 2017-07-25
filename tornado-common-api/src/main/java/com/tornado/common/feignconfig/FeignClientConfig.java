package com.tornado.common.feignconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;

/**
 * Feign Client 配置
 * 
 * @author dante
 */
@Configuration
public class FeignClientConfig {

	/**
	 * Feign 日志配置
	 * 
	 * 1、FeignClient注解中添加
	 * 例如：@FeignClient(..., configuration=FeignClientConfig.class)
	 * 2、在日志配置文件中加入具体Feign Client的配置
	 * 例如：<logger name="org.dante.springcloud.feignclient.UserFeignConfigClient" level="DEBUG" />
	 * 说明：level="DEBUG"，只有DEBUG才生效
	 * 
	 * @return
	 */
	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}
	
}
