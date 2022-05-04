package ar.edu.utn.frba.dds.lugares;

import ar.edu.utn.frba.dds.personas.Miembro;

import java.util.ArrayList;
import java.util.HashSet;

public class Gubernamental extends Organizacion{

    public Gubernamental(String razonSocial,
               ClasificacionOrganizacion clasificacionOrganizacion,
               String ubicacion) {
        this.razonSocial = razonSocial;
        this.tipo = TipoDeOrganizacionEnum.GUBERNAMENTAL;
        this.ubicacion = ubicacion;
        this.clasificacionOrganizacion = clasificacionOrganizacion;
        this.sectores = new HashSet<>();
        this.mediciones = new ArrayList<>();
    }

    @Override
    public void aceptarSolicitud(Miembro miembro, Sector sector) throws Exception {
        if(!this.sectores.contains(sector))
            throw new Exception("El sector no pertenece a la organizacion");
        for(Sector unSector : this.sectores) {
            if (unSector.esMiembro(miembro)) {
                throw new Exception("El miembro ya pertenece a la organizacion");
            }
        }

        if(miembro.trabajaEnTipoDeOrganizacion(TipoDeOrganizacionEnum.EMPRESA)){
            throw new Exception("El miembro no puede pertenecer a una Gubernamental si trabaja en una Empresa");
        }
        else{
            sector.agregarMiembro(miembro);
            miembro.agregarSector(sector);
            sector.quitarPostulante(miembro);
        }
    }


}
