package ar.edu.utn.frba.dds.lugares.geografia;

public class Direccion2 {

    private Integer numero;
    private String calle;
    private String localidad;
    private String municipio;
    private String provincia;
    private String pais;


    public Direccion2(String pais, String provincia, String municipio, String localidad, String calle, Integer numero) {
        this.pais = pais;
        this.provincia = provincia;
        this.municipio = municipio;
        this.localidad = localidad;
        this.calle = calle;
        this.numero = numero;
    }

    public Direccion2(String localidad, String calle, Integer numero) {
        this.pais = "Argentina";
        this.provincia = "Buenos Aires";
        this.municipio = "Avellaneda";
        this.localidad = localidad;
        this.calle = calle;
        this.numero = numero;
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
