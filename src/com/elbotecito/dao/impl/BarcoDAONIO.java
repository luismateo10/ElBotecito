package com.elbotecito.dao.impl;

import com.elbotecito.dao.BarcoDAO;
import com.elbotecito.modelo.Barco;
import com.elbotecito.dao.exception.LlaveDuplicadaException;
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

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.nio.file.StandardOpenOption.APPEND;

public class BarcoDAONIO implements BarcoDAO {

    private final static String NOMBRE_ARCHIVO = "Barcos"; //Kebab case
    private final static int LONGITUD_REGISTRO = 170;
    private final static int LONGITUD_MATRICULA = 20;
    private final static int LONGITUD_CAPACIDADMAXIMA = 10;
    private final static int LONGITUD_NUMEROREGMEC = 20;
    private final static int LONGITUD_FECHAREGMERC = 20;
    private final static int LONGITUD_NOMBRE = 40;
    private final static int LONGITUD_ESTADO = 20;

    private final static int LONGITUD_EMPRESAID = 20;
    private final static int LONGITUD_TIPOID = 20;

    //private final static String ENCODING_WINDOWS = "ASCII";

    // Se utiliza la propiedad del sistema que indica la codificacion de los archivos
    private final static String ENCODING_FILE = System.getProperty("file.encoding");
    private static final String REGISTRO_ELIMINADO_TEXT = "|||||||||||||||";
    private final static Path archivo = Paths.get(NOMBRE_ARCHIVO);

    public BarcoDAONIO() {
        if (!Files.exists(archivo)) {
            try {
                Files.createFile(archivo);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    @Override
    public boolean actualizarBarco(Barco barco) {
        Set<StandardOpenOption> options = new HashSet<StandardOpenOption>();
        options.add(READ);
        options.add(WRITE);
        long lastPosition = 0;
        try (SeekableByteChannel sbc = Files.newByteChannel(archivo, options)) {
            ByteBuffer buf = ByteBuffer.allocate(LONGITUD_REGISTRO);
            String encoding = System.getProperty("file.encoding");
            while (sbc.read(buf) > 0) {
                buf.rewind();
                CharBuffer registro = Charset.forName(encoding).decode(buf);

                String matricula = registro.subSequence(0, LONGITUD_REGISTRO).toString().trim();
                if (matricula.equals(barco.getMatricula())) {
                    sbc.position(lastPosition);
                    sbc.write(ByteBuffer.wrap(parseBarco2String(barco).getBytes()));
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
    public boolean eliminarBarco(String matriculaBarco) {
        Set<StandardOpenOption> options = new HashSet<StandardOpenOption>();
        options.add(READ);
        options.add(WRITE);
        long lastPosition = 0;
        try (SeekableByteChannel sbc = Files.newByteChannel(archivo, options)) {
            ByteBuffer buf = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buf) > 0) {
                buf.rewind();
                CharBuffer registro = Charset.forName(ENCODING_FILE).decode(buf);
                String matricula = registro.subSequence(0, LONGITUD_REGISTRO).toString().trim();
                if (matricula.equals(matriculaBarco)) {
                    sbc.position(lastPosition);
                    new String();
                    sbc.write(ByteBuffer.wrap(REGISTRO_ELIMINADO_TEXT.getBytes()));
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
    public Barco consultarBarcoPorMatricula(String matricula) {
        try (SeekableByteChannel sbc = Files.newByteChannel(archivo)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.forName(ENCODING_FILE).decode(buffer);
                Barco barcoConvertido = parseBarcoRegistro2Objeto(registro);
                if (barcoConvertido.getMatricula().equals(matricula)) {
                    return barcoConvertido;
                }
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Barco> consultarBarcos() {
        List<Barco> barcos = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(archivo)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.forName(ENCODING_FILE).decode(buffer);
                Barco barcoConvertido = parseBarcoRegistro2Objeto(registro);
                buffer.flip();
                barcos.add(barcoConvertido);
            }
        } catch (IOException x) {
            System.out.println("caught exception: " + x);
        }
        return barcos;
    }

    @Override
    public boolean guardarBarco(Barco barco) throws LlaveDuplicadaException {
        if (consultarBarcoPorMatricula(barco.getMatricula()) != null) {
            throw new LlaveDuplicadaException();
        }
        String registro = parseBarco2String(barco);
        byte[] datosRegistro = registro.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
        try (FileChannel fc = (FileChannel.open(archivo, APPEND))) {
            fc.write(byteBuffer);
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }

    }

    private String parseBarco2String(Barco barco) {
        StringBuilder registro = new StringBuilder();
        registro.append(util.completarCampo(barco.getMatricula(), LONGITUD_MATRICULA));
        registro.append(util.completarCampo(barco.getCapacidadMax(), LONGITUD_CAPACIDADMAXIMA));
        registro.append(util.completarCampo(barco.getNumeroRegMerc(), LONGITUD_NUMEROREGMEC));
        registro.append(util.completarCampo(barco.getFechaRegMerc(), LONGITUD_FECHAREGMERC));
        registro.append(util.completarCampo(barco.getNombre(), LONGITUD_NOMBRE));
        registro.append(util.completarCampo(barco.getEstado(), LONGITUD_ESTADO));
        registro.append(util.completarCampo(barco.getTipoId(), LONGITUD_TIPOID));
        return registro.toString();
    }

    private Barco parseBarcoRegistro2Objeto(CharBuffer registro) {
        Barco barco = new Barco();
        String matricula = registro.subSequence(0, LONGITUD_MATRICULA).toString().trim();
        barco.setMatricula(matricula);
        registro.position(LONGITUD_MATRICULA);
        registro = registro.slice();

        String capacidadMax = registro.subSequence(0, LONGITUD_CAPACIDADMAXIMA).toString().trim();
        barco.setCapacidadMax(capacidadMax);
        registro.position(LONGITUD_CAPACIDADMAXIMA);
        registro = registro.slice();

        String numeroRegMerc = registro.subSequence(0, LONGITUD_NUMEROREGMEC).toString().trim();
        barco.setNumeroRegMerc(numeroRegMerc);
        registro.position(LONGITUD_NUMEROREGMEC);
        registro = registro.slice();

        String fechaRegMerc = registro.subSequence(0, LONGITUD_FECHAREGMERC).toString().trim();
        barco.setFechaRegMerc(fechaRegMerc);
        registro.position(LONGITUD_FECHAREGMERC);
        registro = registro.slice();

        String nombre = registro.subSequence(0, LONGITUD_NOMBRE).toString().trim();
        barco.setNombre(nombre);
        registro.position(LONGITUD_NOMBRE);
        registro = registro.slice();

        String estado = registro.subSequence(0, LONGITUD_ESTADO).toString().trim();
        barco.setEstado(estado);
        registro.position(LONGITUD_ESTADO);
        registro = registro.slice();

        String tipoId = registro.subSequence(0, LONGITUD_TIPOID).toString().trim();
        barco.setTipoId(tipoId);

        return barco;
    }
}
