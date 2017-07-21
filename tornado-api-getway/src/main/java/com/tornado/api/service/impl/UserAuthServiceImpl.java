package com.tornado.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tornado.api.client.UserFeignClient;
import com.tornado.api.dto.req.UserAuthReqDTO;
import com.tornado.api.dto.resp.UserAuthRespDTO;
import com.tornado.api.dto.resp.UserRespDTO;
import com.tornado.api.security.JwtTokenUtils;
import com.tornado.api.security.TornadoUserDetailsService;
import com.tornado.api.service.UserAuthService;
import com.tornado.api.vo.LoginUserMenuVO;
import com.tornado.api.vo.LoginUserVO;
import com.tornado.commom.dto.resp.BaseResp;
import com.tornado.commom.dto.resp.RespCodeEnum;
import com.tornado.common.api.exception.TornadoAPIServiceException;
import com.tornado.common.api.security.TornadoLoginUser;
import com.tornado.common.api.security.TornadoPrincipal;

@Service
public class UserAuthServiceImpl implements UserAuthService {
	
	@Autowired
	private UserFeignClient userFeignClient;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	@Autowired
	private TornadoUserDetailsService userDetailsService;

	@Override
	public UserAuthRespDTO login(UserAuthReqDTO userAuthReq) throws TornadoAPIServiceException {
		UserAuthRespDTO authResp = new UserAuthRespDTO();
		final String account = userAuthReq.getAccount().trim();
		final String password = userAuthReq.getPassword().trim();
		UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(account, password);
		final Authentication authentication = authenticationManager.authenticate(upToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final TornadoPrincipal userDetails = (TornadoPrincipal) userDetailsService.loadUserByUsername(account);
		final String token = jwtTokenUtils.generateToken(userDetails);
		authResp.setAccessToken(token);
		authResp.setUser(convertToUserRespDTO(userDetails.getSpiritLoginUser()));
		return authResp;
	}
	
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

	private UserRespDTO convertToUserRespDTO(TornadoLoginUser loginUser) {
		UserRespDTO user = new UserRespDTO();
		user.setId(loginUser.getId());
		user.setAccount(loginUser.getAccount());
		user.setName(loginUser.getName());
		user.setEmail(loginUser.getEmail());
		user.setAuthoritys(loginUser.getAuthoritys());
		return user;
	}
	

}
