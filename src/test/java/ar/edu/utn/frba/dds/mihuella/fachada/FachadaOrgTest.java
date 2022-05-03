package ar.edu.utn.frba.dds.mihuella.fachada;

import ar.edu.utn.frba.dds.mediciones.CalculadoraHCOrganizacion;
import ar.edu.utn.frba.dds.mediciones.Medicion;
import ar.edu.utn.frba.dds.mediciones.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FachadaOrgTest {

     private static final String rt = "src/test/resources/mediciones.csv";

    @Test
    void seObtieneCorrectamenteHC() throws Exception {
        CalculadoraHCOrganizacion calculadora = new CalculadoraHCOrganizacion("src/test/resources/propiedades.csv");

        List<Medible> mediciones = new ArrayList<Medible>();
        mediciones.add(new Medicion("Combustion Fija - Gas Natural", "m3", 100F));

        Assertions.assertEquals(100, calculadora.obtenerHU(mediciones), 1);
    }

    @Test
    void seObtieneCorrectamenteHCNuclear() throws Exception {
        CalculadoraHCOrganizacion calculadora = new CalculadoraHCOrganizacion("src/test/resources/propiedades.csv");

        List<Medible> mediciones = Parser.generarMediciones(rt, "Atucha");

        Assertions.assertEquals(100, calculadora.obtenerHU(mediciones), 1);
    }

    @Test
    void noSeObtieneCorrectamenteHCNuclear() throws Exception {
        CalculadoraHCOrganizacion calculadora = new CalculadoraHCOrganizacion("src/test/resources/propiedades.csv");

        List<Medible> mediciones = Parser.generarMediciones(rt, "Otro");

        Assertions.assertEquals(80, calculadora.obtenerHU(mediciones), 1);
    }
}