package com.tornado.sysmgr.api.dto.resp;

import lombok.Data;

/**
 * 服务模块返回参数
 * 
 * @author dante
 *
 */
@Data
public class ServiceModuleRespDTO {
	private Long id;
	private String name;
	private String url;
	private String updateUserName;
	private String updateDate;

}
