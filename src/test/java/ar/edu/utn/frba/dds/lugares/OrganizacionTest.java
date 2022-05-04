package ar.edu.utn.frba.dds.lugares;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static ar.edu.utn.frba.dds.lugares.TipoDeOrganizacionEnum.*;

public class OrganizacionTest {


    @Test
    public void unaOrganizacionNoPuedeRepetirSector() throws Exception {
        Organizacion unaOrg = new Empresa("Una empresa",
                ClasificacionOrganizacion.ESCUELA,
                "Buenos Aires");

        Sector sistemas = new Sector("Sistemas", unaOrg);

        Assertions.assertThrows(Exception.class, () -> {
            unaOrg.agregarSector(sistemas);
        });
    }

    @Test
    public void agregarSectoresAUnaOrganizacoin() throws Exception {
        Organizacion unaOrg = new ONG("miRazonSocial",
                ClasificacionOrganizacion.UNIVERSIDAD,
                "miUbicacion");

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
