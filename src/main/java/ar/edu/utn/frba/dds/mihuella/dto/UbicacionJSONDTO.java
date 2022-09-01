package ar.edu.utn.frba.dds.mihuella.dto;

public class UbicacionJSONDTO {
    public DireccionJSONDTO direccion;
    public CoordenadaJSONDTO coordenadas;

    //Pueden no ser inner class
    public class DireccionJSONDTO {
        public String pais;
        public String provincia;
        public String municipio;
        public String localidad;
        public String calle;
        public int numero;
    }

    public class CoordenadaJSONDTO {
        public float latitud;
        public float longitud;
    }
}
