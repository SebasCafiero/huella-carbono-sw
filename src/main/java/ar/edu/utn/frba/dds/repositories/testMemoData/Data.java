package ar.edu.utn.frba.dds.repositories.testMemoData;

import java.util.ArrayList;
import java.util.List;

public class Data {

    /* Este es el getData que va posta
    public static List<EntidadPersistente> getData(Class type){

        return DataMedicion.getList();
    }*/

    public static <T> List<T> getDataMedicion(){
        return DataMedicion.getList();
    }

    public static <T> List<T> getDataOrganizacion(){
        return DataOrganizacion.getList();
    }

    public static <T> List<T> getDataFactores() {
        return new ArrayList<T>();
    }
}
