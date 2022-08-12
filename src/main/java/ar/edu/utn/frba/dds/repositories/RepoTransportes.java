package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;

import java.util.ArrayList;
import java.util.List;

public class RepoTransportes {
    List<MedioDeTransporte> medios;
    public static RepoTransportes instance;

    public static RepoTransportes getInstance(){
        if (instance==null){
            instance = new RepoTransportes();
        }
        return instance;
    }

    private RepoTransportes() {
        medios = new ArrayList<MedioDeTransporte>();
    }

    public void agregarMedio(MedioDeTransporte medio) {
        medios.add(medio);
    }

    public List<MedioDeTransporte> getMedios() {
        return medios;
    }

//    public Optional<MedioDeTransporte> findMedio(String nombreMedio, String atrb1, String atrb2) {
//        return medios.stream().filter( me -> me.toString().equals(nombreMedio)
//                        && me.matchAtributo1(atrb1) && me.matchAtributo2(atrb2)
//        ).findFirst();
//    }
}
