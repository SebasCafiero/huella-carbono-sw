package ar.edu.utn.frba.dds.repositories.testMemoData;

import ar.edu.utn.frba.dds.entities.personas.Miembro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataMiembro {
    private static List<Miembro> miembros = new ArrayList<>();

    public static <T> List<T> getList(){
        if(miembros.size() == 0) {

            Miembro m1 = new Miembro();
            m1.setNombre("Jorge");

            Miembro m2 = new Miembro();
            m2.setNombre("Pepe");

            Miembro m3 = new Miembro();
            m3.setNombre("Antonio");

            Miembro m4 = new Miembro();
            m4.setNombre("Leo");


            addAll(m1, m2, m3, m4);
        }
        return (List<T>) miembros;
    }

    private static void addAll(Miembro ... miembros){
        Collections.addAll(ar.edu.utn.frba.dds.repositories.testMemoData.DataMiembro.miembros, miembros);
    }

}
