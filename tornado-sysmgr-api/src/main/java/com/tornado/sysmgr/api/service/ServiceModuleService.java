package com.tornado.sysmgr.api.service;

import java.util.List;

import com.tornado.common.api.exception.TornadoAPIServiceException;
import com.tornado.common.api.template.TornadoAbstractService;
import com.tornado.sysmgr.api.dto.req.ServiceModuleReqDTO;
import com.tornado.sysmgr.api.dto.resp.ServiceModuleRespDTO;

/**
 * 服务模块管理 Service
 * 
 * @author dante
 *
 */
public interface ServiceModuleService extends TornadoAbstractService<ServiceModuleReqDTO, ServiceModuleRespDTO> {
	
	/**
	 * 获取所有的服务模块
	 * 
	 * @return
	 * @throws TornadoAPIServiceException
	 */
	public List<ServiceModuleRespDTO> findServiceModuleResps() throws TornadoAPIServiceException;
	
}
