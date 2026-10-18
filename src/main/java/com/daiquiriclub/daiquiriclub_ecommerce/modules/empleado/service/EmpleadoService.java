package com.daiquiriclub.daiquiriclub_ecommerce.modules.empleado.service;

import com.daiquiriclub.daiquiriclub_ecommerce.common.enums.TipoDocumento;
import com.daiquiriclub.daiquiriclub_ecommerce.common.enums.TipoSexo;
import com.daiquiriclub.daiquiriclub_ecommerce.common.exception.ConflictException;
import com.daiquiriclub.daiquiriclub_ecommerce.common.response.ApiResult;
import com.daiquiriclub.daiquiriclub_ecommerce.infrastructure.storage.S3Service;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.empleado.dto.request.EmpleadoCreateRequest;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.empleado.dto.response.EmpleadoResponse;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.empleado.entity.Empleado;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.empleado.mapper.EmpleadoMapper;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.empleado.repository.EmpleadoRepository;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.usuario.entity.Usuario;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.usuario.repository.UsuarioRepository;
import com.daiquiriclub.daiquiriclub_ecommerce.security.Authorization.Rol;
import com.daiquiriclub.daiquiriclub_ecommerce.security.aws.S3Properties;
import com.daiquiriclub.daiquiriclub_ecommerce.security.model.AuthProvider;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class EmpleadoService {
    private final EmpleadoRepository empleadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmpleadoMapper empleadoMapper;
    private final S3Service s3Service;
    private final S3Properties s3Properties;

    public EmpleadoService(EmpleadoRepository empleadoRepository, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, EmpleadoMapper empleadoMapper, S3Service s3Service, S3Properties s3Properties) {
        this.empleadoRepository = empleadoRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.empleadoMapper = empleadoMapper;
        this.s3Service = s3Service;
        this.s3Properties = s3Properties;
    }
    @Transactional
    public ApiResult<EmpleadoResponse> createEmpleado(EmpleadoCreateRequest empleadoCreateRequest) throws BadRequestException {
        if(empleadoRepository.existsByNumDocumento(empleadoCreateRequest.numDocumento())){
             throw new ConflictException("El numero de documento ya existe");
        }
        if(usuarioRepository.existsByEmail(empleadoCreateRequest.email())){
            throw new ConflictException("El email ya existe");
        }
        boolean existsSexo = Arrays.stream(TipoSexo.values()).anyMatch(
                sexo -> sexo.name().equalsIgnoreCase(empleadoCreateRequest.tipoSexo().name())
        );
        if(!existsSexo)
            throw new BadRequestException("El tipo de sexo es invalido.");
        boolean existsTipoDoc = Arrays.stream(TipoDocumento.values()).anyMatch(
                doc -> doc.name().equalsIgnoreCase(empleadoCreateRequest.tipoDocumento().name())
        );
        if(!existsTipoDoc)
            throw new BadRequestException("El tipo de documento es invalido");
        //CREAR EMPLEADO
        Empleado empleado = empleadoMapper.toEmpleado(empleadoCreateRequest);

        //Añadir usuario
        Usuario usuario = new Usuario();
        usuario.setEmail(empleadoCreateRequest.email());
        usuario.setPassword(passwordEncoder.encode(empleadoCreateRequest.password()));
        Set<Rol> rolAdd = new HashSet<>();
        for(var role : empleadoCreateRequest.roles()){
            boolean existsRol = Arrays.stream(Rol.values()).anyMatch(
                    rol -> rol.name().equalsIgnoreCase(role.name())
            );
            if(!existsRol)
                throw new BadRequestException("Rol invalido "+role.name());
            rolAdd.add(role);
        }
        usuario.setRoles(rolAdd);
        usuario.setAuthProvider(AuthProvider.LOCAL);
        usuario.setProviderId(null);
        usuarioRepository.save(usuario);
        empleado.setUsuario(usuario);
        empleado.setFotoPerfilKey(null);

        String fotoUrl = s3Service.generatePresignedUrl(
                s3Properties.defaultProfileKey(),
                s3Properties.presignedUrlDuration()
        );
        empleadoRepository.save(empleado);
        EmpleadoResponse empleadoResponse = empleadoMapper.toEmpleadoResponse(empleado,fotoUrl);
        return new ApiResult<>(true,"Empleado creado correctamente", empleadoResponse);
    }
}
