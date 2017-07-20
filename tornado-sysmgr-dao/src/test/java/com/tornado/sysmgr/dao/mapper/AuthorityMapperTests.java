package com.tornado.sysmgr.dao.mapper;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tornado.commom.dao.exception.TornadoDaoException;
import com.tornado.sysmgr.dao.TornadoSysMgrDaoApplicationTest;
import com.tornado.sysmgr.dao.bo.AuthorityRoleBO;


public class AuthorityMapperTests extends TornadoSysMgrDaoApplicationTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorityMapperTests.class);
	
	@Autowired
	private AuthorityMapper authorityMapper;
	
	
	@Test
	public void findAuthorityRoleByRoleId() {
		Long roleId = 1L;
		try {
			List<AuthorityRoleBO> authRoles = authorityMapper.findAuthorityRoleByRoleId(roleId);
			Assert.assertNotNull(authRoles);
		} catch (TornadoDaoException e) {
			LOGGER.error("findAuthorityRoleByRoleId 1 error.", e);
		}
	}
}
