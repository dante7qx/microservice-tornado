package com.tornado.api.dto.resp;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户认证返回
 * 
 * @author dante
 *
 */
@Data
public class UserAuthRespDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String accessToken;
	private UserRespDTO user;
}
