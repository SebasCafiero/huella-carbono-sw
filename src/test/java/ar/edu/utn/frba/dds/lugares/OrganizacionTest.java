package ar.edu.utn.frba.dds.lugares;

import ar.edu.utn.frba.dds.excepciones.SectorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static ar.edu.utn.frba.dds.lugares.TipoDeOrganizacionEnum.*;

public class OrganizacionTest {


    @Test
    public void unaOrganizacionNoPuedeRepetirSector() throws SectorException {
        Organizacion unaOrg = new Organizacion("Una empresa",
                TipoDeOrganizacionEnum.EMPRESA,
                new ClasificacionOrganizacion("Escuela"),
                new UbicacionGeografica("Buenos Aires",new Coordenada(10F,5F)));

        Sector sistemas = new Sector("Sistemas", unaOrg);

        Assertions.assertThrows(SectorException.class, () -> {
            unaOrg.agregarSector(sistemas);
        });
    }

    @Test
    public void agregarSectoresAUnaOrganizacoin() throws Exception {
        Organizacion unaOrg = new Organizacion("miRazonSocial",
                ONG,
                new ClasificacionOrganizacion("GreenPeace"),
                new UbicacionGeografica("Buenos Aires",new Coordenada(10F,5F)));


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
