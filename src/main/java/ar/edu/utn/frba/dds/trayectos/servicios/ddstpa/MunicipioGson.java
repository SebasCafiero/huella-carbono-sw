package ar.edu.utn.frba.dds.trayectos.servicios.ddstpa;

public class MunicipioGson {
    public int id;
    public String nombre;
    public ProvinciaInterna provincia;

    public class ProvinciaInterna {
        public int id;
        public String nombre;
    }
}
