package com.tornado.sysmgr.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.tornado.commom.dto.req.PageReq;
import com.tornado.commom.dto.resp.BaseResp;
import com.tornado.commom.dto.resp.PageResp;
import com.tornado.commom.dto.resp.RespCodeEnum;
import com.tornado.common.api.exception.TornadoAPIServiceException;
import com.tornado.sysmgr.api.dto.req.RoleReqDTO;
import com.tornado.sysmgr.api.dto.resp.AuthorityRoleRespDTO;
import com.tornado.sysmgr.api.dto.resp.RoleRespDTO;
import com.tornado.sysmgr.api.service.RoleService;

/**
 * 角色 REST API
 * 
 * @author dante
 *
 */
@RestController
@RequestMapping("/role")
public class RoleController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;
	
	/**
	 * 分页查询角色
	 * 
	 * @param pageReq
	 * @return
	 */
	@PostMapping(value = "/query_page")
	public BaseResp<PageResp<RoleRespDTO>> queryRolePage(@RequestBody PageReq pageReq) {
		BaseResp<PageResp<RoleRespDTO>> result = new BaseResp<>();
		try {
			PageResp<RoleRespDTO> pageResp = roleService.findPage(pageReq);
			result.setData(pageResp);
		} catch (TornadoAPIServiceException e) {
			LOGGER.error("queryRolePage error.", e);
			result.setResultCode(RespCodeEnum.FAILURE.code());
		}
		return result;
	}
	
	/**
	 * 根据id获取角色
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAuthority('sysmgr.role.query')")
	@PostMapping(value = "/query_by_id/{id}")
	public BaseResp<RoleRespDTO> queryByRoleId(@PathVariable Long id) {
		BaseResp<RoleRespDTO> result = new BaseResp<>();
		try {
			RoleRespDTO roleResp = roleService.findById(id);
			result.setData(roleResp);
		} catch (TornadoAPIServiceException e) {
			LOGGER.error("queryByRoleId roleId: {} error.", id, e);
			result.setResultCode(RespCodeEnum.FAILURE.code());
		}
		return result;
	}
	
	/**
	 * 获取所有角色
	 * 
	 * @return
	 */
	@PostMapping(value = "/query_all_role")
	public BaseResp<List<RoleRespDTO>> queryAllRole() {
		BaseResp<List<RoleRespDTO>> result = new BaseResp<>();
		try {
			List<RoleRespDTO> roleResps = roleService.findAllRoles();
			result.setData(roleResps);
		} catch (TornadoAPIServiceException e) {
			LOGGER.error("queryAllRole error.", e);
			result.setResultCode(RespCodeEnum.FAILURE.code());
		}
		return result;
	}
	
	/**
	 * 获取指定角色id下的所有权限
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping(value = "/query_authority_role_by_id/{id}")
	public BaseResp<List<AuthorityRoleRespDTO>> queryByAuthorityRoleId(@PathVariable Long id) {
		BaseResp<List<AuthorityRoleRespDTO>> result = new BaseResp<>();
		try {
			List<AuthorityRoleRespDTO> authorityRoleRespDTOs = roleService.findAuthorityRoleByRoleId(id);
			result.setData(authorityRoleRespDTOs);
		} catch (TornadoAPIServiceException e) {
			LOGGER.error("queryByAuthorityRoleId roleId: {} error.", id, e);
			result.setResultCode(RespCodeEnum.FAILURE.code());
		}
		return result;
	}
	
	/**
	 * 新增角色
	 * 
	 * @param roleReqDto
	 * @return
	 */
	@PostMapping("/add")
	public BaseResp<RoleRespDTO> addRole(@RequestBody RoleReqDTO roleReqDto) {
		BaseResp<RoleRespDTO> result = new BaseResp<>();
		if(!checkParam(roleReqDto)) {
			result.setResultCode(RespCodeEnum.LACK_PARAM.code());
			return result;
		}
		try {
			RoleRespDTO roleRespDto = roleService.persist(roleReqDto);
			result.setData(roleRespDto);
		} catch (TornadoAPIServiceException e) {
			result.setResultCode(RespCodeEnum.FAILURE.code());
			LOGGER.error("addRole role: {} error.", roleReqDto, e);
		}
		return result;
	}
	
	/**
	 * 更新角色
	 * 
	 * @param roleReqDto
	 * @return
	 */
	@PostMapping("/update")
	public BaseResp<RoleRespDTO> updateRole(@RequestBody RoleReqDTO roleReqDto) {
		BaseResp<RoleRespDTO> result = new BaseResp<>();
		if(!checkParam(roleReqDto)) {
			result.setResultCode(RespCodeEnum.LACK_PARAM.code());
			return result;
		}
		try {
			RoleRespDTO roleRespDto = roleService.persist(roleReqDto);
			result.setData(roleRespDto);
		} catch (TornadoAPIServiceException e) {
			result.setResultCode(RespCodeEnum.FAILURE.code());
			LOGGER.error("updateRole role: {} error.", roleReqDto, e);
		}
		return result;
	}
	
	/**
	 * 根据id删除角色（物理删除）
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete_by_id/{id}")
	@SuppressWarnings("rawtypes")
	public BaseResp deleteByRoleId(@PathVariable Long id) {
		BaseResp result = new BaseResp<>();
		try {
			roleService.deleteById(id);
		} catch (TornadoAPIServiceException e) {
			LOGGER.error("deleteByRoleId roleId: {} error.", id, e);
			result.setResultCode(RespCodeEnum.FAILURE.code());
		}
		return result;
	}
	
	/**
	 * 参数校验
	 * 
	 * @param roleReqDTO
	 * @return
	 */
	private boolean checkParam(RoleReqDTO roleReqDTO) {
		if(StringUtils.isEmpty(roleReqDTO.getName())) {
			return false;
		} 
		return true;
	}
	
}
