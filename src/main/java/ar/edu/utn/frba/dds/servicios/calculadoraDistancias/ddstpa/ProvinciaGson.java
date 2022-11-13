package ar.edu.utn.frba.dds.servicios.calculadoraDistancias.ddstpa;

public class ProvinciaGson {
    private int id;
    private String nombre;
    private PaisGson pais;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public PaisGson getPais() {
        return pais;
    }

    public void setPais(PaisGson pais) {
        this.pais = pais;
    }
}
