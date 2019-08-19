package com.elbotecito.bsn.exception;

public class RutaYaExisteException extends Exception {
    @Override
    public String getMessage() {
        return "La ruta ya se encontraba almacenada previamente";
    }
}
