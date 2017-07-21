package com.tornado.commom.dao.template;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tornado.commom.dao.util.TornadoDaoUtils;
import com.tornado.commom.dto.req.PageReq;
import com.tornado.commom.dto.resp.PageResp;

/**
 * RESTful API 服务层公共模板方法
 * 
 * @author dante
 *
 * @param <REQ> ReqDTO
 * @param <RESP> RespDTO
 * @param <P> 数据库PO
 */
public abstract class TornadoServiceTemplate<REQ, RESP, P> {
	
	@Autowired
	private JpaRepository<P, Long> jpaRepository;
	@Autowired
	private JpaSpecificationExecutor<P> specificationExecutor;
	
	/**
	 * 公共分页模板方法
	 * 
	 * @param pageReq
	 * @return
	 */
	protected PageResp<RESP> findPage(PageReq pageReq)  {
		int pageNo = pageReq.getPageNo();
		int pageSize = pageReq.getPageSize();
		String sortCol = pageReq.getSort();
		String sortDir = pageReq.getOrder();
		Map<String, Object> filter = pageReq.getQ();
		Page<P> page;
		Pageable pageRequest = buildPageRequest(pageNo, pageSize, sortCol, sortDir);
		if(!filter.isEmpty()) {
			Specification<P> spec = buildSpecification(filter);
			page = specificationExecutor.findAll(spec, pageRequest); 
		} else {
			page = jpaRepository.findAll(pageRequest);
		}
		PageResp<RESP> pageResp = new PageResp<>();
		List<P> dbList = page.getContent();
		if(!CollectionUtils.isEmpty(dbList)) {
			List<RESP> list = Lists.newArrayList();
			for (P po : dbList) {
				RESP t = convertPoToRespDto(po);
				list.add(t);
			}
			pageResp.setResult(list);
		}
		pageResp.setPageNo(page.getNumber() + 1);
		pageResp.setPageSize(page.getNumberOfElements());
		pageResp.setTotalPage(page.getTotalPages());
		pageResp.setTotalCount(Integer.valueOf(page.getTotalElements() + ""));
		return pageResp;
	}
	
	/**
	 * 构造分页参数
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param sortCol
	 * @param sortDir
	 * @return
	 */
	private Pageable buildPageRequest(int pageNo, int pageSize, String sortCol, String sortDir) {
		return new PageRequest(pageNo - 1, pageSize, TornadoDaoUtils.buildJPABasicOrder(sortCol, sortDir));
	}
	
	/**
	 * 将ReqDTO对象转化为数据库的实体类
	 * 
	 * @param reqDTO
	 * @param po
	 * @return
	 */
	protected abstract P convertReqDtoToPo(REQ reqDTO);
	
	/**
	 * 将数据库的实体类转化为RespDTO对象
	 * 
	 * @param domain
	 * @return
	 */
	protected abstract RESP convertPoToRespDto(P po);
	
	/**
	 * 构造查询条件
	 * 
	 * @param filter
	 * @return
	 */
	protected abstract Specification<P> buildSpecification(Map<String, Object> filter);
	
}
