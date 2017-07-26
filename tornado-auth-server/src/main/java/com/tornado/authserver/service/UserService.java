package com.tornado.authserver.service;

import com.tornado.authserver.dto.req.UserAuthReqDTO;
import com.tornado.authserver.dto.resp.UserAuthRespDTO;
import com.tornado.authserver.dto.resp.UserSecurityRespDTO;
import com.tornado.common.api.exception.TornadoAPIServiceException;

/**
 * 用户 Service
 * 
 * @author dante
 *
 */
public interface UserService {
	
	/**
	 * 根据Id获取用户
	 * 
	 * @param id
	 * @return
	 * @throws TornadoAPIServiceException
	 */
	public UserSecurityRespDTO findById(Long id) throws TornadoAPIServiceException;

	/**
	 * 用户认证
	 * 
	 * @param userAuthReq
	 * @return
	 * @throws TornadoAPIServiceException
	 */
	public UserAuthRespDTO login(UserAuthReqDTO userAuthReq) throws TornadoAPIServiceException;
	
	/**
	 * 刷新 JWT token
	 * 
	 * @param oldToken
	 * @return
	 * @throws TornadoAPIServiceException
	 */
	public String refreshToken(String oldToken) throws TornadoAPIServiceException;
}
