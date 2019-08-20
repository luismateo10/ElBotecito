package com.elbotecito.bsn.exception;

public class EscalaYaExisteException extends Exception {
    @Override
    public String getMessage() {
        return "La escala ya se encontraba almacenada previamente";
    }
}
