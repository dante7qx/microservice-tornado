package com.tornado.api.constant;

/**
 * Spring Security 工具类
 * 
 * @author dante
 *
 */
public class SecurityConsts {
	
	private SecurityConsts() {
		throw new IllegalAccessError("SecurityConsts 常量类，不能实例化！");
	}
	
	public static final String ROLE_PREFIX = "AUTH_";
	public static final String LOGIN_PAGE = "/loginpage";
	public static final String INDEX_PAGE = "/";
	public static final String SESSION_TIMEOUT = "/session-timeout";
	
}
