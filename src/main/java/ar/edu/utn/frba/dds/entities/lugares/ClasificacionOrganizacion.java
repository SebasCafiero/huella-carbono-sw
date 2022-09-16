package ar.edu.utn.frba.dds.entities.lugares;

import javax.persistence.*;

@Entity
@Table(name = "CATEGORIA_ORGANIZACION")
public class ClasificacionOrganizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "nombre")
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
