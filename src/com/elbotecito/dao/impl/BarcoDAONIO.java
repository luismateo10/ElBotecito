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
import java.nio.file.*;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class BarcoDAONIO implements BarcoDAO {

    private final static String NOMBRE_ARCHIVO = "Barcos"; //Kebab case
    private final static int LONGITUD_REGISTRO = 160;
    private final static int LONGITUD_MATRICULA = 20;
    private final static int LONGITUD_CAPACIDADMAXIMA = 10;
    private final static int LONGITUD_NUMEROREGMEC = 20;
    private final static int LONGITUD_FECHAREGMERC = 20;
    private final static int LONGITUD_NOMBRE = 40;
    private final static int LONGITUD_ESTADO = 20;
    private final static int LONGITUD_TIPOBARCO = 30;

    private final static String ENCODING_FILE = System.getProperty("file.encoding");
    private final static Path archivo = Paths.get(NOMBRE_ARCHIVO);
    //private final static String ENCODING_WINDOWS = "ASCII";
    // Se utiliza la propiedad del sistema que indica la codificacion de los archivos
    //private static final String REGISTRO_ELIMINADO_TEXT = "99999999999999999999";

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
    public void guardarBarco(Barco barco) throws LlaveDuplicadaException {
        if (consultarBarcoPorMatricula(barco.getMatricula()) != null) {
            throw new LlaveDuplicadaException();
        }
        String registro = parseBarco2String(barco);
        byte[] datosRegistro = registro.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
        try (FileChannel fc = (FileChannel.open(archivo, APPEND))) {
            fc.write(byteBuffer);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


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
    public boolean actualizarBarco(Barco barco) {
        Set<StandardOpenOption> options = new HashSet<StandardOpenOption>();
        options.add(READ);
        options.add(WRITE);
        long lastPosition = 0;
        try (SeekableByteChannel sbc = Files.newByteChannel(archivo, options)) {
            ByteBuffer buf = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buf) > 0) {
                buf.rewind();
                CharBuffer registro = Charset.forName(ENCODING_FILE).decode(buf);
                String matricula = registro.subSequence(0, LONGITUD_MATRICULA).toString().trim();
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

    // Eliminar opcion 2
    @Override
    public boolean eliminarBarco(String matriculaBarco) {
        boolean res = false;
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
        util.eliminarYCrearArchivo(archivo);
        while (!barcos.isEmpty()) {
            Barco barcoAux = barcos.get(0);
            barcos.remove(0);
            if (barcoAux.getMatricula().equals(matriculaBarco)) {
                res = true;
            } else {
                String registro = parseBarco2String(barcoAux);
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


 /*//Eliminar opcion 1
 //En la que se remplaza el Id por uno generico para todos los registros eliminados.
 Set<StandardOpenOption> options = new HashSet<StandardOpenOption>();
        options.add(READ);
        options.add(WRITE);
        long lastPosition = 0;
        try (SeekableByteChannel sbc = Files.newByteChannel(archivo, options)) {
            ByteBuffer buf = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buf) > 0) {
                buf.rewind();
                CharBuffer registro = Charset.forName(ENCODING_FILE).decode(buf);
                String matricula = registro.subSequence(0, LONGITUD_MATRICULA).toString().trim();
                if (matricula.equals(matriculaBarco)) {
                    sbc.position(lastPosition);
                    sbc.write(ByteBuffer.wrap("REGISTRO_ELIMINADO_TEXT".getBytes()));

                    return true;
                }
                buf.flip();
                lastPosition = sbc.position();
            }
        } catch (IOException ioe) {
            System.out.println("caught exception: " + ioe);
        }
        return false;
  */

    private String parseBarco2String(Barco barco) {
        StringBuilder registro = new StringBuilder();
        registro.append(util.completarCampo(barco.getMatricula(), LONGITUD_MATRICULA));
        registro.append(util.completarCampo(barco.getCapacidadMax(), LONGITUD_CAPACIDADMAXIMA));
        registro.append(util.completarCampo(barco.getNumeroRegMerc(), LONGITUD_NUMEROREGMEC));
        registro.append(util.completarCampo(barco.getFechaRegMerc(), LONGITUD_FECHAREGMERC));
        registro.append(util.completarCampo(barco.getNombre(), LONGITUD_NOMBRE));
        registro.append(util.completarCampo(barco.getEstado(), LONGITUD_ESTADO));
        registro.append(util.completarCampo(barco.getTipoBarco(), LONGITUD_TIPOBARCO));
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

        String tipoBarco = registro.subSequence(0, LONGITUD_TIPOBARCO).toString().trim();
        barco.setTipoBarco(tipoBarco);

        return barco;
    }
}
