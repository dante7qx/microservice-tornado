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
import com.tornado.sysmgr.dao.po.IpRulePO;

/**
 * IP规则查询规约
 * 
 * @author dante
 *
 */
public class IpRuleSpecification {
	
	private IpRuleSpecification() {
		throw new IllegalAccessError("IpRuleSpecification 不可实例化！");
	}
	
	public static Specification<IpRulePO> querySpecification(Map<String, Object> filter) {
		return new Specification<IpRulePO>() {
			@Override
			public Predicate toPredicate(Root<IpRulePO> root, CriteriaQuery<? extends Object> query, CriteriaBuilder cb) {
				List<Predicate> predicates = Lists.newArrayList();
				String ip = (String) filter.get("ip");
				String remark = (String) filter.get("remark");

				if (!StringUtils.isEmpty(ip)) {
					Predicate ipLike = cb.like(root.get("ip").as(String.class), "%" + ip.trim() + "%");
					predicates.add(ipLike);
				}
				if (!StringUtils.isEmpty(remark)) {
					Predicate remarkLike = cb.like(root.get("remark").as(String.class), "%" + remark.trim() + "%");
					predicates.add(remarkLike);
				}
				return predicates.isEmpty() ? cb.conjunction()
						: cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
}
