package ar.edu.utn.frba.dds.servicios.fachadas;

import ar.edu.utn.frba.dds.entities.medibles.Medible;
import ar.edu.utn.frba.dds.servicios.fachadas.exceptions.MedicionSinFactorEmisionException;

import java.util.Collection;
import java.util.Map;

public interface FachadaOrg {

    void cargarParametros(Map<String, Float> parametrosSistema);

    Float obtenerHU(Collection<Medible> mediciones) throws Exception, MedicionSinFactorEmisionException;
}