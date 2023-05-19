package com.pavbatol.talentium.user.service;

import com.pavbatol.talentium.jwt.JwtUtils;
import com.pavbatol.talentium.user.model.User;
import com.pavbatol.talentium.user.storage.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private static final String ENTITY_SIMPLE_NAME = User.class.getSimpleName();
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("%s not found by %s ", ENTITY_SIMPLE_NAME, username)));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                JwtUtils.toRoleNames(user.getRoles()));
    }
}
