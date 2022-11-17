package ar.edu.utn.frba.dds.repositories.dataInicial.memory;

import ar.edu.utn.frba.dds.entities.lugares.*;
import ar.edu.utn.frba.dds.entities.medibles.Periodo;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.entities.transportes.*;
import ar.edu.utn.frba.dds.entities.medibles.Tramo;
import ar.edu.utn.frba.dds.entities.medibles.Trayecto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataTrayecto {

        private static final List<Trayecto> trayectos = new ArrayList<>();

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

            TransportePublico subteA = new TransportePublico(TipoTransportePublico.SUBTE, "A");
            TransportePublico bondi47 = new TransportePublico(TipoTransportePublico.COLECTIVO, "47");
            TransportePublico trenSarmiento = new TransportePublico(TipoTransportePublico.TREN, "Sarmiento");
            VehiculoParticular autoElectrico = new VehiculoParticular(TipoVehiculo.AUTOMOVIL, TipoCombustible.ELECTRICO);
            VehiculoParticular motoNafta = new VehiculoParticular(TipoVehiculo.MOTOCICLETA, TipoCombustible.NAFTA);
            TransporteEcologico caminata = new TransporteEcologico(TipoTransporteEcologico.PIE);

            Municipio caba = new Municipio("Ciudad de Buenos Aires", new Provincia("Ciudad de Buenos Aires", "Argentina"));

            Coordenada coorSubte1 = new Coordenada(50F, 70F);
            UbicacionGeografica ubicacionSubte1 = new UbicacionGeografica(new Direccion(caba, "La Boca", "Brandsen", 805), coorSubte1);

            Coordenada coorSubte2 = new Coordenada(100F, 130F);
            UbicacionGeografica ubicacionSubte2 = new UbicacionGeografica(caba, "Belgrano", "Juramento", 123, coorSubte2);

            Coordenada coorSubte3Bondi1 = new Coordenada(200F, 300F);
            UbicacionGeografica ubicacionSubte3Bondi1 = new UbicacionGeografica(caba, "Balvanera", "Lavalle", 456, coorSubte3Bondi1);

            Coordenada coorBondi2Tren2 = new Coordenada(500F, 480F);
            UbicacionGeografica ubicacionBondi2Tren2 = new UbicacionGeografica(caba, "San Telmo", "Carlos Calvo", 9876, coorBondi2Tren2);

            Coordenada coorTren1 = new Coordenada(46.5F, 73.2F);
            UbicacionGeografica ubicacionTren1 = new UbicacionGeografica(caba, "Flores", "Varela", 555, coorTren1);

            Parada paradaSubte1 = new Parada(ubicacionSubte1, 0F, 10F);
            Parada paradaSubte2 = new Parada(ubicacionSubte2, 10F, 12F);
            Parada paradaSubte3 = new Parada(ubicacionSubte3Bondi1, 12F, 0F);
            Parada paradaBondi1 = new Parada(ubicacionSubte3Bondi1, 0F, 8F);
            Parada paradaBondi2 = new Parada(ubicacionBondi2Tren2, 8F, 0F);
            Parada paradaTren1= new Parada(ubicacionTren1, 0F, 18F);
            Parada paradaTren2= new Parada(ubicacionBondi2Tren2, 18F, 0F);

            subteA.agregarParadas(paradaSubte1, paradaSubte2, paradaSubte3);
            bondi47.agregarParadas(paradaBondi1, paradaBondi2);
            trenSarmiento.agregarParadas(paradaTren1, paradaTren2);


            Trayecto t1 = new Trayecto();
            Tramo t11 = new Tramo(subteA, ubicacionSubte1, ubicacionSubte3Bondi1);
            Tramo t12 = new Tramo(bondi47, ubicacionSubte3Bondi1, ubicacionBondi2Tren2);
            Tramo t13 = new Tramo(caminata, ubicacionBondi2Tren2, ubicacionTren1);
            t11.setId(11);
            t12.setId(12);
            t13.setId(13);
            Collections.addAll(tramos, t11, t12, t13);
            t1.agregarTramos(tramos);
            t1.setPeriodo(new Periodo(2020,10));
            t1.agregarMiembro(m1);
            m1.agregarTrayecto(t1);

            Trayecto t2 = new Trayecto();
            Tramo t21 = new Tramo(trenSarmiento, ubicacionTren1, ubicacionBondi2Tren2);
            Tramo t22 = new Tramo(autoElectrico, ubicacionBondi2Tren2, ubicacionSubte2);
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
            Tramo t31 = new Tramo(motoNafta, ubicacionBondi2Tren2, ubicacionTren1);
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
