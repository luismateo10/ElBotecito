package com.elbotecito.util;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class util {

    //se encarga de tomar los caracteres de un campo y rellenar con espacios hasta que la longitud sea la adecuada
    public static String completarCampo(String campo, int longitud) {
        if (campo.length() > longitud) {
            return campo.substring(0, longitud);
        }
        return String.format("%1$-" + longitud + "s", campo);
    }

    public static void eliminarYCrearArchivo(Path archivo) {

        try {
            Files.deleteIfExists(archivo);
        } catch (NoSuchFileException e) {
            System.out.println("No such file/directory exists");
        } catch (DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty.");
        } catch (IOException e) {
            System.out.println("Invalid permissions.");
        }

        System.out.println("Deletion successful.");

        if (!Files.exists(archivo)) {
            try {
                Files.createFile(archivo);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }


    }
}
