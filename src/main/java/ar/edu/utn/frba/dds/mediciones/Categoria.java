package ar.edu.utn.frba.dds.mediciones;

public class Categoria {
    private Integer id;
    private String actividad;
    private String tipoConsumo;

    public Categoria(String actividad, String tipoConsumo) {
        this.actividad = actividad;
        this.tipoConsumo = tipoConsumo;
    }

    public Categoria(int id, String actividad, String tipoConsumo) {
        this.actividad = actividad;
        this.tipoConsumo = tipoConsumo;
        this.id = id;
    }

    @Override
    public String toString() { return actividad + " - " + tipoConsumo;}

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public String getActividad() {return actividad;}

    public void setActividad(String actividad) {this.actividad = actividad;}

    public String getTipoConsumo() {return tipoConsumo;}
}
