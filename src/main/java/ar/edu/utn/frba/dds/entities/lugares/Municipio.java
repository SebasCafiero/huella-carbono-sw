package ar.edu.utn.frba.dds.entities.lugares;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "MUNICIPIO")
public class Municipio extends AreaSectorial {

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    private Provincia provincia;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "area_id")
    protected Set<Organizacion> organizaciones;

    public Municipio() {
        this.organizaciones = new HashSet<>();
    }

    public Municipio(String nombre, Provincia provincia) {
        this.nombre = nombre;
        this.provincia = provincia;
        this.provincia.agregarMunicipio(this);
        this.organizaciones = new HashSet<>();
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    @Override
    public Set<Organizacion> getOrganizaciones() {
        return organizaciones;
    }

    public void setOrganizaciones(Set<Organizacion> organizaciones) {
        this.organizaciones = organizaciones;
    }

    public void agregarOrganizacion(Organizacion unaOrganizacion) {
        this.organizaciones.add(unaOrganizacion);
    }

    @Override
    public String toString() {
        return "Municipio{" +
                "nombre='" + nombre + '\'' +
                ", provincia=" + provincia +
                "}";
    }
}
