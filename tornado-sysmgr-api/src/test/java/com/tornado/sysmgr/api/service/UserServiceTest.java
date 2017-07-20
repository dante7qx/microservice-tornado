package com.tornado.sysmgr.api.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tornado.commom.dto.req.PageReq;
import com.tornado.commom.dto.resp.PageResp;
import com.tornado.common.api.exception.TornadoAPIServiceException;
import com.tornado.sysmgr.api.TornadoSysMgrAPIApplicationTests;
import com.tornado.sysmgr.api.dto.resp.UserRespDTO;


public class UserServiceTest extends TornadoSysMgrAPIApplicationTests {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void testFindPage() {
		try {
			PageReq pageReq = new PageReq();
			pageReq.getQ().put("account", "superadmin");
			PageResp<UserRespDTO> pageResult = userService.findPage(pageReq);
			Assert.assertNotNull(pageResult);
		} catch (TornadoAPIServiceException e) {
			e.printStackTrace();
		}
	}
}
