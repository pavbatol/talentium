package com.pavbatol.talentium.curator.dto;

import com.pavbatol.talentium.hh.model.Hh;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

@Value
public class CuratorDtoRequest {

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

    @NotNull
    Hh owner;
}
