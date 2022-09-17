package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;

import java.util.Optional;

public interface RepoCategorias extends Repositorio<Categoria> {
    Optional<Categoria> findByNombreCategoria(String categoria);
    Optional<Categoria> findByActividadAndTipoConsumo(String actividad, String tipoConsumo);
}
