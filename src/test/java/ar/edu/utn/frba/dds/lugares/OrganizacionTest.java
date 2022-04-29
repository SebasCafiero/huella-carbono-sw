package ar.edu.utn.frba.dds.lugares;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrganizacionTest {

    @Test
    public void unaOrganizacionNoPuedeRepetirSector() throws Exception{
        Organizacion unaOrg = new Organizacion("Una empresa", TipoDeOrganizacionEnum.EMPRESA, "Buenos Aires");

        Sector sistemas = new Sector("Sistemas", unaOrg);
        Assertions.assertThrows(Exception.class, () -> {
            unaOrg.agregarSector(sistemas);
        });
    }
}
