package com.tornado.api.client.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tornado.api.client.IpRuleFeignClient;
import com.tornado.api.vo.IPRuleVO;
import com.tornado.commom.dto.resp.BaseResp;

@Component
public class IpRuleFeignClientFallback implements IpRuleFeignClient {

	@Override
	public BaseResp<List<IPRuleVO>> findActiveIpRules() {
		return null;
	}

}
