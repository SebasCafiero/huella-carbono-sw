package ar.edu.utn.frba.dds.mediciones;

import ar.edu.utn.frba.dds.mihuella.fachada.FachadaOrg;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;

import java.util.*;

public class CalculadoraHCOrganizacion implements FachadaOrg {
    private static Map<String, Float> factorEmisionMap;

    public CalculadoraHCOrganizacion() {
        factorEmisionMap  = new HashMap<String, Float>();
    }

    public CalculadoraHCOrganizacion(String archivo) throws Exception{
        factorEmisionMap = Parser.generarFE(archivo);
    }

    @Override
    public void cargarParametros(Map<String, Float> parametrosSistema) {
        factorEmisionMap.putAll(parametrosSistema);
    }

    @Override
    public Float obtenerHU(Collection<Medible> mediciones)
    {
        Float huTotal = 0F;
        for(Medible medicion : mediciones){
            String categoria = medicion.getCategoria();
            huTotal = (this.factorEmisionMap.get(categoria) * medicion.getValor()) + huTotal;
        }
        return huTotal;
    }
}
