package com.tornado.sysmgr.api.dto.req;

import java.util.Set;

import lombok.Data;

/**
 * 用户请求参数
 * 
 * @author dante
 *
 */
@Data
public class UserReqDTO {

	private Long id;
	private String account;
	private String name;
	private String password;
	private String email;
	private Long updateUser;
	private Set<Long> roleIds;
	
}
