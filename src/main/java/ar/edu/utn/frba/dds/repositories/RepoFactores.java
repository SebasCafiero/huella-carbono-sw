package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;

import java.util.List;
import java.util.Optional;

public interface RepoFactores extends Repositorio<FactorEmision> {
    List<FactorEmision> findByCategoria(Categoria categoria);
}
