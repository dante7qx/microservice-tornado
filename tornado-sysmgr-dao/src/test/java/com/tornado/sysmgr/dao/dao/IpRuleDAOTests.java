package com.tornado.sysmgr.dao.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tornado.commom.dao.exception.TornadoDaoException;
import com.tornado.sysmgr.dao.TornadoSysMgrDaoApplicationTest;
import com.tornado.sysmgr.dao.po.IpRulePO;

/**
 * IP 规则测试类
 * 
 * @author dante
 *
 */
public class IpRuleDAOTests extends TornadoSysMgrDaoApplicationTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IpRuleDAOTests.class);
	
	@Autowired
	private IpRuleDAO ipRuleDAO;
	
	@Test
	public void testFindAllActiveIpRules() {
		try {
			List<IpRulePO> ipRules = ipRuleDAO.findAllActiveIpRules();
			Assert.assertNotNull(ipRules);
		} catch (TornadoDaoException e) {
			LOGGER.error("findAllActiveIpRules error", e);
		}
	}
	
}
