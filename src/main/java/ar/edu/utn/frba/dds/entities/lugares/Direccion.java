package ar.edu.utn.frba.dds.entities.lugares;

import javax.persistence.*;

@Embeddable
public class Direccion {

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "calle")
    private String calle;

    @Column(name = "localidad")
    private String localidad;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "municipio_id", nullable = false)
    private Municipio municipio;

    public Direccion() {
    }

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

    public Boolean esIgualAOtraDireccion(Direccion otraDireccion) {
        return this.municipio.getProvincia().getNombrePais().equals(otraDireccion.getMunicipio().getProvincia().getNombrePais())
                && this.municipio.getProvincia().getNombre().equals(otraDireccion.getMunicipio().getProvincia().getNombre())
                && this.municipio.getNombre().equals(otraDireccion.getMunicipio().getNombre())
                && this.localidad.equals(otraDireccion.getLocalidad())
                && this.calle.equals(otraDireccion.getCalle())
                && this.numero.equals(otraDireccion.getNumero());
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
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
