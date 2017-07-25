package com.tornado.authserver.service;

import com.tornado.authserver.dto.req.UserAuthReqDTO;
import com.tornado.authserver.dto.resp.UserAuthRespDTO;
import com.tornado.authserver.dto.resp.UserRespDTO;
import com.tornado.common.api.exception.TornadoAPIServiceException;

/**
 * 用户 Service
 * 
 * @author dante
 *
 */
public interface UserService {
	
	/**
	 * 根据account获取用户
	 * 
	 * @param account
	 * @return
	 * @throws TornadoAPIServiceException
	 */
	public UserRespDTO findByAccount(String account) throws TornadoAPIServiceException;

	
	/**
	 * 用户认证
	 * 
	 * @param userAuthReq
	 * @return
	 * @throws TornadoAPIServiceException
	 */
	public UserAuthRespDTO login(UserAuthReqDTO userAuthReq) throws TornadoAPIServiceException;
	
	
}
