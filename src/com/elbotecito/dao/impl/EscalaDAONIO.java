package com.elbotecito.dao.impl;

import com.elbotecito.dao.EscalaDAO;
import com.elbotecito.dao.exception.LlaveDuplicadaException;
import com.elbotecito.modelo.Barco;
import com.elbotecito.modelo.Escala;
import com.elbotecito.modelo.Escala;
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


//todo revisar esta clase que es especial de clave compuesta
public class EscalaDAONIO implements EscalaDAO {
    private final static String NOMBRE_ARCHIVO = "Escalaes"; //Kebab case
    private final static int LONGITUD_REGISTRO = 150;
    private final static int LONGITUD_IDENTIFICACION = 20;
    private final static int LONGITUD_NOMBRE = 10;
    private final static int LONGITUD_SEXO = 20;
    private final static int LONGITUD_ESTADOVIVO = 20;
    private final static int LONGITUD_IDRUTA = 40;
    private final static int LONGITUD_NUMHIJOS = 20;
    private final static int LONGITUD_NUMESPOSAS = 20;
    private final static int LONGITUD_FORTUNA = 20;


    private final static String ENCODING_FILE = System.getProperty("file.encoding");
    private final static Path archivo = Paths.get(NOMBRE_ARCHIVO);

    public EscalaDAONIO() {
        if (!Files.exists(archivo)) {
            try {
                Files.createFile(archivo);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    @Override
    public void guardarEscala(Escala escala) {

        String registro = parseEscala2String(escala);
        byte[] datosRegistro = registro.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
        try (FileChannel fc = (FileChannel.open(archivo, APPEND))) {
            fc.write(byteBuffer);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    @Override
    public List<Escala> consultarEscalaPorIdRuta(String idRuta) {
        List<Escala> escalas = new ArrayList<>();

        try (SeekableByteChannel sbc = Files.newByteChannel(archivo)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.forName(ENCODING_FILE).decode(buffer);
                Escala escalaConvertido = parseEscalaRegistro2Objeto(registro);
                if (escalaConvertido.getIdRuta().equals(idRuta)) {
                    escalas.add(escalaConvertido);
                }
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return escalas;
    }

    @Override
    public List<Escala> consultarEscalas() {
        List<Escala> escalas = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(archivo)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.forName(ENCODING_FILE).decode(buffer);
                Escala escalaConvertido = parseEscalaRegistro2Objeto(registro);
                buffer.flip();
                escalas.add(escalaConvertido);
            }
        } catch (IOException x) {
            System.out.println("caught exception: " + x);
        }
        return escalas;
    }

    @Override
    public boolean actualizarEscala(Escala escala) {
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
                if (matricula.equals(escala.getIdRuta())) {
                    sbc.position(lastPosition);
                    sbc.write(ByteBuffer.wrap(parseEscala2String(escala).getBytes()));
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
    public boolean eliminarEscala(String idEscala) {
        boolean res = false;
        List<Escala> escalas = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(archivo)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.forName(ENCODING_FILE).decode(buffer);
                Escala escalaConvertido = parseEscalaRegistro2Objeto(registro);
                buffer.flip();
                escalas.add(escalaConvertido);
            }
        } catch (IOException x) {
            System.out.println("caught exception: " + x);
        }
        util.eliminarYCrearArchivo(archivo);
        while (!escalas.isEmpty()) {
            Escala escalaAux = escalas.get(0);
            escalas.remove(0);
            if (escalaAux.getIdRuta().equals(idEscala)) {
                res = true;
            } else {
                String registro = parseEscala2String(escalaAux);
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

    private String parseEscala2String(Escala escala) {
        StringBuilder registro = new StringBuilder();
        registro.append(util.completarCampo(escala.getIdRuta(), LONGITUD_IDENTIFICACION));

        return registro.toString();
    }

    private Escala parseEscalaRegistro2Objeto(CharBuffer registro) {
        Escala escala = new Escala();
        String identificacion = registro.subSequence(0, LONGITUD_IDENTIFICACION).toString().trim();
        escala.setIdRuta(identificacion);
        registro.position(LONGITUD_IDENTIFICACION);
        registro = registro.slice();

        String fortuna = registro.subSequence(0, LONGITUD_FORTUNA).toString().trim();
        escala.setIdPuerto(fortuna);

        return escala;
    }
}

