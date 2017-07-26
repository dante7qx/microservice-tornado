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

	public final static String FIND_USER_AUTH_CACHE = "FIND_USER_AUTH_CACHE";
}
