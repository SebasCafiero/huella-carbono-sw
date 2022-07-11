package ar.edu.utn.frba.dds.mihuella.fachada;

import ar.edu.utn.frba.dds.mihuella.MedicionSinFactorEmisionException;
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
    public Float obtenerHU(Collection<Medible> mediciones) throws MedicionSinFactorEmisionException {
        Float huTotal = 0F;
        for(Medible medicion : mediciones){
            String categoria = medicion.getCategoria();
            System.out.println("Categoria: " + categoria + ", valor: " + medicion.getValor().toString());
            System.out.println("Valor fe: " + this.repoFactores.getValor(categoria));

            try{
                huTotal = (this.repoFactores.getValor(categoria) * medicion.getValor()) + huTotal;
                System.out.println("Dato actividad " + categoria + ": " + medicion.getValor().toString());
            }
            catch (NullPointerException e){
                throw new MedicionSinFactorEmisionException(categoria);
            }

        }
        return huTotal;
    }

    private float calcularValor(Medible medicion){
        return medicion.getValor();
    }

    public void setFactorEmision(String nombreFactor, Float valor) {
        this.repoFactores.setValor(nombreFactor, valor);
    }

//    public List<Medible> obtenerMediblesOrganizacion
}
