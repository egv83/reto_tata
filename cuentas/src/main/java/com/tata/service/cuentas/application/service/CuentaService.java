package com.tata.service.cuentas.application.service;

import com.tata.service.cuentas.application.dto.CuentaEstadoDTO;
import com.tata.service.cuentas.application.dto.CuentaRequestDTO;
import com.tata.service.cuentas.application.dto.CuentaResponseDTO;
import com.tata.service.cuentas.application.mapper.CuentaMapper;
import com.tata.service.cuentas.domain.entity.ClienteEntity;
import com.tata.service.cuentas.domain.entity.CuentaEntity;
import com.tata.service.cuentas.domain.entity.MovimientoEntity;
import com.tata.service.cuentas.domain.exception.ClienteInactivoException;
import com.tata.service.cuentas.domain.exception.ClienteNotFoundException;
import com.tata.service.cuentas.domain.exception.CuentaException;
import com.tata.service.cuentas.domain.exception.MovimientoException;
import com.tata.service.cuentas.interfaces.repository.ClienteRepository;
import com.tata.service.cuentas.interfaces.repository.CuentaRepository;
import com.tata.service.cuentas.interfaces.repository.MovimientoRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.tata.service.cuentas.shared.ErrorConstantes.CUENTA_NO_EXISTE;
import static com.tata.service.cuentas.shared.ErrorConstantes.ERROR_ELIMINAR_CUENTA;

@Service
@Transactional
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;
    private final ClienteRepository clienteRepository;
    private final CuentaMapper cuentaMapper;

    public CuentaService(CuentaRepository cuentaRepository, MovimientoRepository movimientoRepository,
                         ClienteRepository clienteRepository, CuentaMapper cuentaMapper) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
        this.clienteRepository = clienteRepository;
        this.cuentaMapper = cuentaMapper;
    }

    public CuentaResponseDTO crearCuenta(CuentaRequestDTO request) {

        ClienteEntity clienteEntity = clienteRepository.findByNombre(request.cliente())
                .orElseThrow(() -> new ClienteNotFoundException());

        if (!clienteEntity.getEstado()) {
            throw new ClienteInactivoException();
        }

        String cuenta = generarNumeroCuenta();

        CuentaEntity cuentaEntity = new CuentaEntity(
                cuenta,
                request.tipoCuenta().toUpperCase(),
                request.saldoInicial(),
                true,
                clienteEntity.getIdentificacion()
        );

        try {
            cuentaRepository.save(cuentaEntity);
        } catch (DataIntegrityViolationException e) {
            throw new CuentaException("Error al crear la cuenta: " + e);
        }

        BigDecimal monto = movimientoRepository.buscarPorNumeroCuenta(cuenta).stream()
                .map(movimiento -> movimiento.getSaldo())
                .reduce(request.saldoInicial(), BigDecimal::add);
//        monto = monto.add(request.saldoInicial());

        MovimientoEntity movimientoEntity = new MovimientoEntity(
                LocalDateTime.now(),
                "CREDITO",
                request.saldoInicial(),
                monto
        );

        try {
            movimientoEntity.agregarCuenta(cuentaEntity);
            movimientoRepository.save(movimientoEntity);
        } catch (DataIntegrityViolationException e) {
            throw new MovimientoException("Error al crear el moviminto: " + e);
        }
        return cuentaMapper.toCuentaResponseDTO(cuentaEntity, clienteEntity);
    }

    public List<CuentaResponseDTO> getCuentas() {
        List<CuentaResponseDTO> lista = new ArrayList<>();

        for (CuentaEntity cuenta : cuentaRepository.findAll()) {
            ClienteEntity cliente = clienteRepository.findByIdentificacion(cuenta.getClienteIdentificacion()).get();
            lista.add(cuentaMapper.toCuentaResponseDTO(cuenta, cliente));

        }

//        cuentaRepository.findAll()
//                .stream()
//                .forEach(
//                        cuenta -> {
//                            lista.add(cuentaMapper.toCuentaResponseDTO(cuenta));
//                        }
//                );
        return lista;
    }

    public CuentaResponseDTO getCuenta(String cuenta) {
        CuentaEntity cuentaEntity = cuentaRepository.findByNumeroCuenta(cuenta)
                .orElseThrow(() -> new CuentaException(CUENTA_NO_EXISTE));

        ClienteEntity clienteEntity = clienteRepository.findByIdentificacion(cuentaEntity.getClienteIdentificacion())
                .get();

        return cuentaMapper.toCuentaResponseDTO(cuentaEntity, clienteEntity);
    }

    public void actualizarEstado(String cuenta, CuentaEstadoDTO request) {
        CuentaEntity cuentaEntity = cuentaRepository.findByNumeroCuenta(cuenta)
                .orElseThrow(() -> new CuentaException(CUENTA_NO_EXISTE));

        cuentaEntity.actualizarEstado(updateEstado(request.estado()));
        cuentaRepository.save(cuentaEntity);
    }

    public String eliminarCuenta(String cuenta) {
        CuentaEntity cuentaEntity = cuentaRepository.findByNumeroCuenta(cuenta)
                .orElseThrow(() -> new CuentaException(CUENTA_NO_EXISTE));

        try {
            cuentaRepository.delete(cuentaEntity);
        } catch (DataIntegrityViolationException e) {
            throw new CuentaException(ERROR_ELIMINAR_CUENTA + e);
        }
        return "Cuenta Eliminada";
    }

    private String generarNumeroCuenta() {
        String numero;
        do {
            numero = String.valueOf(
                    ThreadLocalRandom.current().nextLong(100_00, 999_999)
            );
        } while (cuentaRepository.existsByNumeroCuenta(numero));
        return numero;
    }

    private Boolean updateEstado(boolean estado) {
        if (estado) {
            return true;
        } else {
            return false;
        }
    }

}
