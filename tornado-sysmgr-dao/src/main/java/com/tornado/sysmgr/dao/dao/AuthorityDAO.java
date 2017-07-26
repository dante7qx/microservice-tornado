package com.tornado.sysmgr.dao.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.tornado.commom.dao.exception.TornadoDaoException;
import com.tornado.sysmgr.dao.po.AuthorityPO;

/**
 * 权限管理 DAO
 * 
 * @author dante
 *
 */
public interface AuthorityDAO extends JpaRepository<AuthorityPO, Long> {
	
	/**
	 * 获取所有的根权限
	 * 
	 * @return
	 * @throws TornadoDaoException
	 */
	@Query("select t from AuthorityPO t where t.parentAuthority.id is null order by t.showOrder asc")
	public List<AuthorityPO> findRootAuthority() throws TornadoDaoException;
	
	/**
	 * 获取指定节点Id的权限
	 * 
	 * @param pid
	 * @return
	 * @throws TornadoDaoException
	 */
	@Query("select t from AuthorityPO t where t.parentAuthority.id = :pid order by t.showOrder asc")
	public List<AuthorityPO> findByParentId(@Param("pid") Long pid) throws TornadoDaoException;
	
}
