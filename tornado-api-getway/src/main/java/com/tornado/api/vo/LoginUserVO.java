package com.tornado.api.vo;

import java.util.Set;

import lombok.Data;
import lombok.NonNull;

/**
 * 用户登录 VO
 * 
 * @author dante
 *
 */
@Data
public class LoginUserVO {

	private Long id;
	private String account;
	private String password;
	private String name;
	private String email;
	private String lastPwdUpdateDate;
	private Set<String> authoritys;

}
