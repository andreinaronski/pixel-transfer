package com.pixel.transfer.controller;

import com.pixel.transfer.dto.EmailDto;
import com.pixel.transfer.dto.UserDto;
import com.pixel.transfer.exception.advice.ExceptionResponse;
import com.pixel.transfer.service.EmailService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/emails")
@RequiredArgsConstructor
@Validated
public class EmailController {

    private final EmailService emailService;

    @Operation(summary = "Add email to authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email added successfully")
    })
    @PostMapping
    public ResponseEntity<Void> addEmail(@AuthenticationPrincipal Long userId,
                                         @Valid @RequestBody EmailDto emailDto) {
        emailService.addEmail(userId, emailDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete email by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email deleted successfully")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeEmail(@AuthenticationPrincipal Long userId,
                                            @PathVariable Long id) {
        emailService.removeEmail(userId, id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update email by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email update successfully")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateEmail(@AuthenticationPrincipal Long userId,
                                            @PathVariable Long id,
                                            @RequestBody EmailDto emailDto) {
        emailService.updateEmail(userId, id, emailDto);
        return ResponseEntity.ok().build();
    }
}
