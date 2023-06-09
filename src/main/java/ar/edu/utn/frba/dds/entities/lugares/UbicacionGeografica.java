package ar.edu.utn.frba.dds.entities.lugares;

import javax.persistence.*;

@Entity
@Table(name = "UBICACION")
public class UbicacionGeografica {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ubicacion_id")
    private Integer id;

    @Embedded
    private Coordenada coordenada;

    @Embedded
    private Direccion direccion;

    public UbicacionGeografica() {
    }

    public UbicacionGeografica(Direccion direccion, Coordenada coordenada) {
        this.coordenada = coordenada;
        this.direccion = direccion;
    }

    public UbicacionGeografica(String pais, String provincia, String municipio, String localidad, String calle, Integer numero, Coordenada coordenada) {
        this.coordenada = coordenada;
        this.direccion = new Direccion(pais, provincia, municipio, localidad, calle, numero);
    }

    public UbicacionGeografica(Municipio municipio, String localidad, String calle, Integer numero, Coordenada coordenada) {
        this.coordenada = coordenada;
        this.direccion = new Direccion(municipio, localidad, calle, numero);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCoordenada(Coordenada coordenada) {
        this.coordenada = coordenada;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    @Override
    public String toString() {
        return "UbicacionGeografica{" +
                "coordenada=" + coordenada +
                ", direccion=" + direccion +
                '}';
    }
}
