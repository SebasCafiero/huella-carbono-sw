package ar.edu.utn.frba.dds.repositories.testMemoData;

import ar.edu.utn.frba.dds.entities.EntidadPersistente;
import ar.edu.utn.frba.dds.entities.mediciones.Medicion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataMedicion {
    private static List<Medicion> mediciones = new ArrayList<>();

    public static List<EntidadPersistente> getList(){
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
        return (List<EntidadPersistente>)(List<?>) mediciones;
    }

    private static void addAll(Medicion ... mediciones){
        Collections.addAll(DataMedicion.mediciones, mediciones);
    }
}
