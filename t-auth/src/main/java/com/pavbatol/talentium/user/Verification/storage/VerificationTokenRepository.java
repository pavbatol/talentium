package com.pavbatol.talentium.user.Verification.storage;

import com.pavbatol.talentium.user.Verification.model.VerificationToken;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

    List<VerificationToken> findAllByToken(String token);
}
