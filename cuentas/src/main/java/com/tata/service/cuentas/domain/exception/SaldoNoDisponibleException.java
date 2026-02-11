package com.tata.service.cuentas.domain.exception;

public class SaldoNoDisponibleException extends RuntimeException{

    public SaldoNoDisponibleException(){
        super("Saldo no disponible");
    }

}
