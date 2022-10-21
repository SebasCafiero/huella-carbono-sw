package ar.edu.utn.frba.dds.mihuella.fachada;

import ar.edu.utn.frba.dds.entities.transportes.*;
import ar.edu.utn.frba.dds.repositories.*;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;

public class FachadaMedios {
    private final Repositorio<MedioDeTransporte> repoMedios;
    private final RepoEcologicos repoEcologicos;
    private final RepoPublicos repoPublicos;
    private final RepoContratados repoContratados;
    private final RepoParticulares repoParticulares;
    private final RepoTiposServicio repoTiposServicio;

    public FachadaMedios() {
        this.repoMedios = FactoryRepositorio.get(MedioDeTransporte.class);
        this.repoEcologicos = (RepoEcologicos) FactoryRepositorio.get(TransporteEcologico.class);
        this.repoPublicos = (RepoPublicos) FactoryRepositorio.get(TransportePublico.class);
        this.repoContratados = (RepoContratados) FactoryRepositorio.get(ServicioContratado.class);
        this.repoTiposServicio = (RepoTiposServicio) FactoryRepositorio.get(TipoServicio.class);
        this.repoParticulares = (RepoParticulares) FactoryRepositorio.get(VehiculoParticular.class);
    }

    public MedioDeTransporte obtenerMedio(String tipo, String discriminante1, String discriminante2) {
        if(tipo.equals("contratado")) {
            return obtenerContratado(discriminante1);
        } else if(tipo.equals("publico")) {
            return obtenerPublico(discriminante1, discriminante2);
        } else if(tipo.equals("ecologico")) {
            return obtenerEcologico(discriminante1);
        } else if(tipo.equals("particular")) {
            return obtenerParticular(discriminante1, discriminante2);
        }

        throw new NoExisteMedioException(tipo);
    }

    private VehiculoParticular obtenerParticular(String discriminante1, String discriminante2) {
        if(TipoVehiculo.hasValue(discriminante1) && TipoCombustible.hasValue(discriminante2)) {
            return this.repoParticulares.findByEquality(TipoVehiculo.valueOf(discriminante1), TipoCombustible.valueOf(discriminante2));
        }
        return null;
    }

    private TransporteEcologico obtenerEcologico(String discriminante1) {
        if(TipoTransporteEcologico.hasValue(discriminante1)) {
            return this.repoEcologicos.findByEquality(TipoTransporteEcologico.valueOf(discriminante1));
        }
        return null;
    }

    private TransportePublico obtenerPublico(String discriminante1, String discriminante2) {
        if(TipoTransportePublico.hasValue(discriminante1)) {
            return this.repoPublicos.findByEquality(TipoTransportePublico.valueOf(discriminante1), discriminante2);
        }
        return null;
    }

    private ServicioContratado obtenerContratado(String discriminante1) {
        TipoServicio tipoServicio = this.repoTiposServicio.findByEquality(discriminante1);

        if(tipoServicio != null) {
            return this.repoContratados.findByEquality(tipoServicio);
        }
        return null;
    }

}
