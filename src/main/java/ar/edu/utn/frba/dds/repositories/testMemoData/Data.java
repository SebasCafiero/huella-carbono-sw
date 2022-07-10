package ar.edu.utn.frba.dds.repositories.testMemoData;

import ar.edu.utn.frba.dds.entities.EntidadPersistente;

import java.util.ArrayList;
import java.util.List;

public class Data {

    public static List<EntidadPersistente> getData(Class type){
        return DataOrganizacion.getList();
    }
}
