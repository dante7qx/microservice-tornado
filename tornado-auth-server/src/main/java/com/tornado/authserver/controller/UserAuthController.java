package com.tornado.authserver.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tornado.authserver.dto.req.UserAuthReqDTO;
import com.tornado.authserver.dto.resp.UserAuthRespDTO;
import com.tornado.authserver.dto.resp.UserRespDTO;
import com.tornado.authserver.service.UserService;
import com.tornado.commom.dto.resp.BaseResp;
import com.tornado.commom.dto.resp.RespCodeEnum;
import com.tornado.common.api.constant.JWTConsts;
import com.tornado.common.api.exception.TornadoAPIServiceException;
import com.tornado.common.api.prop.TornadoProperties;

/**
 * 用户认证 Controller
 * 
 * @author dante
 *
 */
@RestController
public class UserAuthController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthController.class);
	
	@Autowired
	private UserService userAuthService;
	@Autowired
	private TornadoProperties tornadoProperties;
	
	/**
	 * 用户认证
	 * 
	 * @param userReqDTO
	 * @return
	 */
	@PostMapping("/auth")
	public BaseResp<UserAuthRespDTO> login(@RequestBody UserAuthReqDTO userReqDTO) {
		BaseResp<UserAuthRespDTO> result = new BaseResp<>();
		try {
			if(!checkParams(userReqDTO)) {
				throw new TornadoAPIServiceException("请求参数非法，请检查后重试！");
			}
			UserAuthRespDTO authResp = userAuthService.login(userReqDTO);
			result.setData(authResp);
		} catch (TornadoAPIServiceException e) {
			result.setResultCode(RespCodeEnum.FAILURE.code());
			LOGGER.error("auth account: {} error.", userReqDTO.getAccount(), e);
		}
		return result;
	}
	
	/**
	 * 刷新JWT Token
	 * 
	 * @param request
	 * @return
	 */
	@PostMapping("/refresh/token")
	public BaseResp<String> refreshToken(HttpServletRequest request) {
		BaseResp<String> result = new BaseResp<>();
		try {
			String oldToken = request.getHeader(tornadoProperties.getJwt().getHeader());
			if(StringUtils.isEmpty(oldToken)) {
				throw new TornadoAPIServiceException("未发现Token信息。");
			}
			String refreshToken = userAuthService.refreshToken(oldToken);
			result.setData(refreshToken);
		} catch (TornadoAPIServiceException e) {
			result.setResultCode(RespCodeEnum.FAILURE.code());
			LOGGER.error("refreshToken error.",  e);
		}
		return result;
	}
	
	/**
	 * 获取用户信息
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/auth/{account}")
	public BaseResp<UserRespDTO> queryByAccount(@PathVariable String account) {
		BaseResp<UserRespDTO> result = new BaseResp<>();
		try {
			Long id = Long.parseLong(account.split(JWTConsts.TOKEN_SPLIT)[1]);
			UserRespDTO userResp = userAuthService.findById(id);
			result.setData(userResp);
		} catch (TornadoAPIServiceException e) {
			result.setResultCode(RespCodeEnum.FAILURE.code());
			LOGGER.error("query auth user: {} error.", account, e);
		}
		return result;
	}
	
	
	/**
	 * 用户认证参数校验
	 * 
	 * @param userReqDTO
	 * @return
	 */
	private boolean checkParams(UserAuthReqDTO userReqDTO) {
		boolean valid = true;
		if(StringUtils.isEmpty(userReqDTO.getAccount()) || StringUtils.isEmpty(userReqDTO.getPassword())) {
			valid = false;
		}
		return valid;
	}
	
}
