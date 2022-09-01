package ar.edu.utn.frba.dds.repositories.testMemoData;

import ar.edu.utn.frba.dds.entities.EntidadPersistente;

import java.util.ArrayList;
import java.util.List;

public class Data {

    /* Este es el getData que va posta
    public static List<EntidadPersistente> getData(Class type){

        return DataMedicion.getList();
    }*/

    public static List<EntidadPersistente> getDataMedicion(){
        return DataMedicion.getList();
    }

    public static List<EntidadPersistente> getDataOrganizacion(){return DataOrganizacion.getList();}

    public static List<EntidadPersistente> getDataMiembro(){return DataMiembro.getList();}
}