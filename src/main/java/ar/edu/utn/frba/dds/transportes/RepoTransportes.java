package ar.edu.utn.frba.dds.transportes;

import ar.edu.utn.frba.dds.lugares.TipoDeOrganizacionEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
