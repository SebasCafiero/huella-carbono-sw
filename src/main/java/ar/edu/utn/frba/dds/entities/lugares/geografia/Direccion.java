package ar.edu.utn.frba.dds.entities.lugares.geografia;

import javax.persistence.*;

@Embeddable
public class Direccion {

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "calle")
    private String calle;

    @Column(name = "localidad")
    private String localidad;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "municipio_id", nullable = false)
    private Municipio municipio;

    public Direccion(Municipio municipio, String localidad, String calle, Integer numero) {
        this.municipio = municipio;
        this.localidad = localidad;
        this.calle = calle;
        this.numero = numero;
    }

    public Direccion(String pais, String provincia, String municipio, String localidad, String calle, Integer numero) {
        this.municipio = new Municipio(municipio, new Provincia(provincia, pais));
        this.localidad = localidad;
        this.calle = calle;
        this.numero = numero;
    }

    /*public Direccion(String barrio, String calle, Integer numero) {
        this.municipio = new Municipio("Ciudad de Buenos Aires", new Provincia("Ciudad de Buenos Aires", "Argentina"));
        this.localidad = barrio;
        this.calle = calle;
        this.numero = numero;
    }*/

    /*public Direccion() {
        this.municipio = new Municipio("Ciudad de Buenos Aires", new Provincia("Ciudad de Buenos Aires", "Argentina"));
        this.localidad = "La Boca";
        this.calle = "Brandsen";
        this.numero = 805;
    }*/

    public Integer getNumero() {
        return numero;
    }

    public String getCalle() {
        return calle;
    }

    public String getLocalidad() {
        return localidad;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    @Override
    public String toString() {
        return "Direccion{" +
                "numero=" + numero +
                ", calle='" + calle + '\'' +
                ", localidad='" + localidad + '\'' +
                ", municipio=" + municipio +
                '}';
    }
}
