package com.elbotecito.dao.impl;

import com.elbotecito.dao.PuertoDAO;
import com.elbotecito.dao.exception.LlaveDuplicadaException;
import com.elbotecito.modelo.Puerto;
import com.elbotecito.util.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.nio.file.StandardOpenOption.*;
import static java.nio.file.StandardOpenOption.APPEND;

public class PuertoDAONIO implements PuertoDAO {
    private final static String NOMBRE_ARCHIVO = "Puertos"; //Kebab case
    private final static int LONGITUD_REGISTRO = 80;
    private final static int LONGITUD_IDENTIFICACION = 20;
    private final static int LONGITUD_NOMBRE = 40;
    private final static int LONGITUD_CIUDAD = 20;

    private final static String ENCODING_FILE = System.getProperty("file.encoding");
    private final static Path archivo = Paths.get(NOMBRE_ARCHIVO);

    public PuertoDAONIO() {
        if (!Files.exists(archivo)) {
            try {
                Files.createFile(archivo);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    @Override
    public void guardarPuerto(Puerto puerto) throws LlaveDuplicadaException {
        if (consultarPuertoPorIdentificacion(puerto.getIdentificacion()) != null) {
            throw new LlaveDuplicadaException();
        }
        String registro = parsePuerto2String(puerto);
        byte[] datosRegistro = registro.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
        try (FileChannel fc = (FileChannel.open(archivo, APPEND))) {
            fc.write(byteBuffer);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    @Override
    public Puerto consultarPuertoPorIdentificacion(String idPuerto) {
        try (SeekableByteChannel sbc = Files.newByteChannel(archivo)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.forName(ENCODING_FILE).decode(buffer);
                Puerto puertoConvertido = parsePuertoRegistro2Objeto(registro);
                if (puertoConvertido.getIdentificacion().equals(idPuerto)) {
                    return puertoConvertido;
                }
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Puerto> consultarPuertos() {
        List<Puerto> puertos = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(archivo)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.forName(ENCODING_FILE).decode(buffer);
                Puerto puertoConvertido = parsePuertoRegistro2Objeto(registro);
                buffer.flip();
                puertos.add(puertoConvertido);
            }
        } catch (IOException x) {
            System.out.println("caught exception: " + x);
        }
        return puertos;
    }

    @Override
    public boolean actualizarPuerto(Puerto puerto) {
        Set<StandardOpenOption> options = new HashSet<StandardOpenOption>();
        options.add(READ);
        options.add(WRITE);
        long lastPosition = 0;
        try (SeekableByteChannel sbc = Files.newByteChannel(archivo, options)) {
            ByteBuffer buf = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buf) > 0) {
                buf.rewind();
                CharBuffer registro = Charset.forName(ENCODING_FILE).decode(buf);
                String matricula = registro.subSequence(0, LONGITUD_IDENTIFICACION).toString().trim();
                if (matricula.equals(puerto.getIdentificacion())) {
                    sbc.position(lastPosition);
                    sbc.write(ByteBuffer.wrap(parsePuerto2String(puerto).getBytes()));
                    return true;
                }
                buf.flip();
                lastPosition = sbc.position();
            }
        } catch (IOException ioe) {
            System.out.println("caught exception: " + ioe);
        }
        return false;
    }

    @Override
    public boolean eliminarPuerto(String idPuerto) {
        boolean res = false;
        List<Puerto> puertos = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(archivo)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.forName(ENCODING_FILE).decode(buffer);
                Puerto puertoConvertido = parsePuertoRegistro2Objeto(registro);
                buffer.flip();
                puertos.add(puertoConvertido);
            }
        } catch (IOException x) {
            System.out.println("caught exception: " + x);
        }
        util.eliminarYCrearArchivo(archivo);
        while (!puertos.isEmpty()) {
            Puerto puertoAux = puertos.get(0);
            puertos.remove(0);
            if (puertoAux.getIdentificacion().equals(idPuerto)) {
                res = true;
            } else {
                String registro = parsePuerto2String(puertoAux);
                byte[] datosRegistro = registro.getBytes();
                ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
                try (FileChannel fc = (FileChannel.open(archivo, APPEND))) {
                    fc.write(byteBuffer);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return res;
    }

    private String parsePuerto2String(Puerto puerto) {
        StringBuilder registro = new StringBuilder();
        registro.append(util.completarCampo(puerto.getIdentificacion(), LONGITUD_IDENTIFICACION));
        registro.append(util.completarCampo(puerto.getNombre(), LONGITUD_NOMBRE));
        registro.append(util.completarCampo(puerto.getCiudad(), LONGITUD_CIUDAD));
        return registro.toString();
    }

    private Puerto parsePuertoRegistro2Objeto(CharBuffer registro) {
        Puerto puerto = new Puerto();
        String identificacion = registro.subSequence(0, LONGITUD_IDENTIFICACION).toString().trim();
        puerto.setIdentificacion(identificacion);
        registro.position(LONGITUD_IDENTIFICACION);
        registro = registro.slice();

        String nombre = registro.subSequence(0, LONGITUD_NOMBRE).toString().trim();
        puerto.setNombre(nombre);
        registro.position(LONGITUD_NOMBRE);
        registro = registro.slice();

        String ciudad = registro.subSequence(0, LONGITUD_CIUDAD).toString().trim();
        puerto.setCiudad(ciudad);

        return puerto;
    }
}
