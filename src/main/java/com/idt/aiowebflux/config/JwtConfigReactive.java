package com.idt.aiowebflux.config;

import com.idt.aiowebflux.auth.converter.CustomJwtAuthenticationConverter;
import com.idt.aiowebflux.auth.decoder.ReactiveJjwtDecoder;
import org.springframework.security.oauth2.jwt.Jwt;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerReactiveAuthenticationManagerResolver;
import org.springframework.security.oauth2.server.resource.authentication.JwtReactiveAuthenticationManager;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class JwtConfigReactive {

    @Value("${sso.jwks.url}")
    private String jwksUrl;
    @Value("${sso.server.url}")
    private String ssoServerUrl;

    @Value("${jwt.secret}")       private String secret;


    /** 권한 변환 로직 (Nimbus‑용) */
    private Converter<Jwt, Mono<? extends AbstractAuthenticationToken>> defaultConverter() {
        JwtGrantedAuthoritiesConverter std = new JwtGrantedAuthoritiesConverter();
        return jwt -> {
            Set<GrantedAuthority> auths = new HashSet<>(std.convert(jwt));
            List<String> roles = jwt.getClaimAsStringList("roles");
            if (roles != null) {
                roles.forEach(role -> auths.add(new SimpleGrantedAuthority("ROLE_" + role)));
            }
            return Mono.just(new JwtAuthenticationToken(jwt, auths));
        };
    }

    @Bean
    JwtIssuerReactiveAuthenticationManagerResolver amResolver(CustomJwtAuthenticationConverter conv) {


        SecretKey hmacKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        ReactiveJwtDecoder hsDec = new ReactiveJjwtDecoder(hmacKey);
        JwtReactiveAuthenticationManager hsMgr =
                new JwtReactiveAuthenticationManager(hsDec);
        hsMgr.setJwtAuthenticationConverter(
                new ReactiveJwtAuthenticationConverterAdapter(conv));


        ReactiveJwtDecoder rsDec = ReactiveJwtDecoders.fromIssuerLocation(ssoServerUrl);
        JwtReactiveAuthenticationManager rsMgr =
                new JwtReactiveAuthenticationManager(rsDec);
        rsMgr.setJwtAuthenticationConverter(defaultConverter());

        return new JwtIssuerReactiveAuthenticationManagerResolver(issuer -> {
            if ("AIO".equals(issuer))        return Mono.just(hsMgr);
            if (ssoServerUrl.equals(issuer)) return Mono.just(rsMgr);
            return Mono.empty();
        });
    }
}
