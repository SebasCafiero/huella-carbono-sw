package ar.edu.utn.frba.dds.repositories.dataInicial.memory;

import ar.edu.utn.frba.dds.entities.medibles.BatchMediciones;
import ar.edu.utn.frba.dds.entities.medibles.Medicion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataBatchMedicion {
    private static final List<BatchMediciones> batches = new ArrayList<>();

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

            BatchMediciones b1 = new BatchMediciones(mediciones1, LocalDate.parse("2022-09-01"));
            BatchMediciones b2 = new BatchMediciones(mediciones2, LocalDate.parse("2022-09-01"));

            addAll(b1,b2);
        }
        return (List<T>) batches;
    }

    private static void addAll(BatchMediciones... batches){
        Collections.addAll(DataBatchMedicion.batches, batches);
    }
}

