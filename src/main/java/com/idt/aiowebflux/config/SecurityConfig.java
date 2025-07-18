package com.idt.aiowebflux.config;

import com.idt.aiowebflux.auth.GenericaAclPermissionEvaluator;
import com.idt.aiowebflux.auth.LicenseAuthenticationProvider;
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.server.ServerWebExchange;


@Slf4j
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Configuration
public class SecurityConfig {
    // private final CorsFilter corsFilter;
    //  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    //  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final ApplicationContext ctx;


    public SecurityConfig(
            //final CorsFilter corsFilter,
            //    final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            //    final JwtAccessDeniedHandler jwtAccessDeniedHandler,
            final ApplicationContext ctx

    ) {
        //  this.corsFilter = corsFilter;
        //  this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        //  this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.ctx = ctx;
    }

    /*

        @Bean
        public JwtFilter jwtFilter(final RedisTokenRepository tokenRepo, final JwtIssuerAuthenticationManagerResolver amr) {
            return new JwtFilter(tokenRepo, amr);
        }

        @Bean
        public ExceptionBridgeFilter exceptionBridgeFilter(
                @Qualifier("handlerExceptionResolver") final HandlerExceptionResolver resolver) {
            return new ExceptionBridgeFilter(resolver);
        }
    */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(final ServerHttpSecurity http/*,
                                                         final AuthenticationWebFilter dualJwtAuthWebFilter*/) {
        // Disable CORS for now; actual config supplied by CorsWebFilter bean below
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable) // actual config supplied by CorsWebFilter bean below
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
/*                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )*/
//                .headers(h -> h.frameOptions().mode(ServerHttpSecurity.HeaderSpec.FrameOptionsSpec.Mode.SAMEORIGIN))
                .authorizeExchange(exchanges -> exchanges
                        // ----- PermitAll (copy of servlet config) -----
                        .pathMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll()
                        .pathMatchers("/api/v1/**").permitAll()
                        .pathMatchers("/h2-console/**").permitAll() // PathRequest not available in WebFlux; simple matcher
                        // Allow OPTIONS preflight for CORS
                        .pathMatchers(HttpMethod.OPTIONS, "**").permitAll()
                        // ----- Everything else requires authentication -----
                        .anyExchange().authenticated()
                );

/*
        // Insert our exception bridge early so that downstream exceptions are mapped consistently.
        http.addFilterBefore(exceptionBridgeWebFilter, SecurityWebFiltersOrder.FIRST);

        // Insert JWT authentication at AUTHENTICATION order.
        http.addFilterAt(dualJwtAuthWebFilter, SecurityWebFiltersOrder.AUTHENTICATION);
*/

        return http.build();
    }




    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain filterChain(final HttpSecurity http,
//                                           final JwtFilter jwtFilter,
//                                           final ExceptionBridgeFilter exceptionBridgeFilter)
//            throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(exceptionBridgeFilter, JwtFilter.class)
//                .exceptionHandling(exceptionHandling -> exceptionHandling
//                        .accessDeniedHandler(jwtAccessDeniedHandler)
//                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                )
//                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
//                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll()
//                        .requestMatchers("/api/v1/signup", "/api/v1/signin").permitAll()
//                        .requestMatchers("/api/v1/license", "/api/v1/license-issue", "/api/v1/license-save").permitAll()
//                        .requestMatchers("/api/v1/project-accounts", "/api/v1/project-account").permitAll()
//                        .requestMatchers("/api/v1/projects", "/api/v1/project").permitAll()
//                        .requestMatchers("/api/v1/accounts", "/api/v1/account").permitAll()
//                        .requestMatchers("/api/v1/emitter").permitAll()
//                        .requestMatchers("/project_folder*/**").permitAll()
//                        .requestMatchers("/api/v1/login/sso","/favicon.ico", "/.well-known/openid-configuration", "/.well-known/appspecific/**").permitAll()
//                        .requestMatchers("/error").permitAll()
//                        .requestMatchers(PathRequest.toH2Console()).permitAll()
//                        .anyRequest().authenticated()
//                )
//                // 세션을 사용하지 않기 때문에 STATELESS로 설정
//                .sessionManagement(sessionManagement ->
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//
//                // enable h2-console
//                .headers(headers ->
//                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
//                );
//        return http.build();
//    }
/*

    @Bean
    AuthenticationManager authenticationManager(final HttpSecurity http, final LicenseAuthenticationProvider licenseAuthenticationProvider)
            throws Exception {
        final AuthenticationManagerBuilder builder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        builder.authenticationProvider(licenseAuthenticationProvider);

        return builder.build();
    }

    @Bean
    MethodSecurityExpressionHandler methodSecurityExpressionHandler(
            GenericaAclPermissionEvaluator evaluator) {

        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setPermissionEvaluator(evaluator);
        handler.setApplicationContext(ctx);
        return handler;
    }

*/

}