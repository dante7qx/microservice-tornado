package com.tornado.sysmgr.api.service;

import java.util.List;

import com.tornado.common.api.exception.TornadoAPIServiceException;
import com.tornado.common.api.template.TornadoAbstractService;
import com.tornado.sysmgr.api.dto.req.AuthorityReqDTO;
import com.tornado.sysmgr.api.dto.resp.AuthorityRespDTO;

/**
 * 权限 Service
 * 
 * @author dante
 *
 */
public interface AuthorityService extends TornadoAbstractService<AuthorityReqDTO, AuthorityRespDTO> {

	/**
	 * 获取所有的第一级的权限
	 * 
	 * @return
	 * @throws TornadoAPIServiceException
	 */
	public List<AuthorityRespDTO> findRootAuthority() throws TornadoAPIServiceException;

	/**
	 * 获取指定节点Id的所有子节点
	 * 
	 * @param pid
	 * @return
	 * @throws TornadoAPIServiceException
	 */
	public List<AuthorityRespDTO> findByPid(Long pid) throws TornadoAPIServiceException;
}
