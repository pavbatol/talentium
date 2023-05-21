package com.pavbatol.talentium.jwt;

import com.pavbatol.talentium.app.exception.NotFoundException;
import com.pavbatol.talentium.role.model.Role;
import com.pavbatol.talentium.role.model.RoleName;
import com.pavbatol.talentium.user.model.User;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {
    public static final String TYP = "typ";
    public static final String JWT = "JWT";
    public static final String ROLES = "roles";
    public static final String FIRST_NAME = "firstName";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER_ = "Bearer ";
    public static final String APP_JWT_ACCESS_KEY = "APP_JWT_ACCESS_KEY";
    public static final String USER_ID = "userId";
    private final SecretKey jwtAccessKey;
    private final SecretKey jwtRefreshKey;
    private final long jwtAccessLife;
    private final long jwtRefreshLife;

    @Autowired
    public JwtProvider(@Value("${app.jwt.access.key:}") String accessSecretStr,
                       @Value("${app.jwt.refresh.key:}") String refreshSecretStr,
                       @Value("${app.jwt.access.life-seconds:60}") long jwtAccessLife,
                       @Value("${app.jwt.refresh.life-seconds:600}") long jwtRefreshLife,
                       Environment env) {
        this.jwtAccessKey = accessSecretStr != null && !"".equals(accessSecretStr)
                ? JwtUtils.toSecretKey(accessSecretStr)
                : JwtUtils.generateSecretKey();
        this.jwtRefreshKey = refreshSecretStr != null && !"".equals(refreshSecretStr)
                ? JwtUtils.toSecretKey(refreshSecretStr)
                : JwtUtils.generateSecretKey();
        this.jwtAccessLife = jwtAccessLife;
        this.jwtRefreshLife = jwtRefreshLife;

        System.setProperty(APP_JWT_ACCESS_KEY, JwtUtils.toSecretStr(this.jwtAccessKey));

        log.debug("-Constructor: source for jwtAccessKey: " + accessSecretStr);
        log.debug("-Constructor: source for jwtRefreshKey: " + refreshSecretStr);
        log.debug("-Constructor: jwtAccessLife: " + this.jwtAccessLife);
        log.debug("-Constructor: jwtRefreshLife: " + this.jwtRefreshLife);

        log.debug("-! CHECK Local_: app.jwt.access.key = " + accessSecretStr);
        log.debug("-! CHECK System: APP_JWT_ACCESS_KEY = " + env.getProperty(APP_JWT_ACCESS_KEY));
    }

    public String createAccessToken(User user) {
        final Set<String> roles = user.getRoles().stream()
                .map(Role::getRoleName)
                .map(RoleName::name)
                .collect(Collectors.toSet());
        final Date now = new Date();
        final Date expiryDate = new Date(now.getTime() + jwtAccessLife * 1000L);
        return Jwts.builder()
                .setHeaderParam(TYP, JWT)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .claim(ROLES, roles)
                .claim(USER_ID, user.getId())
                .claim(FIRST_NAME, user.getFirstName())
                .signWith(jwtAccessKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createRefreshToken(User user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusSeconds(jwtRefreshLife).atZone(ZoneId.systemDefault()).toInstant();
        final Date expiryDate = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(expiryDate)
                .signWith(jwtRefreshKey)
                .compact();
    }

    public Optional<String> resolveToken(@NonNull HttpServletRequest request) {
        final String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_)) {
            return Optional.of(bearerToken.substring(7));
        }
        return Optional.empty();
    }

    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, jwtAccessKey);
    }

    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, jwtRefreshKey);
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

    public String getRefreshTokenUsername(String token) {
        log.debug("-Getting refresh token username");
        return getRefreshClaims(token).getSubject();
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessKey);
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, jwtRefreshKey);
    }

    public Optional<Long> getUserId(@NonNull String accessToken) {
        Claims claims = getClaims(accessToken, jwtAccessKey);
        return Optional.ofNullable(claims.get(USER_ID, Long.class));
    }

    public Long geUserId(HttpServletRequest servletRequest) {
        String token = resolveToken(servletRequest)
                .orElseThrow(() -> new NotFoundException("Token not obtained from HttpServletRequest"));
        return  getUserId(token)
                .orElseThrow(() -> new NotFoundException("user ID not obtained from token"));
    }

    private Claims getClaims(@NonNull String token, @NonNull SecretKey secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
