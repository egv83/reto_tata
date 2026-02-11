package com.tata.service.cuentas.domain.exception;

public class ClienteInactivoException extends RuntimeException{

    public ClienteInactivoException(){
        super("El cliente esta inactivo");
    }

}
