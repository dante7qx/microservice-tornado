package com.tornado.common.api.service;

import com.tornado.common.api.exception.TornadoAPIServiceException;
import com.tornado.common.api.vo.UserAuthVO;

/**
 * 用户认证 Service, 添加缓存
 * 
 * @author dante
 *
 */
public interface UserAuthService {
	
	/**
	 * 根据 account 获取用户信息
	 * 
	 * @param account
	 * @return
	 * @throws TornadoAPIServiceException
	 */
	public UserAuthVO findByAccount(String account) throws TornadoAPIServiceException;
	
}
