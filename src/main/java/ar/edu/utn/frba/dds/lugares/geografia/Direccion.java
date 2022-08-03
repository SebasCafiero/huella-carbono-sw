package ar.edu.utn.frba.dds.lugares.geografia;

public class Direccion {

    private Integer numero;
    private String calle;
    private String localidad;
    private String municipio;
    private String provincia;
    private String pais;


    public Direccion(String pais, String provincia, String municipio, String localidad, String calle, Integer numero) {
        this.pais = pais;
        this.provincia = provincia;
        this.municipio = municipio;
        this.localidad = localidad;
        this.calle = calle;
        this.numero = numero;
    }

    public Direccion(String localidad, String calle, Integer numero) { //TODO defecto
        this.pais = "Argentina";
        this.provincia = "Buenos Aires";
        this.municipio = "Avellaneda";
        this.localidad = localidad;
        this.calle = calle;
        this.numero = numero;
    }

    public Direccion() { //TODO defecto
        this.pais = "Argentina";
        this.provincia = "Ciudad de Buenos Aires";
        this.municipio = "Ciudad de Buenos Aires";
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

    public String getMunicipio() {
        return municipio;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getPais() {
        return pais;
    }
}
