package ar.edu.utn.frba.dds.repositories.testMemoData;

import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataFactorEmision {
    private static List<FactorEmision> factoresDeEmision = new ArrayList<>();
    private static List<Categoria> categoriasFactores = new ArrayList<>();

    public static <T> List<T> getList() {
        if(factoresDeEmision.size() == 0) {

            FactorEmision f1 = new FactorEmision();
            Categoria categoria1 = new Categoria("Traslado de Miembros", "Publico - SUBTE");
            f1.setValor(0.3f);
            f1.setUnidad("m3");
            f1.setCategoria(categoria1);

            FactorEmision f2 = new FactorEmision();
            Categoria categoria2 = new Categoria("Traslado de Miembros", "Particular - GASOIL");
            f2.setValor(0.5f);
            f2.setUnidad("lt");
            f2.setCategoria(categoria2);

            FactorEmision f3 = new FactorEmision();
            Categoria categoria3 = new Categoria("Traslado de Miembros", "Particular - NAFTA");
            f3.setValor(0.1f);
            f3.setUnidad("kg");
            f3.setCategoria(categoria3);

            addAll(f1,f2,f3);
            Collections.addAll(DataFactorEmision.categoriasFactores, categoria1, categoria2, categoria3);
        }
        return (List<T>) factoresDeEmision;
    }

    private static void addAll(FactorEmision ... factoresDeEmision) {
        Collections.addAll(DataFactorEmision.factoresDeEmision, factoresDeEmision);
    }

    public static List<FactorEmision> getListFactores() {
        if(factoresDeEmision.isEmpty()) {
            getList();
        }
        return factoresDeEmision;
    }

    public static List<Categoria> getListCategorias() {
        if(categoriasFactores.isEmpty()) {
            getList();
        }
        return categoriasFactores;
    }

}
