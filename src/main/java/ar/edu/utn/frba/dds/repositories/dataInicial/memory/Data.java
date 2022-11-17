package ar.edu.utn.frba.dds.repositories.dataInicial.memory;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.medibles.Categoria;
import ar.edu.utn.frba.dds.entities.medibles.FactorEmision;
import ar.edu.utn.frba.dds.entities.medibles.Trayecto;

import java.util.List;

public class Data {

    public static <T> List<T> getDataMedicion(){
        return DataMedicion.getList();
    }

    public static List<Organizacion> getDataOrganizacion(){
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

