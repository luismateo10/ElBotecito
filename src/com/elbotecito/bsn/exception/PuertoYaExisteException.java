package com.elbotecito.bsn.exception;

public class PuertoYaExisteException extends Exception{

    @Override
    public String getMessage() {
        return "El puerto ya se encontraba almacenado previamente";
    }
}
