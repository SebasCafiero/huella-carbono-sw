package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;

import java.util.Optional;

public interface RepoMiembros extends Repositorio<Miembro> {
    Optional<Miembro> findByDocumento(TipoDeDocumento tipoDeDocumento, Integer nroDocumento);
}
