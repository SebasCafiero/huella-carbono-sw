package ar.edu.utn.frba.dds.repositories.testMemoData;

import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;

import java.util.List;

public class Data {

    public static <T> List<T> getDataMedicion(){
        return DataMedicion.getList();
    }

    public static <T> List<T> getDataOrganizacion(){
        return DataOrganizacion.getList();
    }

    public static <T> List<T> getDataMiembro(){
        return DataMiembro.getList();
    }

    public static <T> List<T> getDataBatchMedicion(){
        return DataBatchMedicion.getList();
    }

    public static List<FactorEmision> getDataFactorEmision() {
        return DataFactorEmision.getListFactores();
    }

    public static List<Categoria> getDataCategorias() {
        return DataFactorEmision.getListCategorias();
    }

    public static <T> List<T> getDataAgenteSectorial() {
        return DataAgenteSectorial.getList();
    }

}

