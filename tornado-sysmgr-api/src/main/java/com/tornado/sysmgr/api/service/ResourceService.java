package com.tornado.sysmgr.api.service;

import java.util.List;

import com.tornado.common.api.exception.TornadoAPIServiceException;
import com.tornado.common.api.template.TornadoAbstractService;
import com.tornado.sysmgr.api.dto.req.ResourceReqDTO;
import com.tornado.sysmgr.api.dto.resp.ResourceRespDTO;
import com.tornado.sysmgr.api.dto.resp.UserResourceRespDTO;

public interface ResourceService extends TornadoAbstractService<ResourceReqDTO, ResourceRespDTO>{
	
	/**
	 * 获取当前登录用户的所有菜单
	 * 
	 * @param userId
	 * @return
	 * @throws TornadoAPIServiceException
	 */
	public List<UserResourceRespDTO> findUserResourceByUserId(Long userId) throws TornadoAPIServiceException;
	
	/**
	 * 获取所有的第一级节点
	 * 
	 * @return
	 * @throws TornadoAPIServiceException
	 */
	public List<ResourceRespDTO> findRootResource() throws TornadoAPIServiceException;

	/**
	 * 获取指定节点Id下的子节点
	 * 
	 * @param pid
	 * @return
	 * @throws TornadoAPIServiceException
	 */
	public List<ResourceRespDTO> findByPid(Long pid) throws TornadoAPIServiceException;
}
