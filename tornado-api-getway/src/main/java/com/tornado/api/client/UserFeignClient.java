package com.tornado.api.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tornado.api.client.fallback.UserFeignClientFallback;
import com.tornado.api.constant.TornadoServiceConsts;
import com.tornado.api.vo.LoginUserMenuVO;
import com.tornado.api.vo.LoginUserVO;
import com.tornado.commom.dto.resp.BaseResp;
import com.tornado.feignconfig.FeignClientConfig;

/**
 * 用户认证 Feign Client
 * 
 * @author dante
 *
 */
@FeignClient(name = TornadoServiceConsts.SYSMGR_API, fallback = UserFeignClientFallback.class, configuration=FeignClientConfig.class)
public interface UserFeignClient {
	
	/**
	 * 
	 * 
	 * @param account
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/login/{account}")
	public BaseResp<LoginUserVO> findByAccount(@PathVariable("account") String account);
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/login_user_menu/{userId}")
	public BaseResp<List<LoginUserMenuVO>> findUserMenuByUserId(@PathVariable("userId") Long userId);
	
}
