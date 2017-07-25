package com.tornado.authserver.service.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tornado.authserver.dto.req.UserAuthReqDTO;
import com.tornado.authserver.dto.resp.UserAuthRespDTO;
import com.tornado.authserver.dto.resp.UserRespDTO;
import com.tornado.authserver.service.UserService;
import com.tornado.authserver.util.EncryptUtils;
import com.tornado.commom.dao.exception.TornadoDaoException;
import com.tornado.commom.util.DateUtils;
import com.tornado.common.api.exception.TornadoAPIServiceException;
import com.tornado.common.api.security.JwtTokenUtils;
import com.tornado.sysmgr.dao.constant.UserConsts;
import com.tornado.sysmgr.dao.dao.UserDAO;
import com.tornado.sysmgr.dao.po.AuthorityPO;
import com.tornado.sysmgr.dao.po.RolePO;
import com.tornado.sysmgr.dao.po.UserPO;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	private static final Logger Logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	
	@Override
	public UserRespDTO findByAccount(String account) throws TornadoAPIServiceException {
		UserRespDTO userRespDTO;
		try {
			UserPO userPO = userDAO.findByAccount(account);
			if(!UserConsts.STATUS_NORMAL.equalsIgnoreCase(userPO.getStatus())) {
				throw new TornadoAPIServiceException("用户["+account+"]状态["+userPO.getStatus()+"]异常");
			}
			userRespDTO = convertToUserRespDTO(userPO);
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
			UserPO userPO = userDAO.findByAccount(account);
			if(!EncryptUtils.match(EncryptUtils.encrypt(password), userPO.getPassword())) {
				throw new TornadoAPIServiceException("密码不匹配");
			}
			if(!UserConsts.STATUS_NORMAL.equalsIgnoreCase(userPO.getStatus())) {
				throw new TornadoAPIServiceException("用户["+account+"]状态["+userPO.getStatus()+"]异常");
			}
			final String token = jwtTokenUtils.generateToken(account);
			authResp.setAccessToken(token);
			authResp.setUser(convertToUserRespDTO(userPO));
		} catch (TornadoDaoException e) {
			Logger.error("用户: {} 查询错误。", account, e);
			throw new TornadoAPIServiceException(e);
		}
		
		
		return authResp;
	}

	private UserRespDTO convertToUserRespDTO(UserPO userPO) {
		UserRespDTO user = new UserRespDTO();
		user.setId(userPO.getId());
		user.setAccount(userPO.getAccount());
		user.setName(userPO.getName());
		user.setEmail(userPO.getEmail());
		if(userPO.getLastPwdUpdateDate() != null) {
			user.setLastPwdUpdateDate(DateUtils.formatDateTime(userPO.getLastPwdUpdateDate()));
		}
		Set<RolePO> roles = userPO.getRoles();
		if(!CollectionUtils.isEmpty(roles)) {
			for (RolePO rolePO : roles) {
				Set<AuthorityPO> authoritys = rolePO.getAuthoritys();
				if(!CollectionUtils.isEmpty(authoritys)) {
					for (AuthorityPO authorityPO : authoritys) {
						user.getAuthoritys().add(authorityPO.getCode());
					}
				}
			}
		}
		return user;
	}
}
