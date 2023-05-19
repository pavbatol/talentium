package com.pavbatol.talentium.auth.service;

import com.pavbatol.talentium.app.exception.AuthorizationFailedException;
import com.pavbatol.talentium.app.exception.NotFoundException;
import com.pavbatol.talentium.app.util.Checker;
import com.pavbatol.talentium.auth.dto.AuthDtoRequest;
import com.pavbatol.talentium.auth.dto.AuthDtoResponse;
import com.pavbatol.talentium.auth.storage.AuthStorage;
import com.pavbatol.talentium.jwt.JwtAuthentication;
import com.pavbatol.talentium.jwt.JwtProvider;
import com.pavbatol.talentium.user.model.User;
import com.pavbatol.talentium.user.storage.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.pavbatol.talentium.jwt.JwtUtils.toRoleNames;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    public static final String KEY_END_SEPARATOR = "_";
    public static final String USER_AGENT = "User-Agent";
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Qualifier("inRedisAuthStorageImpl")
    private final AuthStorage authStorage;

    public AuthDtoResponse login(@NonNull HttpServletRequest request, @NonNull AuthDtoRequest dtoAuthRequest) {
        final String errMessage = String.format("%s with username: '%s' not authorized",
                User.class.getSimpleName(), dtoAuthRequest.getUsername());
        final User user = userRepository.findByUsername(dtoAuthRequest.getUsername())
                .orElseThrow(() -> {
                    log.debug("-{} with username {} not found", User.class.getSimpleName(), dtoAuthRequest.getUsername());
                    return new AuthorizationFailedException(errMessage);
                });
        if (!checkAuthConditions(dtoAuthRequest.getPassword(), user)) {
            throw new AuthorizationFailedException(errMessage);
        }
        final JwtAuthentication authentication = new JwtAuthentication(
                true,
                user.getUsername(),
                user.getFirstName(),
                toRoleNames(user.getRoles()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String jwtAccess = jwtProvider.createAccessToken(user);
        final String jwtRefresh = jwtProvider.createRefreshToken(user);
        saveRefreshToken(request, dtoAuthRequest.getUsername(), jwtRefresh);
        log.debug("-new JwtAuthentication: {}", authentication);
        log.debug("-Access token created: {}", jwtAccess);
        log.debug("-Refresh token created: {}", jwtRefresh);
        return new AuthDtoResponse(jwtAccess, jwtRefresh);
    }

    public void logout(@NonNull HttpServletRequest request) {
        if (SecurityContextHolder.getContext() != null
                && SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails
        ) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String composedKey = composeKey(request, userDetails.getUsername());
            authStorage.remove(composedKey);
            log.debug("-De-login for {}, refresh token removed", userDetails.getUsername());
        } else {
            log.debug("-The de-login was not made because the user is anonymous");
        }
        SecurityContextHolder.clearContext();
        log.debug("-Context cleared in SecurityContextHolder");
    }

    public AuthDtoResponse getNewAccessToken(@NonNull HttpServletRequest request, @NonNull String refreshToken) {
        return verifyRefreshTokenAndGetOwner(request, refreshToken)
                .map(user -> {
                    String accessToken = jwtProvider.createAccessToken(user);
                    return new AuthDtoResponse(accessToken, null);
                })
                .orElseGet(() -> new AuthDtoResponse(null, null));
    }

    public AuthDtoResponse getNewRefreshToken(@NonNull HttpServletRequest request, @NonNull String refreshToken) {
        return verifyRefreshTokenAndGetOwner(request, refreshToken)
                .map(user -> {
                    String newRefreshToken = jwtProvider.createRefreshToken(user);
                    saveRefreshToken(request, user.getEmail(), newRefreshToken);
                    return new AuthDtoResponse(null, newRefreshToken);
                })
                .orElseGet(() -> new AuthDtoResponse(null, null));
    }

    @Override
    public void removeRefreshTokensByUserId(Long userId) {
        User user = Checker.getNonNullObject(userRepository, userId);
        String username = user.getUsername();
        authStorage.removeAllByLogin(username, KEY_END_SEPARATOR);
        log.debug("-All refresh tokens removed for user with id: {}", userId);
    }

    @Override
    public void removeAllRefreshTokens() {
        authStorage.clear();
        log.debug("-Storage was cleared");
    }

    private boolean checkAuthConditions(String rawPassword, User origUser) {
        if (!passwordEncoder.matches(rawPassword, origUser.getPassword())) {
            log.debug("-{} with id: #{} not passed password", User.class.getSimpleName(), origUser.getId());
            return false;
        }
        if (!origUser.getEnabled()) {
            log.debug("-{} with id: #{} not enabled", User.class.getSimpleName(), origUser.getId());
            return false;
        }
        return true;
    }

    private Optional<User> verifyRefreshTokenAndGetOwner(@NonNull HttpServletRequest request, @NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final String username = jwtProvider.getRefreshTokenUsername(refreshToken);
            final String composedKey = composeKey(request, username);
            return authStorage.find(composedKey)
                    .filter(savedToken -> passwordEncoder.matches(refreshToken, savedToken))
                    .map(savedToken -> refreshToken)
                    .map(token -> getUser(username));
        }
        log.debug("-Not verified refresh token: {}", refreshToken);
        return Optional.empty();
    }

    @NonNull
    private User getUser(String username) {
        return userRepository.findByEmail(username).orElseThrow(() ->
                new NotFoundException(String.format("%s not found by %s ", User.class.getSimpleName(), username)));
    }

    private void saveRefreshToken(@NonNull HttpServletRequest request,
                                  @NonNull String userName,
                                  @NonNull String refreshToken) {
        String composedKey = composeKey(request, userName);
        authStorage.save(composedKey, passwordEncoder.encode(refreshToken));
    }

    private static String composeKey(@NonNull HttpServletRequest request, String baseStr) {
        return String.format("%s%s%s", baseStr, KEY_END_SEPARATOR, request.getHeader(USER_AGENT));
    }
}
