package ar.edu.utn.frba.dds.entities.medibles;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CategoriaTest {

    @Test
    @DisplayName("Dos categorías son iguales cuando coinciden en actividad y en tipo de consumo")
    void testDosCategoriasIguales() {
        Assertions.assertEquals(new Categoria("Hola", "asd"), new Categoria("Hola", "asd"));
    }

    @Test
    @DisplayName("Dos categorías son distintas cuando no coincide el tipo de consumo de ambas")
    void testDosCategoriasDistintasEnTipoDeConsumo() {
        Assertions.assertNotEquals(new Categoria("Hola", "asd"), new Categoria("Hola", "dsa"));
    }

    @Test
    @DisplayName("Dos categorías son distintas cuando no coincide la actividad de ambas")
    void testDosCategoriasDistintasEnActividad() {
        Assertions.assertNotEquals(new Categoria("hola", "asd"), new Categoria("Hola", "asd"));
    }
}
