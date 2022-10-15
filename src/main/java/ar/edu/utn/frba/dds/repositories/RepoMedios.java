package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;

import java.util.Optional;

public interface RepoMedios extends Repositorio<MedioDeTransporte> {
    MedioDeTransporte findByEquality(MedioDeTransporte medio);
}
