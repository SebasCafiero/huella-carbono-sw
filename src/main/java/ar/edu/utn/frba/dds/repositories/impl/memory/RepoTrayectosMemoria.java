package ar.edu.utn.frba.dds.repositories.impl.memory;

import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;
import ar.edu.utn.frba.dds.entities.trayectos.Tramo;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
import ar.edu.utn.frba.dds.repositories.daos.DAOMemoria;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import ar.edu.utn.frba.dds.repositories.utils.RepositorioMemoria;

public class RepoTrayectosMemoria<T> extends RepositorioMemoria<Trayecto> {

    //TODO
    // No sabia donde hacerlo así que cree RepoTrayectos para sincronizar los repos
    // Sino el Data de trayectos debería tenerlos vacíos y en otro lado crear los
    // miembros y usando el repoMiembros agregarlos, y modificar el repoTrayectos

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
        trayecto.getTramos().stream().map(Tramo::getMedioDeTransporte).forEach(mt -> {
            if(!repoMedios.buscarTodos().contains(mt))
                repoMedios.agregar(mt);
        });
    }


}
