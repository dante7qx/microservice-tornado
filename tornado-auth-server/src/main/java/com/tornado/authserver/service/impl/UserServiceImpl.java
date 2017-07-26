package com.tornado.authserver.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tornado.authserver.bo.UserBO;
import com.tornado.authserver.bo.UserConsts;
import com.tornado.authserver.dto.req.UserAuthReqDTO;
import com.tornado.authserver.dto.resp.UserAuthRespDTO;
import com.tornado.authserver.dto.resp.UserRespDTO;
import com.tornado.authserver.dto.resp.UserSecurityRespDTO;
import com.tornado.authserver.mapper.UserMapper;
import com.tornado.authserver.service.UserService;
import com.tornado.authserver.util.EncryptUtils;
import com.tornado.commom.dao.exception.TornadoDaoException;
import com.tornado.commom.util.DateUtils;
import com.tornado.common.api.constant.RedisCacheConsts;
import com.tornado.common.api.exception.TornadoAPIServiceException;
import com.tornado.common.api.prop.TornadoProperties;
import com.tornado.common.api.security.JwtTokenUtils;

@Service
@Transactional(readOnly = true)
@CacheConfig(cacheNames = RedisCacheConsts.FIND_USER_AUTH_CACHE)
public class UserServiceImpl implements UserService {

	private static final Logger Logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	@Autowired
	private TornadoProperties tornadoProperties;

	@Override
	@Cacheable(key = "caches[0].name.concat('_').concat(#id)")
	public UserSecurityRespDTO findById(Long id) throws TornadoAPIServiceException {
		UserSecurityRespDTO userRespDTO;
		try {
			UserBO userBO = userMapper.findUserById(id);
			if (!UserConsts.STATUS_NORMAL.equalsIgnoreCase(userBO.getStatus())) {
				throw new TornadoAPIServiceException("用户[" + id + "]状态[" + userBO.getStatus() + "]异常");
			}
			userRespDTO = convertToUserSecurityRespDTO(userBO);
		} catch (TornadoDaoException e) {
			Logger.error("用户: {} 查询错误。", id, e);
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
			if (!EncryptUtils.match(password, userBO.getPassword())) {
				throw new TornadoAPIServiceException("密码不匹配");
			}
			if (!UserConsts.STATUS_NORMAL.equalsIgnoreCase(userBO.getStatus())) {
				throw new TornadoAPIServiceException("用户[" + account + "]状态[" + userBO.getStatus() + "]异常");
			}
			final String token = jwtTokenUtils.generateToken(userBO.getId(), account);
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

	private UserSecurityRespDTO convertToUserSecurityRespDTO(UserBO userBO) {
		UserSecurityRespDTO user = new UserSecurityRespDTO();
		BeanUtils.copyProperties(userBO, user);
		return user;
	}

	@Override
	public String refreshToken(String oldToken) throws TornadoAPIServiceException {
		String refreshedToken = null;
		try {
			final String token = oldToken.substring(tornadoProperties.getJwt().getTokenHead().length());
			String account = jwtTokenUtils.getUsernameFromToken(token);
			UserBO userBO = userMapper.findUserById(Long.parseLong(account.split("||")[1]));
			Date lastPwdUpdateDate = DateUtils.parseDateTime(userBO.getLastPwdUpdateDate());
			if (jwtTokenUtils.canTokenBeRefreshed(token, lastPwdUpdateDate)) {
				refreshedToken = jwtTokenUtils.refreshToken(token);
			}
		} catch (TornadoDaoException e) {
			Logger.error("Token 刷新失败", oldToken, e);
			throw new TornadoAPIServiceException("Token " + oldToken + " 刷新失败", e);
		}
		return refreshedToken;
	}

}
