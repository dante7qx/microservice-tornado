package com.tornado.common.api.security;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import lombok.Data;

/**
 * 当前登录用户（登录后存入redis session）
 * 
 * @author dante
 *
 */
@Data
public class TornadoLoginUser implements Serializable {

	private static final long serialVersionUID = 2347360770161468992L;
	
	private Long id;
	private String account;
	private String name;
	private String email;
	private String status;
	private Date lastPwdUpdateDate;
	private Set<String> authoritys;
	
}
