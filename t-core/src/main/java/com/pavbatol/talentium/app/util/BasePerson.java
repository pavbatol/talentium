package com.pavbatol.talentium.app.util;

public interface BasePerson<T> extends BasePersonDto {
    Long getId();

    Long getUserId();

    T setId(Long id);

    T setUserId(Long userId);

    T setEmail(String email);

    T setFirstName(String firstName);

    T setSecondName(String secondName);
}
