package com.tornado.authserver.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tornado.authserver.TornadoAuthServerApplicationTest;
import com.tornado.authserver.bo.UserBO;
import com.tornado.commom.dao.exception.TornadoDaoException;

public class UserMapperTests extends TornadoAuthServerApplicationTest {
	
	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void findUserByAccount() {
		try {
			UserBO userBO = userMapper.findUserByAccount("superadmin");
			System.out.println(userBO);
			Assert.assertNotNull(userBO);
		} catch (TornadoDaoException e) {
			e.printStackTrace();
		}
	}
}
