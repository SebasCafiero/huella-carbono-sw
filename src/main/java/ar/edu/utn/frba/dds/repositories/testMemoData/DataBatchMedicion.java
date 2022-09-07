package ar.edu.utn.frba.dds.repositories.testMemoData;

import ar.edu.utn.frba.dds.entities.mediciones.BatchMedicion;
import ar.edu.utn.frba.dds.entities.mediciones.Medicion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataBatchMedicion {
    private static List<BatchMedicion> batches = new ArrayList<>();

    public static <T> List<T> getList(){
        if(batches.size() == 0) {

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

            List<Medicion> mediciones1 = new ArrayList<>();
            mediciones1.add(m1);
            mediciones1.add(m2);

            List<Medicion> mediciones2 = new ArrayList<>();
            mediciones2.add(m3);
            mediciones2.add(m4);

            BatchMedicion b1 = new BatchMedicion();
            b1.setMediciones(mediciones1);
            b1.setFecha("2022-09-01");

            BatchMedicion b2 = new BatchMedicion();
            b2.setMediciones(mediciones2);
            b2.setFecha("2021-09-01");


            addAll(b1,b2);
        }
        return (List<T>) batches;
    }

    private static void addAll(BatchMedicion ... batches){
        Collections.addAll(DataBatchMedicion.batches, batches);
    }
}

