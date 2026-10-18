package com.daiquiriclub.daiquiriclub_ecommerce.modules.categoria.service;

import com.daiquiriclub.daiquiriclub_ecommerce.common.exception.BadRequestException;
import com.daiquiriclub.daiquiriclub_ecommerce.common.exception.ConflictException;
import com.daiquiriclub.daiquiriclub_ecommerce.common.exception.ResourceNotFoundException;
import com.daiquiriclub.daiquiriclub_ecommerce.common.response.ApiResult;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.categoria.dto.request.CategoriaCreateRequest;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.categoria.dto.request.CategoriaUpdateRequest;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.categoria.dto.response.CategoriaResponse;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.categoria.entity.Categoria;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.categoria.mapper.CategoriaMapper;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.categoria.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public CategoriaService(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }
    public ApiResult<List<CategoriaResponse>> listaCategorias(){
        List<CategoriaResponse> categorias = categoriaRepository.findAll().stream().map(
                categoriaMapper::toCategoriaResponse
        ).toList();
        return new ApiResult<>(
                true,
                "Lista de categorias",
                categorias
        );
    }
    public ApiResult<List<CategoriaResponse>> listaCateogirasActivas(){
        List<CategoriaResponse> categorias = categoriaRepository.obtenerCategoriasActivas()
                .stream().map(
                        categoriaMapper::toCategoriaResponse)
                .toList();
        return new ApiResult<>(
                true,
                "Lista de categorias activas",
                categorias
        );
    }
    public ApiResult<CategoriaResponse> findByIdCategoria(String id){
        UUID uuid = parseUUID(id);
        Categoria categoria = categoriaRepository.findById(uuid).orElseThrow(()->new ResourceNotFoundException("No se encontró la categoria"));
        return new ApiResult<>(true,"Categoria encontrada",categoriaMapper.toCategoriaResponse(categoria));
    }
    public ApiResult<CategoriaResponse> crearCategoria(CategoriaCreateRequest categoriaCreateRequest){
        if(categoriaRepository.existsByNombre(categoriaCreateRequest.nombre())){
            throw new ConflictException("La categoria ya existe.");
        }
        Categoria categoria = categoriaMapper.toCategoria(categoriaCreateRequest);
        Categoria categoriaGuardado = categoriaRepository.save(categoria);
        return new ApiResult<>(
                true,
                "Se ha creado la categoria",
                categoriaMapper.toCategoriaResponse(categoriaGuardado)
        );
    }
    public ApiResult<CategoriaResponse> updateCategoria(String id, CategoriaUpdateRequest categoriaUpdateRequest){
        UUID uuid = parseUUID(id);
        if(categoriaRepository.existsByNombre(categoriaUpdateRequest.nombre())){
            throw new ConflictException("La categoria existe");
        }
        Categoria categoria = categoriaRepository.findById(uuid).orElseThrow(()-> new ResourceNotFoundException("La categoria no se encontró"));
        categoriaMapper.updatedCategori(categoriaUpdateRequest,categoria);
        return new ApiResult<>(
                true,
                "Se ha actualizado la categoria",
                categoriaMapper.toCategoriaResponse(categoria)
        );
    }
    public ApiResult<Void> desactivarCategoria(String id){
        UUID uuid = parseUUID(id);
        Categoria categoria = categoriaRepository.findById(uuid).orElseThrow(()-> new ResourceNotFoundException("No se encontro la categoria"));
        categoria.setActive(false);
        categoriaRepository.save(categoria);
        return new ApiResult<>(
                true,
                "Se ha desactivado la categoria",
                   null
        );
    }
    private UUID parseUUID(String id){
        try {
            return UUID.fromString(id);
        }catch (IllegalArgumentException e){
            throw new BadRequestException("El UUID es invalido.");
        }
    }
}
