package com.tornado.sysmgr.api.service;

import java.util.List;

import com.tornado.common.api.exception.TornadoAPIServiceException;
import com.tornado.common.api.template.TornadoAbstractService;
import com.tornado.sysmgr.api.dto.req.IpRuleReqDTO;
import com.tornado.sysmgr.api.dto.resp.IpRuleRespDTO;

/**
 * IP规则 Service
 * 
 * @author dante
 *
 */
public interface IpRuleService extends TornadoAbstractService<IpRuleReqDTO, IpRuleRespDTO> {
	
	/**
	 * 获取所有的已激活IP规则
	 * 
	 * @return
	 * @throws TornadoAPIServiceException
	 */
	public List<IpRuleRespDTO> findAllActiveIpRules() throws TornadoAPIServiceException;
}
