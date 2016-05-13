package com.github.vvserdiuk.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by vvserdiuk on 13.05.2016.
 */
public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}