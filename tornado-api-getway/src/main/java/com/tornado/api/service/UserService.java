package com.tornado.api.service;

import java.util.List;

import com.tornado.api.vo.LoginUserMenuVO;
import com.tornado.api.vo.LoginUserVO;
import com.tornado.common.api.exception.TornadoAPIServiceException;

/**
 * 用户 Service
 * 
 * @author dante
 *
 */
public interface UserService {
	
	/**
	 * 根据account获取登录用户信息
	 * 
	 * @param account
	 * @return
	 * @throws TornadoAPIServiceException
	 */
	public LoginUserVO findByAccount(String account) throws TornadoAPIServiceException;
	
	/**
	 * 根据userId获取用户资源信息
	 * 
	 * @param userId
	 * @return
	 * @throws TornadoAPIServiceException
	 */
	public List<LoginUserMenuVO> findUserResourceByUserId(Long userId) throws TornadoAPIServiceException;
}
