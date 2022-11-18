package ar.edu.utn.frba.dds.interfaces.gui.mappers;

import ar.edu.utn.frba.dds.entities.lugares.Sector;
import ar.edu.utn.frba.dds.entities.medibles.Categoria;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.interfaces.gui.dto.ConsumoHBS;

import java.util.Map;

public class ConsumoMapperHBS {

    /*public static ConsumoHBS toDTO(Map.Entry<?, Float> consumoPorCriterio) {
        ConsumoHBS consumoDTO = new ConsumoHBS();
        consumoDTO.setConcepto(consumoPorCriterio.getKey().toString()); //todo check
        consumoDTO.setConsumo(consumoPorCriterio.getValue());
        return consumoDTO;
    }*/

    public static ConsumoHBS toDTOCategoria(Map.Entry<Categoria, Float> consumoPorCategoria) {
        ConsumoHBS consumoDTO = new ConsumoHBS();
        Categoria categoria = consumoPorCategoria.getKey();
        consumoDTO.setConcepto(categoria.getActividad() + " - " + categoria.getTipoConsumo());
        consumoDTO.setConsumo(consumoPorCategoria.getValue());
        return consumoDTO;
    }

    public static ConsumoHBS toDTOSector(Map.Entry<Sector, Float> consumoPorSector) {
        ConsumoHBS consumoDTO = new ConsumoHBS();
        Sector sector = consumoPorSector.getKey();
        consumoDTO.setConcepto(sector.getNombre());
        consumoDTO.setConsumo(consumoPorSector.getValue());
        return consumoDTO;
    }

    public static ConsumoHBS toDTOMiembro(Map.Entry<Miembro, Float> consumoPorMiembro) {
        ConsumoHBS consumoDTO = new ConsumoHBS();
        Miembro miembro = consumoPorMiembro.getKey();
        String nombreMiembro = miembro.getNombre() + " " + miembro.getApellido();
        String documentoMiembro = miembro.getTipoDeDocumento() + ": " + miembro.getNroDocumento();
        consumoDTO.setConcepto(nombreMiembro + " - " + documentoMiembro);
        consumoDTO.setConsumo(consumoPorMiembro.getValue());
        return consumoDTO;
    }
}
