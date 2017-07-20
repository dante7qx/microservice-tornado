package com.tornado.sysmgr.api.dto.req;

import lombok.Data;

/**
 * 服务模块请求参数
 * 
 * @author dante
 *
 */
@Data
public class ServiceModuleReqDTO {
	private Long id;
	private String name;
	private String url;
	private Long updateUser;

}
