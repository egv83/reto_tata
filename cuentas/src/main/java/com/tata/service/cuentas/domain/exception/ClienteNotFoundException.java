package com.tata.service.cuentas.domain.exception;

public class ClienteNotFoundException extends RuntimeException{

    public ClienteNotFoundException(){
        super("El cliente no existe en el sistema");
    }

}
