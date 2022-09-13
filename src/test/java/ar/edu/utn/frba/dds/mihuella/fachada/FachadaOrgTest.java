package ar.edu.utn.frba.dds.mihuella.fachada;

import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
//import ar.edu.utn.frba.dds.mediciones.Parser;
import ar.edu.utn.frba.dds.entities.mediciones.Medicion;
import ar.edu.utn.frba.dds.mihuella.MedicionSinFactorEmisionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FachadaOrgTest {

    @Test
    void seObtieneCorrectamenteHC() throws Exception {
        HashMap<String, Float> fe = new HashMap<>();
        fe.put("Combustion Fija -> Gas Natural : m3", 1F);
        FachadaOrganizacion calculadora = new FachadaOrganizacion();
        calculadora.cargarParametros(fe);

        List<Medible> mediciones = new ArrayList<Medible>();
        Categoria cat = new Categoria("Combustion Fija","Gas Natural");
        mediciones.add(new Medicion(cat, "m3", 100F));

//        Assertions.assertEquals(100, calculadora.obtenerHU(mediciones), 1);
    }

    @Test
    void fallaPorCategoriaInexistente() throws Exception {
//        Map<String,Float> factoresDeEmision = Parser.generarFE("src/main/resources/propiedades.csv");
        FachadaOrganizacion calculadora = new FachadaOrganizacion();
//        calculadora.cargarParametros(factoresDeEmision);

        List<Medible> mediciones = new ArrayList<Medible>();
        Categoria cat = new Categoria("Combustion","Gas");
        mediciones.add(new Medicion(cat, "m3", 100F));

        Assertions.assertThrows(MedicionSinFactorEmisionException.class,() -> calculadora.obtenerHU(mediciones));
    }
}