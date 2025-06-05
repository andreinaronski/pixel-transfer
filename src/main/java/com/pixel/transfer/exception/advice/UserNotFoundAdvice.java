package com.pixel.transfer.exception.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pixel.transfer.exception.UserNotFoundException;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class UserNotFoundAdvice {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> userNotFoundHandler(UserNotFoundException ex) {
        HttpHeaders headers = new HttpHeaders();
        String value = ex.getMessage();
        try {
            headers.setContentType(MediaType.APPLICATION_JSON);
            value = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(ExceptionResponse.builder()
                    .message(ex.getMessage())
                    .status(HttpStatus.NOT_FOUND)
                    .build());
        } catch (JsonProcessingException e) {
            log.error("Error during parsing ExceptionResponse");
        }
        return new ResponseEntity<>(value, headers, HttpStatus.NOT_FOUND);
    }
}
