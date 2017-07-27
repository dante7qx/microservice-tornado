package com.tornado.api.service;

import java.util.List;

import com.tornado.api.vo.IPRuleVO;

public interface IPRuleService {
	public List<IPRuleVO> findActiveIPRules();
}
