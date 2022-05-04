package ar.edu.utn.frba.dds.lugares;

import ar.edu.utn.frba.dds.personas.Miembro;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static ar.edu.utn.frba.dds.personas.TipoDeDocumento.DNI;

public class OrganizacionAbsTest {

    // TEST SI TRABAJA EN ONG O GUBERNAMENTAL NO PUEDE EN EMPRESA
    @Test
    public void miembroNoEnONGYEmpresa() throws Exception {

        Miembro unMiembro = new Miembro("Pepito","Gomez",DNI,12345);
        Miembro otroMiembro = new Miembro("Pablito","Perez",DNI,54321);
        ONG unaONG = new ONG("miRS",ClasificacionOrganizacion.MINISTERIO,"CABA");
        Empresa unaEmpresa = new Empresa("miRS",ClasificacionOrganizacion.SECTORPRIMARIO,"CABA");

        Sector sectorONG = new Sector("sectorONG",unaONG);
        Sector sectorEmpresa = new Sector("sectorEmpresa",unaEmpresa);

        unMiembro.solicitarIngreso(sectorONG);
        unaONG.aceptarSolicitud(unMiembro,sectorONG);
        unMiembro.solicitarIngreso(sectorEmpresa);
        Assertions.assertThrows(Exception.class,() -> unaEmpresa.aceptarSolicitud(unMiembro,sectorEmpresa));

        otroMiembro.solicitarIngreso(sectorEmpresa);
        unaEmpresa.aceptarSolicitud(otroMiembro,sectorEmpresa);
        otroMiembro.solicitarIngreso(sectorONG);
        Assertions.assertThrows(Exception.class,() -> unaONG.aceptarSolicitud(otroMiembro,sectorONG));

    }

    @Test
    public void miembroEmpresaHastaSeisOrgs() throws Exception {
        Miembro unMiembro = new Miembro("Pepito","Gomez",DNI,12345);

        Empresa empresa1 = new Empresa("miRS",ClasificacionOrganizacion.SECTORPRIMARIO,"CABA");
        Empresa empresa2 = new Empresa("miRS",ClasificacionOrganizacion.SECTORPRIMARIO,"CABA");
        Empresa empresa3 = new Empresa("miRS",ClasificacionOrganizacion.SECTORPRIMARIO,"CABA");
        Empresa empresa4 = new Empresa("miRS",ClasificacionOrganizacion.SECTORPRIMARIO,"CABA");
        Empresa empresa5 = new Empresa("miRS",ClasificacionOrganizacion.SECTORPRIMARIO,"CABA");
        Empresa empresa6 = new Empresa("miRS",ClasificacionOrganizacion.SECTORPRIMARIO,"CABA");
        Empresa empresa7 = new Empresa("miRS",ClasificacionOrganizacion.SECTORPRIMARIO,"CABA");

        Sector sectorEmpresa1 = new Sector("sectorEmpresa",empresa1);
        Sector sectorEmpresa2 = new Sector("sectorEmpresa",empresa2);
        Sector sectorEmpresa3 = new Sector("sectorEmpresa",empresa3);
        Sector sectorEmpresa4 = new Sector("sectorEmpresa",empresa4);
        Sector sectorEmpresa5 = new Sector("sectorEmpresa",empresa5);
        Sector sectorEmpresa6 = new Sector("sectorEmpresa",empresa6);
        Sector sectorEmpresa7 = new Sector("sectorEmpresa",empresa7);

        unMiembro.solicitarIngreso(sectorEmpresa1);
        unMiembro.solicitarIngreso(sectorEmpresa2);
        unMiembro.solicitarIngreso(sectorEmpresa3);
        unMiembro.solicitarIngreso(sectorEmpresa4);
        unMiembro.solicitarIngreso(sectorEmpresa5);
        unMiembro.solicitarIngreso(sectorEmpresa6);
        unMiembro.solicitarIngreso(sectorEmpresa7);

        Assertions.assertEquals(0,unMiembro.cantidadDeTipoDeOrg(TipoDeOrganizacionEnum.EMPRESA));
        Assertions.assertEquals(0,unMiembro.getSectoresDondeTrabaja().size());

        empresa1.aceptarSolicitud(unMiembro,sectorEmpresa1);
        Assertions.assertEquals(1,unMiembro.cantidadDeTipoDeOrg(TipoDeOrganizacionEnum.EMPRESA));
        Assertions.assertEquals(1,unMiembro.getSectoresDondeTrabaja().size());

        empresa2.aceptarSolicitud(unMiembro,sectorEmpresa2);
        Assertions.assertEquals(2,unMiembro.cantidadDeTipoDeOrg(TipoDeOrganizacionEnum.EMPRESA));
        Assertions.assertEquals(2,unMiembro.getSectoresDondeTrabaja().size());

        empresa3.aceptarSolicitud(unMiembro,sectorEmpresa3);
        Assertions.assertEquals(3,unMiembro.cantidadDeTipoDeOrg(TipoDeOrganizacionEnum.EMPRESA));
        Assertions.assertEquals(3,unMiembro.getSectoresDondeTrabaja().size());

        empresa4.aceptarSolicitud(unMiembro,sectorEmpresa4);
        Assertions.assertEquals(4,unMiembro.cantidadDeTipoDeOrg(TipoDeOrganizacionEnum.EMPRESA));
        Assertions.assertEquals(4,unMiembro.getSectoresDondeTrabaja().size());

        empresa5.aceptarSolicitud(unMiembro,sectorEmpresa5);
        Assertions.assertEquals(5,unMiembro.cantidadDeTipoDeOrg(TipoDeOrganizacionEnum.EMPRESA));
        Assertions.assertEquals(5,unMiembro.getSectoresDondeTrabaja().size());

        empresa6.aceptarSolicitud(unMiembro,sectorEmpresa6);
        Assertions.assertEquals(6,unMiembro.cantidadDeTipoDeOrg(TipoDeOrganizacionEnum.EMPRESA));
        Assertions.assertEquals(6,unMiembro.getSectoresDondeTrabaja().size());

        Assertions.assertEquals(6,unMiembro.cantidadDeTipoDeOrg(TipoDeOrganizacionEnum.EMPRESA));
        Assertions.assertThrows(Exception.class,() -> empresa7.aceptarSolicitud(unMiembro,sectorEmpresa7));
    }


}
