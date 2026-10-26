package com.daiquiriclub.daiquiriclub_ecommerce.modules.marca.Controller;

import com.daiquiriclub.daiquiriclub_ecommerce.common.response.ApiResult;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.marca.dto.request.MarcaCreateRequest;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.marca.dto.response.MarcaResponse;
import com.daiquiriclub.daiquiriclub_ecommerce.modules.marca.service.MarcaService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/marca")
public class MarcaController {
    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }
    @GetMapping()
    public ResponseEntity<ApiResult<List<MarcaResponse>>>listMarca(){
        return ResponseEntity.ok(marcaService.listaMarca());
    }
    @GetMapping("/activas")
    public ResponseEntity<ApiResult<List<MarcaResponse>>>listaMarcaActivas(){
        return ResponseEntity.ok(marcaService.listaMarcaActivas());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<MarcaResponse>>findById(@PathVariable String id){
        return ResponseEntity.ok(marcaService.findByIdMarca(id));
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResult<MarcaResponse>>createMarca(@ModelAttribute @Valid MarcaCreateRequest marcaCreateRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(marcaService.createMarca(marcaCreateRequest));
    }
}
