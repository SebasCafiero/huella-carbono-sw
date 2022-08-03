package ar.edu.utn.frba.dds.trayectos.servicios.ddstpa;

public class LocalidadGson {
    public int id;
    public String nombre;
    //public int codigoPostal;
    public MunicipioInterno municipio;

    public class MunicipioInterno {
        public int id;
        public String nombre;
    }
}
