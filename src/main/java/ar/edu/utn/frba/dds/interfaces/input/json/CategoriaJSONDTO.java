package ar.edu.utn.frba.dds.interfaces.input.json;

public class CategoriaJSONDTO {
    private String actividad;
    private String tipoConsumo;

    public CategoriaJSONDTO() {
    }

    public CategoriaJSONDTO(String actividad, String tipoConsumo) {
        this.actividad = actividad;
        this.tipoConsumo = tipoConsumo;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getTipoConsumo() {
        return tipoConsumo;
    }

    public void setTipoConsumoasdasd(String tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
    }
}
