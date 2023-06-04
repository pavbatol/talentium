package com.pavbatol.talentium.user.service;

import com.pavbatol.talentium.app.exception.BadRequestException;
import com.pavbatol.talentium.app.exception.NotFoundException;
import com.pavbatol.talentium.app.util.Checker;
import com.pavbatol.talentium.email.service.EmailService;
import com.pavbatol.talentium.jwt.JwtProvider;
import com.pavbatol.talentium.role.model.Role;
import com.pavbatol.talentium.role.storage.RoleRepository;
import com.pavbatol.talentium.shared.auth.dto.RoleDto;
import com.pavbatol.talentium.shared.auth.dto.UserDtoUpdateInsensitiveData;
import com.pavbatol.talentium.shared.auth.dto.UserDtoUpdateSensitiveData;
import com.pavbatol.talentium.shared.auth.model.RoleName;
import com.pavbatol.talentium.user.Verification.model.VerificationToken;
import com.pavbatol.talentium.user.Verification.service.VerificationTokenService;
import com.pavbatol.talentium.user.Verification.storage.VerificationTokenRepository;
import com.pavbatol.talentium.user.controller.PublicUserController;
import com.pavbatol.talentium.user.dto.*;
import com.pavbatol.talentium.user.mapper.UserMapper;
import com.pavbatol.talentium.user.model.User;
import com.pavbatol.talentium.user.storage.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.pavbatol.talentium.app.util.Checker.getNonNullObject;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableScheduling
public class UserServiceImpl implements UserService {
    private static final String ENTITY_SIMPLE_NAME = User.class.getSimpleName();
    private static final String ID = "id";
    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final VerificationTokenRepository tokenRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final VerificationTokenService tokenService;
    private final JwtProvider jwtProvider;

    public UserDtoResponse add(UserDtoRequest dto) {
        User user = userMapper.toEntity(dto);
        prepareUserForCreat(user);
        log.debug("-Add: New {}'s entity: {} ", ENTITY_SIMPLE_NAME, user);
        User saved = userRepository.save(user);
        if (haseAnyRoleWithoutRoleName(saved)) {
            setRolesByIds(saved,
                    saved.getRoles().stream().map(Role::getId).collect(Collectors.toList()));
        }
        log.debug("-Add: New {} saved: {}", ENTITY_SIMPLE_NAME, saved);
        return userMapper.toResponseDto(saved);
    }

    @Override
    public UserDtoResponse update(Long userId, UserDtoUpdate dto) {
        User user = getNonNullObject(userRepository, userId);
        User updated = userMapper.updateEntity(dto, user);
        updated = userRepository.save(updated);
        log.debug("Updated {}: {}", ENTITY_SIMPLE_NAME, updated);
        return userMapper.toResponseDto(updated);
    }

    @Override
    public UserDtoResponse updateRoles(HttpServletRequest servletRequest, Long userId, UserDtoUpdateSensitiveData dto) {
        User origUser = getNonNullObject(userRepository, userId);
        Long requesterId = jwtProvider.geUserId(servletRequest);
        User requester = getNonNullObject(userRepository, requesterId);

        if (Objects.isNull(dto.getRoles())) {
            throw new BadRequestException("Provided roles is null");
        }

        boolean origHasOnlyCandidate = origUser.getRoles().stream()
                .map(Role::getRoleName).noneMatch(roleName -> roleName != com.pavbatol.talentium.shared.auth.model.RoleName.CANDIDATE);
        boolean dtoHasOnlyIntern = dto.getRoles().stream()
                .map(RoleDto::getName).allMatch(s -> com.pavbatol.talentium.shared.auth.model.RoleName.INTERN.name().equals(s));

        Set<com.pavbatol.talentium.shared.auth.model.RoleName> requesterRoleNames = requester.getRoles().stream()
                .map(Role::getRoleName).collect(Collectors.toSet());

        if (requesterRoleNames.contains(com.pavbatol.talentium.shared.auth.model.RoleName.ADMIN)
                || (requesterRoleNames.contains(com.pavbatol.talentium.shared.auth.model.RoleName.MENTOR) && (origHasOnlyCandidate && dtoHasOnlyIntern))
        ) {
            User updated = userMapper.updateEntity(dto, origUser);
            updated = userRepository.save(updated);
            log.debug("Updated {}: {}", ENTITY_SIMPLE_NAME, updated);
            return userMapper.toResponseDto(updated);
        } else {
            throw new BadRequestException("No condition to allow updating " + ENTITY_SIMPLE_NAME);
        }
    }

    @Override
    public UserDtoResponse updateInsensitive(HttpServletRequest servletRequest, Long userId, UserDtoUpdateInsensitiveData dto) {
        User origUser = getNonNullObject(userRepository, userId);
        Long requesterId = jwtProvider.geUserId(servletRequest);
        if (!Objects.equals(requesterId, origUser.getId())) {
            throw new BadRequestException("You can only edit your own data");
        }
        User updated = userMapper.updateEntity(dto, origUser);
        updated = userRepository.save(updated);
        log.debug("Updated {}: {}", ENTITY_SIMPLE_NAME, updated);
        return userMapper.toResponseDto(updated);
    }

    @Override
    public void remove(Long userId) {
        userRepository.deleteById(userId);
        log.debug("Removed {} by id #{}", ENTITY_SIMPLE_NAME, userId);
    }

