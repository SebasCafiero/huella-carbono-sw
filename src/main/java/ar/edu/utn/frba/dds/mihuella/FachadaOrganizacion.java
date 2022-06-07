package ar.edu.utn.frba.dds.mihuella;

import ar.edu.utn.frba.dds.mediciones.Parser;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaOrg;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;

import java.util.*;

public class FachadaOrganizacion implements FachadaOrg {
    private static Map<String, Float> factorEmisionMap;

    public FachadaOrganizacion() {
        factorEmisionMap  = new HashMap<String, Float>();
    }

    public FachadaOrganizacion(String archivo) throws Exception{
        factorEmisionMap = Parser.generarFE(archivo);
    }

    public FachadaOrganizacion(Map<String, Float> parametrosSistema) throws Exception {
        factorEmisionMap = parametrosSistema;
    }

    @Override
    public void cargarParametros(Map<String, Float> parametrosSistema) {
        factorEmisionMap.putAll(parametrosSistema);
    }

    @Override
    public Float obtenerHU(Collection<Medible> mediciones) throws Exception {
        Float huTotal = 0F;
        for(Medible medicion : mediciones){
            String categoria = medicion.getCategoria();

            try{
                huTotal = (this.factorEmisionMap.get(categoria) * medicion.getValor()) + huTotal;
                //System.out.println("Dato actividad " + categoria + ": " + medicion.getValor().toString();
            }
            catch (NullPointerException e){
                throw new Exception("FE de categoria no cargada");
            }

        }
        return huTotal;
    }

    public void setFactorEmision(String nombreFactor, Float valor) {
        factorEmisionMap.put(nombreFactor, valor);
    }

    public Float getFactorEmision(String nombreFactor) {
        return factorEmisionMap.get(nombreFactor);
    }

}
