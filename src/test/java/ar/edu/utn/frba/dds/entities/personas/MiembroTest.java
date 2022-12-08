package ar.edu.utn.frba.dds.entities.personas;

import ar.edu.utn.frba.dds.entities.exceptions.MiembroException;
import ar.edu.utn.frba.dds.entities.exceptions.SectorException;
import ar.edu.utn.frba.dds.entities.lugares.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MiembroTest {
    @Test
    public void vinculoMiembroConOrganizacionYLoQuito() throws SectorException, MiembroException {
        Organizacion unaOrg = new Organizacion("miRazonSocial",
                TipoDeOrganizacionEnum.EMPRESA,
                new ClasificacionOrganizacion("Sector secundario"),
                buildUbicacion());

        Sector unSector = new Sector("miSector",unaOrg);

        Miembro unMiembro = new Miembro("jose", "pepito",TipoDeDocumento.DNI,12345);

        unMiembro.solicitarIngresoAlSector(unSector);

        Assertions.assertEquals(1, unMiembro.cantidadDeSectoresDondeTrabaja());
        Assertions.assertEquals(1, unMiembro.cantidadDeOrganizacionesDondeTrabaja());
        Assertions.assertTrue(unSector.esMiembro(unMiembro));
        Assertions.assertTrue(unMiembro.trabajaEnSector(unSector));

        unSector.quitarMiembro(unMiembro);

        Assertions.assertEquals(0, unMiembro.cantidadDeSectoresDondeTrabaja());
        Assertions.assertEquals(0, unMiembro.cantidadDeOrganizacionesDondeTrabaja());
        Assertions.assertFalse(unSector.esMiembro(unMiembro));
        Assertions.assertFalse(unMiembro.trabajaEnSector(unSector));
    }

    @Test
    public void vinculoMiembroConMismoSector() throws SectorException, MiembroException {
        Organizacion unaOrg = new Organizacion("miRazonSocial",
                TipoDeOrganizacionEnum.EMPRESA,
                new ClasificacionOrganizacion("Sector secundario"),
                buildUbicacion());

        Sector unSector = new Sector("miSector",unaOrg);
        Miembro unMiembro = new Miembro("jose", "pepito",TipoDeDocumento.DNI,12345);

        unMiembro.solicitarIngresoAlSector(unSector);

        Assertions.assertThrows(MiembroException.class, () -> unMiembro.solicitarIngresoAlSector(unSector));
    }

    @Test
    public void vinculoVariosMiembros() throws Exception{
        Organizacion unaOrg = new Organizacion("miRazonSocial",
                TipoDeOrganizacionEnum.EMPRESA,
                new ClasificacionOrganizacion("Organizacion"),
                buildUbicacion());

        Sector unSector = new Sector("miSector",unaOrg);
        Miembro miembro1 = new Miembro("jose", "pepito",TipoDeDocumento.DNI,12345);
        Miembro miembro2 = new Miembro("pepe", "gomez",TipoDeDocumento.DNI,54321);
        Miembro miembro3 = new Miembro("mario", "lopez",TipoDeDocumento.DNI,11111);

        Assertions.assertEquals(0, unSector.cantidadMiembros());

        miembro1.solicitarIngresoAlSector(unSector);
        miembro2.solicitarIngresoAlSector(unSector);
        miembro3.solicitarIngresoAlSector(unSector);

        Assertions.assertEquals(3, unSector.cantidadMiembros());
    }

    private static UbicacionGeografica buildUbicacion() {
        return new UbicacionGeografica(new Direccion(new Municipio("CABA", new Provincia("CABA", "Argentina")),
                "Mataderos", "Murguiondo", 2211), new Coordenada(10F, 5F));
    }

}
