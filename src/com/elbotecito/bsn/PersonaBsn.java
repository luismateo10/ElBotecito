package com.elbotecito.bsn;

import com.elbotecito.bsn.exception.BarcoYaExisteException;
import com.elbotecito.bsn.exception.PersonaYaExisteException;
import com.elbotecito.dao.*;
import com.elbotecito.dao.exception.LlaveDuplicadaException;
import com.elbotecito.dao.impl.*;
import com.elbotecito.modelo.*;

import java.util.List;

public class PersonaBsn {


    private CapitanDAO capitanDAO;
    private MarineroDAO marineroDAO;
    private EsposaDAO esposaDAO;
    private HijoDAO hijoDAO;

    public PersonaBsn() {
        this.capitanDAO = new CapitanDAONIO();
        this.marineroDAO = new MarineroDAONIO();
        this.esposaDAO = new EsposaDAONIO();
        this.hijoDAO = new HijoDAONIO();
    }

    public void guardarCapitan(Capitan capitan) throws PersonaYaExisteException {
        try {
            this.capitanDAO.guardarCapitan(capitan);
        } catch (LlaveDuplicadaException lde) {
            throw new PersonaYaExisteException();
        }
    }

    public void guardarMarinero(Marinero marinero) throws PersonaYaExisteException {
        try {
            this.marineroDAO.guardarMarinero(marinero);
        } catch (LlaveDuplicadaException lde) {
            throw new PersonaYaExisteException();
        }
    }

    public void guardarEsposa(Esposa esposa) throws PersonaYaExisteException {
        try {
            this.esposaDAO.guardarEsposa(esposa);
        } catch (LlaveDuplicadaException lde) {
            throw new PersonaYaExisteException();
        }
    }

    public void guardarHijo(Hijo hijo) throws PersonaYaExisteException {
        try {
            this.hijoDAO.guardarHijo(hijo);
        } catch (LlaveDuplicadaException lde) {
            throw new PersonaYaExisteException();
        }
    }


    public Capitan consultarCapitan(String identificacion) {
        return this.capitanDAO.consultarCapitanPorIdentificacion(identificacion);
    }

    public Marinero consultarMarinero(String identificacion) {
        return this.marineroDAO.consultarMarineroPorIdentificacion(identificacion);
    }

    public Esposa consultarEsposa(String identificacion) {
        return this.esposaDAO.consultarEsposaPorIdentificacion(identificacion);
    }

    public Hijo consultarHijo(String identificacion) {
        return this.hijoDAO.consultarHijoPorIdentificacion(identificacion);
    }

    public List<Capitan> consultarCapitanes() {
        List<Capitan> listaCapitanes = this.capitanDAO.consultarCapitanes();
        for (Capitan persona:listaCapitanes) {
            if (persona.getSexo().equals("1")){
                persona.setSexo("HOMBRE");
            }else{
                persona.setSexo("MUJER");
            }
            if (persona.getEstadoVivo().equals("1")){
                persona.setEstadoVivo("VIVO");
            }else{
                persona.setEstadoVivo("MUERTO");
            }

        }
        return listaCapitanes;
    }

    public List<Marinero> consultarMarineros() {
        List<Marinero> listaMarineros = this.marineroDAO.consultarMarineros();
        for (Marinero persona:listaMarineros) {
            if (persona.getSexo().equals("1")){
                persona.setSexo("HOMBRE");
            }else{
                persona.setSexo("MUJER");
            }
            if (persona.getEstadoVivo().equals("1")){
                persona.setEstadoVivo("VIVO");
            }else{
                persona.setEstadoVivo("MUERTO");
            }

        }
        return listaMarineros;
    }

    public List<Esposa> consultarEsposas() {
        List<Esposa> listaEsposas = this.esposaDAO.consultarEsposas();
        for (Esposa persona:listaEsposas) {
            if (persona.getSexo().equals("1")){
                persona.setSexo("HOMBRE");
            }else{
                persona.setSexo("MUJER");
            }
            if (persona.getEstadoVivo().equals("1")){
                persona.setEstadoVivo("VIVO");
            }else{
                persona.setEstadoVivo("MUERTO");
            }

        }
        return listaEsposas;
    }

    public List<Hijo> consultarHijos() {
        List<Hijo> listaHijos = this.hijoDAO.consultarHijos();
        for (Hijo persona:listaHijos) {
            if (persona.getSexo().equals("1")){
                persona.setSexo("HOMBRE");
            }else{
                persona.setSexo("MUJER");
            }
            if (persona.getEstadoVivo().equals("1")){
                persona.setEstadoVivo("VIVO");
            }else{
                persona.setEstadoVivo("MUERTO");
            }

        }
        return listaHijos;
    }

    public boolean actualizarCapitan(Capitan capitan) {
        return this.capitanDAO.actualizarCapitan(capitan);

    }

    public boolean actualizarMarinero(Marinero marinero) {
        return this.marineroDAO.actualizarMarinero(marinero);
    }

    public boolean actualizarEsposa(Esposa esposa) {
        return this.esposaDAO.actualizarEsposa(esposa);
    }

    public boolean actualizarHijo(Hijo hijo) {
        return this.hijoDAO.actualizarHijo(hijo);
    }

    public boolean eliminarCapitan(String identificacion) {
        return this.capitanDAO.eliminarCapitan(identificacion);
    }

    public boolean eliminarMarinero(String identificacion) {
        return this.marineroDAO.eliminarMarinero(identificacion);
    }

    public boolean eliminarEsposa(String identificacion) {
        return this.esposaDAO.eliminarEsposa(identificacion);
    }

    public boolean eliminarHijo(String identificacion) {
        return this.hijoDAO.eliminarHijo(identificacion);
    }

}
