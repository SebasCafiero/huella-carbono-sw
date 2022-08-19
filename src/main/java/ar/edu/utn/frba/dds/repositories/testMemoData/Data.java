package ar.edu.utn.frba.dds.repositories.testMemoData;

import ar.edu.utn.frba.dds.entities.EntidadPersistente;

import java.util.ArrayList;
import java.util.List;

public class Data {

    /* Este es el getData que va posta
    public static List<EntidadPersistente> getData(Class type){

        return DataMedicion.getList();
    }*/

    public static <T extends EntidadPersistente> List<T> getDataMedicion(){
        return DataMedicion.getList();
    }

    public static <T extends EntidadPersistente> List<T> getDataOrganizacion(){
        return DataOrganizacion.getList();
    }
}
