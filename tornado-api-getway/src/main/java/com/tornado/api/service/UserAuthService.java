package com.tornado.api.service;

import java.util.List;

import com.tornado.api.dto.req.UserAuthReqDTO;
import com.tornado.api.dto.resp.UserAuthRespDTO;
import com.tornado.api.vo.LoginUserMenuVO;
import com.tornado.api.vo.LoginUserVO;
import com.tornado.common.api.exception.TornadoAPIServiceException;

/**
 * 用户认证 Service
 * 
 * @author dante
 *
 */
public interface UserAuthService {
	
	/**
	 * 用户认证
	 * 
	 * @param userAuthReq
	 * @return
	 * @throws TornadoAPIServiceException
	 */
	public UserAuthRespDTO login(UserAuthReqDTO userAuthReq) throws TornadoAPIServiceException;
	
	
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
