package ar.edu.utn.frba.dds.repositories.testMemoData;

import ar.edu.utn.frba.dds.entities.EntidadPersistente;
import ar.edu.utn.frba.dds.mediciones.Medicion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataMedicion {
    private static List<Medicion> mediciones = new ArrayList<>();

    public static List<EntidadPersistente> getList(){
        if(mediciones.size() == 0) {

            Medicion m1 = new Medicion();
            m1.setValor(0f);

            Medicion m2 = new Medicion();
            m2.setValor(322434.34f);

            addAll(m1, m2);
        }
        return (List<EntidadPersistente>)(List<?>) mediciones;
    }

    private static void addAll(Medicion ... mediciones){
        Collections.addAll(DataMedicion.mediciones, mediciones);
    }
}
