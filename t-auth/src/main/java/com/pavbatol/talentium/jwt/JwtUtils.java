package com.pavbatol.talentium.jwt;

import com.pavbatol.talentium.role.model.Role;
import com.pavbatol.talentium.role.model.RoleName;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {

    public static Set<RoleName> toRoleNames(Collection<Role> roles) {
        return roles.stream()
                .map(Role::getRoleName)
                .collect(Collectors.toSet());
    }

    public static Set<RoleName> getRoleNames(Claims claims) {
        final List<String> roles = claims.get(JwtProvider.ROLES, List.class);
        return roles.stream()
                .map(RoleName::of)
                .collect(Collectors.toSet());
    }

    public static String generateSecretStr() {
        return toSecretStr(generateSecretKey());
    }

    public static SecretKey generateSecretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    public static String toSecretStr(SecretKey secretKey) {
        return Encoders.BASE64.encode(secretKey.getEncoded());
    }

    public static SecretKey toSecretKey(String secretStr) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretStr));
    }
}
