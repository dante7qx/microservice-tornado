package com.tornado.sysmgr.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tornado.commom.dto.resp.BaseResp;
import com.tornado.commom.dto.resp.RespCodeEnum;
import com.tornado.common.api.exception.TornadoAPIServiceException;
import com.tornado.sysmgr.api.dto.req.AuthorityReqDTO;
import com.tornado.sysmgr.api.dto.resp.AuthorityRespDTO;
import com.tornado.sysmgr.api.service.AuthorityService;

/**
 * 权限 REST API
 * 
 * @author dante
 *
 */
@RestController
@RequestMapping("/authority")
public class AuthorityController {
private static final Logger LOGGER = LoggerFactory.getLogger(AuthorityController.class);
	
	@Autowired
	private AuthorityService authorityService;
	
	/**
	 * 根据id获取权限
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/query_by_id/{id}")
	public BaseResp<AuthorityRespDTO> queryByAuthorityId(@PathVariable Long id) {
		BaseResp<AuthorityRespDTO> result = new BaseResp<>();
		try {
			AuthorityRespDTO authorityResp = authorityService.findById(id);
			result.setData(authorityResp);
		} catch (TornadoAPIServiceException e) {
			result.setResultCode(RespCodeEnum.FAILURE.code());
			LOGGER.error("queryByAuthorityId id: {} error.", id, e);
		}
		return result;
	}
	
	/**
	 * 根据pid获取资源
	 * 
	 * @param pid
	 * @return
	 */
	@PostMapping("/query_by_pid/{pid}")
	public BaseResp<List<AuthorityRespDTO>> queryByAuthorityPid(@PathVariable Long pid) {
		BaseResp<List<AuthorityRespDTO>> result = new BaseResp<>();
		try {
			List<AuthorityRespDTO> authorityResp = authorityService.findByPid(pid);
			result.setData(authorityResp);
		} catch (TornadoAPIServiceException e) {
			result.setResultCode(RespCodeEnum.FAILURE.code());
			LOGGER.error("queryByAuthorityPid pid: {} error.", pid, e);
		}
		return result;
	}
	
	/**
	 * 获取所有根资源
	 * 
	 * @return
	 */
	@PostMapping("/query_root")
	public BaseResp<List<AuthorityRespDTO>> queryRootAuthority() {
		BaseResp<List<AuthorityRespDTO>> result = new BaseResp<>();
		try {
			List<AuthorityRespDTO> authorityResp = authorityService.findRootAuthority();
			result.setData(authorityResp);
		} catch (TornadoAPIServiceException e) {
			result.setResultCode(RespCodeEnum.FAILURE.code());
			LOGGER.error("queryRootAuthority error.", e);
		}
		return result;
	}
	
	/**
	 * 新增资源
	 * 
	 * @param authorityReq
	 * @return
	 */
	@PostMapping("/add")
	public BaseResp<AuthorityRespDTO> addAuthority(@RequestBody AuthorityReqDTO authorityReq) {
		BaseResp<AuthorityRespDTO> result = new BaseResp<>();
		try {
			AuthorityRespDTO authorityResp = authorityService.persist(authorityReq);
			result.setData(authorityResp);
		} catch (TornadoAPIServiceException e) {
			result.setResultCode(RespCodeEnum.FAILURE.code());
			LOGGER.error("addAuthority authorityReq: {} error.", authorityReq, e);
		}
		return result;
	}
	
	/**
	 * 更新资源
	 * 
	 * @param authorityReq
	 * @return
	 */
	@PostMapping("/update")
	public BaseResp<AuthorityRespDTO> updateAuthority(@RequestBody AuthorityReqDTO authorityReq) {
		BaseResp<AuthorityRespDTO> result = new BaseResp<>();
		try {
			AuthorityRespDTO authorityResp = authorityService.persist(authorityReq);
			result.setData(authorityResp);
		} catch (TornadoAPIServiceException e) {
			result.setResultCode(RespCodeEnum.FAILURE.code());
			LOGGER.error("updateAuthority authorityReq: {} error.", authorityReq, e);
		}
		return result;
	}
	
	/**
	 * 根据id删除资源（物理删除）
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/delete_by_id/{id}")
	@SuppressWarnings("rawtypes")
	public BaseResp deleteById(@PathVariable Long id) {
		BaseResp result = new BaseResp<>();
		try {
			authorityService.deleteById(id);
		} catch (TornadoAPIServiceException e) {
			result.setResultCode(RespCodeEnum.FAILURE.code());
			LOGGER.error("deleteById id: {} error.", id, e);
		}
		return result;
	}
}
