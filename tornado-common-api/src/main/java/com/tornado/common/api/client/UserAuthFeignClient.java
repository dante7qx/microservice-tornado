package com.tornado.common.api.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tornado.commom.dto.resp.BaseResp;
import com.tornado.common.api.client.fallback.UserAuthFeignClientFallback;
import com.tornado.common.api.constant.TornadoServiceConsts;
import com.tornado.common.api.vo.UserAuthVO;

@FeignClient(name = TornadoServiceConsts.AUTH_SERVER, fallback = UserAuthFeignClientFallback.class)
public interface UserAuthFeignClient {
	@RequestMapping(method = RequestMethod.POST, value = "/auth/{account}")
	public BaseResp<UserAuthVO> findByAccount(@PathVariable("account") String account);
}
