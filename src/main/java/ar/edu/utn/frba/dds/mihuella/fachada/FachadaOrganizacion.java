package ar.edu.utn.frba.dds.mihuella.fachada;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.Sector;
import ar.edu.utn.frba.dds.mihuella.MedicionSinFactorEmisionException;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.repositories.RepoFactores;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;

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
            System.out.println("\nCategoria: " + categoria + ", valor: " + medicion.getValor().toString());
            System.out.println("Valor fe: " + this.repoFactores.getValor(categoria));

            try{
                huTotal = (this.repoFactores.getValor(categoria) * medicion.getValor()) + huTotal;
//                System.out.println("Dato actividad " + categoria + ": " + medicion.getValor().toString());
                System.out.println("DAxFE: " + medicion.getValor() + " x " + this.repoFactores.getValor(categoria) + " = " + huTotal);
            }
            catch (NullPointerException e){
                throw new MedicionSinFactorEmisionException(categoria);
            }

        }
        return huTotal;
    }

    //TODO la fachada deberia ser para una organizacion especifica, deberia ser atributo
    public Float obtenerConsumoTotalTrayectosOrganizacion(Organizacion unaOrg) throws MedicionSinFactorEmisionException {
        Float consumo = 0F;
        for(Trayecto unTrayecto : unaOrg.trayectosDeMiembros()) {
            consumo += obtenerHU(new ArrayList<>(unTrayecto.getTramos())) / unTrayecto.cantidadDeMiembros();
            System.out.println("CONSUMO CADA TRAYECTO DE ORG: " + consumo);
        }
        System.out.println("CONSUMO TRAYECTOS TOTAL DE ORG: " + consumo);
        return consumo;
    }

    public Float obtenerImpactoMiembroEnTrayectos(Organizacion unaOrg, Miembro unMiembro) throws MedicionSinFactorEmisionException {
        Float consumoMiembro = 0F;
        for(Trayecto unTrayecto : unMiembro.getTrayectos()){
            consumoMiembro += obtenerHU(new ArrayList<>(unTrayecto.getTramos())) / unTrayecto.cantidadDeMiembros();
            System.out.println("Consumo Miembro " + unMiembro.getNroDocumento() + " Cada Trayecto: " + consumoMiembro);
        }
        System.out.println("Consumo Miembro " + unMiembro.getNroDocumento() + " Total: " + consumoMiembro);
        return consumoMiembro / obtenerConsumoTotalTrayectosOrganizacion(unaOrg);
    }

    public Float obtenerImpactoSector(Organizacion unaOrg, Sector unSector) throws MedicionSinFactorEmisionException {
        Float impactoMiembro = 0F;
        for(Miembro unMiembro : unSector.getListaDeMiembros()){
            impactoMiembro += obtenerImpactoMiembroEnTrayectos(unaOrg,unMiembro);
        }
        return impactoMiembro;
    }


    private float calcularValor(Medible medicion){
        return medicion.getValor();
    }

    public void setFactorEmision(String nombreFactor, Float valor) {
        this.repoFactores.setValor(nombreFactor, valor);
    }

//    public List<Medible> obtenerMediblesOrganizacion
}
