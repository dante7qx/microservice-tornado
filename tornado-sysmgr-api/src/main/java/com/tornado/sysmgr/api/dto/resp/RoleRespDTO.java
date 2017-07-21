package com.tornado.sysmgr.api.dto.resp;

import java.util.Set;
import com.google.common.collect.Sets;

import lombok.Data;

/**
 * 角色返回参数
 * 
 * @author dante
 *
 */
@Data
public class RoleRespDTO {
	private Long id;
	private String name;
	private String roleDesc;
	private Set<Long> authorityIds;
	private String updateUserName;
	private String updateDate;
	
	public Set<Long> getAuthorityIds() {
		if(this.authorityIds == null) {
			this.authorityIds = Sets.newHashSet();
		}
		return this.authorityIds;
	}

}
