package com.tornado.authserver.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tornado.authserver.dto.resp.UserSecurityRespDTO;
import com.tornado.authserver.service.UserService;
import com.tornado.commom.util.DateUtils;
import com.tornado.common.api.constant.JWTConsts;
import com.tornado.common.api.exception.TornadoAPIServiceException;
import com.tornado.common.api.security.TornadoLoginUser;
import com.tornado.common.api.security.TornadoPrincipal;


@Service
public class UserAuthDetailsService implements UserDetailsService {
	
	private Logger LOGGER = LoggerFactory.getLogger(UserAuthDetailsService.class);
	
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
		UserSecurityRespDTO userAuthVO = null;
		try {
			Long id = Long.parseLong(account.split(JWTConsts.TOKEN_SPLIT)[1]);
			userAuthVO = userService.findById(id);
			if(userAuthVO == null) {
				throw new UsernameNotFoundException("用户名["+account+"]不存在！");
			}
		} catch (TornadoAPIServiceException e) {
			LOGGER.error("loadUserByUsername error, Account:{}", account, e);
			throw new UsernameNotFoundException("用户名["+account+"]认证失败！", e);
		}
		return convertToTornadoPrincipal(userAuthVO);
	}

	private TornadoPrincipal convertToTornadoPrincipal(UserSecurityRespDTO userAuthVO) {
		TornadoLoginUser tornadoLoginUser = new TornadoLoginUser();
		BeanUtils.copyProperties(userAuthVO, tornadoLoginUser, "lastPwdUpdateDate");
		if(!StringUtils.isEmpty(userAuthVO.getLastPwdUpdateDate())) {
			tornadoLoginUser.setLastPwdUpdateDate(DateUtils.parseDateTime(userAuthVO.getLastPwdUpdateDate()));
		}
		return new TornadoPrincipal(tornadoLoginUser, userAuthVO.getPassword());
	}
}
