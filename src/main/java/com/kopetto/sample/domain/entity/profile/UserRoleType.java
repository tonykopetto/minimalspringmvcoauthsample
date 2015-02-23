package com.kopetto.sample.domain.entity.profile;

import org.springframework.security.core.GrantedAuthority;

/**
 * Security role type for UserAccount.
 * 
 */
public enum UserRoleType implements GrantedAuthority{
    ROLE_ADMIN,  
    ROLE_USER   
    ;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
