package com.tornado.api.security;

import java.util.Map;

import org.assertj.core.util.Maps;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tornado.api.TornadoAPIGetwayApplicationTests;

/**
 * JWT 工具测试类
 * 
 * @author dante
 *
 */
public class JwtTokenUtilsTest extends TornadoAPIGetwayApplicationTests {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtilsTest.class);
	
	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	
	@Test
	public void testGenerateToken() {
		Map<String, Object> claims = Maps.newHashMap("sub", "dante");
		String generateToken = jwtTokenUtils.generateToken(claims);
		LOGGER.debug("generateToken => {}", generateToken);
	}
	
}
