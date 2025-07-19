package com.idt.aiowebflux.error;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idt.aiowebflux.exception.BaseCustomException;
import com.idt.aiowebflux.exception.ExternalServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final ObjectMapper mapper;

    /* ───────── 공용 헬퍼 ───────── */
    private Mono<ResponseEntity<ProblemDetail>> build(
            HttpStatus status, Throwable ex, ServerWebExchange exch) {

        ProblemDetail body = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        body.setProperty("path", exch.getRequest().getPath().value());
        return Mono.just(ResponseEntity.status(status).body(body));
    }

    /* ───────── 도메인 예외 ───────── */
    @ExceptionHandler({BaseCustomException.class, ExternalServiceException.class})
    public Mono<ResponseEntity<ProblemDetail>> handleDomain(
            BaseCustomException ex, ServerWebExchange exch) {

        ProblemDetail body = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        body.setProperty("path", exch.getRequest().getPath().value());
        body.setProperty("code", ex.getCode());
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body));
    }

    /* ───────── 검증/바인딩 ───────── */
    @ExceptionHandler({
            WebExchangeBindException.class,          // body @Valid 실패
            ServerWebInputException.class,           // 파라미터/part 누락·타입오류
    })
    public Mono<ResponseEntity<ProblemDetail>> handleValidation(
            Exception ex, ServerWebExchange exch) {
        return build(HttpStatus.BAD_REQUEST, ex, exch);
    }

    /* ───────── 요청/응답 형식 ───────── */
    @ExceptionHandler({
            UnsupportedMediaTypeStatusException.class,
            MethodNotAllowedException.class
    })
    public Mono<ResponseEntity<ProblemDetail>> handleHttpFormat(
            Exception ex, ServerWebExchange exch) {

        return build(HttpStatus.BAD_REQUEST, ex, exch);
    }

    /* ───────── 파일, DB 등 기타 ───────── */
    @ExceptionHandler({
            MaxUploadSizeExceededException.class,
            DataAccessException.class,
            PropertyReferenceException.class,
            IllegalArgumentException.class
    })
    public Mono<ResponseEntity<ProblemDetail>> handleKnown(
            Exception ex, ServerWebExchange exch) {
        return build(HttpStatus.BAD_REQUEST, ex, exch);
    }

    /* ───────── 인증/권한 ───────── */
    @ExceptionHandler(AuthenticationException.class)
    public Mono<ResponseEntity<ProblemDetail>> handleAuth(
            AuthenticationException ex, ServerWebExchange exch) {
        return build(HttpStatus.UNAUTHORIZED, ex, exch);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Mono<ResponseEntity<ProblemDetail>> handleAccessDenied(
            AccessDeniedException ex, ServerWebExchange exch) {
        return build(HttpStatus.FORBIDDEN, ex, exch);
    }

    @ExceptionHandler({io.jsonwebtoken.JwtException.class})
    public Mono<ResponseEntity<ProblemDetail>> handleJwt(
            RuntimeException ex, ServerWebExchange exch) {
        return build(HttpStatus.UNAUTHORIZED, ex, exch);
    }

    /* ───────── WebClient 오류 ───────── */
    @ExceptionHandler(RestClientException.class)
    public Mono<ResponseEntity<JsonNode>> handleRestClient(
            RestClientException ex, ServerWebExchange exch) {

        HttpStatus status = HttpStatus.BAD_GATEWAY;

        JsonNode downstream;
        try {
            downstream = mapper.readTree(ex.getMessage());
        } catch (Exception parse) {
            downstream = mapper.createObjectNode().put("detail", ex.getMessage());
        }
        return Mono.just(ResponseEntity.status(status).body(downstream));
    }

    /* ───────── 마지막 catch‑all ───────── */
    @ExceptionHandler(Throwable.class)
    public Mono<ResponseEntity<ProblemDetail>> handleOthers(
            Throwable ex, ServerWebExchange exch) {
        log.error("UNEXPECTED", ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ex, exch);
    }
}
