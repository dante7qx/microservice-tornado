package com.tornado.sysmgr.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.tornado.commom.dto.req.PageReq;
import com.tornado.commom.dto.resp.BaseResp;
import com.tornado.commom.dto.resp.PageResp;
import com.tornado.commom.dto.resp.RespCodeEnum;
import com.tornado.common.api.exception.TornadoAPIServiceException;
import com.tornado.sysmgr.api.dto.req.UserModifyPasswordReqDTO;
import com.tornado.sysmgr.api.dto.req.UserReqDTO;
import com.tornado.sysmgr.api.dto.resp.UserRespDTO;
import com.tornado.sysmgr.api.service.UserService;

/**
 * 用户 REST API
 * 
 * @author dante
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	/**
	 * 分页查询用户
	 * 
	 * @param pageReq
	 * @return
	 */
	@PostMapping("/query_page")
	public BaseResp<PageResp<UserRespDTO>> queryUserPage(@RequestBody PageReq pageReq) {
		BaseResp<PageResp<UserRespDTO>> result = new BaseResp<>();
		try {
			PageResp<UserRespDTO> pageResp = userService.findPage(pageReq);
			result.setData(pageResp);
		} catch (TornadoAPIServiceException e) {
			LOGGER.error("queryUserPage error.", e);
			result.setResultCode(RespCodeEnum.FAILURE.code());
		}
		return result;
	}

	/**
	 * 根据id获取用户
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping(value = "/query_by_id/{id}")
	public BaseResp<UserRespDTO> queryByUserId(@PathVariable Long id) {
		BaseResp<UserRespDTO> result = new BaseResp<>();
		try {
			UserRespDTO userResp = userService.findById(id);
			result.setData(userResp);
		} catch (Exception e) {
			LOGGER.error("queryByUserId userId: {} error.", id, e);
			result.setResultCode(RespCodeEnum.FAILURE.code());
		}
		return result;
	}

	/**
	 * 新增用户
	 * 
	 * @param userReqDto
	 * @return
	 */
	@PostMapping("/add")
	public BaseResp<UserRespDTO> addUser(@RequestBody UserReqDTO userReqDto) {
		BaseResp<UserRespDTO> result = new BaseResp<>();
		if (!checkParam(userReqDto, true)) {
			result.setResultCode(RespCodeEnum.LACK_PARAM.code());
			return result;
		}
		try {
			UserRespDTO userRespDto = userService.persist(userReqDto);
			result.setData(userRespDto);
		} catch (TornadoAPIServiceException e) {
			result.setResultCode(RespCodeEnum.FAILURE.code());
			LOGGER.error("addUser user: {} error.", userReqDto, e);
		}
		return result;
	}

	/**
	 * 更新用户
	 * 
	 * @param userReqDto
	 * @return
	 */
	@PostMapping("/update")
	public BaseResp<UserRespDTO> updateUser(@RequestBody UserReqDTO userReqDto) {
		BaseResp<UserRespDTO> result = new BaseResp<>();
		if (!checkParam(userReqDto, false)) {
			result.setResultCode(RespCodeEnum.LACK_PARAM.code());
			return result;
		}
		try {
			UserRespDTO userRespDto = userService.persist(userReqDto);
			result.setData(userRespDto);
		} catch (TornadoAPIServiceException e) {
			result.setResultCode(RespCodeEnum.FAILURE.code());
			LOGGER.error("updateUser user: {} error.", userReqDto, e);
		}
		return result;
	}

	/**
	 * 删除用户（逻辑删除）
	 * 
	 * @param userReqDto
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@DeleteMapping(value = "/delete")
	public BaseResp deleteUser(@RequestBody UserReqDTO userReqDto) {
		BaseResp result = new BaseResp<>();
		try {
			userService.delete(userReqDto);
		} catch (TornadoAPIServiceException e) {
			result.setResultCode(RespCodeEnum.FAILURE.code());
			LOGGER.error("deleteUser userReqDto: {} error.", userReqDto, e);
		}
		return result;
	}

	/**
	 * 锁定用户
	 * 
	 * @param userReqDto
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/lock_user")
	public BaseResp lockUser(@RequestBody UserReqDTO userReqDto) {
		BaseResp result = new BaseResp<>();
		try {
			userService.lockUser(userReqDto);
		} catch (TornadoAPIServiceException e) {
			result.setResultCode(RespCodeEnum.FAILURE.code());
			LOGGER.error("lockUser userReqDto: {} error.", userReqDto, e);
		}
		return result;
	}

	/**
	 * 解锁用户
	 * 
	 * @param userReqDto
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/release_lock_user")
	public BaseResp releaseLockUser(@RequestBody UserReqDTO userReqDto) {
		BaseResp result = new BaseResp<>();
		try {
			userService.releaseLockUser(userReqDto);
		} catch (TornadoAPIServiceException e) {
			result.setResultCode(RespCodeEnum.FAILURE.code());
			LOGGER.error("releaseLockUser userReqDto: {} error.", userReqDto, e);
		}
		return result;
	}

	/**
	 * 检查用户密码
	 * 
	 * @param userModifyPasswordReqDTO
	 * @return
	 */
	@PostMapping(value = "/check_password")
	public BaseResp<Boolean> checkPassword(@RequestBody UserModifyPasswordReqDTO userModifyPasswordReqDTO) {
		BaseResp<Boolean> result = new BaseResp<Boolean>();
		try {
			result.setData(userService.checkPassword(userModifyPasswordReqDTO));
		} catch (TornadoAPIServiceException e) {
			result.setResultCode(RespCodeEnum.FAILURE.code());
			LOGGER.error("userModifyPasswordReqDTO error.", userModifyPasswordReqDTO, e);
		}
		return result;
	}

	/**
	 * 修改用户密码
	 * 
	 * @param userModifyPasswordReqDTO
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/modify_password")
	public BaseResp modifyPassword(@RequestBody UserModifyPasswordReqDTO userModifyPasswordReqDTO) {
		BaseResp result = new BaseResp<>();
		try {
			if (!checkPasswordParam(userModifyPasswordReqDTO)) {
				result.setResultCode(RespCodeEnum.LACK_PARAM.code());
				return result;
			}
			userService.modifyPassword(userModifyPasswordReqDTO);
		} catch (TornadoAPIServiceException e) {
			result.setResultCode(RespCodeEnum.FAILURE.code());
			LOGGER.error("modifyPassword error.", userModifyPasswordReqDTO, e);
		}
		return result;
	}

	/**
	 * 根据account获取用户
	 * 
	 * @param account
	 * @return
	 */
	@PostMapping(value = "/query_by_account/{account}")
	public BaseResp<UserRespDTO> queryByAccount(@PathVariable String account) {
		BaseResp<UserRespDTO> result = new BaseResp<>();
		try {
			UserRespDTO userResp = userService.findByAccount(account);
			result.setData(userResp);
		} catch (Exception e) {
			LOGGER.error("queryByAccount account: {} error.", account, e);
		}
		return result;
	}

	/**
	 * 获取指定roleId下的所有用户
	 * 
	 * @param roleId
	 * @return
	 */
	@PostMapping(value = "/query_by_role_id/{roleId}")
	public BaseResp<List<UserRespDTO>> queryByRoleId(@PathVariable Long roleId) {
		BaseResp<List<UserRespDTO>> result = new BaseResp<>();
		try {
			List<UserRespDTO> userResps = userService.findByRoleId(roleId);
			result.setData(userResps);
		} catch (Exception e) {
			LOGGER.error("queryByRoleId roleId: {} error.", roleId, e);
		}
		return result;
	}

	/**
	 * 用户参数校验
	 * 
	 * @param userReqDTO
	 * @return
	 */
	private boolean checkParam(UserReqDTO userReqDTO, boolean isNew) {
		if ((isNew && StringUtils.isEmpty(userReqDTO.getPassword())) || (!isNew && userReqDTO.getId() == null)
				|| StringUtils.isEmpty(userReqDTO.getAccount()) || StringUtils.isEmpty(userReqDTO.getName())
				|| StringUtils.isEmpty(userReqDTO.getEmail()) || userReqDTO.getUpdateUser() == null) {
			return false;
		}
		return true;
	}

	/**
	 * 修改密码参数校验
	 * 
	 * @param userModifyPasswordReqDTO
	 * @return
	 */
	private boolean checkPasswordParam(UserModifyPasswordReqDTO userModifyPasswordReqDTO) {
		if (userModifyPasswordReqDTO.getId() == null || userModifyPasswordReqDTO.getUpdateUser() == null
				|| StringUtils.isEmpty(userModifyPasswordReqDTO.getOldPassword())
				|| StringUtils.isEmpty(userModifyPasswordReqDTO.getNewPassword())) {
			return false;
		}
		return true;
	}
}
