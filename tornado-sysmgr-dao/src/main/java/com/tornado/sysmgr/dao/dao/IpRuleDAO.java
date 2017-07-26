package com.tornado.sysmgr.dao.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.tornado.commom.dao.exception.TornadoDaoException;
import com.tornado.sysmgr.dao.po.IpRulePO;

/**
 * IP规则DAO
 * 
 * @author dante
 *
 */
public interface IpRuleDAO extends JpaRepository<IpRulePO, Long>, JpaSpecificationExecutor<IpRulePO> {
	
	@Query("select t from IpRulePO t where t.active = 1 order by t.id asc")
	public List<IpRulePO> findAllActiveIpRules() throws TornadoDaoException;
}
