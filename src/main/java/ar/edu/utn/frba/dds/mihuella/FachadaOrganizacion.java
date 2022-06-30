package ar.edu.utn.frba.dds.mihuella;

import ar.edu.utn.frba.dds.mihuella.fachada.FachadaOrg;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import ar.edu.utn.frba.dds.repositories.RepoFactores;

import java.util.*;

public class FachadaOrganizacion implements FachadaOrg {
    private RepoFactores repoFactores;

    public FachadaOrganizacion() {
        repoFactores = RepoFactores.getInstance();
    }

    @Override
    public void cargarParametros(Map<String, Float> parametrosSistema) {
        this.repoFactores.putAll(parametrosSistema);
    }

    @Override
    public Float obtenerHU(Collection<Medible> mediciones) throws Exception {
        Float huTotal = 0F;
        for(Medible medicion : mediciones){
            String categoria = medicion.getCategoria();

            try{
                huTotal = (this.repoFactores.getValor(categoria) * medicion.getValor()) + huTotal;
                //System.out.println("Dato actividad " + categoria + ": " + medicion.getValor().toString();
            }
            catch (NullPointerException e){
                throw new Exception("FE de categoria no cargada");
            }

        }
        return huTotal;
    }

    public void setFactorEmision(String nombreFactor, Float valor) {
        this.repoFactores.setValor(nombreFactor, valor);
    }
}
