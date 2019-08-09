package com.elbotecito.dao.exception;

public class LlaveDuplicadaException extends Exception {
    @Override
    public String getMessage() {
        return "El registro con la clave enviada como parámetro ya existía";
    }
}
