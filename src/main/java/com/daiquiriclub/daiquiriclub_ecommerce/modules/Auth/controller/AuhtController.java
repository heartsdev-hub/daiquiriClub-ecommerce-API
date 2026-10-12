package com.daiquiriclub.daiquiriclub_ecommerce.modules.Auth.controller;

import com.daiquiriclub.daiquiriclub_ecommerce.common.response.ApiResult;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.Auth.dto.AuthRequest;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.Auth.dto.AuthResponse;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.Auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuhtController {
    private final AuthService authService;

    public AuhtController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResult<AuthResponse>> login(@RequestBody @Valid AuthRequest authRequest){
        return ResponseEntity.ok(authService.login(authRequest));
    }
}
