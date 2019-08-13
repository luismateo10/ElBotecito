package com.elbotecito.dao.impl;

import com.elbotecito.dao.RutaDAO;
import com.elbotecito.dao.exception.LlaveDuplicadaException;
import com.elbotecito.modelo.Barco;
import com.elbotecito.modelo.Ruta;
import com.elbotecito.modelo.Ruta;
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

public class RutaDAONIO implements RutaDAO {
    private final static String NOMBRE_ARCHIVO = "Rutas"; //Kebab case
    private final static int LONGITUD_REGISTRO = 150;
    private final static int LONGITUD_NUMERO = 20;
    private final static int LONGITUD_NOMBRE = 10;
    private final static int LONGITUD_SEXO = 20;
    private final static int LONGITUD_ESTADOVIVO = 20;
    private final static int LONGITUD_IDRUTA = 40;
    private final static int LONGITUD_NUMHIJOS = 20;
    private final static int LONGITUD_NUMESPOSAS = 20;
    private final static int LONGITUD_FORTUNA = 20;


    private final static String ENCODING_FILE = System.getProperty("file.encoding");
    private final static Path archivo = Paths.get(NOMBRE_ARCHIVO);

    public RutaDAONIO() {
        if (!Files.exists(archivo)) {
            try {
                Files.createFile(archivo);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    @Override
    public void guardarRuta(Ruta ruta) throws LlaveDuplicadaException {
        if (consultarRutaPorIdentificacion(ruta.getNumero()) != null) {
            throw new LlaveDuplicadaException();
        }
        String registro = parseRuta2String(ruta);
        byte[] datosRegistro = registro.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
        try (FileChannel fc = (FileChannel.open(archivo, APPEND))) {
            fc.write(byteBuffer);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    @Override
    public Ruta consultarRutaPorIdentificacion(String idRuta) {
        try (SeekableByteChannel sbc = Files.newByteChannel(archivo)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.forName(ENCODING_FILE).decode(buffer);
                Ruta rutaConvertido = parseRutaRegistro2Objeto(registro);
                if (rutaConvertido.getNumero().equals(idRuta)) {
                    return rutaConvertido;
                }
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ruta> consultarRutas() {
        List<Ruta> rutas = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(archivo)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.forName(ENCODING_FILE).decode(buffer);
                Ruta rutaConvertido = parseRutaRegistro2Objeto(registro);
                buffer.flip();
                rutas.add(rutaConvertido);
            }
        } catch (IOException x) {
            System.out.println("caught exception: " + x);
        }
        return rutas;
    }

    @Override
    public boolean actualizarRuta(Ruta ruta) {
        Set<StandardOpenOption> options = new HashSet<StandardOpenOption>();
        options.add(READ);
        options.add(WRITE);
        long lastPosition = 0;
        try (SeekableByteChannel sbc = Files.newByteChannel(archivo, options)) {
            ByteBuffer buf = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buf) > 0) {
                buf.rewind();
                CharBuffer registro = Charset.forName(ENCODING_FILE).decode(buf);
                String matricula = registro.subSequence(0, LONGITUD_NUMERO).toString().trim();
                if (matricula.equals(ruta.getNumero())) {
                    sbc.position(lastPosition);
                    sbc.write(ByteBuffer.wrap(parseRuta2String(ruta).getBytes()));
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
    public boolean eliminarRuta(String idRuta) {
        boolean res = false;
        List<Ruta> rutas = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(archivo)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.forName(ENCODING_FILE).decode(buffer);
                Ruta rutaConvertido = parseRutaRegistro2Objeto(registro);
                buffer.flip();
                rutas.add(rutaConvertido);
            }
        } catch (IOException x) {
            System.out.println("caught exception: " + x);
        }
        util.eliminarYCrearArchivo(archivo);
        while (!rutas.isEmpty()) {
            Ruta rutaAux = rutas.get(0);
            rutas.remove(0);
            if (rutaAux.getNumero().equals(idRuta)) {
                res = true;
            } else {
                String registro = parseRuta2String(rutaAux);
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

    private String parseRuta2String(Ruta ruta) {
        StringBuilder registro = new StringBuilder();
        registro.append(util.completarCampo(ruta.getNumero(), LONGITUD_NUMERO));

        return registro.toString();
    }

    private Ruta parseRutaRegistro2Objeto(CharBuffer registro) {
        Ruta ruta = new Ruta();
        String numero = registro.subSequence(0, LONGITUD_NUMERO).toString().trim();
        ruta.setNumero(numero);
        registro.position(LONGITUD_NUMERO);
        registro = registro.slice();

        String fortuna = registro.subSequence(0, LONGITUD_FORTUNA).toString().trim();
        ruta.setIdCapitan(fortuna);

        return ruta;
    }
}
