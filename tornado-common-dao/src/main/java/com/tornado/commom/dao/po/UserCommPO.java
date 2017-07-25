package com.tornado.commom.dao.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 公共用户 PO
 *
 * @author dante
 *
 */
@Entity
@Table(name = "t_user")
@Data
public class UserCommPO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	/**
	 * 用户帐号
	 */
	private String account;
	/**
	 * 用户名称
	 */
	private String name;
	/**
	 * 用户密码
	 */
	private String password;
	/**
	 * 用户邮箱
	 */
	private String email;
	/**
	 * 密码最后修改日期
	 */
	private Date lastPwdUpdateDate;
	/**
	 * 用户状态
	 */
	private String status;
	
	public UserCommPO() {
		// 默认构造函数
	}

	public UserCommPO(Long id) {
		this.id = id;
	}
}
