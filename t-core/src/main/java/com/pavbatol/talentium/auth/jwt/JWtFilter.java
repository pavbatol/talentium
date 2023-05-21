package com.pavbatol.talentium.auth.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component("JWtFilter")
@RequiredArgsConstructor
public class JWtFilter extends OncePerRequestFilter {
    private final JwtProvider tokenProvider;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.debug("-Doing {}.doFilterInternal()", getClass().getSimpleName());
        tokenProvider.resolveToken(request)
                .filter(tokenProvider::validateAccessToken)
                .map(accessToken -> {
                    Claims accessClaims = tokenProvider.getAccessClaims(accessToken);
                    return JwtAuthentication.of(accessClaims, true);
                })
                .ifPresent(authentication -> {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.debug("-Authentication was set into SecurityContextHolder: {}",
                            SecurityContextHolder.getContext().getAuthentication().toString());
                });
        filterChain.doFilter(request, response);
    }
}
