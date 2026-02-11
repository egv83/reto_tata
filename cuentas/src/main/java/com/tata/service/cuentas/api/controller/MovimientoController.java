package com.tata.service.cuentas.api.controller;

import com.tata.service.cuentas.application.dto.MovimientoRequestDTO;
import com.tata.service.cuentas.application.dto.MovimientoResponseDTO;
import com.tata.service.cuentas.application.service.MovimientoService;
import com.tata.service.cuentas.interfaces.repository.MovimientoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @PostMapping
    public ResponseEntity<MovimientoResponseDTO> registrarMovimiente(
            @RequestBody MovimientoRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(movimientoService.registrarMovimiento(request));
    }

    @GetMapping
    public ResponseEntity<List<MovimientoResponseDTO>> getMovimientos(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(movimientoService.getMovimientos());
    }

    @GetMapping("/cuenta/{cuenta}")
    public ResponseEntity<List<MovimientoResponseDTO>> getMovimientoPorCuenta(
            @PathVariable String cuenta
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(movimientoService.getListaMovimientosPorCuenta(cuenta));
    }

}