    @Override
    public UserDtoResponse findById(Long userId) {
        User found = getNonNullObject(userRepository, userId);
        log.debug("Found {}: {}", ENTITY_SIMPLE_NAME, found);
        return userMapper.toResponseDto(found);
    }

    @Override
    public List<UserDtoResponse> findAll(Integer from, Integer size) {
        Sort sort = Sort.by(ID);
        PageRequest pageRequest = PageRequest.of(from, size, sort);
        Page<User> page = userRepository.findAll(pageRequest);
        log.debug("Found number of {}'s: {} , total pages: {}, from: {}, size: {}, sort: {}", ENTITY_SIMPLE_NAME,
                page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize(), page.getSort());
        return userMapper.toResponseDtos(page.getContent());
    }

    @Transactional
    @Override
    public void register(HttpServletRequest servletRequest, UserDtoRegistry dtoRegister) {
        User user = userMapper.toEntity(
                dtoRegister,
                passwordEncoder.encode(dtoRegister.getPassword()),
                Checker.getNonNullObject(roleRepository, dtoRegister.getRole().getId()));
        log.debug("-Registry: new {}'s entity: {} ", ENTITY_SIMPLE_NAME, user);
        User savedUser = userRepository.save(user);
        log.debug("-Registry: new {} saved: {} ", ENTITY_SIMPLE_NAME, savedUser);

        String code = CodeGenerator.generateCodeWithoutSpecial();
        String encodedCode = CodeGenerator.encode(code);
        VerificationToken savedToken = tokenService.add(savedUser, code);

        String confirmationLink = UriComponentsBuilder.newInstance()
                .scheme(servletRequest.getScheme())
                .host(servletRequest.getServerName())
                .port(servletRequest.getServerPort())
                .path(servletRequest.getServletPath().replace(PublicUserController.REGISTRY, PublicUserController.CONFIRMATION))
                .queryParam(PublicUserController.CODE, encodedCode)
                .build().encode(CHARSET).toUriString();
        log.debug("-code_____________: {}", code);
        log.debug("-encodedCode______: {}", encodedCode);
        log.debug("-Confirmation Link: {}", confirmationLink);

        String text = String.format("You have received this email because your email-address was specified during " +
                        "registration on '%s'\nTo confirm the registration, follow the link:\n%s",
                servletRequest.getServerName(), confirmationLink);

        // TODO: 20.05.2023 Commented out for a hackathon presentation

//        emailService.sendSimpleMessage(
//                user.getEmail(),
//                "Confirm your email address",
//                text);
        /*
         * The following code is intended for direct saving of the user, without confirmation by mail.
         * This is for testing the application if you don't specify an email for sending.
         */
        user.setEnabled(true)
                .setRegisteredOn(LocalDateTime.now());
        tokenRepository.deleteById(savedToken.getId());
    }

    @Transactional
    @Override
    public void confirmRegistration(String encodedCode) {
        String uriDecoded = URLDecoder.decode(encodedCode, CHARSET);
        String code = CodeGenerator.decode(uriDecoded);

        List<VerificationToken> tokens = tokenRepository.findAllByToken(code);
        if (tokens.size() == 1 && tokens.get(0) != null) {
            VerificationToken token = tokens.get(0);
            User user = token.getUser()
                    .setEnabled(true)
                    .setRegisteredOn(LocalDateTime.now());
            userRepository.save(user);
            tokenRepository.deleteById(token.getId());
            log.debug("-{} with id: #{} confirmed with code: {}", ENTITY_SIMPLE_NAME, user.getId(), code);
        } else {
            log.debug("-Income decoded code = " + code);
            throw new NotFoundException("Invalid confirmation code");
        }
    }

    @Scheduled(cron = "0 0 * * * ?")
    protected void deleteUnconfirmedUsers() {
        log.debug("-Deleting unconfirmed users on schedules");
        userRepository.deleteByConfirmationExpired(LocalDateTime.now());
    }

    private void prepareUserForCreat(@NonNull User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegisteredOn(LocalDateTime.now());
        if (Objects.isNull(user.getEnabled())) {
            user.setEnabled(true);
        }
        if (CollectionUtils.isEmpty(user.getRoles())) {
            Role role = getRoleByName(RoleName.USER);
            log.debug("-Obtained {}.{}: {}", RoleName.class.getSimpleName(), RoleName.USER, role);
            user.setRoles(Set.of(role));
        }
    }

    private void setRolesByIds(User user, Collection<Long> roleIds) {
        log.debug("-Finding {}'s with id's: {}", Role.class.getSimpleName(), roleIds);

        Collection<Role> roles = roleRepository.findAllByIdIn(roleIds);
        log.debug("-Number of found {}'s : {}; collection: {}", Role.class.getSimpleName(), roles.size(), roles);

        Set<Role> roleSet = new HashSet<>(Math.max((int) (roles.size() / .75F) + 1, 16));
        roleSet.addAll(roles);
        user.setRoles(roleSet);
        log.debug("-Set {}'s: {} for {} with id: #{}",
                Role.class.getSimpleName(), roleSet, User.class.getSimpleName(), user.getId());
    }

    private boolean haseAnyRoleWithoutRoleName(User user) {
        return user.getRoles().stream()
                .map(Role::getRoleName)
                .anyMatch(Objects::isNull);
    }

    private Role getRoleByName(RoleName roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> {
                    String errMessage = String.format("%s not found by %s ", Role.class.getSimpleName(), roleName);
                    log.error(errMessage);
                    return new NotFoundException(errMessage);
                });
    }
}
