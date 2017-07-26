package com.tornado.sysmgr.api.dto.resp;

import lombok.Data;

/**
 * IP规则返回参数
 * 
 * @author dante
 *
 */
@Data
public class IpRuleRespDTO {
	private Long id;
	private String ip;
	private String remark;
	private Boolean active;
	private String updateUserName;
	private String updateDate;
	

}
