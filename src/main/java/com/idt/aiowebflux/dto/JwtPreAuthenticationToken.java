package com.idt.aiowebflux.dto;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class JwtPreAuthenticationToken extends AbstractAuthenticationToken {
    private final String token;        // 원본 JWT
    private final String subject;      // 사용자 ID

    public JwtPreAuthenticationToken(final String token, final String subject) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.token    = token;
        this.subject  = subject;
        setAuthenticated(false);
    }
    @Override public Object getCredentials() { return token; }
    @Override public Object getPrincipal()   { return subject; }
}
