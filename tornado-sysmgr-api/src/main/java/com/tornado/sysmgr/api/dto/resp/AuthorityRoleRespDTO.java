package com.tornado.sysmgr.api.dto.resp;

import lombok.Data;

/**
 * 权限角色返回参数
 * 
 * @author dante
 *
 */
@Data
public class AuthorityRoleRespDTO {
	private Long id;
	private Long pid;
	private String name;
	private String code;
	private String authorityDesc;
	private Integer showOrder;
	private Long roleId;
	private Boolean hasRelRole = false;

}
