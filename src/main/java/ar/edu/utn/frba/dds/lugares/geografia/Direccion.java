package ar.edu.utn.frba.dds.lugares.geografia;

public class Direccion {

    private Integer numero;
    private String calle;
    private String localidad;
    private Municipio municipio;


    public Direccion(Municipio municipio, String localidad, String calle, Integer numero) {
        this.municipio = municipio;
        this.localidad = localidad;
        this.calle = calle;
        this.numero = numero;
    }

    public Direccion(String pais, String provincia, String municipio, String localidad, String calle, Integer numero) {
        this.municipio = new Municipio(municipio, provincia, pais);
        this.localidad = localidad;
        this.calle = calle;
        this.numero = numero;
    }

    public Direccion(String barrio, String calle, Integer numero) { //TODO direccion por defecto
        this.municipio = new Municipio("Ciudad de Buenos Aires", "Ciudad de Buenos Aires", "Argentina");
        this.localidad = barrio;
        this.calle = calle;
        this.numero = numero;
    }

    public Direccion() { //TODO direccion por defecto
        this.municipio = new Municipio("Ciudad de Buenos Aires", "Ciudad de Buenos Aires", "Argentina");
        this.localidad = "La Boca";
        this.calle = "Brandsen";
        this.numero = 805;
    }

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
}
