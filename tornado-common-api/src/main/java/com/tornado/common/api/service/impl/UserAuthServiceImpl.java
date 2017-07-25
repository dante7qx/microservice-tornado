package com.tornado.common.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tornado.commom.dto.resp.BaseResp;
import com.tornado.commom.dto.resp.RespCodeEnum;
import com.tornado.common.api.client.UserAuthFeignClient;
import com.tornado.common.api.exception.TornadoAPIServiceException;
import com.tornado.common.api.service.UserAuthService;
import com.tornado.common.api.vo.UserAuthVO;

@Service
public class UserAuthServiceImpl implements UserAuthService {
	
	@Autowired
	private UserAuthFeignClient userAuthFeignClient;

	@Override
	@HystrixCommand
	public UserAuthVO findByAccount(String account) throws TornadoAPIServiceException {
		UserAuthVO userAuthVO = null;
		BaseResp<UserAuthVO> result = userAuthFeignClient.findByAccount(account);
		if(result.getResultCode() == RespCodeEnum.SUCCESS.code()) {
			userAuthVO = result.getData();
		}
		return userAuthVO;
	}
	
}
