package com.pavbatol.talentium.auth.storage;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Value
@Component("inMemoryAuthStorageImpl")
public class InMemoryAuthStorageImpl implements AuthStorage {

    @Getter(AccessLevel.NONE)
    Map<String, String> refreshes = new HashMap<>();

    @Override
    public void save(String login, String refreshToken) {
        refreshes.put(login, refreshToken);
        log.debug("-{}: Saved refreshToken: {}", getClass().getSimpleName(), refreshToken);
    }

    @Override
    public Optional<String> find(String login) {
        String refreshToken = refreshes.get(login);
        log.debug("-{}: Found refreshToken: {}", getClass().getSimpleName(), refreshToken);
        return Optional.ofNullable(refreshToken);
    }

    @Override
    public void remove(String login) {
        refreshes.remove(login);
        log.debug("-{}: Deleted refreshToken by login {}", getClass().getSimpleName(), login);
    }

    @Override
    public void removeAllByLogin(String login, String keyEndSeparator) {
        String pattern = login + keyEndSeparator;
        refreshes.keySet().stream()
                .filter(keyStr -> keyStr.startsWith(pattern))
                .collect(Collectors.toList())
                .forEach(refreshes::remove);
        log.debug("-{}: Deleted all for login {}", getClass().getSimpleName(), login);
    }

    @Override
    public void clear() {
        refreshes.clear();
        log.debug("-{}: Cleared the refresh-store", getClass().getSimpleName());
    }
}
