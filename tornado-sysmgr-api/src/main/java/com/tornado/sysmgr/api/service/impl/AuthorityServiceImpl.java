package com.tornado.sysmgr.api.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tornado.commom.dao.exception.TornadoDaoException;
import com.tornado.commom.dto.req.PageReq;
import com.tornado.commom.dto.resp.PageResp;
import com.tornado.commom.util.DateUtils;
import com.tornado.common.api.constant.RedisCacheConsts;
import com.tornado.common.api.exception.TornadoAPIServiceException;
import com.tornado.sysmgr.api.dto.req.AuthorityReqDTO;
import com.tornado.sysmgr.api.dto.resp.AuthorityRespDTO;
import com.tornado.sysmgr.api.service.AuthorityService;
import com.tornado.sysmgr.dao.dao.AuthorityDAO;
import com.tornado.sysmgr.dao.po.AuthorityPO;
import com.tornado.sysmgr.dao.po.UserPO;

/**
 * 权限服务实现类
 * 
 * @author dante
 *
 */
@Service
@Transactional(readOnly = true)
public class AuthorityServiceImpl implements AuthorityService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorityServiceImpl.class);

	@Autowired
	private AuthorityDAO authorityDAO;

	@Override
	public PageResp<AuthorityRespDTO> findPage(PageReq pageReq) throws TornadoAPIServiceException {
		return null;
	}

	@Override
	public List<AuthorityRespDTO> findRootAuthority() throws TornadoAPIServiceException {
		List<AuthorityRespDTO> authorityRespDTOs = Lists.newArrayList();
		try {
			List<AuthorityPO> pos = authorityDAO.findRootAuthority();
			for (AuthorityPO po : pos) {
				authorityRespDTOs.add(convertPoToRespDto(po));
			}
		} catch (TornadoDaoException e) {
			LOGGER.error("AuthorityDAO findRootAuthority error.", e);
			throw new TornadoAPIServiceException("AuthorityDAO findRootAuthority error.", e);
		}
		return authorityRespDTOs;
	}

	@Override
	public List<AuthorityRespDTO> findByPid(Long pid) throws TornadoAPIServiceException {
		List<AuthorityRespDTO> authorityRespDTOs = Lists.newArrayList();
		try {
			List<AuthorityPO> pos = authorityDAO.findByParentId(pid);
			for (AuthorityPO po : pos) {
				authorityRespDTOs.add(convertPoToRespDto(po));
			}
		} catch (TornadoDaoException e) {
			LOGGER.error("AuthorityDAO findByPid {} error.", pid, e);
			throw new TornadoAPIServiceException("AuthorityDAO findByPid error.", e);
		}
		return authorityRespDTOs;
	}

	@Override
	@Transactional
	@CacheEvict(value=RedisCacheConsts.FIND_USER_AUTH_CACHE, allEntries=true)
	public AuthorityRespDTO persist(AuthorityReqDTO authorityReqDTO) throws TornadoAPIServiceException {
		return convertPoToRespDto(authorityDAO.save(convertReqDtoToPo(authorityReqDTO)));
	}

	@Override
	@Transactional
	@CacheEvict(value=RedisCacheConsts.FIND_USER_AUTH_CACHE, allEntries=true)
	public void deleteById(Long id) throws TornadoAPIServiceException {
		List<AuthorityPO> authoritys = null;
		try {
			authoritys = authorityDAO.findByParentId(id);
		} catch (TornadoDaoException e) {
			LOGGER.error("AuthorityDAO findByParentId {} error.", id, e);
			throw new TornadoAPIServiceException("AuthorityDAO findByParentId error.", e);
		}

		if (!CollectionUtils.isEmpty(authoritys)) {
			authorityDAO.deleteInBatch(authoritys);
		}
		authorityDAO.delete(id);

	}

	@Override
	public AuthorityRespDTO findById(Long id) throws TornadoAPIServiceException {
		AuthorityPO authorityPO = authorityDAO.findOne(id);
		return convertPoToRespDto(authorityPO);
	}

	protected AuthorityPO convertReqDtoToPo(AuthorityReqDTO authorityReqDTO) {
		AuthorityPO authorityPO = new AuthorityPO();
		if (authorityReqDTO != null) {
			BeanUtils.copyProperties(authorityReqDTO, authorityPO, "updateUser");
			authorityPO.setUpdateDate(DateUtils.currentDate());
			if (authorityReqDTO.getUpdateUser() != null) {
				authorityPO.setUpdateUser(new UserPO(authorityReqDTO.getUpdateUser()));
			}
			if(authorityReqDTO.getPid() != null) {
				authorityPO.setParentAuthority(new AuthorityPO(authorityReqDTO.getPid()));
			}
		}
		return authorityPO;
	}

	protected AuthorityRespDTO convertPoToRespDto(AuthorityPO authorityPO) {
		AuthorityRespDTO authorityRespDTO = new AuthorityRespDTO();
		BeanUtils.copyProperties(authorityPO, authorityRespDTO);
		AuthorityPO parentAuthorityPO = authorityPO.getParentAuthority();
		if(parentAuthorityPO != null) {
			authorityRespDTO.setPid(parentAuthorityPO.getId());
		}
		return authorityRespDTO;
	}

	@Override
	public void delete(AuthorityReqDTO reqDTO) throws TornadoAPIServiceException {
		// 逻辑删除，此功能使用物理删除，故本方法不做实现
	}

}
