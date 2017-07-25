package com.tornado.common.api.vo;

import java.util.Set;

import lombok.Data;

/**
 * 用户登录 VO
 * 
 * @author dante
 *
 */
@Data
public class UserAuthVO {

	private Long id;
	private String account;
	private String password;
	private String name;
	private String email;
	private String lastPwdUpdateDate;
	private Set<String> authoritys;

}
