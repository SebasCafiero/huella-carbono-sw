package ar.edu.utn.frba.dds.repositories.testMemoData;

import ar.edu.utn.frba.dds.entities.EntidadPersistente;
import ar.edu.utn.frba.dds.entities.lugares.Organizacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataOrganizacion {
    private static List<Organizacion> organizaciones = new ArrayList<>();

    public static <T extends EntidadPersistente> List<T> getList(){
        if(organizaciones.size() == 0) {

            Organizacion o1 = new Organizacion();
            o1.setRazonSocial("Mercado Libre");

            Organizacion o2 = new Organizacion();
            o2.setRazonSocial("FIFA");

            Organizacion o3 = new Organizacion();
            o3.setRazonSocial("Arcor");

            Organizacion o4 = new Organizacion();
            o4.setRazonSocial("Serenisima");


            addAll(o1, o2, o3, o4);
        }
        return (List<T>)(List<?>) organizaciones;
    }

    private static void addAll(Organizacion ... organizaciones){
        Collections.addAll(DataOrganizacion.organizaciones, organizaciones);
    }
}
