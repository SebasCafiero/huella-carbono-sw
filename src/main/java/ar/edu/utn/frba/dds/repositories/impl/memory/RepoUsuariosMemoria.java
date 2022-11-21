package ar.edu.utn.frba.dds.repositories.impl.memory;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.server.login.User;
import ar.edu.utn.frba.dds.repositories.RepoUsuarios;
import ar.edu.utn.frba.dds.repositories.daos.DAOMemoria;

import java.util.Optional;
import java.util.function.Predicate;

public class RepoUsuariosMemoria<T> extends RepositorioMemoria<User> implements RepoUsuarios {

    public RepoUsuariosMemoria(DAOMemoria<User> dao) {
        super(dao);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.getDao().buscar(condicionDocumento(username)).stream().findFirst();
    }

    @Override
    public Optional<User> findByRolId(String rol, Integer idRol) {
        return this.getDao().buscar(condicionRol(idRol, rol)).stream().findFirst();
    }

    private Predicate<User> condicionDocumento(String username) {
        return user -> user.getUsername().equals(username);
    }

    private Predicate<User> condicionRol(Integer idRol, String rol) {
        return user -> rol.equals("organizacion") && user.getOrganizacion().getId().equals(idRol) ||
                    rol.equals("agente") && user.getAgenteSectorial().getId().equals(idRol) ||
                    rol.equals("miembro") && user.getMiembro().getId().equals(idRol);
    }
}
