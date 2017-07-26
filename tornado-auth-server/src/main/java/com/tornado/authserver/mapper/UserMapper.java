package com.tornado.authserver.mapper;

import com.tornado.authserver.bo.UserBO;
import com.tornado.commom.dao.exception.TornadoDaoException;

public interface UserMapper {
	
	public UserBO findUserByAccount(String account) throws TornadoDaoException;
	
	public UserBO findUserById(Long id) throws TornadoDaoException;
	
}
