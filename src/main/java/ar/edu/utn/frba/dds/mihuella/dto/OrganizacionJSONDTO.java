package ar.edu.utn.frba.dds.mihuella.dto;

import java.util.List;

public class OrganizacionJSONDTO {
    public String organizacion;
    public UbicacionJSONDTO ubicacion;
    public String clasificacion;
    public String tipo;
    public List<SectorJSONDTO> sectores;

    public class SectorJSONDTO {
        public String nombre;
        public List<MiembroJSONDTO> miembros;
    }
}
