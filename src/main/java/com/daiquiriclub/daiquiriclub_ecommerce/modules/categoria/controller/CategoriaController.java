package com.daiquiriclub.daiquiriclub_ecommerce.modules.categoria.controller;

import com.daiquiriclub.daiquiriclub_ecommerce.common.response.ApiResult;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.categoria.dto.request.CategoriaCreateRequest;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.categoria.dto.request.CategoriaUpdateRequest;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.categoria.dto.response.CategoriaResponse;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.categoria.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categoria")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }
    @GetMapping
    public ResponseEntity<ApiResult<List<CategoriaResponse>>> listaCategoria(){
        return ResponseEntity.ok(categoriaService.listaCategorias());
    }
    @GetMapping("/activos")
    public ResponseEntity<ApiResult<List<CategoriaResponse>>> listaCategoriaActivas(){
        return ResponseEntity.ok(categoriaService.listaCateogirasActivas());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<CategoriaResponse>> obtenerPorCategoriId(@PathVariable String id){
        return ResponseEntity.ok(categoriaService.findByIdCategoria(id));
    }
    @PostMapping()
    public ResponseEntity<ApiResult<CategoriaResponse>> crearCategoria(@RequestBody @Valid CategoriaCreateRequest categoriaCreateRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.crearCategoria(categoriaCreateRequest));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<CategoriaResponse>> actualizarCategia(@PathVariable String id, @RequestBody @Valid CategoriaUpdateRequest categoriaUpdateRequest){
        return ResponseEntity.ok(categoriaService.updateCategoria(id,categoriaUpdateRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<Void>> desactivarCategori(@PathVariable String id){
        return ResponseEntity.ok(categoriaService.desactivarCategoria(id));
    }
}
