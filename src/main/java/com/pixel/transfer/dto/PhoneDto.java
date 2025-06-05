package com.pixel.transfer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class PhoneDto implements Serializable {

    private Long id;

    @NotBlank
    @Pattern(regexp = "^375\\d{9}$", message = "Phone must be in format 375XXXXXXXXX")
    private String phone;
}
