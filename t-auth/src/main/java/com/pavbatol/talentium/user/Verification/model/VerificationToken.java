package com.pavbatol.talentium.user.Verification.model;

import com.pavbatol.talentium.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "verify_tokens")
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id", nullable = false)
    Long id;

    @Column(name = "token", nullable = false)
    String token;

    @Column(name = "expired_on", nullable = false)
    LocalDateTime expiredOn = LocalDateTime.now().plusMinutes(EXPIRATION);

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    User user;
}
