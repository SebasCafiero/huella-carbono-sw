package ar.edu.utn.frba.dds.interfaces.gui.dto;

import java.util.List;

public class AgenteHBS {
    private Integer id;
    private List<OrganizacionHBS> organizaciones;
//    private String nombre;
//    private String apellido;
//    private UbicacionHBS area;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<OrganizacionHBS> getOrganizaciones() {
        return organizaciones;
    }

    public void setOrganizaciones(List<OrganizacionHBS> organizaciones) {
        this.organizaciones = organizaciones;
    }

}
