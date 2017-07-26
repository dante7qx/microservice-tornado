package com.tornado.sysmgr.api.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tornado.commom.dao.exception.TornadoDaoException;
import com.tornado.commom.dao.template.TornadoServiceTemplate;
import com.tornado.commom.dto.req.PageReq;
import com.tornado.commom.dto.resp.PageResp;
import com.tornado.commom.util.DateUtils;
import com.tornado.common.api.constant.RedisCacheConsts;
import com.tornado.common.api.exception.TornadoAPIServiceException;
import com.tornado.sysmgr.api.dto.req.IpRuleReqDTO;
import com.tornado.sysmgr.api.dto.resp.IpRuleRespDTO;
import com.tornado.sysmgr.api.service.IpRuleService;
import com.tornado.sysmgr.dao.dao.IpRuleDAO;
import com.tornado.sysmgr.dao.dao.specification.IpRuleSpecification;
import com.tornado.sysmgr.dao.po.IpRulePO;
import com.tornado.sysmgr.dao.po.UserPO;

/**
 * IP规则服务实现类
 * 
 * @author dante
 *
 */
@Service
@Transactional(readOnly = true)
@CacheConfig(cacheNames=RedisCacheConsts.FIND_IP_RULE_CACHE)
public class IpRuleServiceImpl extends TornadoServiceTemplate<IpRuleReqDTO, IpRuleRespDTO, IpRulePO> implements IpRuleService {
	
	@Autowired
	private IpRuleDAO ipRuleDAO;

	@Override
	public PageResp<IpRuleRespDTO> findPage(PageReq pageReq)  {
		return super.findPage(pageReq);
	}

	@Override
	@Transactional
	@CacheEvict(value="caches[0].name", allEntries=true)
	public IpRuleRespDTO persist(IpRuleReqDTO ipRuleReqDTO) throws TornadoAPIServiceException {
		IpRulePO ipRulePO = ipRuleDAO.save(convertReqDtoToPo(ipRuleReqDTO));
		return convertPoToRespDto(ipRulePO);
	}

	@Override
	@CacheEvict(value="caches[0].name", allEntries=true)
	public void deleteById(Long id) throws TornadoAPIServiceException {
		ipRuleDAO.delete(id);
	}

	@Override
	public void delete(IpRuleReqDTO reqDTO) throws TornadoAPIServiceException {
		// 逻辑删除，此功能使用物理删除，故本方法不做实现
	}

	@Override
	public IpRuleRespDTO findById(Long id) throws TornadoAPIServiceException {
		return convertPoToRespDto(ipRuleDAO.findOne(id));
	}

	@Override
	@Cacheable("caches[0].name")
	public List<IpRuleRespDTO> findAllActiveIpRules() throws TornadoAPIServiceException {
		List<IpRuleRespDTO> ipRuleRespDTOs = Lists.newArrayList();
		try {
			List<IpRulePO> ipRulePOs = ipRuleDAO.findAllActiveIpRules();
			if (!CollectionUtils.isEmpty(ipRulePOs)) {
				for (IpRulePO ipRulePO : ipRulePOs) {
					ipRuleRespDTOs.add(convertPoToRespDto(ipRulePO));
				}
			}
		} catch (TornadoDaoException e) {
			throw new TornadoAPIServiceException(e);
		}
		return ipRuleRespDTOs;
	}

	@Override
	protected IpRulePO convertReqDtoToPo(IpRuleReqDTO ipReqDTO) {
		IpRulePO ipRulePO = new IpRulePO();
		if(ipReqDTO != null) {
			BeanUtils.copyProperties(ipReqDTO, ipRulePO, "updateUser");
			ipRulePO.setUpdateDate(DateUtils.currentDate());
			if (ipReqDTO.getUpdateUser() != null) {
				ipRulePO.setUpdateUser(new UserPO(ipReqDTO.getUpdateUser()));
			}
		}
		return ipRulePO;
	}

	@Override
	protected IpRuleRespDTO convertPoToRespDto(IpRulePO ipRulePO) {
		IpRuleRespDTO ipRuleRespDTO = new IpRuleRespDTO();
		if(ipRulePO != null) {
			BeanUtils.copyProperties(ipRulePO, ipRuleRespDTO, "updateUser", "updateDate");
			if (ipRulePO.getUpdateUser() != null) {
				ipRuleRespDTO.setUpdateUserName(ipRulePO.getUpdateUser().getName());
			}
			if (ipRulePO.getUpdateDate() != null) {
				ipRuleRespDTO.setUpdateDate(DateUtils.formatDateTime(ipRulePO.getUpdateDate()));
			}
		}
		return ipRuleRespDTO;
	}

	@Override
	protected Specification<IpRulePO> buildSpecification(Map<String, Object> filter) {
		return IpRuleSpecification.querySpecification(filter);
	}

}
