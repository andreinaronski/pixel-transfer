package com.pixel.transfer.exception.advice;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ExceptionResponse {
    private String message;
    private HttpStatus status;
}
