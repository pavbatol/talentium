package com.pavbatol.talentium.auth.storage;

import java.util.Optional;

public interface AuthStorage {

    void save(String login, String refreshToken);

    Optional<String> find(String login);

    void remove(String login);

    void removeAllByLogin(String login, String keyEndSeparator);

    void clear();
}
