package com.pixel.transfer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
public class UserDto implements Serializable {

    private Long id;

    private String name;

    private LocalDate dateOfBirth;

    private Set<EmailDto> emails;

    private Set<PhoneDto> phones;

    private BigDecimal balance;
}
