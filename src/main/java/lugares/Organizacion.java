package lugares;

import personas.Miembro;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Organizacion {
    private String razonSocial;
    private TipoDeOrganizacion tipoDeOrganizacion;
    private UbicacionGeografica ubicacionGeografica;
    private ClasificacionDeOrganizacion clasificacionDeOrganizacion;
    private List<Sector> listaDeSectores;

    public Organizacion() {
        this.listaDeSectores = new ArrayList<>();
    }

    public List<Miembro> obtenerMiembrosDeLaOrganizacion(){
        //Hay que traer cada miembro de cada sector y que no hayan repetidos.
        return listaDeSectores
                .stream()
                .flatMap(sector -> sector.getListaDeMiembros().stream())
                .collect(Collectors.toList());
    }

    public void agregarSector(Sector unSector) {
            listaDeSectores.add(unSector);
    }

    public List<Sector> getListaDeSectores() {
        return listaDeSectores;
    }

    public Integer calcularHC() {
        return 5;
    }
}
