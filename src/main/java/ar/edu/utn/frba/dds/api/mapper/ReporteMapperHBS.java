package ar.edu.utn.frba.dds.api.mapper;

import ar.edu.utn.frba.dds.api.dto.ConsumoHBS;
import ar.edu.utn.frba.dds.api.dto.ReporteHBS;
import ar.edu.utn.frba.dds.entities.mediciones.ReporteOrganizacion;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteMapperHBS {

    public static ReporteHBS toDTO(ReporteOrganizacion reporte) {
        ReporteHBS reporteDTO = new ReporteHBS();
        reporteDTO.setFechaCreacion(reporte.getFechaCreacion().toString()); //todo format
        reporteDTO.setFechaReferencia(reporte.getPeriodoReferencia().getMes() + "-" + reporte.getPeriodoReferencia().getAnio());
        reporteDTO.setConsumoTotal(reporte.getConsumoTotal());
        reporteDTO.setConsumoMediciones(reporte.getConsumoMediciones());
        reporteDTO.setConsumoTrayectos(reporte.getConsumoTotal()- reporteDTO.getConsumoMediciones());

        reporteDTO.setConsumoPorCategoria(reporte.getConsumoPorCategoria().entrySet().stream().map(ConsumoMapperHBS::toDTO).collect(Collectors.toList()));
        reporteDTO.setConsumoPorSector(reporte.getConsumoPorSector().entrySet().stream().map(ConsumoMapperHBS::toDTO).collect(Collectors.toList()));
        reporteDTO.setConsumoPorMiembro(reporte.getConsumoPorMiembro().entrySet().stream().map(ConsumoMapperHBS::toDTO).collect(Collectors.toList()));
        return reporteDTO;
    }
}
