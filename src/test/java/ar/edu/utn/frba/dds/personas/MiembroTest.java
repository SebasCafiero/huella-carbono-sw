package ar.edu.utn.frba.dds.personas;

import ar.edu.utn.frba.dds.lugares.ClasificacionOrganizacion;
import ar.edu.utn.frba.dds.lugares.Organizacion;
import ar.edu.utn.frba.dds.lugares.Sector;
import ar.edu.utn.frba.dds.lugares.TipoDeOrganizacionEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class MiembroTest {

//TODO VER DE USAR @BEFORE O AGRUPAR DECLARACIONES REPETIDAS

    @Test
    public void vinculoMiembroConOrganizacion() throws Exception {

        Organizacion unaOrg = new Organizacion("miRazonSocial",
                TipoDeOrganizacionEnum.EMPRESA,
                new ClasificacionOrganizacion("Sector Primario", new String[]{}),
                "miUbicacion");
        Sector unSector = new Sector("miSector",unaOrg);

        Miembro miembro1 = new Miembro("jose", "pepito",TipoDeDocumento.DNI,12345);

        miembro1.solicitarIngreso(unSector);

        Assertions.assertEquals(0,miembro1.cantidadDeSectoresDondeTrabaja());
        Assertions.assertEquals(0,miembro1.cantidadDeOrganizacionesDondeTrabaja());
        Assertions.assertEquals(false,unSector.esMiembro(miembro1));
        Assertions.assertEquals(true,unSector.esPostulante(miembro1));
        Assertions.assertEquals(false,miembro1.trabajaEnSector(unSector));

        unaOrg.aceptarSolicitud(miembro1,unSector);

        Assertions.assertEquals(1,miembro1.cantidadDeSectoresDondeTrabaja());
        Assertions.assertEquals(1,miembro1.cantidadDeOrganizacionesDondeTrabaja());
        Assertions.assertEquals(true,unSector.esMiembro(miembro1));
        Assertions.assertEquals(false,unSector.esPostulante(miembro1));
        Assertions.assertEquals(true,miembro1.trabajaEnSector(unSector));

    }

    @Test
    public void rechazoVinculoMiembroConOrganizacion() throws Exception {

        Organizacion unaOrg = new Organizacion("miRazonSocial",
                TipoDeOrganizacionEnum.EMPRESA,
                new ClasificacionOrganizacion("Sector Secundario", new String[]{}),
                "miUbicacion");
        Sector unSector = new Sector("miSector",unaOrg);

        Miembro unMiembro = new Miembro("jose", "pepito",TipoDeDocumento.DNI,12345);

        unMiembro.solicitarIngreso(unSector);

        Assertions.assertEquals(0,unMiembro.cantidadDeSectoresDondeTrabaja());
        Assertions.assertEquals(0,unMiembro.cantidadDeOrganizacionesDondeTrabaja());
        Assertions.assertEquals(false,unSector.esMiembro(unMiembro));
        Assertions.assertEquals(true,unSector.esPostulante(unMiembro));
        Assertions.assertEquals(false,unMiembro.trabajaEnSector(unSector));

        unaOrg.rechazarSolicitud(unMiembro,unSector);

        Assertions.assertEquals(0,unMiembro.cantidadDeSectoresDondeTrabaja());
        Assertions.assertEquals(0,unMiembro.cantidadDeOrganizacionesDondeTrabaja());
        Assertions.assertEquals(false,unSector.esMiembro(unMiembro));
        Assertions.assertEquals(false,unSector.esPostulante(unMiembro));
        Assertions.assertEquals(false,unMiembro.trabajaEnSector(unSector));

    }

    @Test
    public void vinculoMiembroConMismoSector() throws Exception {
        Organizacion unaOrg = new Organizacion("miRazonSocial",
                TipoDeOrganizacionEnum.EMPRESA,
                new ClasificacionOrganizacion("Sector Secundario", new String[]{}),
                "miUbicacion");
        Sector unSector = new Sector("miSector",unaOrg);
        Miembro unMiembro = new Miembro("jose", "pepito",TipoDeDocumento.DNI,12345);

        unMiembro.solicitarIngreso(unSector);
        unaOrg.aceptarSolicitud(unMiembro,unSector);

        Assertions.assertThrows(Exception.class,() -> unMiembro.solicitarIngreso(unSector));

    }

    @Test
    public void vinculoVariosMiembros() throws Exception{
        Organizacion unaOrg = new Organizacion("miRazonSocial",
                TipoDeOrganizacionEnum.EMPRESA,
                new ClasificacionOrganizacion("Sector Secundario", new String[]{}),
                "miUbicacion");
        Sector unSector = new Sector("miSector",unaOrg);
        Miembro miembro1 = new Miembro("jose", "pepito",TipoDeDocumento.DNI,12345);
        Miembro miembro2 = new Miembro("pepe", "gomez",TipoDeDocumento.DNI,54321);
        Miembro miembro3 = new Miembro("mario", "lopez",TipoDeDocumento.DNI,11111);

        Assertions.assertEquals(0,unSector.cantidadMiembros());

        miembro1.solicitarIngreso(unSector);
        miembro2.solicitarIngreso(unSector);
        miembro3.solicitarIngreso(unSector);

        unaOrg.aceptarSolicitud(miembro1,unSector);
        unaOrg.aceptarSolicitud(miembro2,unSector);
        unaOrg.aceptarSolicitud(miembro3,unSector);

        Assertions.assertEquals(3,unSector.cantidadMiembros());
        
    }

}
