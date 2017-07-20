package com.tornado.sysmgr.dao.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tornado.commom.dao.exception.TornadoDaoException;
import com.tornado.sysmgr.dao.po.UserPO;


/**
 * 用户 DAO 
 * 
 * @author dante
 *
 */
public interface UserDAO extends JpaRepository<UserPO, Long>, JpaSpecificationExecutor<UserPO>{
	
	/**
	 * 根据account获取用户
	 * 
	 * @param account
	 * @return
	 * @throws TornadoDaoException
	 */
	public UserPO findByAccount(String account) throws TornadoDaoException;
	
	/**
	 * 根据userId删除用户，及其对应的角色
	 * 
	 * @param userId
	 */
	@Modifying
	@Query(value = "delete from t_user_role where user_id = ?1", nativeQuery = true)
	public void deleteUserRoleByUserId(Long userId);
	
}
