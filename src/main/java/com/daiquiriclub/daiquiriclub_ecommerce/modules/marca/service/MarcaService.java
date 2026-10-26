package com.daiquiriclub.daiquiriclub_ecommerce.modules.marca.service;

import com.daiquiriclub.daiquiriclub_ecommerce.common.enums.PaisOrigen;
import com.daiquiriclub.daiquiriclub_ecommerce.common.exception.BadRequestException;
import com.daiquiriclub.daiquiriclub_ecommerce.common.exception.ConflictException;
import com.daiquiriclub.daiquiriclub_ecommerce.common.exception.ResourceNotFoundException;
import com.daiquiriclub.daiquiriclub_ecommerce.common.response.ApiResult;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.marca.dto.request.MarcaCreateRequest;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.marca.dto.request.MarcaUpdateRequest;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.marca.dto.response.MarcaResponse;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.marca.entity.Marca;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.marca.mapper.MarcaMapper;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.marca.repository.MarcaRepository;
import com.daiquiriclub.daiquiriclub_ecommerce.security.cloudinary.CloudinaryService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class MarcaService {
    private final MarcaRepository marcaRepository;
    private final MarcaMapper marcaMapper;
    private final CloudinaryService cloudinaryService;

    public MarcaService(MarcaRepository marcaRepository, MarcaMapper marcaMapper, CloudinaryService cloudinaryService) {
        this.marcaRepository = marcaRepository;
        this.marcaMapper = marcaMapper;
        this.cloudinaryService = cloudinaryService;
    }
    @PreAuthorize("hasAuthority('MARCA_READ')")
    public ApiResult<List<MarcaResponse>> listaMarca(){
        List<MarcaResponse> marcas = marcaRepository.findAll().stream().map(
                marcaMapper::toMarcaResponse
        ).toList();
        return new ApiResult<>(true,"Lista de marcas",marcas);
    }
    @PreAuthorize("hasAuthority('MARCA_READ_ACTIVE')")
    public ApiResult<List<MarcaResponse>> listaMarcaActivas(){
        List<MarcaResponse> marcas = marcaRepository.findByActiveTrue().stream().map(marcaMapper::toMarcaResponse).toList();
        return new ApiResult<>(true,"Lista de categorias activas",marcas);
    }
    @PreAuthorize("hasAuthority('MARCA_READ')")
    public ApiResult<MarcaResponse> findByIdMarca(String id ){
        UUID uuid = parseID(id);
        Marca marca = marcaRepository.findById(uuid).orElseThrow(()-> new ResourceNotFoundException("No se encontroó la marca"));
        return new ApiResult<>(true,"Marca encontrada", marcaMapper.toMarcaResponse(marca));
    }
    @Transactional
    @PreAuthorize("hasAuthority('MARCA_CREATE')")
    public ApiResult<MarcaResponse> createMarca(MarcaCreateRequest marcaCreateRequest){
        if(marcaRepository.existsByNombre(marcaCreateRequest.nombre()))
            throw new ConflictException("La marca ya existe");
        if(marcaRepository.existsBySlug(marcaCreateRequest.slug()))
            throw new ConflictException("El slug ya existe en otra marca.");
        Marca marca = marcaMapper.toMarca(marcaCreateRequest);
        //subir imagen a Cloudinary
        if(marcaCreateRequest.logo() != null && !marcaCreateRequest.logo().isEmpty()){
            try{
                String logoUrl = cloudinaryService.uploadImage(marcaCreateRequest.logo());
                marca.setLogoUrl(logoUrl);
            }catch (IOException e){
                throw new IllegalStateException("Error al subir el logotipo");
            }
        }
        Marca marcaGuardado = marcaRepository.save(marca);
        return new ApiResult<>(
                true,
                "Se ha creado la marca correctamente",
                marcaMapper.toMarcaResponse(marcaGuardado)
        );
    }
    @Transactional
    @PreAuthorize("hasAuthority('MARCA_UPDATE')")
    public ApiResult<MarcaResponse> actualizarMarca(String id, MarcaUpdateRequest marcaUpdateRequest){
        UUID uuid = parseID(id);
        if(marcaRepository.existsBySlug(marcaUpdateRequest.slug()))
            throw new ConflictException("Ya existe el slug");
        if(marcaRepository.existsByNombre(marcaUpdateRequest.nombre()))
            throw new ConflictException("La marca ya existe");
       boolean existePaisOrigen = Arrays.stream(PaisOrigen.values()).anyMatch(
               pais -> pais.name().equalsIgnoreCase(marcaUpdateRequest.paisOrigen().name())
       );
       if(existePaisOrigen)
           throw new ConflictException("El pais es invalido");
       Marca marca = marcaRepository.findById(uuid).orElseThrow(
               ()-> new ResourceNotFoundException("No se encontró la marca")
       );
       marcaMapper.updateMarca(marcaUpdateRequest,marca);
       Marca marcaSv = marcaRepository.save(marca);
       return new ApiResult<>(true,"Se actualizó la marca",marcaMapper.toMarcaResponse(marcaSv));
    }
    @Transactional
    @PreAuthorize("hasAuthority('MARCA_UPDATE')")
    public ApiResult<MarcaResponse> actualizarlOGO(String id, MultipartFile logo) throws IOException {
        UUID uuid = parseID(id);
        Marca marca = marcaRepository.findById(uuid).orElseThrow(
                ()-> new ResourceNotFoundException("No encontro´la marca")
        );
        if (logo == null || logo.isEmpty())
            throw new IllegalArgumentException("Debes Seleccionar una imagen");
        String nuevoUrl = cloudinaryService.uploadImage(logo);
        marca.setLogoUrl(nuevoUrl);
        Marca marcaSv = marcaRepository.save(marca);
        return new ApiResult<>(true,
                "Se ha actualizado el logo",
                marcaMapper.toMarcaResponse(marcaSv));
    }
    private UUID parseID(String id){
        try{
            return UUID.fromString(id);
        }catch (IllegalArgumentException exception){
            throw new BadRequestException("El UUID es invalido");
        }
    }
}
