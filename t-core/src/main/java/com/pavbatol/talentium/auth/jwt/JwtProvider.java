package com.pavbatol.talentium.auth.jwt;

import com.pavbatol.talentium.app.exception.NotFoundException;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Optional;

@Slf4j
@Component
public class JwtProvider {
    public static final String ROLES = "roles";
    public static final String FIRST_NAME = "firstName";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER_ = "Bearer ";
    public static final String APP_JWT_ACCESS_KEY = "APP_JWT_ACCESS_KEY";
    public static final String USER_ID = "userId";
    public static final String TOKEN_NOT_OBTAINED_FROM_HTTP_SERVLET_REQUEST = "Token not obtained from HttpServletRequest";
    public static final String USER_ID_NOT_OBTAINED_FROM_TOKEN = "user ID not obtained from token";
    private final SecretKey jwtAccessKey;

    @Autowired
    public JwtProvider(@Value("${app.jwt.access.key}") String accessSecretStr,
                       Environment env) {
        this.jwtAccessKey = JwtUtils.toSecretKey(accessSecretStr);

        log.debug("-! CHECK Local_: app.jwt.access.key = " + accessSecretStr);
        log.debug("-! CHECK System: APP_JWT_ACCESS_KEY = " + env.getProperty(APP_JWT_ACCESS_KEY));
    }

    public Optional<String> resolveToken(@NonNull HttpServletRequest request) {
        final String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_)) {
            return Optional.of(bearerToken.substring(7));
        }
        return Optional.empty();
    }

    public String resolveTokenAsNotNull(@NonNull HttpServletRequest request) {
        return resolveToken(request)
                .orElseThrow(() -> new NotFoundException(TOKEN_NOT_OBTAINED_FROM_HTTP_SERVLET_REQUEST));
    }

    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, jwtAccessKey);
    }

    public Optional<Long> getUserId(@NonNull String accessToken) {
        Claims claims = getClaims(accessToken, jwtAccessKey);
        return Optional.ofNullable(claims.get(USER_ID, Long.class));
    }

    @NotNull
    public Long geUserId(HttpServletRequest servletRequest) {
        String token = resolveToken(servletRequest)
                .orElseThrow(() -> new NotFoundException(TOKEN_NOT_OBTAINED_FROM_HTTP_SERVLET_REQUEST));
        return getUserId(token)
                .orElseThrow(() -> new NotFoundException(USER_ID_NOT_OBTAINED_FROM_TOKEN));
    }

    public boolean validateToken(@NonNull String token, @NonNull SecretKey secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            log.debug("-Token validation successful!");
            return true;
        } catch (MalformedJwtException ex) {
            log.error("-Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("-Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("-Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("-JWT claims string is empty");
        } catch (io.jsonwebtoken.security.SignatureException e) {
            log.error("-There is an error with the signature of you token ");
        }
        return false;
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessKey);
    }

    private Claims getClaims(@NonNull String token, @NonNull SecretKey secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
