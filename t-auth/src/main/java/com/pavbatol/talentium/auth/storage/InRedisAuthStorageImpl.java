package com.pavbatol.talentium.auth.storage;

import com.pavbatol.talentium.app.util.ApplicationContextProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component("inRedisAuthStorageImpl")
@RequiredArgsConstructor
public class InRedisAuthStorageImpl implements AuthStorage {

    private static final String KEY_PREFIX = "REFRESH_TOKEN_";
    private final RedisTemplate<String, String> redisTemplate;
    private final ApplicationContextProvider applicationContextProvider;

    private final long jwtRefreshLife = ApplicationContextProvider.getContext().getEnvironment()
            .getProperty("app.jwt.refresh.life-seconds", Long.class, 600L);

    @Override
    public void save(String login, String refreshToken) {
        String key = KEY_PREFIX + login;
        redisTemplate.opsForValue().set(key, refreshToken);
        redisTemplate.expire(key, jwtRefreshLife, TimeUnit.SECONDS);
        log.debug("-Key for saving refresh token: {}", key);
        log.debug("-Storage expire: {} sec", jwtRefreshLife);
        log.debug("-{}: Saved refreshToken: {}", getClass().getSimpleName(), refreshToken);
    }

    @Override
    public Optional<String> find(String login) {
        String key = KEY_PREFIX + login;
        String refreshToken = redisTemplate.opsForValue().get(key);
        log.debug("-{}: Found refreshToken: {}", getClass().getSimpleName(), refreshToken);
        return Optional.ofNullable(refreshToken);
    }

    @Override
    public void remove(String login) {
        String key = KEY_PREFIX + login;
        redisTemplate.delete(key);
        log.debug("-{}: Deleted refreshToken by login {}", getClass().getSimpleName(), login);
    }

    @Override
    public void removeAllByLogin(String login, String keyEndSeparator) {
        String pattern = KEY_PREFIX + login + keyEndSeparator + "*";
        Set<String> keys = redisTemplate.keys(pattern);
        if (keys != null && !keys.isEmpty()) {
            log.debug("Found {} keys to delete by login: {}", keys.size(), login);
            redisTemplate.delete(keys);
            log.debug("-{}: Deleted all for login {}", getClass().getSimpleName(), login);
        } else {
            log.debug("-Not found keys to delete by login: {}", login);
        }
    }

    /**
     * In this application the number of users is limited, so I will allow myself this method
     * instead of redisTemplate.getConnectionFactory().getConnection().flushAll();
     */
    @Override
    public void clear() {
        Set<String> keys = redisTemplate.keys(KEY_PREFIX + "*");
        if (keys != null && !keys.isEmpty()) {
            log.debug("Found {} keys to delete", keys.size());
            redisTemplate.delete(keys);
            log.debug("-{}: Cleared the refresh-store", getClass().getSimpleName());
        } else {
            log.debug("-Not found keys to delete");
        }
    }
}
