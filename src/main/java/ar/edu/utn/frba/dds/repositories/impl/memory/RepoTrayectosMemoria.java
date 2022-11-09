package ar.edu.utn.frba.dds.repositories.impl.memory;

import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;
import ar.edu.utn.frba.dds.entities.transportes.Parada;
import ar.edu.utn.frba.dds.entities.transportes.TransportePublico;
import ar.edu.utn.frba.dds.entities.trayectos.Tramo;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
import ar.edu.utn.frba.dds.repositories.daos.DAOMemoria;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import ar.edu.utn.frba.dds.repositories.utils.RepositorioMemoria;

import java.util.List;

public class RepoTrayectosMemoria<T> extends RepositorioMemoria<Trayecto> {

    //TODO Cree RepoTrayectos para sincronizar los repo,sino el Data de trayectos
    // debería tenerlos vacío y al utilizarlos agregar cada dato en cada
    // repositorio, pero sin agregar dos veces (con if o usando Set en vez de List)

    public RepoTrayectosMemoria(DAOMemoria<Trayecto> dao) {
        super(dao);
        this.buscarTodos().forEach(this::sincronizarRepos);
    }

    private void sincronizarRepos(Trayecto trayecto) {
        Repositorio<Miembro> repoMiembros = FactoryRepositorio.get(Miembro.class);
        trayecto.getMiembros().forEach(m -> {
            if(!repoMiembros.buscarTodos().contains(m))
                repoMiembros.agregar(m);
        });

        Repositorio<MedioDeTransporte> repoMedios = FactoryRepositorio.get(MedioDeTransporte.class);
        Repositorio<Parada> repoParadas = FactoryRepositorio.get(Parada.class);
        trayecto.getTramos().stream().map(Tramo::getMedioDeTransporte).forEach(mt -> {
            if(!repoMedios.buscarTodos().contains(mt))
                repoMedios.agregar(mt);
            if(mt instanceof TransportePublico){
                List<Parada> paradas = ((TransportePublico) mt).getParadas();
                paradas.forEach(p -> {
                    if(!repoParadas.buscarTodos().contains(p))
                        repoParadas.agregar(p); //todo quizas sacar de acá y que las ids se establezcan por cada transporte!
                });
            }
        });
    }

}
