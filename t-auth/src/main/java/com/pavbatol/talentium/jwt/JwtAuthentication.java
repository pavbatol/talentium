package com.pavbatol.talentium.jwt;

import com.pavbatol.talentium.shared.auth.model.RoleName;
import io.jsonwebtoken.Claims;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JwtAuthentication implements Authentication {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Boolean authenticated;
    private String username;
    private String firstName;
    private Set<RoleName> roleNames;

    public static JwtAuthentication of(Claims claims) {
        return of(claims, false);
    }

    public static JwtAuthentication of(Claims claims, boolean authenticated) {
        JwtAuthentication jwtAuthentication = new JwtAuthentication();
        jwtAuthentication.setAuthenticated(authenticated);
        jwtAuthentication.setUsername(claims.getSubject());
        jwtAuthentication.setFirstName(claims.get(JwtProvider.FIRST_NAME, String.class));
        jwtAuthentication.setRoleNames(JwtUtils.getRoleNames(claims));
        return jwtAuthentication;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleNames;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        this.authenticated = authenticated;
    }

    @Override
    public String getName() {
        return firstName;
    }
}
