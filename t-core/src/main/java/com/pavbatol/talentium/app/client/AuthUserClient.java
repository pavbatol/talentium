package com.pavbatol.talentium.app.client;

import com.pavbatol.talentium.shared.auth.dto.UserDtoUpdateInsensitiveData;
import com.pavbatol.talentium.shared.auth.dto.UserDtoUpdateSensitiveData;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface AuthUserClient {
    Mono<ResponseEntity<String>> updateInsensitive(long userId, String token, UserDtoUpdateInsensitiveData dto);

    Mono<ResponseEntity<String>> updateRoles(long userId, String token, UserDtoUpdateSensitiveData dto);

    Mono<ResponseEntity<String>> updatePassword(long userId, String token, UserDtoUpdateSensitiveData dto);
}
