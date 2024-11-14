package com.chungkathon.squirrel.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

// 확장을 해서 만듦
public class MemberAuthentication extends UsernamePasswordAuthenticationToken {
    public MemberAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public static MemberAuthentication createMemberAuthentication(String username) {
        return new MemberAuthentication(username, null, null);
    }
}