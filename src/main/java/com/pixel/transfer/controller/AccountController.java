package com.pixel.transfer.controller;

import com.pixel.transfer.dto.TransferRequest;
import com.pixel.transfer.dto.UserDto;
import com.pixel.transfer.exception.advice.ExceptionResponse;
import com.pixel.transfer.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "Transfer money from one person to another")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transferred money successfully")
    })
    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@AuthenticationPrincipal Long userId,
                                         @Valid @RequestBody TransferRequest request) {
        accountService.transferMoney(userId, request.getToUserId(), request.getValue());
        return ResponseEntity.ok().build();
    }
}
