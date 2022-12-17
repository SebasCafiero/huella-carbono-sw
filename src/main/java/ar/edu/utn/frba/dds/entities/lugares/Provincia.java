package ar.edu.utn.frba.dds.entities.lugares;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "PROVINCIA")
public class Provincia extends AreaSectorial {

    @Column(name = "pais")
    private String nombrePais;

    @OneToMany(mappedBy = "provincia", cascade = CascadeType.ALL)
    private Set<Municipio> municipios;

    public Provincia() {
        this.municipios = new HashSet<>();
    }

    public Provincia(String nombre, String pais) {
        this.nombre = nombre;
        this.nombrePais = pais;
        this.municipios = new HashSet<>();
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void agregarMunicipio(Municipio unMunicipio) {
        this.municipios.add(unMunicipio);
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public Set<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(Set<Municipio> municipios) {
        this.municipios = municipios;
    }

    public Set<Organizacion> getOrganizaciones() {
        return getMunicipios().stream().flatMap(m -> m.getOrganizaciones().stream()).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "Provincia{" +
                "nombre='" + nombre + '\'' +
                ", nombrePais='" + nombrePais + '\'' +
                "}";
    }
}
