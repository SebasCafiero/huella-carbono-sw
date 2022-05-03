package ar.edu.utn.frba.dds.lugares;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class Entrega1Test {
    @Test
    public void unaOrganizacionConSectoresMinimosOK() throws Exception {
        Organizacion unaOrg = new Organizacion("Una empresa",
                TipoDeOrganizacionEnum.EMPRESA,
                new ClasificacionOrganizacion("Ministerio",
                        new String[]{"Presidencia", "Contabilidad", "RRHH"}),
                "Buenos Aires");
        System.out.println("Instancie organización");
        new Sector("Presidencia", unaOrg);
        new Sector("Contabilidad", unaOrg);
        new Sector("RRHH", unaOrg);
        System.out.println("Instancie sectores");
        Assertions.assertTrue(unaOrg.esValida());
    }
}
