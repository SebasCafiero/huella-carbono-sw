package ar.edu.utn.frba.dds.interfaces.input.json;

public class UbicacionJSONDTO {
    private DireccionJSONDTO direccion;
    private CoordenadaJSONDTO coordenadas;

    public UbicacionJSONDTO() {
    }

    public UbicacionJSONDTO(DireccionJSONDTO direccion, CoordenadaJSONDTO coordenadas) {
        this.direccion = direccion;
        this.coordenadas = coordenadas;
    }

    public DireccionJSONDTO getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionJSONDTO direccion) {
        this.direccion = direccion;
    }

    public CoordenadaJSONDTO getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(CoordenadaJSONDTO coordenadas) {
        this.coordenadas = coordenadas;
    }
}
