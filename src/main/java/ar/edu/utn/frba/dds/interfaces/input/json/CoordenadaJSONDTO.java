package ar.edu.utn.frba.dds.interfaces.input.json;

public class CoordenadaJSONDTO {
    private float latitud;
    private float longitud;

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }
}