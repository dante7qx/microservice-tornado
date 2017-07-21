package com.tornado.sysmgr.dao.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.tornado.commom.dao.exception.TornadoDaoException;
import com.tornado.sysmgr.dao.TornadoSysMgrDaoApplicationTest;
import com.tornado.sysmgr.dao.po.UserPO;

/**
 * UserDAO 测试类
 * 
 * @author dante
 *
 */
@Transactional(readOnly = true)
public class UserDAOTests extends TornadoSysMgrDaoApplicationTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOTests.class);
	
	@Autowired
	private UserDAO userDAO;
	
	@Test
	public void findByAccount() {
		try {
			UserPO user = userDAO.findByAccount("superadmin");
			Assert.assertNull(user);
		} catch (TornadoDaoException e) {
			LOGGER.error("findByAccount error test.", e);
		}
	}

	@Test
	public void qyeryAll() {
		List<UserPO> users = userDAO.findAll();
		Assert.assertNotNull(users);
	}
	
	@Test
	@Transactional
	public void deleteUserRoleByUserId() {
		Long userId = -1L;
		userDAO.deleteUserRoleByUserId(userId);
	}
	
}
