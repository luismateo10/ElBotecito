package com.elbotecito.bsn.exception;

public class BarcoYaExisteException extends Exception{

    @Override
    public String getMessage() {
        return "El barco ya se encontraba almacenado previamente";
    }
}
