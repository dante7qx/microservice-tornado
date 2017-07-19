package com.tornado.common.api.template;

import com.tornado.commom.dto.req.PageReq;
import com.tornado.commom.dto.resp.PageResp;
import com.tornado.common.api.exception.TornadoAPIServiceException;

/**
 * 抽象API业务接口，所有业务Service接口需要继承此接口
 * 
 * @author dante
 *
 * @param <ReqDTO>
 * @param <RespDTO>
 */
public abstract interface TornadoAbstractService<ReqDTO, RespDTO> {
	
	/**
	 * 分页查询
	 * 
	 * @param pageReq
	 * @return
	 * @throws TornadoAPIServiceException
	 */
	public PageResp<RespDTO> findPage(PageReq pageReq) throws TornadoAPIServiceException;
	
	/**
	 * 持久化（新增、更新）
	 * 
	 * @param reqDTO
	 * @return
	 * @throws TornadoAPIServiceException
	 */
	public RespDTO persist(ReqDTO reqDTO) throws TornadoAPIServiceException;
	
	/**
	 * 物理删除
	 * 
	 * @param id
	 * @throws TornadoAPIServiceException
	 */
	public void deleteById(Long id) throws TornadoAPIServiceException;
	
	/**
	 * 逻辑删除
	 * 
	 * reqDTO：id、updateUser必须传递，不能为空
	 * 
	 * @param reqDTO
	 * @throws TornadoAPIServiceException
	 */
	public void delete(ReqDTO reqDTO) throws TornadoAPIServiceException;
	
	/**
	 * 根据id（主键）查询
	 * 
	 * @param id
	 * @return
	 * @throws TornadoAPIServiceException
	 */
	public RespDTO findById(Long id) throws TornadoAPIServiceException;
	
}
