package ar.edu.utn.frba.dds.entities.lugares;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CoordenadaTest {

    @Test
    public void testIgualdadDeSoloLatitud_thenFalse() {
        Assertions.assertFalse(new Coordenada(0F, 0F).esIgualAOtraCoordenada(new Coordenada(0F, 1F)));
    }

    @Test
    public void testIgualdadDeSoloLongitud_thenFalse() {
        Assertions.assertFalse(new Coordenada(0F, 0F).esIgualAOtraCoordenada(new Coordenada(1F, 0F)));
    }

    @Test
    public void testIgualdadDeCoordenadasIguales_thenTrue() {
        Assertions.assertTrue(new Coordenada(0F, 0F).esIgualAOtraCoordenada(new Coordenada(0F, 0F)));
    }

    @Test
    public void testFallaPorSignoDeLatitud() {
        Assertions.assertFalse(new Coordenada(-1F, 5F).esIgualAOtraCoordenada(new Coordenada(1F, 5F)));
    }

    @Test
    public void testFallaPorSignoDeLongitud() {
        Assertions.assertFalse(new Coordenada(-1F, 5F).esIgualAOtraCoordenada(new Coordenada(-1F, -5F)));
    }

    @Test
    public void testFallaLatitudPorAproximacion() {
        Assertions.assertFalse(new Coordenada(-1.00001F, 5F).esIgualAOtraCoordenada(new Coordenada(-1F, 5F)));
    }

    @Test
    public void testFallaLongitudPorAproximacion() {
        Assertions.assertFalse(new Coordenada(-1F, 5.00001F).esIgualAOtraCoordenada(new Coordenada(-1F, 5F)));
    }
}