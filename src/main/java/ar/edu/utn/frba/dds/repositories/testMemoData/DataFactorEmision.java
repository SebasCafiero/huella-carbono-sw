package ar.edu.utn.frba.dds.repositories.testMemoData;

import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataFactorEmision {
    private static List<FactorEmision> factoresDeEmision = new ArrayList<>();

    public static <T> List<T> getList(){
        if(factoresDeEmision.size() == 0) {

            FactorEmision f1 = new FactorEmision();
            f1.setValor(0.3f);
            f1.setUnidad("m3");
            f1.setCategoria(new Categoria("Traslado de Miembros", "Particular - GNC"));

            FactorEmision f2 = new FactorEmision();
            f2.setValor(0.5f);
            f2.setUnidad("lt");
            f2.setCategoria(new Categoria("Traslado de Miembros", "Particular - GASOIL"));

            FactorEmision f3 = new FactorEmision();
            f3.setValor(0.1f);
            f3.setUnidad("kg");
            f3.setCategoria(new Categoria("Traslado de Miembros", "Particular - NAFTA"));

            addAll(f1,f2,f3);
        }
        return (List<T>) factoresDeEmision;
    }

    private static void  addAll(FactorEmision ... factoresDeEmision) {Collections.addAll(DataFactorEmision.factoresDeEmision,factoresDeEmision);}
}
