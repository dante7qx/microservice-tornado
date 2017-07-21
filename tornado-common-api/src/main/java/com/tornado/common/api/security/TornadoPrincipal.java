package com.tornado.common.api.security;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;

/**
 * 认证实体类
 * 
 * @author dante
 *
 */
public class TornadoPrincipal implements UserDetails {
	private static final long serialVersionUID = 1L;

	private TornadoLoginUser tornadoLoginUser;
	private String password;

	public TornadoPrincipal() {
		// TODO Auto-generated constructor stub
	}

	public TornadoPrincipal(TornadoLoginUser tornadoLoginUser, String password) {
		this.tornadoLoginUser = tornadoLoginUser;
		this.password = password;
	}

	/**
	 * 当前认证实体的所有权限
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> grantedAuthoritys = Lists.newLinkedList();
		Set<String> authoritys = this.getSpiritLoginUser().getAuthoritys();
		if (!CollectionUtils.isEmpty(authoritys)) {
			grantedAuthoritys = AuthorityUtils.createAuthorityList(authoritys.toArray(new String[authoritys.size()]));
		}
		return grantedAuthoritys;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return tornadoLoginUser.getAccount();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public TornadoLoginUser getSpiritLoginUser() {
		return tornadoLoginUser;
	}

	/**
	 * 重写equals()方法
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this.getClass() == obj.getClass()) {
			return getUsername().equals(((TornadoPrincipal) obj).getUsername());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getUsername().hashCode();
	}

	@Override
	public String toString() {
		return "TornadoPrincipal [tornadoLoginUser=" + tornadoLoginUser + "]";
	}

}
