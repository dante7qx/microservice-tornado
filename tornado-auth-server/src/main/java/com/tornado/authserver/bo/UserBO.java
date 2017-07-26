package com.tornado.authserver.bo;

import java.util.Set;

import lombok.Data;

@Data
public class UserBO {
	private Long id;
	private String account;
	private String password;
	private String name;
	private String email;
	private String lastPwdUpdateDate;
	private String status;
	private Set<String> authoritys;
}
