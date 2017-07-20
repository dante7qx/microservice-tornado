package com.tornado.sysmgr.dao.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tornado.sysmgr.dao.po.IpRulePO;

/**
 * IP规则DAO
 * 
 * @author dante
 *
 */
public interface IpRuleDAO extends JpaRepository<IpRulePO, Long>, JpaSpecificationExecutor<IpRulePO> {

}
