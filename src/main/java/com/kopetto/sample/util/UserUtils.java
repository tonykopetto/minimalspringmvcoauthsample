package com.kopetto.sample.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.kopetto.sample.domain.entity.profile.User;

/**
 * 
 */
public class UserUtils {
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public static UserDetails getLoginUser() {
		if (getAuthentication() != null
				&& getAuthentication().getPrincipal() instanceof UserDetails) {
			return (UserDetails) getAuthentication().getPrincipal();
		}
		return null;
	}

	public static String getLoginUserName() {
		UserDetails details = getLoginUser();
		return (details == null) ? null : details.getUsername();
	}

	private UserUtils() {
	}
}
