package com.elbotecito.bsn.exception;

public class PersonaYaExisteException extends  Exception{
    @Override
    public String getMessage() {
        return "La persona ya se encontraba almacenado previamente";
    }
}
