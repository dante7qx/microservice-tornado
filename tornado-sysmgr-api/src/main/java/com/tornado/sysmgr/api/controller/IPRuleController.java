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
import com.tornado.sysmgr.api.dto.req.IpRuleReqDTO;
import com.tornado.sysmgr.api.dto.resp.IpRuleRespDTO;
import com.tornado.sysmgr.api.service.IpRuleService;

/**
 * IP 规则 REST API
 * 
 * @author dante
 *
 */
@RestController
@RequestMapping("/iprule")
public class IPRuleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(IPRuleController.class);
	
	@Autowired
	private IpRuleService ipRuleService;
	
	/**
	 * 分页查询IP规则
	 * 
	 * @param pageReq
	 * @return
	 */
	@PreAuthorize("hasAuthority('sysmgr.iprule.query')")
	@PostMapping(value = "/query_page")
	public BaseResp<PageResp<IpRuleRespDTO>> queryIpRulePage(@RequestBody PageReq pageReq) {
		BaseResp<PageResp<IpRuleRespDTO>> result = new BaseResp<>();
		try {
			PageResp<IpRuleRespDTO> pageResp = ipRuleService.findPage(pageReq);
			result.setData(pageResp);
		} catch (TornadoAPIServiceException e) {
			LOGGER.error("queryIpRulePage error.", e);
			result.setResultCode(RespCodeEnum.FAILURE.code());
		}
		return result;
	}
	
	/**
	 * 根据id获取IP规则
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAuthority('sysmgr.iprule.query')")
	@PostMapping(value = "/query_by_id/{id}")
	public BaseResp<IpRuleRespDTO> queryByIpRuleId(@PathVariable Long id) {
		BaseResp<IpRuleRespDTO> result = new BaseResp<>();
		try {
			IpRuleRespDTO roleResp = ipRuleService.findById(id);
			result.setData(roleResp);
		} catch (TornadoAPIServiceException e) {
			LOGGER.error("queryByIpRuleId roleId: {} error.", id, e);
			result.setResultCode(RespCodeEnum.FAILURE.code());
		}
		return result;
	}
	
	/**
	 * 获取所有已激活IP规则
	 * 
	 * @return
	 */
	@PostMapping(value = "/query_ip_rules")
	public BaseResp<List<IpRuleRespDTO>> queryAllIpRule() {
		BaseResp<List<IpRuleRespDTO>> result = new BaseResp<>();
		try {
			List<IpRuleRespDTO> roleResps = ipRuleService.findAllActiveIpRules();
			result.setData(roleResps);
		} catch (TornadoAPIServiceException e) {
			LOGGER.error("query_ip_rules error.", e);
			result.setResultCode(RespCodeEnum.FAILURE.code());
		}
		return result;
	}
	
	/**
	 * 新增IP规则
	 * 
	 * @param ipRuleReqDto
	 * @return
	 */
	@PreAuthorize("hasAuthority('sysmgr.role.update')")
	@PostMapping("/add")
	public BaseResp<IpRuleRespDTO> addIpRule(@RequestBody IpRuleReqDTO ipRuleReqDto) {
		BaseResp<IpRuleRespDTO> result = new BaseResp<>();
		if(!checkParam(ipRuleReqDto)) {
			result.setResultCode(RespCodeEnum.LACK_PARAM.code());
			return result;
		}
		try {
			IpRuleRespDTO roleRespDto = ipRuleService.persist(ipRuleReqDto);
			result.setData(roleRespDto);
		} catch (TornadoAPIServiceException e) {
			result.setResultCode(RespCodeEnum.FAILURE.code());
			LOGGER.error("addIpRule role: {} error.", ipRuleReqDto, e);
		}
		return result;
	}
	
	/**
	 * 更新IP规则
	 * 
	 * @param ipRuleReqDto
	 * @return
	 */
	@PreAuthorize("hasAuthority('sysmgr.iprule.update')")
	@PostMapping("/update")
	public BaseResp<IpRuleRespDTO> updateIpRule(@RequestBody IpRuleReqDTO ipRuleReqDto) {
		BaseResp<IpRuleRespDTO> result = new BaseResp<>();
		if(!checkParam(ipRuleReqDto)) {
			result.setResultCode(RespCodeEnum.LACK_PARAM.code());
			return result;
		}
		try {
			IpRuleRespDTO roleRespDto = ipRuleService.persist(ipRuleReqDto);
			result.setData(roleRespDto);
		} catch (TornadoAPIServiceException e) {
			result.setResultCode(RespCodeEnum.FAILURE.code());
			LOGGER.error("updateIpRule role: {} error.", ipRuleReqDto, e);
		}
		return result;
	}
	
	/**
	 * 根据id删除IP规则（物理删除）
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAuthority('sysmgr.iprule.delete')")
	@DeleteMapping(value = "/delete_by_id/{id}")
	@SuppressWarnings("rawtypes")
	public BaseResp deleteByIpRuleId(@PathVariable Long id) {
		BaseResp result = new BaseResp<>();
		try {
			ipRuleService.deleteById(id);
		} catch (TornadoAPIServiceException e) {
			LOGGER.error("deleteByIpRuleId roleId: {} error.", id, e);
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
	private boolean checkParam(IpRuleReqDTO ipRuleReqDto) {
		if(StringUtils.isEmpty(ipRuleReqDto.getIp())) {
			return false;
		} 
		return true;
	}
	
}
