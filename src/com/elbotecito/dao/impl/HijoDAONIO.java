package com.elbotecito.dao.impl;

import com.elbotecito.dao.HijoDAO;
import com.elbotecito.dao.exception.LlaveDuplicadaException;
import com.elbotecito.modelo.Barco;
import com.elbotecito.modelo.Hijo;
import com.elbotecito.modelo.Hijo;
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

public class HijoDAONIO implements HijoDAO {
    private final static String NOMBRE_ARCHIVO = "Hijos"; //Kebab case
    private final static int LONGITUD_REGISTRO = 102;
    private final static int LONGITUD_IDENTIFICACION = 20;
    private final static int LONGITUD_NOMBRE = 40;
    private final static int LONGITUD_SEXO = 1;
    private final static int LONGITUD_ESTADOVIVO = 1;
    private final static int LONGITUD_IDPADRE = 20;
    private final static int LONGITUD_PORCHERENCIA = 20;


    private final static String ENCODING_FILE = System.getProperty("file.encoding");
    private final static Path archivo = Paths.get(NOMBRE_ARCHIVO);

    public HijoDAONIO() {
        if (!Files.exists(archivo)) {
            try {
                Files.createFile(archivo);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    @Override
    public void guardarHijo(Hijo hijo) throws LlaveDuplicadaException {
        if (consultarHijoPorIdentificacion(hijo.getIdentificacion()) != null) {
            throw new LlaveDuplicadaException();
        }
        String registro = parseHijo2String(hijo);
        byte[] datosRegistro = registro.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
        try (FileChannel fc = (FileChannel.open(archivo, APPEND))) {
            fc.write(byteBuffer);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    @Override
    public Hijo consultarHijoPorIdentificacion(String idHijo) {
        try (SeekableByteChannel sbc = Files.newByteChannel(archivo)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.forName(ENCODING_FILE).decode(buffer);
                Hijo hijoConvertido = parseHijoRegistro2Objeto(registro);
                if (hijoConvertido.getIdentificacion().equals(idHijo)) {
                    return hijoConvertido;
                }
                buffer.flip();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Hijo> consultarHijos() {
        List<Hijo> hijos = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(archivo)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.forName(ENCODING_FILE).decode(buffer);
                Hijo hijoConvertido = parseHijoRegistro2Objeto(registro);
                buffer.flip();
                hijos.add(hijoConvertido);
            }
        } catch (IOException x) {
            System.out.println("caught exception: " + x);
        }
        return hijos;
    }

    @Override
    public boolean actualizarHijo(Hijo hijo) {
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
                if (matricula.equals(hijo.getIdentificacion())) {
                    sbc.position(lastPosition);
                    sbc.write(ByteBuffer.wrap(parseHijo2String(hijo).getBytes()));
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
    public boolean eliminarHijo(String idHijo) {
        boolean res = false;
        List<Hijo> hijos = new ArrayList<>();
        try (SeekableByteChannel sbc = Files.newByteChannel(archivo)) {
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer) > 0) {
                buffer.rewind();
                CharBuffer registro = Charset.forName(ENCODING_FILE).decode(buffer);
                Hijo hijoConvertido = parseHijoRegistro2Objeto(registro);
                buffer.flip();
                hijos.add(hijoConvertido);
            }
        } catch (IOException x) {
            System.out.println("caught exception: " + x);
        }
        util.eliminarYCrearArchivo(archivo);
        while (!hijos.isEmpty()) {
            Hijo hijoAux = hijos.get(0);
            hijos.remove(0);
            if (hijoAux.getIdentificacion().equals(idHijo)) {
                res = true;
            } else {
                String registro = parseHijo2String(hijoAux);
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

    private String parseHijo2String(Hijo hijo) {
        StringBuilder registro = new StringBuilder();
        registro.append(util.completarCampo(hijo.getIdentificacion(), LONGITUD_IDENTIFICACION));
        registro.append(util.completarCampo(hijo.getNombre(), LONGITUD_NOMBRE));
        registro.append(util.completarCampo(hijo.getSexo(), LONGITUD_SEXO));
        registro.append(util.completarCampo(hijo.getEstadoVivo(), LONGITUD_ESTADOVIVO));
        registro.append(util.completarCampo(hijo.getIdPadre(), LONGITUD_IDPADRE));
        registro.append(util.completarCampo(hijo.getPorcHerencia(), LONGITUD_PORCHERENCIA));

        return registro.toString();
    }

    private Hijo parseHijoRegistro2Objeto(CharBuffer registro) {
        Hijo hijo = new Hijo();
        String identificacion = registro.subSequence(0, LONGITUD_IDENTIFICACION).toString().trim();
        hijo.setIdentificacion(identificacion);
        registro.position(LONGITUD_IDENTIFICACION);
        registro = registro.slice();

        String nombre = registro.subSequence(0, LONGITUD_NOMBRE).toString().trim();
        hijo.setNombre(nombre);
        registro.position(LONGITUD_NOMBRE);
        registro = registro.slice();

        String sexo = registro.subSequence(0, LONGITUD_SEXO).toString().trim();
        hijo.setSexo(sexo);
        registro.position(LONGITUD_SEXO);
        registro = registro.slice();

        String estadoVivo = registro.subSequence(0, LONGITUD_ESTADOVIVO).toString().trim();
        hijo.setEstadoVivo(estadoVivo);
        registro.position(LONGITUD_ESTADOVIVO);
        registro = registro.slice();

        String idPadre = registro.subSequence(0, LONGITUD_IDPADRE).toString().trim();
        hijo.setIdPadre(idPadre);
        registro.position(LONGITUD_IDPADRE);
        registro = registro.slice();

        String porcHerencia = registro.subSequence(0, LONGITUD_PORCHERENCIA).toString().trim();
        hijo.setPorcHerencia(porcHerencia);

        return hijo;
    }
}
