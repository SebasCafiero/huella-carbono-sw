package ar.edu.utn.frba.dds.interfaces.gui.mappers;

import ar.edu.utn.frba.dds.interfaces.gui.dto.OrganizacionHBS;
import ar.edu.utn.frba.dds.entities.lugares.Organizacion;

public class OrganizacionMapperHBS {
    public static OrganizacionHBS toDTO(Organizacion organizacion) {
        OrganizacionHBS organizacionDTO = new OrganizacionHBS();
        organizacionDTO.setId(organizacion.getId());
        organizacionDTO.setRazonSocial(organizacion.getRazonSocial());
        return organizacionDTO;
    }
}
