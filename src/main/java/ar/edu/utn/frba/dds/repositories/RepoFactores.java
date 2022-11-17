package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.medibles.Categoria;
import ar.edu.utn.frba.dds.entities.medibles.FactorEmision;

import java.util.List;

public interface RepoFactores extends Repositorio<FactorEmision> {
    List<FactorEmision> findByCategoria(Categoria categoria);
}
