package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.lugares.Organizacion;
import ar.edu.utn.frba.dds.lugares.Sector;
import ar.edu.utn.frba.dds.lugares.TipoDeOrganizacionEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
//VER DE CAMBIAR EL POM POR LA JUNIT DEL SEMINARIO O PREGUNTAR AL PROFE
public class AdminTest {

    @Test
    @Disabled
    public void calculoDeLaHC() throws Exception {
        Organizacion unaOrg = new Organizacion("Una empresa", TipoDeOrganizacionEnum.EMPRESA, "Buenos Aires");
        // int hc = unaOrg.calcularHC();
        int hcManual = 5; //Hacer el calculo manual
        throw new Exception("Falta implementar test");
        // Assertions.assertEquals(hcManual,hc);
    }




}
