package com.pixel.transfer.controller;

import com.pixel.transfer.dto.PhoneDto;
import com.pixel.transfer.service.PhoneService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/phones")
@RequiredArgsConstructor
@Validated
public class PhoneController {

    private final PhoneService phoneService;

    @Operation(summary = "Add phone to authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Phone added successfully")
    })
    @PostMapping()
    public ResponseEntity<Void> addPhone(@AuthenticationPrincipal Long userId,
                                         @Valid @RequestBody PhoneDto dto) {
        phoneService.addPhone(userId, dto.getPhone());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete phone by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Phone deleted successfully")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removePhone(@AuthenticationPrincipal Long userId,
                                            @PathVariable Long id) {
        phoneService.removePhone(userId, id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update phone by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Phone updated successfully")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePhone(@AuthenticationPrincipal Long userId,
                                            @PathVariable Long id,
                                            @RequestBody PhoneDto newPhone) {
        phoneService.updatePhone(userId, id, newPhone);
        return ResponseEntity.ok().build();
    }
}
