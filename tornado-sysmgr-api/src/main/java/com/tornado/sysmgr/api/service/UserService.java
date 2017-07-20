package com.tornado.sysmgr.api.service;

import java.util.List;

import com.tornado.common.api.exception.TornadoAPIServiceException;
import com.tornado.common.api.template.TornadoAbstractService;
import com.tornado.sysmgr.api.dto.req.UserModifyPasswordReqDTO;
import com.tornado.sysmgr.api.dto.req.UserReqDTO;
import com.tornado.sysmgr.api.dto.resp.UserAuthRespDTO;
import com.tornado.sysmgr.api.dto.resp.UserRespDTO;

/**
 * 用户 Service
 * 
 * @author dante
 *
 */
public interface UserService extends TornadoAbstractService<UserReqDTO, UserRespDTO> {
	
	/**
	 * 根据account登录
	 * 
	 * @param account
	 * @return
	 * @throws TornadoAPIServiceException
	 */
	public UserAuthRespDTO loginAccount(String account) throws TornadoAPIServiceException;

	/**
	 * 根据account获取用户
	 * 
	 * @param account
	 * @return
	 * @throws TornadoAPIServiceException
	 */
	public UserRespDTO findByAccount(String account) throws TornadoAPIServiceException;

	/**
	 * 根据roleId获取用户
	 * 
	 * @param roleId
	 * @return
	 * @throws TornadoAPIServiceException
	 */
	public List<UserRespDTO> findByRoleId(Long roleId) throws TornadoAPIServiceException;
	
	/**
	 * 锁定用户
	 * 
	 * @param userReqDTO
	 * @throws TornadoAPIServiceException
	 */
	public void lockUser(UserReqDTO userReqDTO) throws TornadoAPIServiceException;
	
	/**
	 * 解锁用户
	 * 
	 * @param userReqDTO
	 * @throws TornadoAPIServiceException
	 */
	public void releaseLockUser(UserReqDTO userReqDTO) throws TornadoAPIServiceException;
	
	/**
	 * 检测用户原始密码是否正确
	 * 
	 * @param userModifyPasswordReqDTO
	 * @return
	 * @throws TornadoAPIServiceException
	 */
	public Boolean checkPassword(UserModifyPasswordReqDTO userModifyPasswordReqDTO) throws TornadoAPIServiceException;
	
	/**
	 * 修改用户密码
	 * 
	 * @param userModifyPasswordReqDTO
	 * @throws TornadoAPIServiceException
	 */
	public void modifyPassword(UserModifyPasswordReqDTO userModifyPasswordReqDTO) throws TornadoAPIServiceException;
}
