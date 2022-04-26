package java.ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.lugares.Organizacion;
import ar.edu.utn.frba.dds.lugares.Sector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//VER DE CAMBIAR EL POM POR LA JUNIT DEL SEMINARIO O PREGUNTAR AL PROFE
public class AdminTest {

    @Test
    public void agregoNuevoSector() {
        Organizacion unaOrg = new Organizacion();

        Assertions.assertEquals(0,unaOrg.getListaDeSectores().size());

        unaOrg.agregarSector(new Sector());

        Assertions.assertEquals(1, unaOrg.getListaDeSectores().size());

        unaOrg.agregarSector(new Sector());

        Assertions.assertEquals(2, unaOrg.getListaDeSectores().size());

    }

    @Test
    public void calculoDeLaHC() {
        Organizacion unaOrg = new Organizacion();
        int hc = unaOrg.calcularHC();
        int hcManual = 5; //Hacer el calculo manual

        Assertions.assertEquals(hcManual,hc);
    }




}
