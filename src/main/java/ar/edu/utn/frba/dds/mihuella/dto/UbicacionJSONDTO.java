package ar.edu.utn.frba.dds.mihuella.dto;

public class UbicacionJSONDTO {
    private DireccionJSONDTO direccion;
    private CoordenadaJSONDTO coordenadas;

    //Pueden no ser inner class
    public class DireccionJSONDTO {
        private String pais;
        private String provincia;
        private String municipio;
        private String localidad;
        private String calle;
        private int numero;

        public String getPais() {
            return pais;
        }

        public void setPais(String pais) {
            this.pais = pais;
        }

        public String getProvincia() {
            return provincia;
        }

        public void setProvincia(String provincia) {
            this.provincia = provincia;
        }

        public String getMunicipio() {
            return municipio;
        }

        public void setMunicipio(String municipio) {
            this.municipio = municipio;
        }

        public String getLocalidad() {
            return localidad;
        }

        public void setLocalidad(String localidad) {
            this.localidad = localidad;
        }

        public String getCalle() {
            return calle;
        }

        public void setCalle(String calle) {
            this.calle = calle;
        }

        public int getNumero() {
            return numero;
        }

        public void setNumero(int numero) {
            this.numero = numero;
        }
    }

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
