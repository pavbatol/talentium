package com.pavbatol.talentium.user.Verification.service;

import com.pavbatol.talentium.user.Verification.model.VerificationToken;
import com.pavbatol.talentium.user.model.User;

public interface VerificationTokenService {
    VerificationToken add(User user, String token);
}
