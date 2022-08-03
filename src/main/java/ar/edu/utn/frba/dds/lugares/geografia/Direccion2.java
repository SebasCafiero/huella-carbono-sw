package ar.edu.utn.frba.dds.lugares.geografia;

public class Direccion2 {

    private Integer numero;
    private String calle;
    private String localidad;
    private Municipio municipio;


    public Direccion2(Municipio municipio, String localidad, String calle, Integer numero) {
        this.municipio = municipio;
        this.localidad = localidad;
        this.calle = calle;
        this.numero = numero;
    }

    public Direccion2(String pais, String provincia, String municipio, String localidad, String calle, Integer numero) {
        this.municipio = new Municipio(municipio, provincia, pais);
        this.localidad = localidad;
        this.calle = calle;
        this.numero = numero;
    }

    public Direccion2(String localidad, String calle, Integer numero) {
        this.municipio = new Municipio("Avellaneda", "Buenos Aires", "Argentina");
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

    public Municipio getMunicipio() {
        return municipio;
    }
}
