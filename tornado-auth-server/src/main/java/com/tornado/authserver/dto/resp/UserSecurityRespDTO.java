package com.tornado.authserver.dto.resp;

/**
 * 用户安全信息
 * 
 * @author dante
 *
 */
public class UserSecurityRespDTO extends UserRespDTO {
	
	private static final long serialVersionUID = 1L;
	
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
