package ar.edu.utn.frba.dds.fachada;

import ar.edu.utn.frba.dds.entities.medibles.Categoria;
import ar.edu.utn.frba.dds.entities.medibles.FactorEmision;
import ar.edu.utn.frba.dds.entities.medibles.Medible;
import ar.edu.utn.frba.dds.entities.medibles.Medicion;
import ar.edu.utn.frba.dds.servicios.fachadas.FachadaOrganizacion;
import ar.edu.utn.frba.dds.servicios.fachadas.exceptions.MedicionSinFactorEmisionException;
import ar.edu.utn.frba.dds.repositories.daos.DAOMemoria;
import ar.edu.utn.frba.dds.repositories.impl.memory.RepoFactoresMemoria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FachadaOrganizacionesTest {

    private static FachadaOrganizacion calculadora;

    @BeforeAll
    public static void initialize() {
        Map<String, Float> fe = new HashMap<>();
        fe.put("Combustion Fija -> Gas Natural : m3", 1F);
        fe.put("Traslado de Miembros -> Publico - COLECTIVO : lt", 10F);
        calculadora = new FachadaOrganizacion(new RepoFactoresMemoria<>(new DAOMemoria<>(FactorEmision.class)));
        calculadora.cargarParametros(fe);
    }

    @Test
    void seObtieneCorrectamenteHC() {
        List<Medible> mediciones = new ArrayList<>();
        Categoria cat = new Categoria("Combustion Fija","Gas Natural");
        mediciones.add(new Medicion(cat, "m3", 100F));

        Assertions.assertEquals(100, calculadora.obtenerHU(mediciones), 1);
    }

    @Test
    void ignoraLaUnidad() {
        List<Medible> mediciones = new ArrayList<>();
        Categoria cat = new Categoria("Combustion Fija","Gas Natural");
        mediciones.add(new Medicion(cat, "lt", 100F));

        Assertions.assertEquals(100, calculadora.obtenerHU(mediciones), 1);
    }

    @Test
    void fallaPorCategoriaInexistenteEnMedicion() {
        List<Medible> mediciones = new ArrayList<>();
        Categoria cat = new Categoria("Combustion","Gas");
        mediciones.add(new Medicion(cat, "m3", 100F));

        Assertions.assertThrows(MedicionSinFactorEmisionException.class, () -> {
            calculadora.obtenerHU(mediciones);
        });
    }

//    @Test
//    void calculaCorrectamenteUnTramo() {
//        List<Medible> mediciones = new ArrayList<>();
//        MedioDeTransporte medio = new TransportePublico(TipoTransportePublico.COLECTIVO, "47");
//        Tramo tramo = new Tramo(medio,
//                new UbicacionGeografica(new Coordenada(1F,1F)),
//                new UbicacionGeografica(new Coordenada(2F,2F)));
//        tramo.setValor(4F);
//
//        mediciones.add(tramo);
//        Assertions.assertEquals(40, calculadora.obtenerHU(mediciones), 1);
//    }
//
//    @Test
//    void fallaPorCategoriaInexistenteEnTramo() {
//        List<Medible> mediciones = new ArrayList<>();
//        MedioDeTransporte medio = new TransportePublico(TipoTransportePublico.SUBTE, "A");
//        Tramo tramo = new Tramo(medio,
//                new UbicacionGeografica(new Coordenada(1F,1F)),
//                new UbicacionGeografica(new Coordenada(2F,2F)));
//        tramo.setValor(4F);
//
//        mediciones.add(tramo);
//
//        Assertions.assertThrows(MedicionSinFactorEmisionException.class, () -> {
//            calculadora.obtenerHU(mediciones);
//        });
//    }
}