package com.pavbatol.talentium.user.Verification.service;

import com.pavbatol.talentium.user.Verification.model.VerificationToken;
import com.pavbatol.talentium.user.Verification.storage.VerificationTokenRepository;
import com.pavbatol.talentium.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private final VerificationTokenRepository tokenRepository;
    private static final String ENTITY_SIMPLE_NAME = VerificationToken.class.getSimpleName();

    @Override
    public VerificationToken add(User user, String token) {
        VerificationToken verificationToken = new VerificationToken().setUser(user).setToken(token);
        VerificationToken saved = tokenRepository.save(verificationToken);
        log.debug("New {} saved: {}", ENTITY_SIMPLE_NAME, saved);
        return saved;
    }
}
