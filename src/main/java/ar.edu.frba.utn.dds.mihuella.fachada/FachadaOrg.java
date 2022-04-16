package ar.edu.frba.utn.dds.mihuella.fachada;

import java.util.Collection;
import java.util.Map;

public interface FachadaOrg {

    void cargarParametros(Map<String, Float> parametrosSistema);

    Float obtenerHU(Collection<Medible> mediciones);
}