package com.tornado.sysmgr.api.controller;

import java.util.List;

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
import com.tornado.sysmgr.api.dto.req.ServiceModuleReqDTO;
import com.tornado.sysmgr.api.dto.resp.ServiceModuleRespDTO;
import com.tornado.sysmgr.api.service.ServiceModuleService;

/**
 * 服务模块 REST API
 * 
 * @author dante
 *
 */
@RestController
@RequestMapping("/servicemodule")
public class ServiceModuleController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceModuleController.class);

	@Autowired
	private ServiceModuleService serviceModuleService;

	/**
	 * 分页查询服务模块
	 * 
	 * @param pageReq
	 * @return
	 */
	@PreAuthorize("hasAuthority('sysmgr.servicemodule.query')")
	@PostMapping(value = "/query_page")
	public BaseResp<PageResp<ServiceModuleRespDTO>> queryServiceModulePage(@RequestBody PageReq pageReq) {
		BaseResp<PageResp<ServiceModuleRespDTO>> result = new BaseResp<>();
		try {
			PageResp<ServiceModuleRespDTO> pageResp = serviceModuleService.findPage(pageReq);
			result.setData(pageResp);
		} catch (TornadoAPIServiceException e) {
			LOGGER.error("queryServiceModulePage {} error.", pageReq, e);
			result.setResultCode(RespCodeEnum.FAILURE.code());
		}
		return result;
	}

	/**
	 * 根据Id获取服务模块
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAuthority('sysmgr.servicemodule.query')")
	@PostMapping(value = "/query_by_id/{id}")
	public BaseResp<ServiceModuleRespDTO> queryByServiceModuleId(@PathVariable Long id) {
		BaseResp<ServiceModuleRespDTO> result = new BaseResp<>();
		try {
			ServiceModuleRespDTO serviceModuleResp = serviceModuleService.findById(id);
			result.setData(serviceModuleResp);
		} catch (TornadoAPIServiceException e) {
			LOGGER.error("queryByServiceModuleId ServiceModuleId: {} error.", id, e);
			result.setResultCode(RespCodeEnum.FAILURE.code());
		}
		return result;
	}

	/**
	 * 获取所有服务模块
	 * 
	 * @return
	 */
	@PreAuthorize("hasAuthority('sysmgr.servicemodule.query')")
	@PostMapping(value = "/query_all")
	public BaseResp<List<ServiceModuleRespDTO>> queryAllServiceModule() {
		BaseResp<List<ServiceModuleRespDTO>> result = new BaseResp<>();
		try {
			List<ServiceModuleRespDTO> serviceModuleResp = serviceModuleService.findServiceModuleResps();
			result.setData(serviceModuleResp);
		} catch (TornadoAPIServiceException e) {
			LOGGER.error("queryAllServiceModule error.", e);
			result.setResultCode(RespCodeEnum.FAILURE.code());
		}
		return result;
	}

	/**
	 * 新增服务模块
	 * 
	 * @param serviceModuleReqDto
	 * @return
	 */
	@PreAuthorize("hasAuthority('sysmgr.servicemodule.update')")
	@PostMapping("/add")
	public BaseResp<ServiceModuleRespDTO> addServiceModule(@RequestBody ServiceModuleReqDTO serviceModuleReqDto) {
		BaseResp<ServiceModuleRespDTO> result = new BaseResp<>();
		if (!checkParam(serviceModuleReqDto, true)) {
			result.setResultCode(RespCodeEnum.LACK_PARAM.code());
			return result;
		}
		try {
			ServiceModuleRespDTO serviceModuleRespDto = serviceModuleService.persist(serviceModuleReqDto);
			result.setData(serviceModuleRespDto);
		} catch (TornadoAPIServiceException e) {
			result.setResultCode(RespCodeEnum.FAILURE.code());
			LOGGER.error("addServiceModule serviceModule: {} error.", serviceModuleReqDto, e);
		}
		return result;
	}

	/**
	 * 更新服务模块
	 * 
	 * @param serviceModuleReqDto
	 * @return
	 */
	@PreAuthorize("hasAuthority('sysmgr.servicemodule.update')")
	@PostMapping("/update")
	public BaseResp<ServiceModuleRespDTO> updateServiceModule(@RequestBody ServiceModuleReqDTO serviceModuleReqDto) {
		BaseResp<ServiceModuleRespDTO> result = new BaseResp<>();
		if (!checkParam(serviceModuleReqDto, false)) {
			result.setResultCode(RespCodeEnum.LACK_PARAM.code());
			return result;
		}
		try {
			ServiceModuleRespDTO serviceModuleRespDto = serviceModuleService.persist(serviceModuleReqDto);
			result.setData(serviceModuleRespDto);
		} catch (TornadoAPIServiceException e) {
			result.setResultCode(RespCodeEnum.FAILURE.code());
			LOGGER.error("updateServiceModule serviceModule: {} error.", serviceModuleReqDto, e);
		}
		return result;
	}

	/**
	 * 根据id删除服务模块（物理删除）
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAuthority('sysmgr.servicemodule.delete')")
	@DeleteMapping(value = "/delete_by_id/{id}")
	public BaseResp deleteByServiceModuleId(@PathVariable Long id) {
		BaseResp result = new BaseResp();
		try {
			serviceModuleService.deleteById(id);
		} catch (TornadoAPIServiceException e) {
			LOGGER.error("deleteByServiceModuleId serviceModuleId: {} error.", id, e);
			result.setResultCode(RespCodeEnum.FAILURE.code());
		}
		return result;
	}

	/**
	 * 参数校验
	 * 
	 * @param userReqDTO
	 * @return
	 */
	private boolean checkParam(ServiceModuleReqDTO serviceModuleReqDTO, boolean isNew) {
		if ((!isNew && serviceModuleReqDTO.getId() == null) 
				|| StringUtils.isEmpty(serviceModuleReqDTO.getName())
				|| StringUtils.isEmpty(serviceModuleReqDTO.getUrl()) 
				|| serviceModuleReqDTO.getUpdateUser() == null) {
			return false;
		}
		return true;
	}

}
