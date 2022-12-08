package ar.edu.utn.frba.dds.entities.lugares;

import ar.edu.utn.frba.dds.entities.exceptions.SectorException;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrganizacionTest {

    @Test
    public void unaOrganizacionNoPuedeRepetirSector() {
        Organizacion unaOrg = buildOrganizacion();
        Sector sistemas = new Sector("Sistemas", unaOrg);

        Assertions.assertThrows(SectorException.class,
                () -> unaOrg.agregarSector(sistemas),
                "El sector ya pertenece a la organización");
    }

    @Test
    public void agregarSectoresAUnaOrganizacion() {
        Organizacion unaOrg = buildOrganizacion();

        Assertions.assertEquals(0, unaOrg.cantidadSectores());

        new Sector("sector1", unaOrg);
        Assertions.assertEquals(1, unaOrg.cantidadSectores());

        new Sector("sector2", unaOrg);
        Assertions.assertEquals(2, unaOrg.cantidadSectores());

        new Sector("sector3", unaOrg);
        Assertions.assertEquals(3, unaOrg.cantidadSectores());

        new Sector("sector4", unaOrg);
        Assertions.assertEquals(4, unaOrg.cantidadSectores());
    }

    @Test
    public void unaOrganizacionNoPuedeEliminarSectorSiEsteNoLePertenece() {
        Organizacion org1 = buildOrganizacion();

        Organizacion org2 = buildOrganizacion();

        Sector sistemas = new Sector("Sistemas", org1);

        Assertions.assertThrows(SectorException.class,
                () -> org2.quitarSector(sistemas),
                "El sector no pertenece a la organización");
    }

    @Test
    public void unaOrganizacionNoPuedeEliminarSectorSiEsteTieneEmpleados() {
        Organizacion unaOrg = buildOrganizacion();
        Sector sistemas = new Sector("Sistemas", unaOrg);

        Miembro miembro = new Miembro("Carlos", "Alverga", TipoDeDocumento.DNI, 12345678);

        sistemas.agregarMiembro(miembro);

        Assertions.assertThrows(SectorException.class,
                () -> unaOrg.quitarSector(sistemas),
                "No puede eliminarse un sector que tiene empleados");
    }

    public Organizacion buildOrganizacion() {
        return new Organizacion("Una empresa", TipoDeOrganizacionEnum.EMPRESA, new ClasificacionOrganizacion("Escuela"),
                new UbicacionGeografica(
                        new Direccion(new Municipio("Esteban Echeverria", new Provincia("Buenos Aires", "Argentina")),
                                "Luis Guillon", "Juan Manuel de Rosas", 778),
                        new Coordenada(-34.814605f, -58.446584f)));
    }

}
