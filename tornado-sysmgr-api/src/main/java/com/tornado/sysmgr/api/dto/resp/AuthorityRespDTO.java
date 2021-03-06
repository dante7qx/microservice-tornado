package com.tornado.sysmgr.api.dto.resp;

import lombok.Data;

/**
 * 权限返回参数
 * 
 * @author dante
 *
 */
@Data
public class AuthorityRespDTO {
	private Long id;
	private String code;
	private String name;
	private String authorityDesc;
	private Long pid;
	private Integer showOrder;

}
