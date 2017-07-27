package com.tornado.api.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tornado.api.client.fallback.IpRuleFeignClientFallback;
import com.tornado.api.constant.TornadoServiceConsts;
import com.tornado.api.vo.IPRuleVO;
import com.tornado.commom.dto.resp.BaseResp;

/**
 * 获取所有的IP规则
 * 
 * @author dante
 *
 */
@FeignClient(name = TornadoServiceConsts.SYSMGR_API, fallback = IpRuleFeignClientFallback.class)
public interface IpRuleFeignClient {
	@RequestMapping(method = RequestMethod.POST, value = "/iprule/query_ip_rules")
	public BaseResp<List<IPRuleVO>> findActiveIpRules();
}
