package com.tata.service.cuentas.api.controller;

import com.tata.service.cuentas.application.dto.CuentaEstadoDTO;
import com.tata.service.cuentas.application.dto.CuentaRequestDTO;
import com.tata.service.cuentas.application.dto.CuentaResponseDTO;
import com.tata.service.cuentas.application.service.CuentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping
    public ResponseEntity<CuentaResponseDTO> crear(
            @Valid @RequestBody CuentaRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cuentaService.crearCuenta(request));
    }

    @GetMapping
    public ResponseEntity<List<CuentaResponseDTO>> cuentas() {
        return ResponseEntity.ok(cuentaService.getCuentas());
    }

    @GetMapping("/{cuenta}")
    public ResponseEntity<CuentaResponseDTO> obtenetCuenta(
            @PathVariable String cuenta
    ) {
        return ResponseEntity.ok(cuentaService.getCuenta(cuenta));
    }

    @PutMapping("/{cuenta}")
    public ResponseEntity<Void> actualizarCuenta(
            @PathVariable String cuenta,
            @RequestBody CuentaEstadoDTO request
    ) {
        cuentaService.actualizarEstado(cuenta,request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{cuenta}")
    public ResponseEntity<String> eliminar(
            @PathVariable String cuenta
    ){
//        cuentaService.eliminarCuenta(cuenta);
        return ResponseEntity.status(HttpStatus.OK)
                .body(cuentaService.eliminarCuenta(cuenta));
    }

}
