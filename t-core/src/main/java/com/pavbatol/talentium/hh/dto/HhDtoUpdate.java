package com.pavbatol.talentium.hh.dto;

import com.pavbatol.talentium.app.util.BasePersonDto;
import com.pavbatol.talentium.management.model.Management;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

import java.util.Set;

@Value
public class HhDtoUpdate implements BasePersonDto {

    @Email
    String email;

    @Pattern(regexp = "^(?!\\s)(?!.*\\s{2,}.*)(?!.*\\s$).{2,}$",
            message = "The string must: " +
                    " - not start/end with spaces;" +
                    " - not be more than one space in a row;" +
                    " - at least 2 characters")
    String firstName;

    @Pattern(regexp = "^(?!\\s)(?!.*\\s{2,}.*)(?!.*\\s$).{2,}$",
            message = "The string must: " +
                    " - not start/end with spaces;" +
                    " - not be more than one space in a row;" +
                    " - at least 2 characters")
    String secondName;

    String authority;

    Set<Management> managements;

    String address;

    String contacts;
}
