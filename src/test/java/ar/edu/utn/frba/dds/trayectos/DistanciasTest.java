package ar.edu.utn.frba.dds.trayectos;

import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;
import ar.edu.utn.frba.dds.entities.transportes.*;
import ar.edu.utn.frba.dds.entities.trayectos.Tramo;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
import ar.edu.utn.frba.dds.servicios.calculadoraDistancias.AdaptadorServicioDDSTPA;
import ar.edu.utn.frba.dds.servicios.calculadoraDistancias.CalculadoraDistancias;
import ar.edu.utn.frba.dds.servicios.calculadoraDistancias.ServicioSimulado;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class DistanciasTest {


    private VehiculoParticular unVehiculoParticular;
    private ServicioContratado unUber;

    @Mock private CalculadoraDistancias servicioCalcularDistancia = new AdaptadorServicioDDSTPA();

    @BeforeEach
    public void configMock() {
        MockitoAnnotations.openMocks(this);

        unVehiculoParticular = new VehiculoParticular(TipoVehiculo.AUTOMOVIL, TipoCombustible.NAFTA);
        unVehiculoParticular.setServicioDistancias(servicioCalcularDistancia);

        unUber = new ServicioContratado(new TipoServicio("Uber"));
        unUber.setServicioDistancias(servicioCalcularDistancia);
    }

    @Test
    public void testCalculoDistanciaTramos(){
        Trayecto unTrayecto = new Trayecto();
        Tramo tramo1 = new Tramo(unVehiculoParticular,new Coordenada(10F,5F),new Coordenada(13F,6F));
        Tramo tramo2 = new Tramo(unVehiculoParticular,new Coordenada(7F,7F),new Coordenada(10F,7F));
        Tramo tramo3 = new Tramo(unVehiculoParticular,new Coordenada(3F,0F),new Coordenada(5F,4F));
        List<Tramo> listaTramos = new ArrayList<>();
        listaTramos.add(tramo1);
        listaTramos.add(tramo2);
        unTrayecto.agregarTramos(listaTramos);
        unTrayecto.agregarTramo(tramo3);
        Assertions.assertEquals(0,unTrayecto.calcularDistancia());
//        Assertions.assertEquals(4+3+6,unTrayecto.calcularDistancia());
    }

    @Test
    public void testCalculoDistanciaTramosTransportePublico(){
        TransportePublico unTransportePublico = new TransportePublico(TipoTransportePublico.COLECTIVO,"26");
        /*unTransportePublico.agregarParada(new Parada(new Coordenada(5F,5F),0.3F,0.6F));
        unTransportePublico.agregarParada(new Parada(new Coordenada(8F,12F),0.6F,0.8F));
        unTransportePublico.agregarParada(new Parada(new Coordenada(11F,15F),0.5F,0.3F));*/

        unTransportePublico.agregarParadas(
                new Parada(new Coordenada(5F,5F),0.3F,0.6F),
                new Parada(new Coordenada(8F,12F),0.6F,0.8F),
                new Parada(new Coordenada(11F,15F),0.5F,0.3F)
        );

        Trayecto unTrayecto = new Trayecto();
        Tramo tramo1 = new Tramo(unTransportePublico,new Coordenada(5F,5F),new Coordenada(8F,12F));
        Tramo tramo2 = new Tramo(unTransportePublico,new Coordenada(8F,12F),new Coordenada(11F,15F));
        Tramo tramo3 = new Tramo(unTransportePublico,new Coordenada(11F,15F),new Coordenada(5F,5F));
        ArrayList listaTramos = new ArrayList<>();
        listaTramos.add(tramo1);
        listaTramos.add(tramo2);
        unTrayecto.agregarTramos(listaTramos);
        unTrayecto.agregarTramo(tramo3);
        Assertions.assertEquals(0.6F+0.8F+0.3F,unTrayecto.calcularDistancia());
    }

    @Test
    public void testCalculoDistanciaTramosCombinandoTransportes(){
        TransportePublico unSubte = new TransportePublico(TipoTransportePublico.SUBTE,"C");
        unSubte.agregarParada(new Parada(new Coordenada(15F,13F),0.1F,0.2F));
        unSubte.agregarParada(new Parada(new Coordenada(17F,17F),0.2F,0.3F));
        unSubte.agregarParada(new Parada(new Coordenada(19F,21F),0.3F,0.4F));

        Trayecto unTrayecto = new Trayecto();
        unTrayecto.agregarTramo(new Tramo(unUber,new Coordenada(3F,3F), new Coordenada(17F,17F)));
        unTrayecto.agregarTramo(new Tramo(unSubte,new Coordenada(17F,17F), new Coordenada(19F,21F)));
        Assertions.assertEquals(0+0.3F,unTrayecto.calcularDistancia());
//        Assertions.assertEquals(28+0.3F,unTrayecto.calcularDistancia());
    }

    @Test
    public void testServicioSimulado() {
        CalculadoraDistancias servicioContratado = new ServicioSimulado();
        UbicacionGeografica ubicacionInicial = new UbicacionGeografica(new Coordenada(5F,20F));
        UbicacionGeografica ubicacionFinal = new UbicacionGeografica(new Coordenada(55F,58F));

        Assertions.assertEquals(88F,servicioContratado.calcularDistancia(ubicacionInicial, ubicacionFinal));
    }

    @Test
    public void testMockServicio() {
        UbicacionGeografica ubicacionInicial = new UbicacionGeografica(new Coordenada(5F,20F));
        UbicacionGeografica ubicacionFinal = new UbicacionGeografica(new Coordenada(55F,58F));

        Assertions.assertEquals(0F,servicioCalcularDistancia.calcularDistancia(ubicacionInicial, ubicacionFinal));
        verify(servicioCalcularDistancia).calcularDistancia(ubicacionInicial, ubicacionFinal);
    }

    @Test
    public void testMockServicioIntegral() {
        UbicacionGeografica ubicacionInicial = new UbicacionGeografica(new Coordenada(5F,20F));
        UbicacionGeografica ubicacionFinal = new UbicacionGeografica(new Coordenada(55F,58F));

        unVehiculoParticular.setServicioDistancias(servicioCalcularDistancia);
        Tramo unTramo = new Tramo(unVehiculoParticular, ubicacionInicial, ubicacionFinal);

        Assertions.assertEquals(0F,unVehiculoParticular.calcularDistancia(unTramo));
        Assertions.assertEquals(0F,unTramo.getValor());
        verify(servicioCalcularDistancia, times(2)).calcularDistancia(ubicacionInicial, ubicacionFinal);
    }

    @Test
    public void testMockServicioGenericoConAnswer() {

        class CalcMock implements Answer {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return 4F;
            }
        }
        CalculadoraDistancias mockCalc = mock(CalculadoraDistancias.class, new CalcMock());

        UbicacionGeografica ubicacionInicial = new UbicacionGeografica(new Coordenada(5F,20F));
        UbicacionGeografica ubicacionFinal = new UbicacionGeografica(new Coordenada(55F,58F));

        Assertions.assertEquals(4F,mockCalc.calcularDistancia(ubicacionInicial, ubicacionFinal));
        verify(mockCalc).calcularDistancia(ubicacionInicial, ubicacionFinal);
    }

    @Test
    public void testMockServicioGenericoConAnswerIntegral() {

        class CalcMock implements Answer {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return 4F;
            }
        }
        CalculadoraDistancias mockCalc = mock(CalculadoraDistancias.class, new CalcMock());

        UbicacionGeografica ubicacionInicial = new UbicacionGeografica(new Coordenada(5F,20F));
        UbicacionGeografica ubicacionFinal = new UbicacionGeografica(new Coordenada(55F,58F));
        Tramo unTramo = new Tramo(unVehiculoParticular, ubicacionInicial, ubicacionFinal);
        unVehiculoParticular.setServicioDistancias(mockCalc);

        when(mockCalc.calcularDistancia(ubicacionInicial,ubicacionFinal)).thenReturn(7F); //Otra forma directa
        Assertions.assertEquals(7F, mockCalc.calcularDistancia(ubicacionInicial, ubicacionFinal));
        Assertions.assertEquals(7F, unVehiculoParticular.calcularDistancia(unTramo));
        Assertions.assertEquals(7F, unTramo.getValor());
        verify(mockCalc, times(3)).calcularDistancia(ubicacionInicial, ubicacionFinal);
    }

    @Test
    public void testConexion() { //TODO no valdría la pena testear la conexion, o al menos no acá.
        CalculadoraDistancias servicioExterno = new AdaptadorServicioDDSTPA();
        UbicacionGeografica ubicacionInicial = new UbicacionGeografica(new Coordenada(5F,20F));
        UbicacionGeografica ubicacionFinal = new UbicacionGeografica(new Coordenada(55F,58F));
        Assertions.assertDoesNotThrow(()->servicioExterno.calcularDistancia(ubicacionInicial, ubicacionFinal));
    }
}
