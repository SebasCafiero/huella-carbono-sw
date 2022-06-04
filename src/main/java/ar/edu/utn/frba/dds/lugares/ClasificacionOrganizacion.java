package ar.edu.utn.frba.dds.lugares;

public class ClasificacionOrganizacion {
    private String nombre;

    public ClasificacionOrganizacion(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}


/* Ejemplos de uso:
* ministerio = new ClasificacionOrganizacion("Ministerio")
* universidad = new ClasificacionOrganizacion("Universidad")
* escuela = new ClasificacionOrganizacion("Escuela")
* empresaSectorPrimario = new ClasificacionOrganizacion("Empresa del Sector Primario")
* empresaSectorSecundario = new ClasificacionOrganizacion("Empresa del Sector Secundario")
* */
