package ar.edu.utn.frba.dds.servicios.clients.calculadoraDistancias.ddstpa.dto;

public class LocalidadGson {
    private int id;
    private String nombre;
    //public int codigoPostal;
    private MunicipioInterno municipio;

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

    public MunicipioInterno getMunicipio() {
        return municipio;
    }

    public void setMunicipio(MunicipioInterno municipio) {
        this.municipio = municipio;
    }

    public class MunicipioInterno {
        private int id;
        private String nombre;

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
    }
}
