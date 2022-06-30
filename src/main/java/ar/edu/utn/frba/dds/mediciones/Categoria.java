package ar.edu.utn.frba.dds.mediciones;

public class Categoria {
    private Integer id;
    private String actividad;
    private String tipoConsumo;

    public Categoria(String actividad, String tipoConsumo) {
        this.actividad = actividad;
        this.tipoConsumo = tipoConsumo;
    }

    @Override
    public String toString() {
        return actividad + " - " + tipoConsumo;
    }
}
