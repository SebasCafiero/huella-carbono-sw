package ar.edu.utn.frba.dds.lugares;

import ar.edu.utn.frba.dds.personas.Miembro;

import java.util.ArrayList;
import java.util.HashSet;

public class Institucion extends Organizacion{

    public Institucion(String razonSocial,
               ClasificacionOrganizacion clasificacionOrganizacion,
               String ubicacion) {
        this.razonSocial = razonSocial;
        this.tipo = TipoDeOrganizacionEnum.INSTITUCION;
        this.ubicacion = ubicacion;
        this.clasificacionOrganizacion = clasificacionOrganizacion;
        this.sectores = new HashSet<>();
        this.mediciones = new ArrayList<>();
    }

    @Override
    public void aceptarSolicitud(Miembro miembro, Sector sector) throws Exception {
        super.aceptarSolicitud(miembro, sector);
    }

    @Override
    public void rechazarSolicitud(Miembro miembro, Sector sector) throws Exception {
        super.rechazarSolicitud(miembro, sector);
    }
}
