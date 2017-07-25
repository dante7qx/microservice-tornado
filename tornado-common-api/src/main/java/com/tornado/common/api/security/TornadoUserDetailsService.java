package com.tornado.common.api.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tornado.commom.util.DateUtils;
import com.tornado.common.api.exception.TornadoAPIServiceException;
import com.tornado.common.api.service.UserAuthService;
import com.tornado.common.api.vo.UserAuthVO;


@Service
public class TornadoUserDetailsService implements UserDetailsService {
	
	private Logger LOGGER = LoggerFactory.getLogger(TornadoUserDetailsService.class);
	
	@Autowired
	private UserAuthService userAuthService;

	@Override
	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
		UserAuthVO userAuthVO = null;
		try {
			userAuthVO = userAuthService.findByAccount(account);
			if(userAuthVO == null) {
				throw new UsernameNotFoundException("用户名["+account+"]不存在！");
			}
		} catch (TornadoAPIServiceException e) {
			LOGGER.error("loadUserByUsername error, Account:{}", account, e);
			throw new UsernameNotFoundException("用户名["+account+"]认证失败！", e);
		}
		return convertToTornadoPrincipal(userAuthVO);
	}

	private TornadoPrincipal convertToTornadoPrincipal(UserAuthVO userAuthVO) {
		TornadoLoginUser tornadoLoginUser = new TornadoLoginUser();
		BeanUtils.copyProperties(userAuthVO, tornadoLoginUser, "lastPwdUpdateDate");
		if(!StringUtils.isEmpty(userAuthVO.getLastPwdUpdateDate())) {
			tornadoLoginUser.setLastUpdateDate(DateUtils.parseDateTime(userAuthVO.getLastPwdUpdateDate()));
		}
		return new TornadoPrincipal(tornadoLoginUser, userAuthVO.getPassword());
	}
}
