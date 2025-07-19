package com.idt.aiowebflux.auth;

import com.idt.aiowebflux.dto.AccountSecret;
import com.idt.aiowebflux.entity.License;
import com.idt.aiowebflux.exception.DomainExceptionCode;
import com.idt.aiowebflux.repository.LicenseRepository;
import com.idt.aiowebflux.service.AccountRoleService;
import com.idt.aiowebflux.util.EncryptUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * SecurityConfig에서 LicenseAuthenticationProvider를 등록하여 사용 (authenticationManager 메서드)
 */
@Component
@RequiredArgsConstructor
public class LicenseAuthenticationProvider implements AuthenticationProvider {
    private final AccountRoleService accountRoleService;
    private final LicenseRepository licenseRepository;
    private final PasswordEncoder encoder;
    private final EncryptUtil encryptUtil;


    @Override
    public Authentication authenticate(final Authentication raw) {
        final String id = raw.getName();
        final String pw = raw.getCredentials().toString();

        final AccountSecret accountSecret = accountRoleService.findAccountRoleById(id);

        final License license = licenseRepository.findOne();

        final int term = encryptUtil.getTermFromLicenseKey(license.getLicenseKey());

        if (term < 0) {
            throw DomainExceptionCode.USER_LICENSE_KEY_EXPIRED.newInstance("라이선스 키가 만료되었습니다.");
        }

        if (!encoder.matches(pw, accountSecret.pw())) {
            throw DomainExceptionCode.USER_PASSWORD_MISMATCH.newInstance("비밀번호가 일치하지 않습니다.");
        }
        if (encryptUtil.validateLicenseKey(license.getLicenseKey()).isBlank()) {       // 라이선스 검증
            throw DomainExceptionCode.USER_LICENSE_KEY_VALIDATION_FAILED.newInstance("라이선스 키가 유효하지 않습니다.");
        }

        final List<SimpleGrantedAuthority> roles = accountSecret.roleNames().stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new UsernamePasswordAuthenticationToken(id, null, roles);
    }

    @Override
    public boolean supports(Class<?> auth) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(auth);
    }
}
