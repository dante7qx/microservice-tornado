package com.tornado.authserver.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tornado.authserver.bo.UserBO;
import com.tornado.authserver.bo.UserConsts;
import com.tornado.authserver.dto.req.UserAuthReqDTO;
import com.tornado.authserver.dto.resp.UserAuthRespDTO;
import com.tornado.authserver.dto.resp.UserRespDTO;
import com.tornado.authserver.mapper.UserMapper;
import com.tornado.authserver.service.UserService;
import com.tornado.authserver.util.EncryptUtils;
import com.tornado.commom.dao.exception.TornadoDaoException;
import com.tornado.common.api.exception.TornadoAPIServiceException;
import com.tornado.common.api.security.JwtTokenUtils;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	private static final Logger Logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	
	@Override
	public UserRespDTO findByAccount(String account) throws TornadoAPIServiceException {
		UserRespDTO userRespDTO;
		try {
			UserBO userBO = userMapper.findUserByAccount(account);
			if(!UserConsts.STATUS_NORMAL.equalsIgnoreCase(userBO.getStatus())) {
				throw new TornadoAPIServiceException("用户["+account+"]状态["+userBO.getStatus()+"]异常");
			}
			userRespDTO = convertToUserRespDTO(userBO);
		} catch (TornadoDaoException e) {
			Logger.error("用户: {} 查询错误。", account, e);
			throw new TornadoAPIServiceException(e);
		}
		return userRespDTO;
	}

	@Override
	public UserAuthRespDTO login(UserAuthReqDTO userAuthReq) throws TornadoAPIServiceException {
		UserAuthRespDTO authResp = new UserAuthRespDTO();
		final String account = userAuthReq.getAccount().trim();
		final String password = userAuthReq.getPassword().trim();
		try {
			UserBO userBO = userMapper.findUserByAccount(account);
			if(!EncryptUtils.match(password, userBO.getPassword())) {
				throw new TornadoAPIServiceException("密码不匹配");
			}
			if(!UserConsts.STATUS_NORMAL.equalsIgnoreCase(userBO.getStatus())) {
				throw new TornadoAPIServiceException("用户["+account+"]状态["+userBO.getStatus()+"]异常");
			}
			final String token = jwtTokenUtils.generateToken(account);
			authResp.setAccessToken(token);
			authResp.setUser(convertToUserRespDTO(userBO));
		} catch (TornadoDaoException e) {
			Logger.error("用户: {} 查询错误。", account, e);
			throw new TornadoAPIServiceException(e);
		}
		
		
		return authResp;
	}

	private UserRespDTO convertToUserRespDTO(UserBO userBO) {
		UserRespDTO user = new UserRespDTO();
		BeanUtils.copyProperties(userBO, user);
		return user;
	}
}
