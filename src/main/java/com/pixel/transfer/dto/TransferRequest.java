package com.pixel.transfer.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class TransferRequest {

    @NotNull
    private Long toUserId;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal value;
}
