package com.tornado.common.api.constant;

/**
 * 公共缓存常量类
 * 
 * @author dante
 *
 */
public final class RedisCacheConsts {
	
	private RedisCacheConsts() {
		throw new IllegalAccessError("RedisCacheConsts 工具类，不能实例化！");
	}

	/**
	 * 用户缓存名称
	 */
	public final static String FIND_USER_AUTH_CACHE = "FIND_USER_AUTH_CACHE";
	
	/**
	 * IP规则缓存名称
	 */
	public final static String FIND_IP_RULE_CACHE = "FIND_IP_RULE_CACHE";
}
