package com.tornado.sysmgr.dao.mapper;

import java.util.List;

import com.tornado.commom.dao.exception.TornadoDaoException;
import com.tornado.sysmgr.dao.bo.AuthorityRoleBO;

/**
 * 权限 Mapper
 * 
 * @author dante
 *
 */
public interface AuthorityMapper {

	public List<AuthorityRoleBO> findAuthorityRoleByRoleId(Long roleId) throws TornadoDaoException;
	
}
