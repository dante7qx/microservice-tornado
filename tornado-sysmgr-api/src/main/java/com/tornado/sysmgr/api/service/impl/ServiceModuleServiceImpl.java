package com.tornado.sysmgr.api.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tornado.commom.dto.req.PageReq;
import com.tornado.commom.dto.resp.PageResp;
import com.tornado.commom.util.DateUtils;
import com.tornado.common.api.exception.TornadoAPIServiceException;
import com.tornado.common.api.template.TornadoServiceTemplate;
import com.tornado.sysmgr.api.dto.req.ServiceModuleReqDTO;
import com.tornado.sysmgr.api.dto.resp.ServiceModuleRespDTO;
import com.tornado.sysmgr.api.service.ServiceModuleService;
import com.tornado.sysmgr.dao.dao.ServiceModuleDAO;
import com.tornado.sysmgr.dao.dao.specification.ServiceModuleSpecification;
import com.tornado.sysmgr.dao.po.ServiceModulePO;
import com.tornado.sysmgr.dao.po.UserPO;

/**
 * 服务模块服务实现类
 * 
 * @author dante
 *
 */
@Service
@Transactional(readOnly = true)
public class ServiceModuleServiceImpl extends TornadoServiceTemplate<ServiceModuleReqDTO, ServiceModuleRespDTO, ServiceModulePO> implements ServiceModuleService {

	@Autowired
	private ServiceModuleDAO serviveModuleDAO;
	
	@Override
	public PageResp<ServiceModuleRespDTO> findPage(PageReq pageReq) throws TornadoAPIServiceException {
		return super.findPage(pageReq);
	}

	@Override
	@Transactional
	public ServiceModuleRespDTO persist(ServiceModuleReqDTO reqDTO) throws TornadoAPIServiceException {
		return convertPoToRespDto(serviveModuleDAO.save(convertReqDtoToPo(reqDTO)));
	}

	@Override
	@Transactional
	public void deleteById(Long id) throws TornadoAPIServiceException {
		serviveModuleDAO.delete(id);
	}

	@Override
	public ServiceModuleRespDTO findById(Long id) throws TornadoAPIServiceException {
		return convertPoToRespDto(serviveModuleDAO.findOne(id));
	}

	@Override
	public List<ServiceModuleRespDTO> findServiceModuleResps() throws TornadoAPIServiceException {
		List<ServiceModuleRespDTO> dtos = Lists.newLinkedList();
		List<ServiceModulePO> pos = serviveModuleDAO.findAll(new Sort(Direction.ASC, "name"));
		if(!CollectionUtils.isEmpty(pos)) {
			for (ServiceModulePO po : pos) {
				dtos.add(convertPoToRespDto(po));
			}
		}
		return dtos;
	}
	
	@Override
	protected Specification<ServiceModulePO> buildSpecification(Map<String, Object> filter) {
		return ServiceModuleSpecification.querySpecification(filter);
	}

	@Override
	protected ServiceModulePO convertReqDtoToPo(ServiceModuleReqDTO reqDTO) {
		ServiceModulePO serviveModulePO = new ServiceModulePO();
		if(reqDTO != null) {
			BeanUtils.copyProperties(reqDTO, serviveModulePO, "updateUser");
			serviveModulePO.setUpdateDate(DateUtils.currentDate());
			if(reqDTO.getUpdateUser() != null) {
				serviveModulePO.setUpdateUser(new UserPO(reqDTO.getUpdateUser()));
			}
		}
		return serviveModulePO;
	}
	
	@Override
	protected ServiceModuleRespDTO convertPoToRespDto(ServiceModulePO po) {
		ServiceModuleRespDTO serviceModuleRespDTO = new ServiceModuleRespDTO();
		if(po != null) {
			BeanUtils.copyProperties(po, serviceModuleRespDTO, "updateUser", "updateDate");
			if (po.getUpdateUser() != null) {
				serviceModuleRespDTO.setUpdateUserName(po.getUpdateUser().getName());
			}
			if (po.getUpdateDate() != null) {
				serviceModuleRespDTO.setUpdateDate(DateUtils.formatDateTime(po.getUpdateDate()));
			}
		}
		return serviceModuleRespDTO;
	}

	@Override
	public void delete(ServiceModuleReqDTO reqDTO) throws TornadoAPIServiceException {
		// 逻辑删除，此功能使用物理删除，故本方法不做实现
	}

}
