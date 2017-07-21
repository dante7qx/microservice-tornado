package com.tornado.sysmgr.api.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tornado.commom.dao.exception.TornadoDaoException;
import com.tornado.commom.dao.template.TornadoServiceTemplate;
import com.tornado.commom.dto.req.PageReq;
import com.tornado.commom.dto.resp.PageResp;
import com.tornado.commom.util.DateUtils;
import com.tornado.common.api.exception.TornadoAPIServiceException;
import com.tornado.sysmgr.api.dto.req.RoleReqDTO;
import com.tornado.sysmgr.api.dto.resp.AuthorityRoleRespDTO;
import com.tornado.sysmgr.api.dto.resp.RoleRespDTO;
import com.tornado.sysmgr.api.service.RoleService;
import com.tornado.sysmgr.dao.bo.AuthorityRoleBO;
import com.tornado.sysmgr.dao.dao.RoleDAO;
import com.tornado.sysmgr.dao.dao.specification.RoleSpecification;
import com.tornado.sysmgr.dao.mapper.AuthorityMapper;
import com.tornado.sysmgr.dao.po.AuthorityPO;
import com.tornado.sysmgr.dao.po.RolePO;
import com.tornado.sysmgr.dao.po.UserPO;

/**
 * 角色服务实现类
 * 
 * @author dante
 *
 */
@Service
@Transactional(readOnly = true)
public class RoleServiceImpl extends TornadoServiceTemplate<RoleReqDTO, RoleRespDTO, RolePO> implements RoleService {

	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private AuthorityMapper authorityMapper;

	@Override
	public PageResp<RoleRespDTO> findPage(PageReq pageReq)  {
		return super.findPage(pageReq);
	}

	@Override
	@Transactional
	public RoleRespDTO persist(RoleReqDTO roleReqDTO) throws TornadoAPIServiceException {
		RolePO rolePO = roleDAO.save(convertReqDtoToPo(roleReqDTO));
		return convertPoToRespDto(rolePO);
	}

	@Override
	@Transactional
	public void deleteById(Long id) throws TornadoAPIServiceException {
		roleDAO.delete(id);
	}

	@Override
	public RoleRespDTO findById(Long id) throws TornadoAPIServiceException {
		return convertPoToRespDto(roleDAO.findOne(id));
	}

	@Override
	public List<AuthorityRoleRespDTO> findAuthorityRoleByRoleId(Long roleId) throws TornadoAPIServiceException {
		List<AuthorityRoleRespDTO> authorityRoleRespDtos = Lists.newArrayList();
		try {
			List<AuthorityRoleBO> authorityRoleBOs = authorityMapper.findAuthorityRoleByRoleId(roleId);
			if (!CollectionUtils.isEmpty(authorityRoleBOs)) {
				for (AuthorityRoleBO authorityRoleBO : authorityRoleBOs) {
					AuthorityRoleRespDTO authorityRoleRespDTO = new AuthorityRoleRespDTO();
					BeanUtils.copyProperties(authorityRoleBO, authorityRoleRespDTO);
					authorityRoleRespDtos.add(authorityRoleRespDTO);
				}
			}
		} catch (TornadoDaoException e) {
			throw new TornadoAPIServiceException(e);
		}
		return authorityRoleRespDtos;
	}

	@Override
	public List<RoleRespDTO> findAllRoles() throws TornadoAPIServiceException {
		List<RoleRespDTO> roleRespDTOs = Lists.newLinkedList();
		List<RolePO> rolePOs = roleDAO.findAll(new Sort(Direction.ASC, "name"));
		if (!CollectionUtils.isEmpty(rolePOs)) {
			for (RolePO rolePO : rolePOs) {
				roleRespDTOs.add(convertPoToRespDto(rolePO));
			}
		}
		return roleRespDTOs;
	}

	@Override
	protected RolePO convertReqDtoToPo(RoleReqDTO roleReqDTO) {
		RolePO rolePO = new RolePO();
		if (roleReqDTO != null) {
			BeanUtils.copyProperties(roleReqDTO, rolePO, "updateUser");
			rolePO.setUpdateDate(DateUtils.currentDate());
			if (roleReqDTO.getUpdateUser() != null) {
				rolePO.setUpdateUser(new UserPO(roleReqDTO.getUpdateUser()));
			}
			Set<Long> authorityIds = roleReqDTO.getAuthorityIds();
			if (!CollectionUtils.isEmpty(authorityIds)) {
				Set<AuthorityPO> authorityPOs = Sets.newHashSet();
				for (Long authorityId : authorityIds) {
					authorityPOs.add(new AuthorityPO(authorityId));
				}
				rolePO.setAuthoritys(authorityPOs);
			}
		}
		return rolePO;
	}

	@Override
	protected RoleRespDTO convertPoToRespDto(RolePO rolePO) {
		RoleRespDTO roleRespDTO = new RoleRespDTO();
		if (rolePO != null) {
			BeanUtils.copyProperties(rolePO, roleRespDTO, "updateUser", "updateDate");
			if (rolePO.getUpdateUser() != null) {
				roleRespDTO.setUpdateUserName(rolePO.getUpdateUser().getName());
			}
			if (rolePO.getUpdateDate() != null) {
				roleRespDTO.setUpdateDate(DateUtils.formatDateTime(rolePO.getUpdateDate()));
			}
			Set<AuthorityPO> authoritys = rolePO.getAuthoritys();
			if (!CollectionUtils.isEmpty(authoritys)) {
				for (AuthorityPO authority : authoritys) {
					roleRespDTO.getAuthorityIds().add(authority.getId());
				}
			}
		}
		return roleRespDTO;
	}

	@Override
	protected Specification<RolePO> buildSpecification(Map<String, Object> filter) {
		return RoleSpecification.querySpecification(filter);
	}

	@Override
	public void delete(RoleReqDTO reqDTO) throws TornadoAPIServiceException {
		// 逻辑删除，此功能使用物理删除，故本方法不做实现
	}
}
