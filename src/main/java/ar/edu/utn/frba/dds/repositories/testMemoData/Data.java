package ar.edu.utn.frba.dds.repositories.testMemoData;

import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;

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

    public static List<Trayecto> getDataTrayecto() {
        return DataTrayecto.getList();
    }

}

