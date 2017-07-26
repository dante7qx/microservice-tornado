package com.tornado.sysmgr.api.dto.req;

import lombok.Data;

/**
 * IP规则请求参数
 * 
 * @author dante
 *
 */
@Data
public class IpRuleReqDTO {

	private Long id;
	private String ip;
	private String remark;
	private Long updateUser;
	private Boolean active;
}
