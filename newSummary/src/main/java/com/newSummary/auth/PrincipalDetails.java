package com.newSummary.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.newSummary.domain.entity.User;

public class PrincipalDetails implements UserDetails, OAuth2User {

	private User user;

	public PrincipalDetails(User user) {
		this.user = user;
	}

	// 권한 관련 작업을 하기 위한 role return
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collections = new ArrayList<>();
		collections.add(() -> {
			return user.getUserRole().name();
		});

		return collections;
	}

	// get Password 메서드
	@Override
	public String getPassword() {
		return user.getUserPw();
	}

	// get Username 메서드 (생성한 User은 userEmail 사용)
	@Override
	public String getUsername() {
		return user.getUserEmail();
	}

	// 계정이 만료 되었는지 (true: 만료X)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정이 잠겼는지 (true: 잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 비밀번호가 만료되었는지 (true: 만료X)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정이 활성화(사용가능)인지 (true: 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}

	// OAuth 로그인
	private Map<String, Object> attributes;

	public PrincipalDetails(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}
}
