package ar.edu.utn.frba.dds.repositories.dataInicial.memory;

import ar.edu.utn.frba.dds.entities.medibles.Medicion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataMedicion {
    private static List<Medicion> mediciones = new ArrayList<>();

    public static <T> List<T> getList(){
        if(mediciones.size() == 0) {

            Medicion m1 = new Medicion();
            m1.setValor(10f);
            m1.setUnidad("m3");

            Medicion m2 = new Medicion();
            m2.setValor(322434.34f);
            m2.setUnidad("m3");

            Medicion m3 = new Medicion();
            m3.setValor(300f);
            m3.setUnidad("lt");

            Medicion m4 = new Medicion();
            m4.setValor(10f);
            m4.setUnidad("kg");


            addAll(m1,m2,m3,m4);
        }
        return (List<T>) mediciones;
    }

    private static void addAll(Medicion ... mediciones){
        Collections.addAll(DataMedicion.mediciones, mediciones);
    }
}
