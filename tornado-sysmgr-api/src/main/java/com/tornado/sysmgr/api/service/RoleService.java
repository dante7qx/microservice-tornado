package com.tornado.sysmgr.api.service;

import java.util.List;

import com.tornado.common.api.exception.TornadoAPIServiceException;
import com.tornado.common.api.template.TornadoAbstractService;
import com.tornado.sysmgr.api.dto.req.RoleReqDTO;
import com.tornado.sysmgr.api.dto.resp.AuthorityRoleRespDTO;
import com.tornado.sysmgr.api.dto.resp.RoleRespDTO;

public interface RoleService extends TornadoAbstractService<RoleReqDTO, RoleRespDTO>{
	
	public List<RoleRespDTO> findAllRoles() throws TornadoAPIServiceException;
	
	public List<AuthorityRoleRespDTO> findAuthorityRoleByRoleId(Long roleId) throws TornadoAPIServiceException;
	
}
