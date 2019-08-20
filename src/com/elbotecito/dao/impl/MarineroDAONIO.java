package com.elbotecito.dao.impl;

import com.elbotecito.dao.MarineroDAO;
import com.elbotecito.dao.exception.LlaveDuplicadaException;
import com.elbotecito.modelo.Barco;
import com.elbotecito.modelo.Marinero;
import com.elbotecito.modelo.Marinero;
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

public class MarineroDAONIO implements MarineroDAO {
    private final static String NOMBRE_ARCHIVO = "Marineros"; //Kebab case
    private final static int LONGITUD_REGISTRO = 82;
    private final static int LONGITUD_IDENTIFICACION = 20;
    private final static int LONGITUD_NOMBRE = 40;
    private final static int LONGITUD_SEXO = 1;
    private final static int LONGITUD_ESTADOVIVO = 1;
    private final static int LONGITUD_IDRUTA = 20;


    private final static String ENCODING_FILE = System.getProperty("file.encoding");
    private final static Path archivo = Paths.get(NOMBRE_ARCHIVO);

    public MarineroDAONIO() {
        if (!Files.exists(archivo)) {
            try {
                Files.createFile(archivo);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    @Override
    public void guardarMarinero(Marinero marinero) throws LlaveDuplicadaException {
        if (consultarMarineroPorIdentificacion(marinero.getIdentificacion()) != null) {
            throw new LlaveDuplicadaException();
        }
        String registro = parseMarinero2String(marinero);
        byte[] datosRegistro = registro.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
        try (FileChannel fc = (FileChannel.open(archivo, APPEND))) {
            fc.write(byteBuffer);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    @Override
    public Marinero consultarMarineroPorIdentificacion(String idMarinero) {
        try (SeekableByteChannel sbc = Files.newByteChannel(archivo)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.forName(ENCODING_FILE).decode(buffer);
                Marinero marineroConvertido = parseMarineroRegistro2Objeto(registro);
                if (marineroConvertido.getIdentificacion().equals(idMarinero)) {
                    return marineroConvertido;
                }
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Marinero> consultarMarineros() {
        List<Marinero> marineros = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(archivo)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.forName(ENCODING_FILE).decode(buffer);
                Marinero marineroConvertido = parseMarineroRegistro2Objeto(registro);
                buffer.flip();
                marineros.add(marineroConvertido);
            }
        } catch (IOException x) {
            System.out.println("caught exception: " + x);
        }
        return marineros;
    }

    @Override
    public boolean actualizarMarinero(Marinero marinero) {
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
                if (matricula.equals(marinero.getIdentificacion())) {
                    sbc.position(lastPosition);
                    sbc.write(ByteBuffer.wrap(parseMarinero2String(marinero).getBytes()));
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
    public boolean eliminarMarinero(String idMarinero) {
        boolean res = false;
        List<Marinero> marineros = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(archivo)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.forName(ENCODING_FILE).decode(buffer);
                Marinero marineroConvertido = parseMarineroRegistro2Objeto(registro);
                buffer.flip();
                marineros.add(marineroConvertido);
            }
        } catch (IOException x) {
            System.out.println("caught exception: " + x);
        }
        util.eliminarYCrearArchivo(archivo);
        while (!marineros.isEmpty()) {
            Marinero marineroAux = marineros.get(0);
            marineros.remove(0);
            if (marineroAux.getIdentificacion().equals(idMarinero)) {
                res = true;
            } else {
                String registro = parseMarinero2String(marineroAux);
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

    private String parseMarinero2String(Marinero marinero) {
        StringBuilder registro = new StringBuilder();
        registro.append(util.completarCampo(marinero.getIdentificacion(), LONGITUD_IDENTIFICACION));
        registro.append(util.completarCampo(marinero.getNombre(), LONGITUD_NOMBRE));
        registro.append(util.completarCampo(marinero.getSexo(), LONGITUD_SEXO));
        registro.append(util.completarCampo(marinero.getEstadoVivo(), LONGITUD_ESTADOVIVO));
        registro.append(util.completarCampo(marinero.getIdRuta(), LONGITUD_IDRUTA));

        return registro.toString();
    }

    private Marinero parseMarineroRegistro2Objeto(CharBuffer registro) {
        Marinero marinero = new Marinero();
        String identificacion = registro.subSequence(0, LONGITUD_IDENTIFICACION).toString().trim();
        marinero.setIdentificacion(identificacion);
        registro.position(LONGITUD_IDENTIFICACION);
        registro = registro.slice();

        String nombre = registro.subSequence(0, LONGITUD_NOMBRE).toString().trim();
        marinero.setNombre(nombre);
        registro.position(LONGITUD_NOMBRE);
        registro = registro.slice();

        String sexo = registro.subSequence(0, LONGITUD_SEXO).toString().trim();
        marinero.setSexo(sexo);
        registro.position(LONGITUD_SEXO);
        registro = registro.slice();

        String estadoVivo = registro.subSequence(0, LONGITUD_ESTADOVIVO).toString().trim();
        marinero.setEstadoVivo(estadoVivo);
        registro.position(LONGITUD_ESTADOVIVO);
        registro = registro.slice();

        String idRuta = registro.subSequence(0, LONGITUD_IDRUTA).toString().trim();
        marinero.setIdRuta(idRuta);

        return marinero;
    }
}
