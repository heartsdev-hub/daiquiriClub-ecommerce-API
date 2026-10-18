package com.daiquiriclub.daiquiriclub_ecommerce.security.oauth2;

import com.daiquiriclub.daiquiriclub_ecommerce.modules.cliente.entity.Cliente;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.cliente.repository.ClienteRepository;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.usuario.entity.Usuario;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.usuario.repository.UsuarioRepository;
import com.daiquiriclub.daiquiriclub_ecommerce.security.Authorization.Rol;
import com.daiquiriclub.daiquiriclub_ecommerce.security.model.AuthProvider;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class GoogleUserService {
    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;

    public GoogleUserService(UsuarioRepository usuarioRepository, ClienteRepository clienteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
    }
    @Transactional
    public Usuario loginGoogle(OidcUser googleUser) {

        // 1. Verificar el correo de Google
        if (!Boolean.TRUE.equals(googleUser.getEmailVerified())) {
            throw new BadCredentialsException(
                    "El correo de Google no está verificado"
            );
        }

        // 2. Obtener datos de Google
        String googleId = googleUser.getSubject();
        String email = googleUser.getEmail();
        String nombre = googleUser.getGivenName();
        String apellidos = googleUser.getFamilyName();

        if (googleId == null || googleId.isBlank()
                || email == null || email.isBlank()) {
            throw new BadCredentialsException(
                    "Google no proporcionó los datos necesarios"
            );
        }

        // 3. Buscar una cuenta ya vinculada con Google
        Usuario usuarioExistente = usuarioRepository
                .findByAuthProviderAndProviderId(
                        AuthProvider.GOOGLE,
                        googleId
                )
                .orElse(null);

        if (usuarioExistente != null) {

            if (!usuarioExistente.isActive()) {
                throw new BadCredentialsException(
                        "La cuenta está desactivada"
                );
            }

            if (!clienteRepository.existsByUsuarioId(
                    usuarioExistente.getId()
            )) {
                throw new BadCredentialsException(
                        "El usuario no está asociado a un cliente"
                );
            }

            // Cuenta Google existente: iniciar sesión
            return usuarioExistente;
        }

        // 4. Verificar si el correo ya está registrado
        Usuario usuarioPorEmail = usuarioRepository
                .findByEmail(email)
                .orElse(null);

        if (usuarioPorEmail != null) {

            // Cuenta registrada mediante correo y contraseña
            if (usuarioPorEmail.getAuthProvider()
                    == AuthProvider.LOCAL) {

                throw new BadCredentialsException(
                        "Este correo ya está registrado. " +
                                "Inicia sesión con tu contraseña para vincular Google."
                );
            }

            // Correo coincidente, pero identidad Google diferente
            if (usuarioPorEmail.getAuthProvider()
                    == AuthProvider.GOOGLE) {

                throw new BadCredentialsException(
                        "Este correo ya pertenece a otra identidad de Google"
                );
            }

            throw new BadCredentialsException(
                    "Este correo ya está asociado a otra cuenta"
            );
        }

        // 5. Crear usuario nuevo
        Usuario usuario = new Usuario();

        usuario.setEmail(email);
        usuario.setPassword(null);
        usuario.setAuthProvider(AuthProvider.GOOGLE);
        usuario.setProviderId(googleId);
        usuario.setActive(true);
        usuario.setRoles(
                new HashSet<>(Set.of(Rol.CLIENTE))
        );

        Usuario usuarioGuardado =
                usuarioRepository.save(usuario);

        // 6. Crear cliente asociado
        Cliente cliente = new Cliente();

        cliente.setNombre(nombre);
        cliente.setApellidos(apellidos);
        cliente.setUsuario(usuarioGuardado);
        cliente.setActive(true);

        // Foto predeterminada de Amazon S3
        cliente.setFotoPerfilKey(null);

        clienteRepository.save(cliente);

        // 7. Retornar usuario para generar JWT
        return usuarioGuardado;
    }
}
