package com.elbotecito.util;

public class util {

    //se encarga de tomar los caracteres de un campo y rellenar con espacios hasta que la longitud sea la adecuada
    public static String completarCampo(String campo, int longitud){
        if(campo.length()>longitud){
            return campo.substring(0, longitud);
        }
        return String.format("%1$-"+longitud+"s", campo);
    }
}
