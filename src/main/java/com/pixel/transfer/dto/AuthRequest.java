package com.pixel.transfer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthRequest {

    @NotBlank
    private String login;

    @NotBlank
    private String password;
}
