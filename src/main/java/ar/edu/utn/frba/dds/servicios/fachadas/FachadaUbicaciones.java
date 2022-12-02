package ar.edu.utn.frba.dds.servicios.fachadas;

import ar.edu.utn.frba.dds.entities.lugares.AreaSectorial;
import ar.edu.utn.frba.dds.entities.lugares.Municipio;
import ar.edu.utn.frba.dds.entities.lugares.Provincia;
import ar.edu.utn.frba.dds.repositories.Repositorio;
import ar.edu.utn.frba.dds.repositories.utils.FactoryRepositorio;

import java.util.Optional;

public class FachadaUbicaciones {
    private final Repositorio<AreaSectorial> repoAreas;
    private final Repositorio<Municipio> repoMunicipios;
    private final Repositorio<Provincia> repoProvincias;

    public FachadaUbicaciones() {
        this.repoAreas = FactoryRepositorio.get(AreaSectorial.class);
        this.repoMunicipios = FactoryRepositorio.get(Municipio.class);
        this.repoProvincias = FactoryRepositorio.get(Provincia.class);
    }

    public Optional<Municipio> getMunicipio(String municipio, String provincia) {
        return this.repoMunicipios.buscarTodos().stream()
                .filter(muni -> muni.getNombre().equals(municipio) &&
                        provincia.equals(muni.getProvincia().getNombre())).findFirst();
    }
}
