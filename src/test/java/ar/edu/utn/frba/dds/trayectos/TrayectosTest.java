package ar.edu.utn.frba.dds.trayectos;

import ar.edu.utn.frba.dds.lugares.Coordenada;
import ar.edu.utn.frba.dds.transportes.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TrayectosTest {

    @Test
    public void calculoDistanciaTramos(){
        VehiculoParticular unVehiculoParticular = new VehiculoParticular(TipoVehiculo.AUTOMOVIL, TipoCombustible.NAFTA);

        Trayecto unTrayecto = new Trayecto();
        Tramo tramo1 = new Tramo(unVehiculoParticular,new Coordenada(10F,5F),new Coordenada(13F,6F));
        Tramo tramo2 = new Tramo(unVehiculoParticular,new Coordenada(7F,7F),new Coordenada(10F,7F));
        Tramo tramo3 = new Tramo(unVehiculoParticular,new Coordenada(3F,0F),new Coordenada(5F,4F));
        ArrayList listaTramos = new ArrayList<>();
        listaTramos.add(tramo1);
        listaTramos.add(tramo2);
        unTrayecto.agregarTramos(listaTramos);
        unTrayecto.agregarTramo(tramo3);
        Assertions.assertEquals(4+3+6,unTrayecto.calcularDistancia());
    }

    @Test
    public void calculoDistanciaTramosTransportePublico(){
        TransportePublico unTransportePublico = new TransportePublico(TipoTransportePublico.COLECTIVO,"26");
        unTransportePublico.agregarParada(new Parada(new Coordenada(5F,5F),0.3F,0.6F));
        unTransportePublico.agregarParada(new Parada(new Coordenada(8F,12F),0.6F,0.8F));
        unTransportePublico.agregarParada(new Parada(new Coordenada(11F,15F),0.5F,0.3F));

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
    public void calculoDistanciaTramosCombinandoTransportes(){
        TransportePublico unSubte = new TransportePublico(TipoTransportePublico.SUBTE,"C");
        unSubte.agregarParada(new Parada(new Coordenada(15F,13F),0.1F,0.2F));
        unSubte.agregarParada(new Parada(new Coordenada(17F,17F),0.2F,0.3F));
        unSubte.agregarParada(new Parada(new Coordenada(19F,21F),0.3F,0.4F));

        ServicioContratado unUber = new ServicioContratado(new TipoServicio("Uber"));
        Trayecto unTrayecto = new Trayecto();
        unTrayecto.agregarTramo(new Tramo(unUber,new Coordenada(3F,3F), new Coordenada(17F,17F)));
        unTrayecto.agregarTramo(new Tramo(unSubte,new Coordenada(17F,17F), new Coordenada(19F,21F)));
        Assertions.assertEquals(28+0.3F,unTrayecto.calcularDistancia());
    }

}
