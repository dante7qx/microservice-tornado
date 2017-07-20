package com.tornado.sysmgr.dao.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * IP规则 PO
 * 
 * @author dante
 *
 */
@Entity
@Table(name = "t_ip_rule")
@Data
@NoArgsConstructor
public class IpRulePO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	/**
	 * IP地址
	 */
	private String ip;

	/**
	 * 说明
	 */
	private String remark;

	/**
	 * 是否激活，默认为 false
	 */
	private Boolean active = false;

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
	
	public IpRulePO(Long id) {
		this.id = id;
	}
}
