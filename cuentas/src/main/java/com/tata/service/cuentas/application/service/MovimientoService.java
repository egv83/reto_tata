package com.tata.service.cuentas.application.service;

import com.tata.service.cuentas.application.dto.MovimientoRequestDTO;
import com.tata.service.cuentas.application.dto.MovimientoResponseDTO;
import com.tata.service.cuentas.application.mapper.MovimientoMapper;
import com.tata.service.cuentas.domain.entity.CuentaEntity;
import com.tata.service.cuentas.domain.entity.MovimientoEntity;
import com.tata.service.cuentas.domain.exception.CuentaException;
import com.tata.service.cuentas.domain.exception.MovimientoException;
import com.tata.service.cuentas.domain.exception.SaldoNoDisponibleException;
import com.tata.service.cuentas.interfaces.repository.CuentaRepository;
import com.tata.service.cuentas.interfaces.repository.MovimientoRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.tata.service.cuentas.shared.ErrorConstantes.CUENTA_NO_EXISTE;

@Service
@Transactional
public class MovimientoService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;
    private final MovimientoMapper movimientoMapper;

    public MovimientoService(CuentaRepository cuentaRepository, MovimientoRepository movimientoRepository, MovimientoMapper movimientoMapper) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
        this.movimientoMapper = movimientoMapper;
    }

    public MovimientoResponseDTO registrarMovimiento(MovimientoRequestDTO request) {
        CuentaEntity cuentaEntity = cuentaRepository.findByNumeroCuenta(request.numeroCuenta())
                .orElseThrow(() -> new CuentaException(CUENTA_NO_EXISTE));

        MovimientoEntity movimientoEntity = movimientoRepository
                .buscarUltimoMovimiento(request.numeroCuenta()).get();

        /*para obtener el saldo */
        BigDecimal saldoActual = movimientoEntity.getSaldo() != BigDecimal.ZERO
                ? movimientoEntity.getSaldo() : cuentaEntity.getSaldoInicial();

        /*AQUI SE SUMA CON EL VALOR QUE VIENE DE LA PETICIÓN*/
        BigDecimal saldoTMP = saldoActual.add(request.valor());

        if (saldoTMP.compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoNoDisponibleException();
        }

        String tipoMovimiento = request.valor().compareTo(BigDecimal.ZERO) >= 0 ?
                "CREDITO" : "DEBITO";

        MovimientoEntity movimiento = new MovimientoEntity(
                LocalDateTime.now(),
                tipoMovimiento,
                request.valor(),
                saldoTMP
        );

        try {
            cuentaEntity.agregarMovimiento(movimiento);
            cuentaRepository.save(cuentaEntity);
        } catch (DataIntegrityViolationException e) {
            throw new MovimientoException("Error al crear el registro del movimiento: " + e);
        }

        return movimientoMapper.toMovimientoDTO(movimiento);

    }

    public List<MovimientoResponseDTO> getMovimientos() {
        List<MovimientoResponseDTO> lista = new ArrayList<>();

        movimientoRepository.findAll()
                .stream()
                .forEach(
                        movimientos -> {
                            lista.add(movimientoMapper.toMovimientoDTO(movimientos));
                        }
                );
        return lista;
    }

    public List<MovimientoResponseDTO> getListaMovimientosPorCuenta(String cuenta) {
        List<MovimientoResponseDTO> lista = new ArrayList<>();

        movimientoRepository.buscarPorNumeroCuenta(cuenta)
                .stream()
                .forEach(
                        movimiento -> {
                            lista.add(movimientoMapper.toMovimientoDTO(movimiento));
                        }
                );
        return lista;
    }

}
