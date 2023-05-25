package com.pavbatol.talentium.app.util;

import com.pavbatol.talentium.app.exception.NotEnoughRightsException;
import com.pavbatol.talentium.auth.jwt.JwtProvider;
import com.pavbatol.talentium.auth.jwt.JwtUtils;
import com.pavbatol.talentium.auth.role.model.RoleName;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ServiceUtils {

    public static Long getUserId(HttpServletRequest servletRequest, JwtProvider jwtProvider) {
        return jwtProvider.geUserId(servletRequest);
    }

    public static void checkIdsEqualOrAdminRole(HttpServletRequest servletRequest,
                                                Long userId, Long entityUserId,
                                                JwtProvider jwtProvider) {
        if (!Objects.equals(entityUserId, userId) && !hasRole(RoleName.ADMIN, servletRequest, jwtProvider)) {
            throw new NotEnoughRightsException("The IDs don't match and there are also no Administrator rights ");
        }
    }

    public static boolean hasRole(RoleName roleName, HttpServletRequest servletRequest, JwtProvider jwtProvider) {
        return JwtUtils.hasRole(servletRequest, roleName.name(), jwtProvider);
    }
}
