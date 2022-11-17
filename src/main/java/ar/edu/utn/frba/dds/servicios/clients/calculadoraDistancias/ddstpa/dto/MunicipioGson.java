package ar.edu.utn.frba.dds.servicios.clients.calculadoraDistancias.ddstpa.dto;

public class MunicipioGson {
    private int id;
    private String nombre;
    private ProvinciaInterna provincia;

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

    public ProvinciaInterna getProvincia() {
        return provincia;
    }

    public void setProvincia(ProvinciaInterna provincia) {
        this.provincia = provincia;
    }

    public class ProvinciaInterna {
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
