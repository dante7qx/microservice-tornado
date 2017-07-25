package com.tornado.authserver.dto.resp;

import java.io.Serializable;
import java.util.Set;

import com.google.common.collect.Sets;

import lombok.Data;

/**
 * 用户基本信息
 * 
 * @author dante
 *
 */
@Data
public class UserRespDTO implements Serializable {

	private static final long serialVersionUID = -697606318323663547L;
	
	private Long id;
	private String account;
	private String name;
	private String email;
	private String lastPwdUpdateDate;
	private Set<String> authoritys;
	
	public Set<String> getAuthoritys() {
		if(this.authoritys == null) {
			this.authoritys = Sets.newHashSet();
		}
		return this.authoritys;
	}
	
}
