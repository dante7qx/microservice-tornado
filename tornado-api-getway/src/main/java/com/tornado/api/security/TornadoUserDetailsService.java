package com.tornado.api.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tornado.api.service.UserService;
import com.tornado.api.vo.LoginUserVO;
import com.tornado.common.api.exception.TornadoAPIServiceException;
import com.tornado.common.api.security.TornadoLoginUser;
import com.tornado.common.api.security.TornadoPrincipal;


@Service
public class TornadoUserDetailsService implements UserDetailsService {
	
	private Logger LOGGER = LoggerFactory.getLogger(TornadoUserDetailsService.class);
	
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
		LoginUserVO loginUserVO = null;
		try {
			loginUserVO = userService.findByAccount(account);
			if(loginUserVO == null) {
				throw new UsernameNotFoundException("用户名["+account+"]不存在！");
			}
		} catch (TornadoAPIServiceException e) {
			LOGGER.error("loadUserByUsername error, Account:{}", account, e);
			throw new UsernameNotFoundException("用户名["+account+"]认证失败！", e);
		}
		return convertToTornadoPrincipal(loginUserVO);
	}

	private TornadoPrincipal convertToTornadoPrincipal(LoginUserVO loginUserVO) {
		TornadoLoginUser tornadoLoginUser = new TornadoLoginUser();
		BeanUtils.copyProperties(loginUserVO, tornadoLoginUser, "lastPwdUpdateDate");
		return new TornadoPrincipal(tornadoLoginUser, loginUserVO.getPassword());
	}
}
