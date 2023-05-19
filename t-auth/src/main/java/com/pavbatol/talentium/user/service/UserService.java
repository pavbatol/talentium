package com.pavbatol.talentium.user.service;

import com.pavbatol.talentium.user.dto.UserDtoRegistry;
import com.pavbatol.talentium.user.dto.UserDtoRequest;
import com.pavbatol.talentium.user.dto.UserDtoResponse;
import com.pavbatol.talentium.user.dto.UserDtoUpdate;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserService {

    UserDtoResponse add(UserDtoRequest dto);

    UserDtoResponse update(Long userId, UserDtoUpdate dto);

    void remove(Long userId);

    UserDtoResponse findById(Long userId);

    List<UserDtoResponse> findAll(Integer from, Integer size);

    void register(HttpServletRequest servletRequest, UserDtoRegistry dtoRegister);

    void confirmRegistration(String code);
}
