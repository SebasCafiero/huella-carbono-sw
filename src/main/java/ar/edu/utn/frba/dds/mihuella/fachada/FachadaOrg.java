package ar.edu.utn.frba.dds.mihuella.fachada;

import java.util.Collection;
import java.util.Map;

public interface FachadaOrg {

    void cargarParametros(Map<String, Float> parametrosSistema);

    Float obtenerHU(Collection<Medible> mediciones) throws Exception;
}