package com.tornado.api.vo;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

/**
 * 用户拥有的所有资源 VO
 * 
 * @author dante
 *
 */
@Data
public class LoginUserMenuVO {
	private Long id;
	private String name;
	private String url;
	private List<LoginUserMenuVO> children;

	public List<LoginUserMenuVO> getChildren() {
		if (this.children == null) {
			this.children = Lists.newLinkedList();
		}
		return children;
	}


}
