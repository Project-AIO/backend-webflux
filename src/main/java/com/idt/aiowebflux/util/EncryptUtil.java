package com.idt.aiowebflux.util;


import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.annotation.PostConstruct;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class EncryptUtil {
    /*
        private final String key = "123456789ABCDEF!";
        private final String algorithm = "AES/CBC/PKCS5Padding";*/
    // Base64로 인코딩된 256bit HMAC 키 (예: 환경변수 LICENSE_HMAC_KEY)
    @Value("${license.hmac.key}")
    private String base64HmacKey;

    // Base64로 인코딩된 256bit AES 키 (예: 환경변수 LICENSE_AES_KEY)
    @Value("${license.aes.key}")
    private String base64AesKey;


    private byte[] hmacKeyBytes;
    private byte[] aesKeyBytes;

    @PostConstruct
    public void initKeys() {
        hmacKeyBytes = Base64.getDecoder().decode(base64HmacKey);
        aesKeyBytes = Base64.getDecoder().decode(base64AesKey);

        if (hmacKeyBytes.length != 32 || aesKeyBytes.length != 32) {
            throw new IllegalStateException("키 길이가 256비트가 아닙니다.");
        }
    }

    public String validateLicenseKey(final String token) {
        try {
            // 1) JWE 파싱 및 복호화
            final JWEObject jweObject = JWEObject.parse(token);
            final DirectDecrypter decrypter = new DirectDecrypter(aesKeyBytes);
            jweObject.decrypt(decrypter);

            // 2) 내부 페이로드(JWS) 파싱
            final SignedJWT signedJWT = jweObject.getPayload().toSignedJWT();
            if (signedJWT == null) {
                throw new JOSEException("내부 토큰이 서명된 JWT가 아닙니다.");
            }

            // 3) 서명 검증
            final JWSVerifier verifier = new MACVerifier(hmacKeyBytes);
            if (!signedJWT.verify(verifier)) {
                throw new JOSEException("JWT 서명 검증 실패");
            }

            // 4) 클레임 검증 (만료 등)
            final JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            final Date exp = claims.getExpirationTime();
            if (exp == null || new Date().after(exp)) {
                throw new JOSEException("토큰이 만료되었습니다.");
            }

            // 5) 유효한 경우 subject(accountId) 반환
            return claims.getSubject();
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException("EncryptUtil.class -> 라이센스 검증 실패", e);
        }
    }


    /**
     * 라이선스 키에서 발급 기간(term, 일 수)만 계산하여 반환
     */
    public int getTermFromLicenseKey(final String licenseKey) {
        try {
            // JWE 복호화 & JWS 파싱
            final JWEObject jweObject = JWEObject.parse(licenseKey);
            jweObject.decrypt(new DirectDecrypter(aesKeyBytes));
            final SignedJWT signedJWT = jweObject.getPayload().toSignedJWT();
            final JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            final Date iat = claims.getIssueTime();
            final Date exp = claims.getExpirationTime();
            if (iat == null || exp == null) {
                throw new JOSEException("IssueTime 또는 ExpirationTime이 존재하지 않습니다.");
            }

            final long days = ChronoUnit.DAYS.between(
                    iat.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    exp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            );
            return (int) days;
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException("EncryptUtil.class -> getTermFromLicenseKey 실패", e);
        }
    }

    /*
        라이선스키 유효기간 설정 (생성일에 Term 일수 플러스)
     */
    public String expireDate(final int term) {
        final LocalDate now = LocalDate.now().plusDays(term);
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        return now.format(formatter);
    }

    /**
     * 라이선스 키 생성: accountId|만료일 을 JWS로 서명한 뒤, JWE(AES-GCM)로 암호화하여 반환
     */
    public String genLicenseKey(final String adminId, final int term) {
        try {
            // 1) 클레임 빌드
            final JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(adminId)
                    .issueTime(new Date())
                    .expirationTime(Date.from(Instant.now().plus(term, ChronoUnit.DAYS)))
                    .build();

            // 2) JWS(Signature)
            final SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader(JWSAlgorithm.HS256),
                    claims
            );
            signedJWT.sign(new MACSigner(hmacKeyBytes));

            // 3) JWE(Encryption)
            final JWEObject jwe = new JWEObject(
                    new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A256GCM)
                            .contentType("JWT")
                            .build(),
                    new Payload(signedJWT)
            );
            jwe.encrypt(new DirectEncrypter(aesKeyBytes));

            return jwe.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("genLicenseKey 실패", e);
        }
    }


    /**
     * 라이선스 키에서 만료일(LocalDate)만 추출
     */
    public LocalDate selLicenseKeyToDate(final String licenseKey) {
        try {
            // JWE 복호화
            final JWEObject jweObject = JWEObject.parse(licenseKey);
            jweObject.decrypt(new DirectDecrypter(aesKeyBytes));

            // 내부 JWS 파싱
            final SignedJWT signedJWT = jweObject.getPayload().toSignedJWT();
            final Date exp = signedJWT.getJWTClaimsSet().getExpirationTime();

            return exp.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException("selLicenseKeyToDate 실패", e);
        }
    }

}
