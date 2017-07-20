package com.tornado.sysmgr.dao.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.tornado.commom.dao.exception.TornadoDaoException;
import com.tornado.sysmgr.dao.po.ResourcePO;


public interface ResourceDAO extends JpaRepository<ResourcePO, Long>, JpaSpecificationExecutor<ResourcePO>{
	
	@Query("select r from ResourcePO r where r.parentResource.id is null order by r.showOrder asc")
	public List<ResourcePO> findRootResource() throws TornadoDaoException;
	
	@Query("select r from ResourcePO r where r.parentResource.id =:pid order by r.showOrder asc")
	public List<ResourcePO> findByPid(@Param("pid") Long pid) throws TornadoDaoException;
	
	@Query("select distinct r.parentResource.id from ResourcePO r where r.parentResource.id is not null")
	public List<Long> findAllParentId() throws TornadoDaoException;
}
