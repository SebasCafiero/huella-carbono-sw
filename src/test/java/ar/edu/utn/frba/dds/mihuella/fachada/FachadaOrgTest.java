package ar.edu.utn.frba.dds.mihuella.fachada;

import ar.edu.utn.frba.dds.mediciones.CalculadoraHCOrganizacion;
import ar.edu.utn.frba.dds.mediciones.Medicion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FachadaOrgTest {

    @Test
    void seObtieneCorrectamenteHC() throws Exception {
        CalculadoraHCOrganizacion calculadora = new CalculadoraHCOrganizacion();
        Map<String, Float> parametrosSistema = new HashMap<>();
        parametrosSistema.put("comF_gasNatural", (float) 123.1);

        List<Medible> mediciones = new ArrayList<Medible>();
        mediciones.add(new Medicion("comF_gasNatural", "m3", 100F));

        Assertions.assertEquals(50, calculadora.obtenerHU(mediciones), 1);
    }
    // parametrosSistema.put("comM_nafta", (float) 2223.4);
}
