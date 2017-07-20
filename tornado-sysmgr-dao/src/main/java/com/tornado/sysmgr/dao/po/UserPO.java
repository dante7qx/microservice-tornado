package com.tornado.sysmgr.dao.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * 用户 PO
 * 
 * @author dante
 *
 */
@Entity
@Table(name = "t_user")
@Data
public class UserPO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
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
	/**
	 * 更新人
	 */
	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "update_user")
	private UserPO updateUser;
	/**
	 * 更新时间
	 */
	private Date updateDate;
	/**
	 * 角色信息
	 */
	@ManyToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinTable(name = "t_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	private Set<RolePO> roles;

	public UserPO() {
		// 默认构造函数
	}

	public UserPO(Long id) {
		this.id = id;
	}

}
