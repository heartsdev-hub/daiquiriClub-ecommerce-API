package com.daiquiriclub.daiquiriclub_ecommerce.modules.Auth.service;

import com.daiquiriclub.daiquiriclub_ecommerce.common.exception.UnauthorizedException;
import com.daiquiriclub.daiquiriclub_ecommerce.common.response.ApiResult;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.Auth.dto.request.AuthRequest;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.Auth.dto.response.AuthResponse;
import com.daiquiriclub.daiquiriclub_ecommerce.security.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }
    public ApiResult<AuthResponse> login(AuthRequest authRequest){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.email(),
                            authRequest.password()
                    )
            );
            String token = jwtService.generateToken(authRequest.email());
            return new ApiResult<>(true,"Login exitoso",new AuthResponse(token));
        }catch (BadCredentialsException exception){
            throw new UnauthorizedException("Correo o contraseña son incorrectas");
        }
    }
}
