package ar.edu.utn.frba.dds.interfaces.gui.mappers;

import ar.edu.utn.frba.dds.interfaces.gui.dto.ReporteHBS;
import ar.edu.utn.frba.dds.entities.medibles.ReporteOrganizacion;

import java.util.stream.Collectors;

public class ReporteMapperHBS {

    public static ReporteHBS toDTO(ReporteOrganizacion reporte) {
        ReporteHBS reporteDTO = new ReporteHBS();
        reporteDTO.setFechaCreacion(reporte.getFechaCreacion().toString()); //todo format
        reporteDTO.setFechaReferencia(reporte.getPeriodoReferencia().getMes() + "-" + reporte.getPeriodoReferencia().getAnio());
        reporteDTO.setConsumoTotal(reporte.getConsumoTotal());
        reporteDTO.setConsumoMediciones(reporte.getConsumoMediciones());
        reporteDTO.setConsumoTrayectos(reporte.getConsumoTotal()- reporteDTO.getConsumoMediciones());

        reporteDTO.setConsumoPorCategoria(reporte.getConsumoPorCategoria().entrySet().stream().map(ConsumoMapperHBS::toDTOCategoria).collect(Collectors.toList()));
        reporteDTO.setConsumoPorSector(reporte.getConsumoPorSector().entrySet().stream().map(ConsumoMapperHBS::toDTOSector).collect(Collectors.toList()));
        reporteDTO.setConsumoPorMiembro(reporte.getConsumoPorMiembro().entrySet().stream().map(ConsumoMapperHBS::toDTOMiembro).collect(Collectors.toList()));

        reporteDTO.setOrganizacion(OrganizacionMapperHBS.toDTO(reporte.getOrganizacion()));
        return reporteDTO;
    }
}
