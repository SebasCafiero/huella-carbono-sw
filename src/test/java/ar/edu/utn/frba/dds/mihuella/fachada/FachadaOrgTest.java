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
        HashMap<String, Float> fe = new HashMap<>();
        fe.put("Combustion Fija - Gas Natural", 1F);
        CalculadoraHCOrganizacion calculadora = new CalculadoraHCOrganizacion(fe);

        List<Medible> mediciones = new ArrayList<Medible>();
        mediciones.add(new Medicion("Combustion Fija - Gas Natural", "m3", 100F));

        Assertions.assertEquals(100, calculadora.obtenerHU(mediciones), 1);
    }
}