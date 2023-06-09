package ar.edu.utn.frba.dds.entities.lugares;

import ar.edu.utn.frba.dds.entities.exceptions.SectorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static ar.edu.utn.frba.dds.entities.lugares.TipoDeOrganizacionEnum.*;

public class OrganizacionTest {

    @Test
    public void unaOrganizacionNoPuedeRepetirSector() throws SectorException {
        Organizacion unaOrg = new Organizacion("Una empresa",
                TipoDeOrganizacionEnum.EMPRESA,
                new ClasificacionOrganizacion("Escuela"),
                new UbicacionGeografica(new Direccion(new Municipio("Esteban Echeverria",new Provincia("Buenos Aires","Argentina")),"Luis Guillon","Juan Manuel de Rosas",778),new Coordenada(-34.814605f,-58.446584f)));

        Sector sistemas = new Sector("Sistemas", unaOrg);

        Assertions.assertThrows(SectorException.class, () -> {
            unaOrg.agregarSector(sistemas);
        });
    }

    @Test
    public void agregarSectoresAUnaOrganizacion() throws Exception {
        Organizacion unaOrg = new Organizacion("miRazonSocial",
                ONG,
                new ClasificacionOrganizacion("GreenPeace"),
                new UbicacionGeografica(new Direccion(new Municipio("Esteban Echeverria",new Provincia("Buenos Aires","Argentina")),"Luis Guillon","Juan Manuel de Rosas",778),new Coordenada(-34.814605f,-58.446584f)));

        Assertions.assertEquals(0,unaOrg.cantidadSectores());
        Sector sector1 = new Sector("sector1",unaOrg);
        Assertions.assertEquals(1,unaOrg.cantidadSectores());
        Sector sector2 = new Sector("sector2",unaOrg);
        Assertions.assertEquals(2,unaOrg.cantidadSectores());
        Sector sector3 = new Sector("sector3",unaOrg);
        Assertions.assertEquals(3,unaOrg.cantidadSectores());
        Sector sector4 = new Sector("sector4",unaOrg);
        Assertions.assertEquals(4,unaOrg.cantidadSectores());
    }

}
