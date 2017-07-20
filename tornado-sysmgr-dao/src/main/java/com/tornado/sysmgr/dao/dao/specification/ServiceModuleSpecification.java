package com.tornado.sysmgr.dao.dao.specification;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.tornado.sysmgr.dao.po.ServiceModulePO;

/**
 * 服务模块查询规约
 * 
 * @author dante
 *
 */
public class ServiceModuleSpecification {
	
	private ServiceModuleSpecification() {
		throw new IllegalAccessError("ServiceModuleSpecification 不可实例化！");
	}
	
	public static Specification<ServiceModulePO> querySpecification(Map<String, Object> filter) {
		return new Specification<ServiceModulePO>() {
			@Override
			public Predicate toPredicate(Root<ServiceModulePO> root, CriteriaQuery<? extends Object> query, CriteriaBuilder cb) {
				List<Predicate> predicates = Lists.newArrayList();
				String name = (String) filter.get("name");
				String url = (String) filter.get("url");
				if (!StringUtils.isEmpty(name)) {
					Predicate nameLike = cb.like(root.get("name").as(String.class), "%" + name.trim() + "%");
					predicates.add(nameLike);
				}
				if (!StringUtils.isEmpty(url)) {
					Predicate urlLike = cb.like(root.get("url").as(String.class), "%" + url.trim() + "%");
					predicates.add(urlLike);
				}
				return predicates.isEmpty() ? cb.conjunction()
						: cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
}
