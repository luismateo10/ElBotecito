package com.elbotecito.dao.impl;

import com.elbotecito.dao.EsposaDAO;
import com.elbotecito.dao.exception.LlaveDuplicadaException;
import com.elbotecito.modelo.Esposa;
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

public class EsposaDAONIO implements EsposaDAO {
    private final static String NOMBRE_ARCHIVO = "Esposas"; //Kebab case
    private final static int LONGITUD_REGISTRO = 102;
    private final static int LONGITUD_IDENTIFICACION = 20;
    private final static int LONGITUD_NOMBRE = 40;
    private final static int LONGITUD_SEXO = 1;
    private final static int LONGITUD_ESTADOVIVO = 1;
    private final static int LONGITUD_ESPOSO = 20;
    private final static int LONGITUD_PORCHERENCIA = 20;


    private final static String ENCODING_FILE = System.getProperty("file.encoding");
    private final static Path archivo = Paths.get(NOMBRE_ARCHIVO);

    public EsposaDAONIO() {
        if (!Files.exists(archivo)) {
            try {
                Files.createFile(archivo);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    @Override
    public void guardarEsposa(Esposa esposa) throws LlaveDuplicadaException {
        if (consultarEsposaPorIdentificacion(esposa.getIdentificacion()) != null) {
            throw new LlaveDuplicadaException();
        }
        String registro = parseEsposa2String(esposa);
        byte[] datosRegistro = registro.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
        try (FileChannel fc = (FileChannel.open(archivo, APPEND))) {
            fc.write(byteBuffer);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    @Override
    public Esposa consultarEsposaPorIdentificacion(String idEsposa) {
        try (SeekableByteChannel sbc = Files.newByteChannel(archivo)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.forName(ENCODING_FILE).decode(buffer);
                Esposa esposaConvertido = parseEsposaRegistro2Objeto(registro);
                if (esposaConvertido.getIdentificacion().equals(idEsposa)) {
                    return esposaConvertido;
                }
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Esposa> consultarEsposas() {
        List<Esposa> esposas = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(archivo)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.forName(ENCODING_FILE).decode(buffer);
                Esposa esposaConvertido = parseEsposaRegistro2Objeto(registro);
                buffer.flip();
                esposas.add(esposaConvertido);
            }
        } catch (IOException x) {
            System.out.println("caught exception: " + x);
        }
        return esposas;
    }

    @Override
    public boolean actualizarEsposa(Esposa esposa) {
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
                if (matricula.equals(esposa.getIdentificacion())) {
                    sbc.position(lastPosition);
                    sbc.write(ByteBuffer.wrap(parseEsposa2String(esposa).getBytes()));
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
    public boolean eliminarEsposa(String idEsposa) {
        boolean res = false;
        List<Esposa> esposas = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(archivo)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.forName(ENCODING_FILE).decode(buffer);
                Esposa esposaConvertido = parseEsposaRegistro2Objeto(registro);
                buffer.flip();
                esposas.add(esposaConvertido);
            }
        } catch (IOException x) {
            System.out.println("caught exception: " + x);
        }
        util.eliminarYCrearArchivo(archivo);
        while (!esposas.isEmpty()) {
            Esposa esposaAux = esposas.get(0);
            esposas.remove(0);
            if (esposaAux.getIdentificacion().equals(idEsposa)) {
                res = true;
            } else {
                String registro = parseEsposa2String(esposaAux);
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

    private String parseEsposa2String(Esposa esposa) {
        StringBuilder registro = new StringBuilder();
        registro.append(util.completarCampo(esposa.getIdentificacion(), LONGITUD_IDENTIFICACION));
        registro.append(util.completarCampo(esposa.getNombre(), LONGITUD_NOMBRE));
        registro.append(util.completarCampo(esposa.getSexo(), LONGITUD_SEXO));
        registro.append(util.completarCampo(esposa.getEstadoVivo(), LONGITUD_ESTADOVIVO));
        registro.append(util.completarCampo(esposa.getEsposo(), LONGITUD_ESPOSO));
        registro.append(util.completarCampo(esposa.getPorcHerencia(), LONGITUD_PORCHERENCIA));

        return registro.toString();
    }

    private Esposa parseEsposaRegistro2Objeto(CharBuffer registro) {
        Esposa esposa = new Esposa();
        String identificacion = registro.subSequence(0, LONGITUD_IDENTIFICACION).toString().trim();
        esposa.setIdentificacion(identificacion);
        registro.position(LONGITUD_IDENTIFICACION);
        registro = registro.slice();

        String nombre = registro.subSequence(0, LONGITUD_NOMBRE).toString().trim();
        esposa.setNombre(nombre);
        registro.position(LONGITUD_NOMBRE);
        registro = registro.slice();

        String sexo = registro.subSequence(0, LONGITUD_SEXO).toString().trim();
        esposa.setSexo(sexo);
        registro.position(LONGITUD_SEXO);
        registro = registro.slice();

        String estadoVivo = registro.subSequence(0, LONGITUD_ESTADOVIVO).toString().trim();
        esposa.setEstadoVivo(estadoVivo);
        registro.position(LONGITUD_ESTADOVIVO);
        registro = registro.slice();

        String esposo = registro.subSequence(0, LONGITUD_ESPOSO).toString().trim();
        esposa.setEsposo(esposo);
        registro.position(LONGITUD_ESPOSO);
        registro = registro.slice();

        String porcHerencia = registro.subSequence(0, LONGITUD_PORCHERENCIA).toString().trim();
        esposa.setPorcHerencia(porcHerencia);

        return esposa;
    }
}
