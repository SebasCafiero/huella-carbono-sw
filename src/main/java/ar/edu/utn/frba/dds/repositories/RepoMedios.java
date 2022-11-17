package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;

public interface RepoMedios extends Repositorio<MedioDeTransporte> {

    MedioDeTransporte findByEquality(MedioDeTransporte medio);
    MedioDeTransporte findByEquality(String tipo, String primerDiscriminante, String segundoDiscriminante);
}
