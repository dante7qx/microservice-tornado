package com.tornado.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tornado.api.client.UserFeignClient;
import com.tornado.api.service.UserService;
import com.tornado.api.vo.LoginUserMenuVO;
import com.tornado.api.vo.LoginUserVO;
import com.tornado.commom.dto.resp.BaseResp;
import com.tornado.commom.dto.resp.RespCodeEnum;
import com.tornado.common.api.exception.TornadoAPIServiceException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserFeignClient userFeignClient;

	@Override
	@HystrixCommand
	public LoginUserVO findByAccount(String account) throws TornadoAPIServiceException {
		LoginUserVO loginUserVO = null;
		BaseResp<LoginUserVO> loginUserResp = userFeignClient.findByAccount(account);
		if(loginUserResp.getResultCode() == RespCodeEnum.SUCCESS.code()) {
			loginUserVO = loginUserResp.getData();
		}
		return loginUserVO;
	}

	@Override
	@HystrixCommand
	public List<LoginUserMenuVO> findUserResourceByUserId(Long userId) throws TornadoAPIServiceException {
		List<LoginUserMenuVO> userMenus = null;
		BaseResp<List<LoginUserMenuVO>> userMenuResp = userFeignClient.findUserMenuByUserId(userId);
		if(userMenuResp.getResultCode() == RespCodeEnum.SUCCESS.code()) {
			userMenus = userMenuResp.getData();
		}
		return userMenus;
	}

}
