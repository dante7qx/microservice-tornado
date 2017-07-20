package com.tornado.sysmgr.api.dto.resp;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

/**
 * 当前用户所有资源返回参数
 * 
 * @author dante
 *
 */
@Data
public class UserResourceRespDTO {
	private Long id;
	private String name;
	private String url;
	private Long pid;
	private List<UserResourceRespDTO> children;

	public List<UserResourceRespDTO> getChildren() {
		if (this.children == null) {
			this.children = Lists.newLinkedList();
		}
		return children;
	}

	public void setChildren(List<UserResourceRespDTO> children) {
		this.children = children;
	}

}
