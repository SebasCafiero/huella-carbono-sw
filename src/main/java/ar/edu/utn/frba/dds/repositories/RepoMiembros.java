package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;

import java.util.Optional;

public interface RepoMiembros extends Repositorio<Miembro> {
    Optional<Miembro> findByDocumento(TipoDeDocumento tipoDeDocumento, Integer nroDocumento);

    Optional<Miembro> findByUser(Integer id);
}
