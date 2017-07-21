package com.tornado.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tornado.api.dto.req.UserAuthReqDTO;
import com.tornado.api.dto.resp.UserAuthRespDTO;
import com.tornado.api.service.UserAuthService;
import com.tornado.commom.dto.resp.BaseResp;
import com.tornado.commom.dto.resp.RespCodeEnum;
import com.tornado.common.api.exception.TornadoAPIServiceException;

@RestController
public class AuthController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private UserAuthService userAuthService;
	
	@PostMapping("/auth")
	public BaseResp<UserAuthRespDTO> login(@RequestBody UserAuthReqDTO userReqDTO) {
		BaseResp<UserAuthRespDTO> result = new BaseResp<>();
		try {
			UserAuthRespDTO authResp = userAuthService.login(userReqDTO);
			result.setData(authResp);
		} catch (TornadoAPIServiceException e) {
			result.setResultCode(RespCodeEnum.FAILURE.code());
			LOGGER.error("auth account: {} error.", userReqDTO.getAccount(), e);
		}
		return result;
	}
}
