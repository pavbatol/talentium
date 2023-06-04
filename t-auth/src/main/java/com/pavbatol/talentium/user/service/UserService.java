package com.pavbatol.talentium.user.service;

import com.pavbatol.talentium.shared.auth.dto.UserDtoUpdateInsensitiveData;
import com.pavbatol.talentium.shared.auth.dto.UserDtoUpdateSensitiveData;
import com.pavbatol.talentium.user.dto.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserService {

    UserDtoResponse add(UserDtoRequest dto);

    UserDtoResponse update(Long userId, UserDtoUpdate dto);

    UserDtoResponse updateRoles(HttpServletRequest servletRequest, Long userId, UserDtoUpdateSensitiveData dto);

    UserDtoResponse updateInsensitive(HttpServletRequest servletRequest, Long userId, UserDtoUpdateInsensitiveData dto);

    void remove(Long userId);

    UserDtoResponse findById(Long userId);

    List<UserDtoResponse> findAll(Integer from, Integer size);

    void register(HttpServletRequest servletRequest, UserDtoRegistry dtoRegister);

    void confirmRegistration(String code);
}
