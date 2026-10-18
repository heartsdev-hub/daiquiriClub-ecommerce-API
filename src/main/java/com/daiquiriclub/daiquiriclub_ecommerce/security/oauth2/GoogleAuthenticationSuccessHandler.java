package com.daiquiriclub.daiquiriclub_ecommerce.security.oauth2;

import com.daiquiriclub.daiquiriclub_ecommerce.modules.usuario.entity.Usuario;
import com.daiquiriclub.daiquiriclub_ecommerce.security.jwt.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

@Component
public class GoogleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final GoogleUserService googleUserService;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    public GoogleAuthenticationSuccessHandler(GoogleUserService googleUserService, JwtService jwtService, ObjectMapper objectMapper) {
        this.googleUserService = googleUserService;
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //obtener el usuario autenticado por google
        if(!(authentication.getPrincipal() instanceof OidcUser googleUser)){
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Authentication de Google invalida"
            );
            return;
        }
        //registrar o buscar usuario en la bd postgres
        Usuario usuario = googleUserService.loginGoogle(googleUser);
        //generar jwt propio de nuestra app
        String token = jwtService.generateToken(usuario.getEmail());
        //configurar respuesta http
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        //devolver token como json
        objectMapper.writeValue(
                response.getWriter(),
                Map.of(
                        "success", true,
                        "message", "Login con Google exitoso",
                        "token", token,
                        "tokenType", "Bearer"
                )
        );
    }
}
