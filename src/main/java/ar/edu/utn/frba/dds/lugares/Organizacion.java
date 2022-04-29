package ar.edu.utn.frba.dds.lugares;

import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import ar.edu.utn.frba.dds.personas.Miembro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Organizacion {
    private String razonSocial;
    private TipoDeOrganizacionEnum tipo;
    private String ubicacion;
    private ClasificacionDeOrganizacion clasificacionDeOrganizacion;
    private List<Sector> sectores;
    private List<Medible> mediciones;

    public Organizacion(String razonSocial, TipoDeOrganizacionEnum tipo, String ubicacion) {
        this.razonSocial = razonSocial;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.sectores = new ArrayList<Sector>();
        this.mediciones = new ArrayList<Medible>();
    }

    // Para qué sirve ??
    public List<Miembro> obtenerMiembrosDeLaOrganizacion(){
        //Hay que traer cada miembro de cada sector y que no hayan repetidos.
        return sectores
                .stream()
                .flatMap(sector -> sector.getListaDeMiembros().stream())
                .collect(Collectors.toList());
    }

    public void agregarSector(Sector sector) throws Exception{
        if(this.sectores.contains(sector)) {
            throw new Exception();
        }
        sectores.add(sector);
    }

    public List<Sector> getListaDeSectores() {
        List<Sector> shallowCopy = new ArrayList<Sector>();
        shallowCopy.addAll(this.sectores);
        return shallowCopy;
    }

    public void aceptarSolicitud(Miembro miembro, Sector sector) throws Exception {
        if(!this.sectores.contains(sector))
            throw new Exception("El sector no pertenece a la organizacion");
        for(Sector unSector : this.sectores) {
            if (unSector.esMiembro(miembro)) {
                throw new Exception("El miembro ya pertenece a la organizacion");
            }
        }
        sector.agregarMiembro(miembro);
        miembro.agregarSector(sector);
    }

}
