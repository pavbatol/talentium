package com.pavbatol.talentium.user.storage;

import com.pavbatol.talentium.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "delete from users u " +
            "using verify_tokens vt " +
            "where " +
            "   u.user_id = vt.user_id " +
            "   and vt.expired_on < ?1 " +
            "   and u.registered_on is null" +
            "   and u.enabled is false; "
            , nativeQuery = true)
    void deleteByConfirmationExpired(LocalDateTime limit);
}
