package com.tornado.authserver.config;

import java.lang.reflect.Method;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {

	/**
	 * 缓存管理器
	 */
	@Bean
	public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
		CacheManager cacheManager = new RedisCacheManager(redisTemplate);
		return cacheManager;
	}
	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, String> template = new RedisTemplate<String, String>();
		template.setConnectionFactory(redisConnectionFactory);
		Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);  
        ObjectMapper om = new ObjectMapper();  
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);  
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);  
        jackson2JsonRedisSerializer.setObjectMapper(om);  
        template.setValueSerializer(jackson2JsonRedisSerializer);  
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();  
		return template;
	}
	@Override
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
	           @Override
	           public Object generate(Object o, Method method, Object... objects) {
	              StringBuilder sb = new StringBuilder();
	              sb.append(o.getClass().getName());
	              sb.append(method.getName());
	              for (Object obj : objects) {
	                  sb.append(obj.toString());
	              }
	              return sb.toString();
	           }
	       };
	}
	@Override
	public CacheResolver cacheResolver() {
		return super.cacheResolver();
	}
	@Override
	public CacheErrorHandler errorHandler() {
		return super.errorHandler();
	}

}