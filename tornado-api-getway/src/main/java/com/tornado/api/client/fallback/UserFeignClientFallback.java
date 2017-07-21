package com.tornado.api.client.fallback;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.tornado.api.client.UserFeignClient;
import com.tornado.api.vo.LoginUserMenuVO;
import com.tornado.api.vo.LoginUserVO;
import com.tornado.commom.dto.resp.BaseResp;
import com.tornado.commom.dto.resp.RespCodeEnum;

@Component
public class UserFeignClientFallback implements UserFeignClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserFeignClientFallback.class);

	@Override
	public BaseResp<LoginUserVO> findByAccount(String account) {
		LOGGER.error("findByAccount account {} hystrix.", account);
		BaseResp<LoginUserVO> resp = new BaseResp<LoginUserVO>();
		resp.setResultCode(RespCodeEnum.REMOTE_FAILURE.code());
		return resp;
	}

	@Override
	public BaseResp<List<LoginUserMenuVO>> findUserMenuByUserId(Long userId) {
		LOGGER.error("findUserMenuByUserId user {} hystrix.", userId);
		BaseResp<List<LoginUserMenuVO>> resp = new BaseResp<List<LoginUserMenuVO>>();
		LoginUserMenuVO userMenu = new LoginUserMenuVO();
		userMenu.setName("暂时不可用");
		resp.setData(Lists.newArrayList(userMenu));
		resp.setResultCode(RespCodeEnum.REMOTE_FAILURE.code());
		return resp;
	}

}
