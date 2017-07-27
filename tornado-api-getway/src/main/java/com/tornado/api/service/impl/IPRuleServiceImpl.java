package com.tornado.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tornado.api.client.IpRuleFeignClient;
import com.tornado.api.service.IPRuleService;
import com.tornado.api.vo.IPRuleVO;
import com.tornado.commom.dto.resp.BaseResp;
import com.tornado.commom.dto.resp.RespCodeEnum;

@Service
public class IPRuleServiceImpl implements IPRuleService {
	
	@Autowired
	private IpRuleFeignClient ipRuleFeignClient;

	@Override
	public List<IPRuleVO> findActiveIPRules() {
		List<IPRuleVO> ipRuleVOs = null;
		BaseResp<List<IPRuleVO>> result = ipRuleFeignClient.findActiveIpRules();
		if(result.getResultCode() == RespCodeEnum.SUCCESS.code()) {
			ipRuleVOs = result.getData();
		}
		return ipRuleVOs;
	}

}
