package com.tornado.sysmgr.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tornado.commom.dto.resp.BaseResp;
import com.tornado.commom.dto.resp.RespCodeEnum;
import com.tornado.sysmgr.api.dto.resp.UserAuthRespDTO;
import com.tornado.sysmgr.api.dto.resp.UserResourceRespDTO;
import com.tornado.sysmgr.api.service.ResourceService;
import com.tornado.sysmgr.api.service.UserService;

/**
 * 用户登录 REST API
 * 
 * @author dante
 *
 */
@RestController
public class LoginController {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private ResourceService resourceService;

	/**
	 * 指定account登录请求
	 * 
	 * @param account
	 * @return
	 */
	@PostMapping(value = "/login/{account}")
	public BaseResp<UserAuthRespDTO> login(@PathVariable String account) {
		BaseResp<UserAuthRespDTO> result = new BaseResp<>();
		try {
			LOGGER.info("用户： {} 请求登录.", account);
			UserAuthRespDTO userAuthRespDTO = userService.loginAccount(account);
			result.setData(userAuthRespDTO);
		} catch (Exception e) {
			LOGGER.error("login account: {} error.", account, e);
			result.setResultCode(RespCodeEnum.FAILURE.code());
		}
		return result;
	}
	
	/**
	 * 获取指定登录userId的所有资源
	 * 
	 * @param userId
	 * @return
	 */
	@PostMapping(value = "/login_user_menu/{userId}")
	public BaseResp<List<UserResourceRespDTO>> loginUserMenu(@PathVariable Long userId) {
		BaseResp<List<UserResourceRespDTO>> result = new BaseResp<>();
		try {
			List<UserResourceRespDTO> userResources = resourceService.findUserResourceByUserId(userId);
			result.setData(userResources);
		} catch (Exception e) {
			LOGGER.error("loginUserMenu userId: {} error.", userId, e);
			result.setResultCode(RespCodeEnum.FAILURE.code());
		}
		return result;
	}
}
