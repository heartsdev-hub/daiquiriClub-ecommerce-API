package com.daiquiriclub.daiquiriclub_ecommerce.modules.categoria.mapper;

import com.daiquiriclub.daiquiriclub_ecommerce.modules.categoria.dto.request.CategoriaCreateRequest;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.categoria.dto.request.CategoriaUpdateRequest;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.categoria.dto.response.CategoriaResponse;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.categoria.entity.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    Categoria toCategoria(CategoriaCreateRequest categoriaCreateRequest);
    CategoriaResponse toCategoriaResponse(Categoria categoria);
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "active",ignore = true)
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updatedCategori(CategoriaUpdateRequest categoriaUpdateRequest, @MappingTarget Categoria categoria);
}