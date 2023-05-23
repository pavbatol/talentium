package com.pavbatol.talentium.hh.dto;

import com.pavbatol.talentium.management.model.Management;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

import java.util.Set;

@Value
public class HhDtoRequest {

    @NotBlank
    @Email
    String email;

    @NotNull
    @Pattern(regexp = "^(?!\\s)(?!.*\\s{2,}.*)(?!.*\\s$).{2,}$",
            message = "The string must: " +
                    " - not start/end with spaces;" +
                    " - not be more than one space in a row;" +
                    " - at least 2 characters")
    String firstName;

    @NotNull
    @Pattern(regexp = "^(?!\\s)(?!.*\\s{2,}.*)(?!.*\\s$).{2,}$",
            message = "The string must: " +
                    " - not start/end with spaces;" +
                    " - not be more than one space in a row;" +
                    " - at least 2 characters")
    String secondName;

    @NotBlank
    String authority;

    @NotNull
    Set<Management> managements;

    @NotBlank
    String address;

    @NotBlank
    String contacts;
}
