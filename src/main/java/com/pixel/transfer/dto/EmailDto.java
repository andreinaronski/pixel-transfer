package com.pixel.transfer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class EmailDto implements Serializable {

    private Long id;

    @NotBlank
    @Email
    private String email;
}
