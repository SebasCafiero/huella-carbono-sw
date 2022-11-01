package ar.edu.utn.frba.dds.repositories.testMemoData;

import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.geografia.Direccion;
import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;
import ar.edu.utn.frba.dds.entities.mediciones.Periodo;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.entities.transportes.*;
import ar.edu.utn.frba.dds.entities.trayectos.Tramo;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataTrayecto {

        private static List<Trayecto> trayectos = new ArrayList<>();

    public static List<Trayecto> getList(){
        if(trayectos.size() == 0) {
            List<Tramo> tramos = new ArrayList<>();

            Miembro m1 = new Miembro("Lionel", "Messi", TipoDeDocumento.DNI, 10);
            Miembro m2 = new Miembro("Leandro", "Paredes", TipoDeDocumento.DNI, 123);
            Miembro m3 = new Miembro("Ángel", "Di María", TipoDeDocumento.DNI, 456);
            Miembro m4 = new Miembro("Rodrigo", "De Paul", TipoDeDocumento.DNI, 789);

//            m1.setId(1);
//            m2.setId(2);
//            m3.setId(3);
//            m4.setId(4);

            MedioDeTransporte subteA = new TransportePublico(TipoTransportePublico.SUBTE, "A");
            MedioDeTransporte bondi47 = new TransportePublico(TipoTransportePublico.COLECTIVO, "47");
            MedioDeTransporte trenSarmiento = new TransportePublico(TipoTransportePublico.TREN, "Sarmiento");
            MedioDeTransporte autoElectrico = new VehiculoParticular(TipoVehiculo.AUTOMOVIL, TipoCombustible.ELECTRICO);
            MedioDeTransporte motoNafta = new VehiculoParticular(TipoVehiculo.MOTOCICLETA, TipoCombustible.NAFTA);
            MedioDeTransporte caminata = new TransporteEcologico(TipoTransporteEcologico.PIE);

            Coordenada coor1 = new Coordenada(50F, 70F);
            Coordenada coor2 = new Coordenada(100F, 130F);
            Coordenada coor3 = new Coordenada(200F, 300F);
            Coordenada coor4 = new Coordenada(500F, 480F);

            UbicacionGeografica ubicacion1 = new UbicacionGeografica(new Direccion(), coor1);
            UbicacionGeografica ubicacion2 = new UbicacionGeografica("Belgrano", "Juramento", 123, coor2);
            UbicacionGeografica ubicacion3 = new UbicacionGeografica("Balvanera", "Lavalle", 456, coor3);
            UbicacionGeografica ubicacion4 = new UbicacionGeografica("San Telmo", "Carlos Calvo", 9876, coor4);
            UbicacionGeografica ubicacion5 = new UbicacionGeografica("Flores", "Varela", 555, coor1);

            Parada parada1subteA = new Parada(ubicacion1, 10F, 0F);
            Parada parada2subteA = new Parada(ubicacion2, 10F, 10F);
            Parada parada3subteA = new Parada(ubicacion3, 10F, 10F);
            Parada parada1bondi47 = new Parada(ubicacion4, 5F, 0F);
            Parada parada2bondi47 = new Parada(ubicacion1, 5F, 5F);
            Parada parada1tren = new Parada(ubicacion5, 1F, 0F);

            ((TransportePublico) subteA).agregarParadas(parada1subteA, parada2subteA, parada3subteA);
            ((TransportePublico) bondi47).agregarParadas(parada1bondi47, parada2bondi47);
            ((TransportePublico) trenSarmiento).agregarParada(parada1tren);


            Trayecto t1 = new Trayecto();
            Tramo t11 = new Tramo(subteA, coor1, coor2);
            Tramo t12 = new Tramo(bondi47, coor2, coor3);
            Tramo t13 = new Tramo(caminata, coor3, coor4);
            t11.setId(11);
            t12.setId(12);
            t13.setId(13);
            Collections.addAll(tramos, t11, t12, t13);
            t1.agregarTramos(tramos);
            t1.setPeriodo(new Periodo(2020,10));
            t1.agregarMiembro(m1);
            m1.agregarTrayecto(t1);

            Trayecto t2 = new Trayecto();
            Tramo t21 = new Tramo(trenSarmiento, coor3, coor4);
            Tramo t22 = new Tramo(autoElectrico, coor4, coor2);
            t21.setId(21);
            t22.setId(22);
            t2.agregarTramo(t21);
            t2.agregarTramo(t22);
//            tramos.clear();
//            Collections.addAll(tramos, t21, t22);
//            t2.agregarTramos(tramos);
            t2.setPeriodo(new Periodo(2021,9));
            t2.agregarMiembro(m2);
            m2.agregarTrayecto(t2);
            t2.agregarMiembro(m3);
            m3.agregarTrayecto(t2);
            t2.agregarMiembro(m4);
            m4.agregarTrayecto(t2);

            Trayecto t3 = new Trayecto();
            Tramo t31 = new Tramo(motoNafta, coor3, coor1);
            t31.setId(31);
            t3.agregarTramo(t31);
            t3.setPeriodo(new Periodo(2022,11));
            t3.agregarMiembro(m1);
            m1.agregarTrayecto(t3);
            t3.agregarMiembro(m3);
            m3.agregarTrayecto(t3);

            addAll(t1, t2, t3);
        }
        return trayectos;
    }

    private static void addAll(Trayecto ... trayectos){
        Collections.addAll(DataTrayecto.trayectos, trayectos);
    }

}
