package com.pavbatol.talentium.app.util;

import com.pavbatol.talentium.app.client.AuthUserClient;
import com.pavbatol.talentium.app.exception.NotEnoughRightsException;
import com.pavbatol.talentium.app.exception.NotFoundException;
import com.pavbatol.talentium.auth.jwt.JwtProvider;
import com.pavbatol.talentium.auth.jwt.JwtUtils;
import com.pavbatol.talentium.shared.auth.dto.UserDtoUpdateInsensitiveData;
import com.pavbatol.talentium.shared.auth.model.RoleName;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ServiceUtils {

    public static Long getUserId(HttpServletRequest servletRequest, JwtProvider jwtProvider) {
        return jwtProvider.geUserId(servletRequest);
    }

    public static void checkIdsEqualOrAdminRole(Long verifyingUserId,
                                                Long entityUserId,
                                                HttpServletRequest servletRequest,
                                                JwtProvider jwtProvider) throws NotEnoughRightsException {
        if (!Objects.equals(entityUserId, verifyingUserId) && !hasRole(RoleName.ADMIN, servletRequest, jwtProvider)) {
            throw new NotEnoughRightsException("The IDs don't match and there are also no Administrator rights ");
        }
    }

    public static boolean hasRole(RoleName roleName, HttpServletRequest servletRequest, JwtProvider jwtProvider) {
        return JwtUtils.hasRole(servletRequest, roleName.name(), jwtProvider);
    }

    public static void updateUserInsensitiveInAuthService(BasePersonDto newData,
                                                          BasePerson<?> origEntity,
                                                          HttpServletRequest servletRequest,
                                                          JwtProvider jwtProvider,
                                                          AuthUserClient authUserClient) throws RuntimeException {
        boolean emailChanged = Objects.nonNull(newData.getEmail())
                && !origEntity.getEmail().equals(newData.getEmail());
        boolean firstNameChanged = Objects.nonNull(newData.getFirstName())
                && !origEntity.getFirstName().equals(newData.getFirstName());
        boolean secondNameChanged = Objects.nonNull(newData.getSecondName())
                && !origEntity.getSecondName().equals(newData.getSecondName());
        String token = jwtProvider.resolveToken(servletRequest).orElseThrow(() -> new NotFoundException("Token not found"));
        if (emailChanged || firstNameChanged || secondNameChanged) {
            ResponseEntity<String> responseEntity = authUserClient.updateInsensitive(origEntity.getUserId(), token,
                    new UserDtoUpdateInsensitiveData(newData.getEmail(), newData.getFirstName(),
                            newData.getSecondName())).block();
            if (Objects.isNull(responseEntity) || !responseEntity.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to update User in Auth service");
            }
        }
    }
}
