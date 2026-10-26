package com.daiquiriclub.daiquiriclub_ecommerce.modules.marca.mapper;

import com.daiquiriclub.daiquiriclub_ecommerce.modules.marca.dto.request.MarcaCreateRequest;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.marca.dto.request.MarcaUpdateRequest;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.marca.dto.response.MarcaResponse;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.marca.entity.Marca;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MarcaMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "logoUrl", ignore = true)
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "updatedAt",ignore = true)
    Marca toMarca (MarcaCreateRequest marcaCreateRequest);

    MarcaResponse toMarcaResponse (Marca marca);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "logoUrl", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateMarca(MarcaUpdateRequest marcaUpdateRequest, @MappingTarget Marca marca);
}
