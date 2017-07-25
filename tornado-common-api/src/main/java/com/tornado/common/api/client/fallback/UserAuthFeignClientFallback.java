package com.tornado.common.api.client.fallback;

import org.springframework.stereotype.Component;

import com.tornado.commom.dto.resp.BaseResp;
import com.tornado.common.api.client.UserAuthFeignClient;
import com.tornado.common.api.vo.UserAuthVO;

@Component
public class UserAuthFeignClientFallback implements UserAuthFeignClient {

	@Override
	public BaseResp<UserAuthVO> findByAccount(String account) {
		UserAuthVO vo = new UserAuthVO();
		BaseResp<UserAuthVO> resp = new BaseResp<>();
		resp.setData(vo);
		return resp;
	}

}
