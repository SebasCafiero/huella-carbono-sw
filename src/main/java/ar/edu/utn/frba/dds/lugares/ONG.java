package ar.edu.utn.frba.dds.lugares;

import ar.edu.utn.frba.dds.personas.Miembro;

import java.util.ArrayList;
import java.util.HashSet;

public class ONG extends Organizacion{

    public ONG(String razonSocial,
                   ClasificacionOrganizacion clasificacionOrganizacion,
                   String ubicacion) {
        this.razonSocial = razonSocial;
        this.tipo = TipoDeOrganizacionEnum.ONG;
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
            throw new Exception("El miembro no puede pertenecer a una ONG si trabaja en una Empresa");
        }
        else{
            sector.agregarMiembro(miembro);
            miembro.agregarSector(sector);
            sector.quitarPostulante(miembro);
        }
    }

    @Override
    public void rechazarSolicitud(Miembro miembro, Sector sector) throws Exception {
        //TODO
        sector.quitarPostulante(miembro);
    }

}
