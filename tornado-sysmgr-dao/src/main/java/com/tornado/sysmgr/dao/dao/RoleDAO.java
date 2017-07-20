package com.tornado.sysmgr.dao.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.tornado.sysmgr.dao.po.RolePO;


public interface RoleDAO extends JpaRepository<RolePO, Long>, JpaSpecificationExecutor<RolePO>{

}
