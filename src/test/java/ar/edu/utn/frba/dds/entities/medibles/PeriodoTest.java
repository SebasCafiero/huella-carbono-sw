package ar.edu.utn.frba.dds.entities.medibles;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PeriodoTest {

    @Test
    void testPeriodosDistintaPeriodicidadFalse() {
        Assertions.assertNotEquals(new Periodo(12, 2022), new Periodo(2022));
        Assertions.assertNotEquals(new Periodo(2022), new Periodo(12, 2022));
    }

    @Test
    void testPeriodoDistintoMesFalse() {
        Assertions.assertNotEquals(new Periodo(12, 2022), new Periodo(11, 2022));
    }

    @Test
    void testPeriodoDistintoAnioFalse() {
        Assertions.assertNotEquals(new Periodo(12, 2021), new Periodo(12, 2022));
        Assertions.assertNotEquals(new Periodo(2021), new Periodo(2022));
    }

    @Test
    void testPeriodoIgualesTrue() {
        Assertions.assertEquals(new Periodo(12, 2021), new Periodo(12, 2021));
        Assertions.assertEquals(new Periodo(2022), new Periodo(2022));
    }

}
